package courseworks.sql;

import courseworks.model.*;

import java.sql.*;

public class CourseworksWriter implements ICourseworksWriter {

    private ISqlHelper _helper;

    public CourseworksWriter() {
        _helper = new SqlHelper();
    }

    @Override
    public boolean createProfessor(Professor prof) {
        Connection conn = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_PROFESSOR);
            stmt.setString("uni", prof.uni);
            stmt.setString("name", prof.name);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            _helper.tryClose(null, conn);
        }

        return true;
    }

    @Override
    public boolean createStudent(Student student) {
        Connection conn = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_STUDENT);
            stmt.setString("uni", student.uni);
            stmt.setString("name", student.name);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            _helper.tryClose(null, conn);
        }

        return true;
    }

    @Override
    public int createEvent(int calendar_id, Event event) {
        Connection conn = null;
        ResultSet rset = null;
        int newEventId = 0;

        // check to make sure prof teaches course
        // SELECT C.uni
        // FROM Calendars Cal
        // INNER JOIN Courses C ON C.course_id = Cal.course_id
        // WHERE Cal.calendar_id = @calendar_id

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, WriterQueries.IdIncrement.GET_MAX_EVENT_ID);

            while (rset.next()) {
                newEventId = rset.getInt(1) + 1;
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_EVENT);
            stmt.setInt("event_id", newEventId);
            stmt.setInt("calendar_id", calendar_id);
            stmt.setString("title", event.title);
            stmt.setTimestamp("startTime", new Timestamp(event.start.getTime()));
            stmt.setTimestamp("endTime", new Timestamp(event.end.getTime()));
            stmt.setString("description", event.description);
            stmt.setString("location", event.location);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return newEventId;
    }

    @Override
    public int createCalendar(int course_id, Calendar cal) {
        Connection conn = null;
        ResultSet rset = null;
        int newCalendarId = 0;

        // check to make sure professor teaches course
        // SELECT C.uni
        // FROM Courses C
        // WHERE C.course_id = @course_id

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, WriterQueries.IdIncrement.GET_MAX_CALENDAR_ID);

            while (rset.next()) {
                newCalendarId = rset.getInt(1) + 1;
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_CALENDAR);
            stmt.setInt("calendar_id", newCalendarId);
            stmt.setInt("course_id", course_id);
            stmt.setString("name", cal.name);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return newCalendarId;
    }

    @Override
    public int createAnnouncement(int course_id, Announcement anncmnt) {
        Connection conn = null;
        ResultSet rset = null;
        int newAnncmntId = 0;

        // check to make sure prof teaches course
        // SELECT C.uni
        // FROM Courses C
        // WHERE C.course_id = @course_id

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, WriterQueries.IdIncrement.GET_MAX_ANNCMNT_ID);

            while (rset.next()) {
                newAnncmntId = rset.getInt(1) + 1;
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_ANNCMNT);
            stmt.setInt("anncmnt_id", newAnncmntId);
            stmt.setInt("course_id", course_id);
            stmt.setString("message", anncmnt.message);
            stmt.setTimestamp("time_posted", _helper.getCurrentTime());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return newAnncmntId;
    }

    @Override
    public int createDocument(int event_id, Document doc) {
        Connection conn = null;
        ResultSet rset = null;
        int newDocId = 0;

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, WriterQueries.IdIncrement.GET_MAX_DOCUMENT_ID);

            while (rset.next()) {
                newDocId = rset.getInt(1) + 1;
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_DOCUMENT);
            stmt.setInt("document_id", newDocId);
            stmt.setString("file_path", doc.file_path);
            stmt.setInt("event_id", event_id);

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return newDocId;
    }

    @Override
    public int createMessage(int event_id, Message msg) {
        Connection conn = null;
        ResultSet rset = null;
        int newMessageId = 0;

        // check to make sure student is enrolled
        // SELECT E.uni
        // FROM Message M
        // INNER JOIN Events Ev ON Ev.event_id = M.event_id
        // INNER JOIN Enrollment E ON E.course_id = Ev.course_id

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, WriterQueries.IdIncrement.GET_MAX_MESSAGE_ID);

            while (rset.next()) {
                newMessageId = rset.getInt(1) + 1;
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_MESSAGE);
            stmt.setInt("message_id", newMessageId);
            stmt.setString("uni", msg.author.uni);
            stmt.setInt("event_id", event_id);
            stmt.setString("message", msg.message);
            stmt.setTimestamp("time_posted", _helper.getCurrentTime());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return newMessageId;
    }

    @Override
    public int createCourse(String prof_uni, Course course) {
        Connection conn = null;
        ResultSet rset = null;
        int newCourseId = 0;

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, WriterQueries.IdIncrement.GET_MAX_COURSE_ID);

            while (rset.next()) {
                newCourseId = rset.getInt(1) + 1;
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_COURSE);
            stmt.setInt("course_id", newCourseId);
            stmt.setString("uni", prof_uni);
            stmt.setString("courseNumber", course.course_number);
            stmt.setString("name", course.name);
            stmt.setString("location", course.location);
            stmt.setString("description", course.description);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return newCourseId;
    }

    @Override
    public boolean enrollStudentInCourse(String uni, int course_id) {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_ENROLLMENT);
            stmt.setString("uni", uni);
            stmt.setInt("course_id", course_id);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            _helper.tryClose(null, conn);
        }

        return true;
    }

    @Override
    public boolean updateAnncmntRead(int anncmnt_id, String uni, boolean hasRead) {
        Connection conn = null;

        // check to make sure student enrolled in course
        // select e.uni
        // from Enrollment e
        // inner join Announcements a on a.course_id = e.course_id

        try {

            conn = _helper.getConnection();

            CallableStatement stmt;

            if (hasRead) {
                stmt = conn.prepareCall(WriterQueries.INSERT_READ_ANNCMNT);
            }
            else {
                stmt = conn.prepareCall(WriterQueries.DELETE_READ_ANNCMNT);
            }

            stmt.setInt("anncmnt_id", anncmnt_id);
            stmt.setString("uni", uni);

            if (hasRead) {
                stmt.setTimestamp("time_read", _helper.getCurrentTime());
            }

            return stmt.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            _helper.tryClose(null, conn);
        }
    }
}
