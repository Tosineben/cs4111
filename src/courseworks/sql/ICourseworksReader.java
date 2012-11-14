package courseworks.sql;

import courseworks.model.Course;
import courseworks.model.Professor;
import courseworks.model.Student;

import java.util.List;

public interface ICourseworksReader {

    List<Professor> getProfessors();

    List<Student> getStudents();

    List<Course> getCourses();

    List<Course> getCoursesForProfessor(String uni);

    List<Course> getCoursesForStudent(String uni);

}
