import java.util.List;
import java.util.ArrayList;
import java.util.*;
import org.sql2o.*;

public class Department {
  private int id;
  private String major;

  public int getId() {
    return id;
  }


  public String getMajor() {
    return major;
  }

  public Department(String major) {
    this.major = major;
  }

  @Override
  public boolean equals(Object otherMajor){
    if (!(otherMajor instanceof Department)) {
      return false;
    } else {
      Department newMajor = (Department) otherMajor;
      return this.getMajor().equals(newMajor.getMajor()) &&
             this.getId() == newMajor.getId();
    }
  }


  public static List<Department> all() {
    String sql = "SELECT * FROM departments ORDER BY major";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Department.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO departments (major) VALUES (:major)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("major", major)
        .executeUpdate()
        .getKey();
    }
  }


  public static Department find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students where id=:id";
      Department major = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Department.class);
      return major;
    }
  }

  public void update(String major) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE departments SET major = :major WHERE id = :id";
      con.createQuery(sql)
        .addParameter("major", major)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  // public void addCourse(Course course) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO courses_students (course_id, student_id, iscompleted) VALUES (:course_id, :student_id, :iscompleted)";
  //     con.createQuery(sql)
  //       .addParameter("course_id", course.getId())
  //       .addParameter("student_id", this.getId())
  //       .addParameter("iscompleted", false)
  //       .executeUpdate();
  //   }
  // }

  public ArrayList<Student> getStudents() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT id FROM students WHERE major_id = :major_id";
      List<Integer> studentIds = con.createQuery(sql)
        .addParameter("major_id", this.getId())
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


}
