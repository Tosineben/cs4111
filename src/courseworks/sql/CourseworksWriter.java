package courseworks.sql;

import courseworks.model.*;

public class CourseworksWriter implements ICourseworksWriter {

    private ISqlHelper _helper;

    public CourseworksWriter() {
        _helper = new SqlHelper();
    }

    @Override
    public boolean createProfessor(Professor prof) {
        return false;
    }

    @Override
    public boolean createStudent(Student student) {
        return false;
    }

    @Override
    public int createEvent(int calendar_id, Event event) {
        return 0;
    }

    @Override
    public int createCalendar(int course_id, Calendar cal) {
        return 0;
    }

    @Override
    public int createAnnouncement(int course_id, Announcement anncmnt) {
        return 0;
    }

    @Override
    public int createDocument(int event_id, Document doc) {
        return 0;
    }

    @Override
    public int createMessage(int event_id, Message msg) {
        return 0;
    }

    @Override
    public int createCourse(String prof_uni, Course course) {
        return 0;
    }

    @Override
    public boolean enrollStudentInCourse(String uni, int course_id) {
        return false;
    }

    @Override
    public boolean updateAnncmntRead(int anncmnt_id, String student_uni, boolean hasRead) {
        return false;
    }
}
