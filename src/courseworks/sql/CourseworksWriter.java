package courseworks.sql;

import courseworks.model.*;

import java.sql.*;

public class CourseworksWriter implements ICourseworksWriter {

    private ISqlHelper _helper;

    public CourseworksWriter() {
        _helper = new SqlHelper();
    }

    // TODO: check that the same student UNI doesnt exist
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

    // TODO: check that the same prof UNI doesnt exist
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

    // TODO data validation
    @Override
    public int createEvent(int calendar_id, Event event) {
        Connection conn = null;
        int newEventId = 0;

        try {
            conn = _helper.getConnection();

            // if (_helper.executeScalar(conn, WriterQueries.Validation.VALIDATE_EVENT, calendar_id, logged_in_prof_uni) == 0) {
            //     throw new SecurityException(String.format("professor %s does not have permission to create event for calendar %d", logged_in_prof_uni, calendar_id));
            // }

            newEventId = _helper.executeScalar(conn, WriterQueries.IdIncrement.GET_MAX_EVENT_ID) + 1;

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
            _helper.tryClose(null, conn);
        }

        return newEventId;
    }

    @Override
    public boolean deleteEvent(int event_id, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_EVENT, event_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to delete event %d", prof_uni, event_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.DELETE_EVENT);
            stmt.setInt("event_id", event_id);
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

    // TODO check to make sure professor teaches course
    @Override
    public int createCalendar(int course_id, Calendar cal) {
        Connection conn = null;
        int newCalendarId = 0;

        // SELECT C.uni
        // FROM Courses C
        // WHERE C.course_id = @course_id

        try {
            conn = _helper.getConnection();
            newCalendarId = _helper.executeScalar(conn, WriterQueries.IdIncrement.GET_MAX_CALENDAR_ID) + 1;

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
            _helper.tryClose(null, conn);
        }

        return newCalendarId;
    }

    @Override
    public boolean deleteCalendar(int calendar_id, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_CALENDAR, calendar_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to delete calendar %d", prof_uni, calendar_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.DELETE_CALENDAR);
            stmt.setInt("calendar_id", calendar_id);
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

    // TODO check to make sure prof teaches course
    @Override
    public int createAnnouncement(int course_id, Announcement anncmnt) {
        Connection conn = null;
        int newAnncmntId = 0;

        // SELECT C.uni
        // FROM Courses C
        // WHERE C.course_id = @course_id

        try {
            conn = _helper.getConnection();
            newAnncmntId = _helper.executeScalar(conn, WriterQueries.IdIncrement.GET_MAX_ANNCMNT_ID) + 1;

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
            _helper.tryClose(null, conn);
        }

        return newAnncmntId;
    }

    @Override
    public boolean deleteAnnouncement(int anncmnt_id, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_ANNCMNT, anncmnt_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to delete announcement %d", prof_uni, anncmnt_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.DELETE_ANNCMNT);
            stmt.setInt("anncmnt_id", anncmnt_id);
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

    // TODO check logged in prof owns event
    @Override
    public int createDocument(int event_id, Document doc) {
        Connection conn = null;
        int newDocId = 0;

        try {
            conn = _helper.getConnection();
            newDocId = _helper.executeScalar(conn, WriterQueries.IdIncrement.GET_MAX_DOCUMENT_ID) + 1;

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
            _helper.tryClose(null, conn);
        }

        return newDocId;
    }

    @Override
    public boolean deleteDocument(int document_id, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_DOCUMENT, document_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to delete document %d", prof_uni, document_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.DELETE_DOCUMENT);
            stmt.setInt("document_id", document_id);
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

    // TODO check to make sure student is enrolled
    @Override
    public int createMessage(int event_id, Message msg) {
        Connection conn = null;
        int newMessageId = 0;

        // SELECT E.uni
        // FROM Message M
        // INNER JOIN Events Ev ON Ev.event_id = M.event_id
        // INNER JOIN Enrollment E ON E.course_id = Ev.course_id

        try {
            conn = _helper.getConnection();
            newMessageId = _helper.executeScalar(conn, WriterQueries.IdIncrement.GET_MAX_MESSAGE_ID) + 1;

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
            _helper.tryClose(null, conn);
        }

        return newMessageId;
    }

    // TODO: validate same event name/location doesnt exist
    @Override
    public int createCourse(Course course) {
        Connection conn = null;
        int newCourseId = 0;

        try {
            conn = _helper.getConnection();
            newCourseId = _helper.executeScalar(conn, WriterQueries.IdIncrement.GET_MAX_COURSE_ID) + 1;

            CallableStatement stmt = conn.prepareCall(WriterQueries.INSERT_COURSE);
            stmt.setInt("course_id", newCourseId);
            stmt.setString("uni", course.professor.uni);
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
            _helper.tryClose(null, conn);
        }

        return newCourseId;
    }

    @Override
    public boolean updateCourse(Course course, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_COURSE, course.course_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to udpate course %d", prof_uni, course.course_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.UPDATE_COURSE);
            stmt.setString("courseNumber", course.course_number);
            stmt.setString("name", course.name);
            stmt.setString("location", course.location);
            stmt.setString("description", course.description);
            stmt.setInt("course_id", course.course_id);
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
    public boolean deleteCourse(int course_id, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_COURSE, course_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to delete course %d", prof_uni, course_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.DELETE_COURSE);
            stmt.setInt("course_id", course_id);
            stmt.setString("uni", prof_uni);
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
    public boolean enrollStudentInCourse(String uni, int course_id) throws SecurityException {
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
    public boolean unEnrollStudentFromCourse(String uni, int course_id) {
        Connection conn = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(WriterQueries.DELETE_ENROLLMENT);
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

        // TODO: check to make sure student enrolled in course
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
