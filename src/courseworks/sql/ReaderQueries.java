package courseworks.sql;

public final class ReaderQueries {

    public static final String GET_PROFESSORS =
            "select p.uni, p.name " +
            "from Professors p";


    public static final String GET_STUDENTS =
            "select s.uni, s.name " +
            "from Students s ";

    public static final String GET_STUDENTS_FOR_COURSE =
            GET_STUDENTS +
            "inner join Enrollment e on e.uni = s.uni " +
            "where e.course_id = :course_id";


    public static final String GET_COURSES =
            "select c.course_id, c.courseNumber as course_number, c.name, c.location, c.description, c.uni, p.name as prof_name " +
                    "from Courses c " +
                    "inner join Professors p on p.uni = c.uni ";

    public static final String GET_COURSES_FOR_PROF =
            GET_COURSES +
            "where c.uni = :uni";

    public static final String GET_COURSES_FOR_STUDENT =
            GET_COURSES +
            "inner join Enrollment e on e.course_id = c.course_id " +
            "where e.uni = :uni";


    public static final String GET_EVENTS =
            "select ev.event_id, ev.calendar_id, ev.title, ev.startTime, ev.endTime, ev.description, ev.location " +
            "from Events ev ";

    public static final String GET_EVENTS_FOR_CALENDAR =
            GET_EVENTS +
            "where ev.calendar_id = :calendar_id";


    public static final String GET_ANNCMNTS =
            "select a.anncmnt_id, a.course_id, a.message, a.time_posted " +
            "from Announcements a ";

    public static final String GET_ANNCMNTS_FOR_COURSE =
            GET_ANNCMNTS +
            "where a.course_id = :course_id";


    public static final String GET_READ_ANNCMNTS =
            "select a.anncmnt_id, a.course_id, a.message, a.time_posted, s.uni, s.name, ra.time_read " +
            "from ReadAnnouncement ra " +
            "inner join Announcements a on a.anncmnt_id = ra.anncmnt_id " +
            "inner join Students s on s.uni = ra.uni ";

    public static final String GET_READ_ANNCMNTS_FOR_ANNCMNT =
            GET_READ_ANNCMNTS +
            "where ra.anncmnt_id = :anncmnt_id";


    public static final String GET_CALENDARS =
            "select ca.calendar_id, ca.course_id, ca.name " +
            "from Calendars ca ";

    public static final String GET_CALENDARS_FOR_COURSE =
            GET_CALENDARS +
            "where ca.course_id = :course_id";


    public static final String GET_MESSAGES =
            "select m.message_id, m.event_id, m.message, m.time_posted, s.uni, s.name " +
            "from Messages m " +
            "inner join Students s on s.uni = m.uni ";

    public static final String GET_MESSAGES_FOR_EVENT =
            GET_MESSAGES +
            "where m.event_id = :event_id";


    public static final String GET_DOCUMENTS =
            "select d.document_id, d.file_path, d.event_id " +
            "from Documents d ";

    public static final String GET_DOCUMENTS_FOR_COURSE =
            GET_DOCUMENTS +
            "where d.event_id = :event_id";
}
