package courseworks.model;

import courseworks.sql.*;

import java.util.List;

public class Student {

    public String uni;
    public String name;

    public List<Course> getCourses() {
        ICourseworksReader repo = new CourseworksReader();
        return repo.getCoursesForStudent(uni);
    }

    public boolean enrollInCourse(int course_id) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.enrollStudentInCourse(uni, course_id);
    }
}

