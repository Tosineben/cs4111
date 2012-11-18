package courseworks.model;

import courseworks.sql.*;

import java.util.List;

public class Calendar {

    public int calendar_id;
    public String name;

    public List<Event> getEvents() {
        ICourseworksReader rdr = new CourseworksReader();
        return rdr.getEventsForCalendar(calendar_id);
    }

    public int addEvent(Event event) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.createEvent(calendar_id, event);
    }
}

