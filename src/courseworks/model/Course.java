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

    public List<Student> getStudents() {
        ICourseworksReader rdr = new CourseworksReader();
        return rdr.getStudentsForCourse(course_id);
    }

    public List<Announcement> getAnnouncements() {
        ICourseworksReader rdr = new CourseworksReader();
        return rdr.getAnnouncementsForCourse(course_id);
    }

    public Map<String, Calendar> getCalendarsByName() {
        ICourseworksReader rdr = new CourseworksReader();
        return rdr.getCalendarsForCourse(course_id);
    }

    public int addCalendar(Calendar cal) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.createCalendar(course_id, cal);
    }

    public int addAnnouncement(Announcement anncmnt) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.createAnnouncement(course_id, anncmnt);
    }

}

