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

}

