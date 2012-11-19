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

    @Override
    public int createEvent(int calendar_id, Event event, String prof_uni) throws SecurityException {
        Connection conn = null;
        int newEventId = 0;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_CALENDAR, calendar_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to add event to calendar %d", prof_uni, calendar_id));
            }

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
    public boolean updateEvent(Event event, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_PROF_EDIT_EVENT, event.event_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to update event %d", prof_uni, event.event_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.UPDATE_EVENT);
            stmt.setString("title", event.title);
            stmt.setTimestamp("startTime", new Timestamp(event.start.getTime()));
            stmt.setTimestamp("endTime", new Timestamp(event.end.getTime()));
            stmt.setString("description", event.description);
            stmt.setString("location", event.location);
            stmt.setInt("event_id", event.event_id);
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
    public boolean deleteEvent(int event_id, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_PROF_EDIT_EVENT, event_id, prof_uni) == 0) {
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

    @Override
    public int createCalendar(int course_id, Calendar cal, String prof_uni) throws SecurityException {
        Connection conn = null;
        int newCalendarId = 0;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_COURSE, course_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to add calendar for course %d", prof_uni, course_id));
            }

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
    public boolean updateCalendar(Calendar cal, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_CALENDAR, cal.calendar_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to update calendar %d", prof_uni, cal.calendar_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.UPDATE_CALENDAR);
            stmt.setString("name", cal.name);
            stmt.setInt("calendar_id", cal.calendar_id);
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

    @Override
    public int createAnnouncement(int course_id, Announcement anncmnt, String prof_uni) throws SecurityException {
        Connection conn = null;
        int newAnncmntId = 0;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_COURSE, course_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to add anncmnt to course %d", prof_uni, course_id));
            }

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
    public boolean updateAnnouncement(Announcement anncmnt, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_PROF_EDIT_ANNCMNT, anncmnt.anncmnt_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to update anncmnt %d", prof_uni, anncmnt.anncmnt_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.UPDATE_ANNCMNT);
            stmt.setString("message", anncmnt.message);
            stmt.setInt("anncmnt_id", anncmnt.anncmnt_id);
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
    public boolean deleteAnnouncement(int anncmnt_id, String prof_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_PROF_EDIT_ANNCMNT, anncmnt_id, prof_uni) == 0) {
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

    @Override
    public int createDocument(int event_id, Document doc, String prof_uni) throws SecurityException {
        Connection conn = null;
        int newDocId = 0;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_PROF_EDIT_EVENT, event_id, prof_uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to add document for event %d", prof_uni, event_id));
            }

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

    @Override
    public int createMessage(int event_id, Message msg) throws SecurityException {
        Connection conn = null;
        int newMessageId = 0;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_STUDENT_EDIT_EVENT, event_id, msg.author.uni) == 0) {
                throw new SecurityException(String.format("student %s does not have permission to add message for event %d", msg.author.uni, event_id));
            }

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

    @Override
    public boolean updateMessage(Message msg) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_MESSAGE, msg.message_id, msg.author.uni) == 0) {
                throw new SecurityException(String.format("student %s does not have permission to update message %d", msg.author.uni, msg.message_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.UPDATE_MESSAGE);
            stmt.setString("message", msg.message);
            stmt.setInt("message_id", msg.message_id);
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
    public boolean deleteMessage(int message_id, String student_uni) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_MESSAGE, message_id, student_uni) == 0) {
                throw new SecurityException(String.format("student %s does not have permission to delete message %d", student_uni, message_id));
            }

            CallableStatement stmt = conn.prepareCall(WriterQueries.DELETE_MESSAGE);
            stmt.setInt("message_id", message_id);
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
    public boolean updateCourse(Course course) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_EDIT_COURSE, course.course_id, course.professor.uni) == 0) {
                throw new SecurityException(String.format("professor %s does not have permission to update course %d", course.professor.uni, course.course_id));
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
    public boolean updateAnncmntRead(int anncmnt_id, String student_uni, boolean hasRead) throws SecurityException {
        Connection conn = null;

        try {
            conn = _helper.getConnection();

            if (_helper.executeScalar(conn, WriterQueries.Validation.CAN_STUDENT_EDIT_ANNCMNT, anncmnt_id, student_uni) == 0) {
                throw new SecurityException(String.format("student %s does not have permission to update announcement %d", student_uni, anncmnt_id));
            }

            CallableStatement stmt;

            if (hasRead) {
                stmt = conn.prepareCall(WriterQueries.INSERT_READ_ANNCMNT);
            }
            else {
                stmt = conn.prepareCall(WriterQueries.DELETE_READ_ANNCMNT);
            }

            stmt.setInt("anncmnt_id", anncmnt_id);
            stmt.setString("uni", student_uni);

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
