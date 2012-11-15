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
        return wtr.createCourse(uni, course);
    }

}

