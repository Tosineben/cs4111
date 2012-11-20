package courseworks.model;

import courseworks.sql.*;

import java.util.List;
import java.util.Map;

public class Course {

    public int course_id;
    public String course_number;
    public String name;
    public String location;
    public String description;
    public Professor professor;
    public List<Calendar> calendars;

}

