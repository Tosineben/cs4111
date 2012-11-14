package courseworks.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {

    public int course_id;
    public String course_number;
    public String name;
    public String location;
    public String description;
    public Professor professor;

    public List<Student> getStudents() {
        return new ArrayList<Student>();
    }

    public List<Announcement> getAnnouncements() {
        return new ArrayList<Announcement>();
    }

    public Map<String, Calendar> getCalendarsByName() {
        return new HashMap<String, Calendar>();
    }

}

