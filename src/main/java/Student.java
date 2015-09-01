import java.util.List;
import java.util.ArrayList;
import java.util.*;
import org.sql2o.*;

public class Student {
  private int id;
  private String name, date;
  private boolean isCompleted;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDate(){
    return date;
  }

  public Student(String name, String date) {
    this.name = name;
    this.date = date;
  }

  @Override
  public boolean equals(Object otherStudent){
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
             this.getId() == newStudent.getId() &&
             this.getDate() == newStudent.getDate();
    }
  }


  public static List<Student> all() {
    String sql = "SELECT * FROM students ORDER BY name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students (name, date) VALUES (:name, :date)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("date", date)
        .executeUpdate()
        .getKey();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students where id=:id";
      Student student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
      return student;
    }
  }

  public void update(String name, String date) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET name = :name, date = :date WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("date", date)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void addCourse(Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses_students (course_id, student_id, iscompleted) VALUES (:course_id, :student_id, :iscompleted)";
      con.createQuery(sql)
        .addParameter("course_id", course.getId())
        .addParameter("student_id", this.getId())
        .addParameter("iscompleted", false)
        .executeUpdate();
    }
  }

  public ArrayList<Course> getCourses() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT course_id FROM courses_students WHERE student_id = :student_id";
      List<Integer> courseIds = con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Course> courses = new ArrayList<Course>();

      for (Integer courseId : courseIds) {
          String studentQuery = "Select * From courses WHERE id = :courseId";
          Course course = con.createQuery(studentQuery)
            .addParameter("courseId", courseId)
            .executeAndFetchFirst(Course.class);
            courses.add(course);
      }
      return courses;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM students WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM courses_students WHERE student_id = :studentId";
        con.createQuery(joinDeleteQuery)
          .addParameter("studentId", this.getId())
          .executeUpdate();
    }
  }

  // public void setCompleted(int courseId) {
  //   isCompleted = true;
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE course_students SET iscompleted = true WHERE id = :id AND course_id = :course_id";
  //     con.createQuery(sql)
  //       .addParameter("id", this.id)
  //       .addParamter("course_id", courseId);
  //       .executeUpdate();
  //   }
  // }
  //
  // public static Boolean checkIfCompleted(int courseId) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT isCompleted FROM courses_students WHERE (courses_students.course_id = :course_id)
  //     AND (courses_students.student_id = :student_id)";
  //     Boolean isCompleted = con.createQuery(sql)
  //       .addParameter("student_id", this.id)
  //       .addParameter("course_id", courseId)
  //       .executeAndFetch(Course.class);
  //       courseList.add(course);
  //     return courseList;
  //   }
  // }
  //
  //
  // public void isNotCompleted() {
  //   isCompleted = false;
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE students SET iscompleted = false WHERE id = :id";
  //     con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }
  //
  // public static List<Student> incompletedStudents() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM students WHERE iscompleted = false ORDER BY name";
  //     return con.createQuery(sql).executeAndFetch(Student.class);
  //   }
  // }

  public void date(String setDate) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET date = :date WHERE id = :id";
      con.createQuery(sql)
        .addParameter("date", setDate)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

}
