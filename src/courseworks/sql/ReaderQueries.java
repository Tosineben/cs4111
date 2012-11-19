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

    public static final String GET_EVENTS_FOR_STUDENT =
            "select ev.title, ev.event_id, ev.startTime as start_time, ev.endTime as end_time, ev.description, " +
                    "ev.location, c.courseNumber as course_number, cal.calendar_id " +
            "from Events ev " +
                    "inner join Calendars cal on cal.calendar_id = ev.calendar_id " +
                    "inner join Enrollment e on e.course_id = cal.course_id " +
                    "inner join Courses c on c.course_id = e.course_id " +
            "where e.uni = :student_uni ";

    public static final String GET_MESSAGES_BY_STUDENT_COURSE_FOR_PROF =
            "SELECT S.uni, C.course_id, COUNT(M.message_id) AS num_messages " +
            "FROM Students S " +
            "INNER JOIN Enrollment E ON E.uni = S.uni " +
            "INNER JOIN Courses C ON C.course_id = E.course_id " +
            "LEFT OUTER JOIN Calendars Cal ON Cal.course_id = C.course_id " +
            "LEFT OUTER JOIN Events Ev ON Ev.calendar_id = Cal.calendar_id " +
            "LEFT OUTER JOIN Messages M ON (M.uni = S.uni AND M.event_id = Ev.event_id) " +
            "WHERE C.uni = :prof_uni " +
            "GROUP BY S.uni, C.course_id";

    //TODO remove either course_name or course_number from query
    public static final String GET_ANNOUNCEMENTS_FOR_STUDENT =
            "SELECT A.anncmnt_id, A.message, A.time_posted, " +
                    "P.name AS author, C.name AS course_name, Ra.time_read, C.courseNumber as course_number " +
            "FROM Enrollment E " +
                    "INNER JOIN Announcements A ON A.course_id = E.course_id " +
                    "INNER JOIN Courses C ON C.course_id = E.course_id " +
                    "INNER JOIN Professors P ON P.uni = C.uni " +
                    "LEFT OUTER JOIN ReadAnnouncement Ra ON (Ra.anncmnt_id = A.anncmnt_id  AND Ra.uni = E.uni) " +
            "WHERE E.uni = :student_uni ";
}
