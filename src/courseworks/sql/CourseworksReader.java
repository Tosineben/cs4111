package courseworks.sql;

import courseworks.model.*;
import courseworks.model.Calendar;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class CourseworksReader implements ICourseworksReader {

    private ISqlHelper _helper;

    public CourseworksReader() {
        _helper = new SqlHelper();
    }

    @Override
    public List<Professor> getProfessors() {
        List<Professor> profs = new ArrayList<Professor>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, ReaderQueries.GET_PROFESSORS);

            while (rset.next()) {
                Professor prof = new Professor();
                prof.uni = rset.getString("uni");
                prof.name = rset.getString("name");
                profs.add(prof);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return profs;
    }

    @Override
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<Student>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, ReaderQueries.GET_STUDENTS);
            students = parseStudents(rset);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return students;
    }

    @Override
    public List<Student> getStudentsForCourse(int course_id) {
        List<Student> students = new ArrayList<Student>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_STUDENTS_FOR_COURSE);
            stmt.setInt("course_id", course_id);
            rset = stmt.executeQuery();
            students = parseStudents(rset);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return students;
    }

    @Override
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<Course>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, ReaderQueries.GET_COURSES);
            courses = parseCourses(rset);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return courses;
    }

    @Override
    public List<Course> getCoursesForProfessor(String uni) {
        List<Course> courses = new ArrayList<Course>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_COURSES_FOR_PROF);
            stmt.setString("uni", uni);
            rset = stmt.executeQuery();
            courses = parseCourses(rset);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return courses;
    }

    @Override
    public List<Course> getCoursesForStudent(String uni) {
        List<Course> courses = new ArrayList<Course>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_COURSES_FOR_STUDENT);
            stmt.setString("uni", uni);
            rset = stmt.executeQuery();
            courses = parseCourses(rset);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return courses;
    }

    @Override
    public List<Event> getEventsForCalendar(int calendar_id) {
        List<Event> events = new ArrayList<Event>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_EVENTS_FOR_CALENDAR);
            stmt.setInt("calendar_id", calendar_id);
            rset = stmt.executeQuery();

            while (rset.next()) {
                Event event = new Event();
                event.event_id = rset.getInt("event_id");
                event.description = rset.getString("description");
                event.end = rset.getTimestamp("endTime");
                event.location = rset.getString("location");
                event.start = rset.getTimestamp("startTime");
                event.title = rset.getString("title");
                events.add(event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return events;
    }

    @Override
    public List<Announcement> getAnnouncementsForCourse(int course_id) {
        List<Announcement> anncmnts = new ArrayList<Announcement>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_ANNCMNTS_FOR_COURSE);
            stmt.setInt("course_id", course_id);
            rset = stmt.executeQuery();

            while (rset.next()) {
                Announcement anncmnt = new Announcement();
                anncmnt.anncmnt_id = rset.getInt("anncmnt_id");
                anncmnt.message = rset.getString("message");
                anncmnt.time_posted = rset.getTimestamp("time_posted");
                anncmnts.add(anncmnt);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return anncmnts;
    }

    @Override
    public Map<Integer, List<Announcement>> getAnnouncementsForProf(String prof_uni) {
        Map<Integer, List<Announcement>> anncmntsByCourse = new HashMap<Integer, List<Announcement>>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_ANNCMNTS_FOR_PROF);
            stmt.setString("uni", prof_uni);
            rset = stmt.executeQuery();

            while (rset.next()) {
                int course_id = rset.getInt("course_id");
                if (!anncmntsByCourse.containsKey(course_id)) {
                    anncmntsByCourse.put(course_id, new ArrayList<Announcement>());
                }
                Announcement anncmnt = new Announcement();
                anncmnt.anncmnt_id = rset.getInt("anncmnt_id");
                anncmnt.message = rset.getString("message");
                anncmnt.time_posted = rset.getTimestamp("time_posted");
                anncmntsByCourse.get(course_id).add(anncmnt);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return anncmntsByCourse;
    }

    @Override
    public Map<Integer, List<Document>> getDocumentsForProf(String prof_uni) {
        Map<Integer, List<Document>> docsByEvent = new HashMap<Integer, List<Document>>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_DOCUMENTS_FOR_PROF);
            stmt.setString("uni", prof_uni);
            rset = stmt.executeQuery();

            while (rset.next()) {
                int event_id = rset.getInt("event_id");
                if (!docsByEvent.containsKey(event_id)) {
                    docsByEvent.put(event_id, new ArrayList<Document>());
                }
                Document doc = new Document();
                doc.document_id = rset.getInt("document_id");
                doc.file_path = rset.getString("file_path");
                docsByEvent.get(event_id).add(doc);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return docsByEvent;
    }

    @Override
    public List<ReadAnnouncment> getStudentsReadForAnnouncment(int anncmnt_id) {
        List<ReadAnnouncment> ras = new ArrayList<ReadAnnouncment>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_READ_ANNCMNTS_FOR_ANNCMNT);
            stmt.setInt("anncmnt_id", anncmnt_id);
            rset = stmt.executeQuery();

            while (rset.next()) {
                ReadAnnouncment ra = new ReadAnnouncment();
                ra.time_read = rset.getTimestamp("time_read");
                ra.student = new Student();
                ra.student.uni = rset.getString("uni");
                ra.student.name = rset.getString("name");
                ra.anncmnt = new Announcement();
                ra.anncmnt.anncmnt_id = rset.getInt("anncmnt_id");
                ra.anncmnt.time_posted = rset.getTimestamp("time_posted");
                ra.anncmnt.message = rset.getString("message");
                ras.add(ra);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return ras;
    }

    @Override
    public Map<String, Calendar> getCalendarsForCourse(int course_id) {
        Map<String, Calendar> cals = new HashMap<String, Calendar>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_CALENDARS_FOR_COURSE);
            stmt.setInt("course_id", course_id);
            rset = stmt.executeQuery();

            while (rset.next()) {
                Calendar cal = new Calendar();
                cal.calendar_id = rset.getInt("calendar_id");
                cal.name = rset.getString("name");
                cals.put(cal.name, cal);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return cals;
    }


    public List<Calendar> getCalendarListForCourse(int course_id) {
        List<Calendar> cals = new ArrayList<Calendar>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_CALENDARS_FOR_COURSE);
            stmt.setInt("course_id", course_id);
            rset = stmt.executeQuery();

            while (rset.next()) {
                Calendar cal = new Calendar();
                cal.calendar_id = rset.getInt("calendar_id");
                cal.name = rset.getString("name");
                cals.add(cal);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return cals;
    }

    @Override
    public Map<Integer, List<Calendar>> getCalendarsForProf(String prof_uni) {
        Map<Integer, List<Calendar>> calsByCourse = new HashMap<Integer, List<Calendar>>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_CALENDARS_FOR_PROF);
            stmt.setString("uni", prof_uni);
            rset = stmt.executeQuery();

            while (rset.next()) {
                int course_id = rset.getInt("course_id");
                if (!calsByCourse.containsKey(course_id)) {
                    calsByCourse.put(course_id, new ArrayList<Calendar>());
                }
                Calendar cal = new Calendar();
                cal.calendar_id = rset.getInt("calendar_id");
                cal.name = rset.getString("name");
                calsByCourse.get(course_id).add(cal);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return calsByCourse;
    }

    @Override
    public Map<Integer, Map<Integer, List<Event>>> getEventsForProf(String prof_uni) {
        Map<Integer, Map<Integer, List<Event>>> eventsByCalByCourse = new HashMap<Integer, Map<Integer, List<Event>>>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_EVENTS_FOR_PROF);
            stmt.setString("uni", prof_uni);
            rset = stmt.executeQuery();

            while (rset.next()) {
                int course_id = rset.getInt("course_id");
                int calendar_id = rset.getInt("calendar_id");
                if (!eventsByCalByCourse.containsKey(course_id)) {
                    eventsByCalByCourse.put(course_id, new HashMap<Integer, List<Event>>());
                }
                if (!eventsByCalByCourse.get(course_id).containsKey(calendar_id)){
                    eventsByCalByCourse.get(course_id).put(calendar_id, new ArrayList<Event>());
                }
                Event event = new Event();
                event.event_id = rset.getInt("event_id");
                event.description = rset.getString("description");
                event.end = rset.getTimestamp("end_time");
                event.location = rset.getString("location");
                event.start = rset.getTimestamp("start_time");
                event.title = rset.getString("title");
                eventsByCalByCourse.get(course_id).get(calendar_id).add(event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return eventsByCalByCourse;
    }

    @Override
    public List<Message> getMessagesForEvent(int event_id) {
        List<Message> msgs = new ArrayList<Message>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_MESSAGES_FOR_EVENT);
            stmt.setInt("event_id", event_id);
            rset = stmt.executeQuery();

            while (rset.next()) {
                Message msg = new Message();
                msg.message = rset.getString("message");
                msg.message_id = rset.getInt("message_id");
                msg.time_posted = rset.getTimestamp("time_posted");
                msg.author = new Student();
                msg.author.uni = rset.getString("uni");
                msg.author.name = rset.getString("name");
                msgs.add(msg);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return msgs;
    }

    @Override
    public Map<String, Document> getDocumentsForEvent(int event_id) {
        Map<String, Document> docs = new HashMap<String, Document>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_DOCUMENTS_FOR_COURSE);
            stmt.setInt("event_id", event_id);
            rset = stmt.executeQuery();

            while (rset.next()) {
                Document doc = new Document();
                doc.document_id = rset.getInt("document_id");
                doc.file_path = rset.getString("file_path");
                docs.put(doc.file_path, doc);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return docs;
    }

    private List<Course> parseCourses(ResultSet rset) throws SQLException {
        List<Course> courses = new ArrayList<Course>();
        while (rset.next()) {
            Course course = new Course();
            course.course_id = rset.getInt("course_id");
            course.course_number = rset.getString("course_number");
            course.description = rset.getString("description");
            course.location = rset.getString("location");
            course.name = rset.getString("name");
            course.professor = new Professor();
            course.professor.uni = rset.getString("uni");
            course.professor.name = rset.getString("prof_name");
            courses.add(course);
        }
        return courses;
    }

    private List<Student> parseStudents(ResultSet rset) throws SQLException {
        List<Student> students = new ArrayList<Student>();
        while (rset.next()) {
            Student student = new Student();
            student.uni = rset.getString("uni");
            student.name = rset.getString("name");
            students.add(student);
        }
        return students;
    }

    public List<Event> getEventsForStudent(String student_uni) {
        List<Event> events = new ArrayList<Event>();
            Connection conn = null;
            ResultSet rset = null;

            try {
                conn = _helper.getConnection();
                CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_EVENTS_FOR_STUDENT);
                stmt.setString("student_uni", student_uni);

                System.out.print(stmt);
                rset = stmt.executeQuery();

            while (rset.next()) {
                Event event = new Event();
                event.event_id = rset.getInt("event_id");
                event.description = rset.getString("description");
                event.end = rset.getTimestamp("end_time");
                event.location = rset.getString("location");
                event.start = rset.getTimestamp("start_time");
                event.title = rset.getString("title");
                event.course_id = rset.getInt("course_id");
                event.calendar_id = rset.getInt("calendar_id");
                events.add(event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return events;
    }

    @Override
    public Map<Integer, Map<String, Document>> getDocumentsForStudent(String student_uni) {
        Map<Integer, Map<String, Document>> docsByEvent = new HashMap<Integer, Map<String, Document>>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_DOCUMENTS_FOR_STUDENT);
            stmt.setString("uni", student_uni);
            rset = stmt.executeQuery();

            while (rset.next()) {
                int event_id = rset.getInt("event_id");
                if (!docsByEvent.containsKey(event_id)) {
                    docsByEvent.put(event_id, new HashMap<String, Document>());
                }
                Document doc = new Document();
                doc.document_id = rset.getInt("document_id");
                doc.file_path = rset.getString("file_path");
                docsByEvent.get(event_id).put(doc.file_path, doc);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return docsByEvent;
    }

    @Override
    public Map<Integer, List<Message>> getMessagesForStudent(String student_uni) {
        Map<Integer, List<Message>> msgsByEvent = new HashMap<Integer, List<Message>>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_MESSAGES_FOR_STUDENT);
            stmt.setString("uni", student_uni);
            rset = stmt.executeQuery();

            while (rset.next()) {
                int event_id = rset.getInt("event_id");
                if (!msgsByEvent.containsKey(event_id)) {
                    msgsByEvent.put(event_id, new ArrayList<Message>());
                }
                Message msg = new Message();
                msg.message = rset.getString("message");
                msg.message_id = rset.getInt("message_id");
                msg.time_posted = rset.getTimestamp("time_posted");
                msg.author = new Student();
                msg.author.uni = rset.getString("uni");
                msg.author.name = rset.getString("name");
                msgsByEvent.get(event_id).add(msg);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return msgsByEvent;
    }

    public List<Event> getUpcomingEventsForStudent(String student_uni) {
        List<Event> events = new ArrayList<Event>();
        Connection conn = null;
        ResultSet rset = null;

        Date time_now = new Date();

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_UPCOMING_EVENTS_FOR_STUDENT);
            stmt.setString("student_uni", student_uni);
            stmt.setDate("current_time", (java.sql.Date) time_now);

            System.out.print(stmt);
            rset = stmt.executeQuery();

            while (rset.next()) {
                Event event = new Event();
                event.event_id = rset.getInt("event_id");
                event.description = rset.getString("description");
                event.end = rset.getDate("end_time");
                event.location = rset.getString("location");
                event.start = rset.getDate("start_time");
                event.title = rset.getString("title");
                event.course_id = rset.getInt("course_id");
                event.calendar_id = rset.getInt("calendar_id");
                events.add(event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return events;
    }

    public List<Announcement> getAnnouncementsForStudent(String student_uni) {
        List<Announcement> anncmnts = new ArrayList<Announcement>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_ANNOUNCEMENTS_FOR_STUDENT);
            stmt.setString("student_uni", student_uni);
            rset = stmt.executeQuery();

            while (rset.next()) {
                Announcement anncmnt = new Announcement();
                anncmnt.anncmnt_id = rset.getInt("anncmnt_id");
                anncmnt.message = rset.getString("message");
                anncmnt.time_posted = rset.getTimestamp("time_posted");
                anncmnt.course_id = rset.getInt("course_id");
                anncmnt.author = rset.getString("author");
                anncmnt.time_read = rset.getTimestamp("time_read");
                anncmnts.add(anncmnt);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return anncmnts;
    }

    public HashMap<Integer, HashMap<String, Integer>> getMessageCountByStudentByCourse(String prof_uni) {
        HashMap<Integer, HashMap<String, Integer>> results = new HashMap<Integer, HashMap<String, Integer>>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReaderQueries.GET_MESSAGES_BY_STUDENT_COURSE_FOR_PROF);
            stmt.setString("uni", prof_uni);

            rset = stmt.executeQuery();

            while (rset.next()) {
                String uni = rset.getString("uni");
                int course_id = rset.getInt("course_id");
                int num_messages = rset.getInt("num_messages");

                if (!results.containsKey(course_id)) {
                    results.put(course_id,  new HashMap<String, Integer>());
                }

                results.get(course_id).put(uni, num_messages);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return results;
    }
}
