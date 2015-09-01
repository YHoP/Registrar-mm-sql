import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("students", Student.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add_student", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String date = request.queryParams("date");
      Student newStudent = new Student(name, date);
      newStudent.save();
      response.redirect("/");
      return null;
    });



    // get("/courses/:id", (request,response) ->{
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int id = Integer.parseInt(request.params("id"));
    //   Course course = Course.find(id);
    //   model.put("course", course);
    //   model.put("allStudents", Student.all());
    //   model.put("completedCourseStudents", course.completedCourseStudents());
    //   model.put("incompletedCourseStudents", course.incompletedCourseStudents());
    //   model.put("template", "templates/course.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/delete_courses/:course_id", (request, response) -> {
    //   int course_id = Integer.parseInt(request.params("course_id"));
    //   Course myCourse = Course.find(course_id);
    //   myCourse.delete();
    //   response.redirect("/courses");
    //   return null;
    // });
    //

    //
    // post("/update_course/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int course_id = Integer.parseInt(request.params("id")); ///?course_id
    //   Course myCourse = Course.find(course_id);
    //   String name = request.queryParams("name");
    //   myCourse.update(name);
    //   response.redirect("/courses/" + course_id);
    //   return null;
    // });
    //
    // post("/add_students", (request, response) -> {
    //   int studentId = Integer.parseInt(request.queryParams("student_id"));
    //   int courseId = Integer.parseInt(request.queryParams("course_id"));
    //   Course course = Course.find(courseId);
    //   Student student = Student.find(studentId);
    //   course.addStudent(student);
    //   response.redirect("/courses/" + courseId);
    //   return null;
    // });
    //
    // get("/:course_id/isCompleted_students/:student_id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int course_id = Integer.parseInt(request.params("course_id"));
    //   int student_id = Integer.parseInt(request.params("student_id"));
    //   Student myStudent = Student.find(student_id);
    //   myStudent.isCompleted();
    //   response.redirect("/courses/" + course_id);
    //   return null;
    // });
    //
    // get("/:course_id/isNotCompleted_students/:student_id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int course_id = Integer.parseInt(request.params("course_id"));
    //   int student_id = Integer.parseInt(request.params("student_id"));
    //   Student myStudent = Student.find(student_id);
    //   myStudent.isNotCompleted();
    //   response.redirect("/courses/" + course_id);
    //   return null;
    // });
    //
    // get("/:course_id/delete_students/:student_id", (request, response) -> {
    //   int course_id = Integer.parseInt(request.params("course_id"));
    //   int student_id = Integer.parseInt(request.params("student_id"));
    //   Student myStudent = Student.find(student_id);
    //   myStudent.delete();
    //   response.redirect("/courses/" + course_id);
    //   return null;
    // });
    //
    // get("/students", (request,response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   List<Student> students = Student.all();
    //   model.put("students", students);
    //   model.put("completedStudents", Student.completedStudents());
    //   model.put("incompletedStudents", Student.incompletedStudents());
    //   model.put("template", "templates/students.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/students/:id", (request,response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int id = Integer.parseInt(request.params("id"));
    //   Student student = Student.find(id);
    //   model.put("student", student);
    //   model.put("allCourses", Course.all());
    //   model.put("template", "templates/student.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/students", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   String name = request.queryParams("name");
    //   Student newStudent = new Student(name);
    //   newStudent.save();
    //   response.redirect("/students");
    //   return null;
    // });
    //
    //
    //
    // get("/delete_students/:student_id", (request, response) -> {
    //   int student_id = Integer.parseInt(request.params("student_id"));
    //   Student myStudent = Student.find(student_id);
    //   myStudent.delete();
    //   response.redirect("/students");
    //   return null;
    // });
    //
    // post("/add_courses", (request, response) -> {
    //   int studentId = Integer.parseInt(request.queryParams("student_id"));
    //   int courseId = Integer.parseInt(request.queryParams("course_id"));
    //   Course course = Course.find(courseId);
    //   Student student = Student.find(studentId);
    //   student.addCourse(course);
    //   response.redirect("/students/" + studentId);
    //   return null;
    // });
    //
    // get("/isCompleted_students/:student_id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int student_id = Integer.parseInt(request.params("student_id"));
    //   Student myStudent = Student.find(student_id);
    //   myStudent.isCompleted();
    //   response.redirect("/students");
    //   return null;
    // });
    //
    // get("/isNotCompleted_students/:student_id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int student_id = Integer.parseInt(request.params("student_id"));
    //   Student myStudent = Student.find(student_id);
    //   myStudent.isNotCompleted();
    //   response.redirect("/students");
    //   return null;
    // });
    //
    // get("/update_students/:id", (request,response) ->{
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int id = Integer.parseInt(request.params("id"));
    //   Student student = Student.find(id);
    //   model.put("student", student);
    //   model.put("template", "templates/student-update.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/update_students/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int student_id = Integer.parseInt(request.params("id"));
    //   Student myStudent = Student.find(student_id);
    //   String name = request.queryParams("name");
    //   myStudent.update(name);
    //   response.redirect("/students/" + student_id);
    //   return null;
    // });
    //
    // post("/date_students/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int student_id = Integer.parseInt(request.params("id"));
    //   Student myStudent = Student.find(student_id);
    //   String date = request.queryParams("date");
    //   myStudent.date(date);
    //   response.redirect("/students/" + student_id);
    //   return null;
    // });

    /*
    put("/students/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Student student = Student.find(Integer.parseInt(request.params("id")));
      String name = request.queryParams("name");
      student.update("name");
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    delete("/students/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Student student = Student.find(Integer.parseInt(request.params("id")));
      student.delete();
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    */

  }
}
