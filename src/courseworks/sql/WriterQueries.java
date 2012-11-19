package courseworks.sql;

public final class WriterQueries {

    public static final String INSERT_PROFESSOR =
            "insert into Professors (uni, name) " +
            "values (:uni, :name)";

    public static final String UPDATE_PROFESSOR =
            "update Professors set " +
            "name = :name " +
            "where uni = :uni";

    public static final String DELETE_PROFESSOR =
            "delete from Students s " +
            "where s.uni = :uni";


    public static final String INSERT_STUDENT =
            "insert into Students (uni, name) " +
            "values (:uni, :name)";

    public static final String UPDATE_STUDENT =
            "update Students set " +
            "name = :name " +
            "where uni = :uni";

    public static final String DELETE_STUDENT =
            "delete from Students s " +
            "where s.uni = :uni";


    public static final String INSERT_EVENT =
            "insert into Events (event_id, calendar_id, title, startTime, endTime, description, location) " +
            "values (:event_id, :calendar_id, :title, :startTime, :endTime, :description, :location)";

    public static final String UPDATE_EVENT =
            "update Events set " +
            "title = :title," +
            "startTime = :startTime," +
            "endTime = :endTime," +
            "description = :description," +
            "location = :location " +
            "where event_id = :event_id";

    public static final String DELETE_EVENT =
            "delete from Events ev " +
            "where ev.event_id = :event_id";


    public static final String INSERT_CALENDAR =
            "insert into Calendars (calendar_id, course_id, name) " +
            "values (:calendar_id, :course_id, :name)";

    public static final String UPDATE_CALENDAR =
            "update Calendars set " +
            "name = :name " +
            "where calendar_id = :calendar_id";

    public static final String DELETE_CALENDAR =
            "delete from Calendars ca " +
            "where ca.calendar_id = :calendar_id";


    public static final String INSERT_ANNCMNT =
            "insert into Announcements (anncmnt_id, course_id, message, time_posted) " +
            "values (:anncmnt_id, :course_id, :message, :time_posted)";

    public static final String UPDATE_ANNCMNT =
            "update Announcements set " +
            "message = :message " +
            "where anncmnt_id = :anncmnt_id";

    public static final String DELETE_ANNCMNT =
            "delete from Announcements a " +
            "where a.anncmnt_id = :anncmnt_id";


    public static final String INSERT_DOCUMENT =
            "insert into Documents (document_id, file_path, event_id) " +
            "values (:document_id, :file_path, :event_id)";

    public static final String DELETE_DOCUMENT =
            "delete from Documents d " +
            "where d.document_id = :document_id";


    public static final String INSERT_MESSAGE =
            "insert into Messages (message_id, uni, event_id, message, time_posted) " +
            "values (:message_id, :uni, :event_id, :message, :time_posted)";

    public static final String UPDATE_MESSAGE =
            "update Messages set " +
            "message = :message " +
            "where message_id = :message_id";

    public static final String DELETE_MESSAGE =
            "delete from Messages m " +
            "where m.message_id = :message_id";


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
            "delete from Courses c " +
            "where c.course_id = :course_id";


    public static final String INSERT_ENROLLMENT =
            "insert into Enrollment (uni, course_id) " +
            "values (:uni, :course_id)";

    public static final String DELETE_ENROLLMENT =
            "delete from Enrollment e " +
            "where e.uni = :uni " +
            "and e.course_id = :course_id";


    public static final String INSERT_READ_ANNCMNT =
            "insert into ReadAnnouncement (anncmnt_id, uni, time_read) " +
            "values (:anncmnt_id, :uni, :time_read)";

    public static final String DELETE_READ_ANNCMNT =
            "delete from ReadAnnouncement ra " +
            "where ra.anncmnt_id = :anncmnt_id " +
            "and ra.uni = :uni";

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

        public static final String CAN_EDIT_COURSE =
                "select count(c.uni) " +
                "from Courses c " +
                "where c.course_id = :course_id " +
                "and c.uni = :uni";

        public static final String CAN_PROF_EDIT_ANNCMNT =
                "select count(c.uni) " +
                "from Courses c " +
                "inner join Announcements a on a.course_id = c.course_id " +
                "where a.anncmnt_id = :anncmnt_id " +
                "and c.uni = :uni";

        public static final String CAN_EDIT_CALENDAR =
                "select count(c.uni) " +
                "from Courses c " +
                "inner join Calendars ca on ca.course_id = c.course_id " +
                "where ca.calendar_id = :calendar_id " +
                "and c.uni = :uni";

        public static final String CAN_PROF_EDIT_EVENT =
                "select count(c.uni) " +
                "from Courses c " +
                "inner join Calendars ca on ca.course_id = ca.course_id " +
                "inner join Events ev on ev.calendar_id = ca.calendar_id " +
                "where ev.event_id = :event_id " +
                "and c.uni = :uni";

        public static final String CAN_EDIT_DOCUMENT =
                "select count(c.uni) " +
                "from Courses c " +
                "inner join Calendars ca on ca.course_id = c.course_id " +
                "inner join Events ev on ev.calendar_id = ca.calendar_id " +
                "inner join Documents d on d.event_id = ev.event_id " +
                "where d.document_id = :document_id " +
                "and c.uni = :uni";

        public static final String CAN_STUDENT_EDIT_ANNCMNT =
                "select count(e.uni) " +
                        "from Enrollment e " +
                        "inner join Announcements a on a.course_id = e.course_id " +
                        "where a.anncmnt_id = :anncmnt_id " +
                        "and e.uni = :uni";

        public static final String CAN_STUDENT_EDIT_EVENT =
                "select count(e.uni) " +
                "from Enrollment e " +
                "inner join Calendars ca on ca.course_id = e.course_id " +
                "inner join Events ev on ev.calendar_id = ca.calendar_id " +
                "where ev.event_id = :event_id " +
                "and e.uni = :uni";

        public static final String CAN_EDIT_MESSAGE =
                "select count(e.uni) " +
                "from Enrollment e " +
                "inner join Calendars ca on ca.course_id = e.course_id " +
                "inner join Events ev on ev.calendar_id = ca.calendar_id " +
                "inner join Messages m on m.event_id = ev.event_id " +
                "where m.message_id = :message_id " +
                "and e.uni = :uni";

    }
}
