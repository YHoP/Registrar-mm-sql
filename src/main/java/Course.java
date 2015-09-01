import java.util.List;
import java.util.ArrayList;
import java.util.*;
import org.sql2o.*;

public class Course {
  private int id;
  private String name, number;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getNumber() {
    return number;
  }

  public Course(String name, String number) {
    this.name = name;
    this.number = number;
  }

  public static List<Course> all() {
    String sql = "SELECT * FROM courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  @Override
  public boolean equals(Object otherCourse){
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getName().equals(newCourse.getName()) &&
              this.getNumber().equals(newCourse.getNumber());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses (name, number) VALUES (:name, :number)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("number", this.number)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id";
      Course Course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return Course;
    }
  }

  public void update(String name, String number) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET name = :name, number = :number WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("number", number)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses_students (course_id, student_id) VALUES (:course_id, :student_id)";
      con.createQuery(sql)
        .addParameter("course_id", this.getId())
        .addParameter("student_id", student.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Student> getStudents() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT student_id FROM courses_students WHERE course_id = :course_id";
      List<Integer> studentIds = con.createQuery(sql)
        .addParameter("course_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Student> students = new ArrayList<Student>();

      for (Integer studentId : studentIds) {
          String studentQuery = "Select * From students WHERE id = :studentId";
          Student student = con.createQuery(studentQuery)
            .addParameter("studentId", studentId)
            .executeAndFetchFirst(Student.class);
          students.add(student);
      }
      return students;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM courses WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM courses_students WHERE course_id = :courseId";
        con.createQuery(joinDeleteQuery)
          .addParameter("courseId", this.getId())
          .executeUpdate();
    }
  }

  // public static List<Student> completedCourseStudents() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String joinQuery = "SELECT students.* FROM courses JOIN courses_students ON (courses.id=courses_students.course_id) JOIN students ON (courses_students.student_id = students.id) WHERE students.iscompleted = true ORDER BY duedate";
  //     return con.createQuery(joinQuery).executeAndFetch(Student.class);
  //   }
  // }
  //
  // public static List<Student> incompletedCourseStudents() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String joinQuery = "SELECT students.* FROM courses JOIN courses_students ON (courses.id=courses_students.course_id) JOIN students ON (courses_students.student_id = students.id) WHERE students.iscompleted = false ORDER BY duedate";
  //     return con.createQuery(joinQuery).executeAndFetch(Student.class);
  //   }
  // }

}
