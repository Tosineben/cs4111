package courseworks.model;

import courseworks.sql.*;

import java.util.List;

public class Professor {

    public String uni;
    public String name;

    public List<Course> getCourses() {
        ICourseworksReader rdr = new CourseworksReader();
        return rdr.getCoursesForProfessor(uni);
    }

    public int addCourse(Course course) {
        ICourseworksWriter wtr = new CourseworksWriter();
        course.professor = this;
        return wtr.createCourse(course);
    }

    public boolean updateCourse(Course course) {
        ICourseworksWriter wtr = new CourseworksWriter();
        course.professor = this;
        return wtr.updateCourse(course);
    }

    public boolean removeCourse(int course_id) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.deleteCourse(course_id, uni);
    }
}

