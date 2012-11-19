package courseworks.sql;

public final class WriterQueries {

    public static final String INSERT_PROFESSOR =
            "insert into Professors (uni, name) " +
            "values (:uni, :name)";

    public static final String INSERT_STUDENT =
            "insert into Students (uni, name) " +
            "values (:uni, :name)";

    public static final String INSERT_EVENT =
            "insert into Events (event_id, calendar_id, title, startTime, endTime, description, location) " +
            "values (:event_id, :calendar_id, :title, :startTime, :endTime, :description, :location)";

    public static final String INSERT_CALENDAR =
            "insert into Calendars (calendar_id, course_id, name) " +
            "values (:calendar_id, :course_id, :name)";

    public static final String INSERT_ANNCMNT =
            "insert into Announcements (anncmnt_id, course_id, message, time_posted) " +
            "values (:anncmnt_id, :course_id, :message, :time_posted)";

    public static final String INSERT_DOCUMENT =
            "insert into Documents (document_id, file_path, event_id) " +
            "values (:document_id, :file_path, :event_id)";

    public static final String INSERT_MESSAGE =
            "insert into Messages (message_id, uni, event_id, message, time_posted) " +
            "values (:message_id, :uni, :event_id, :message, :time_posted)";

    public static final String INSERT_COURSE =
            "insert into Courses (course_id, uni, courseNumber, name, location, description) " +
            "values (:course_id, :uni, :courseNumber, :name, :location, :description)";

    public static final String UPDATE_COURSE =
            "update Courses set " +
            "courseNumber = :courseNumber," +
            "name = :name," +
            "location = :location," +
            "description = :description " +
            "where course_id = :course_id";

    public static final String DELETE_COURSE =
            "delete from Courses " +
            "where course_id = :course_id " +
            "and uni = :uni";

    public static final String INSERT_ENROLLMENT =
            "insert into Enrollment (uni, course_id) " +
            "values (:uni, :course_id)";

    public static final String DELETE_ENROLLMENT =
            "delete from Enrollment " +
            "where uni = :uni " +
            "and course_id = :course_id";

    public static final String INSERT_READ_ANNCMNT =
            "insert into ReadAnnouncement (anncmnt_id, uni, time_read) " +
            "values (:anncmnt_id, :uni, :time_read)";

    public static final String DELETE_READ_ANNCMNT =
            "delete from ReadAnnouncement " +
            "where anncmnt_id = :anncmnt_id " +
              "and uni = :uni";


    public static final class IdIncrement {

        public static final String GET_MAX_EVENT_ID =
                "select max(ev.event_id) " +
                "from Events ev";

        public static final String GET_MAX_CALENDAR_ID =
                "select max(ca.calendar_id) " +
                "from Calendars ca";

        public static final String GET_MAX_COURSE_ID =
                "select max(c.course_id) " +
                "from Courses c";

        public static final String GET_MAX_ANNCMNT_ID =
                "select max(a.anncmnt_id) " +
                "from Announcements a";

        public static final String GET_MAX_DOCUMENT_ID =
                "select max(d.document_id) " +
                "from Documents d";

        public static final String GET_MAX_MESSAGE_ID =
                "select max(m.message_id) " +
                "from Messages m";
    }

    public static final class Validation {

        public static final String VALIDATE_EVENT =
        "select count(c.uni) " +
        "from Calendars ca " +
        "inner join Courses c ON c.course_id = ca.course_id " +
        "where ca.calendar_id = :calendar_id " +
        "and c.uni = :uni";


    }
}
