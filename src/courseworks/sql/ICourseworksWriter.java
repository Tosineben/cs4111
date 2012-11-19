package courseworks.sql;

import courseworks.model.*;

//TODO: maybe delete professors and students??

// all methods returning a boolean return true on success, false on failure
public interface ICourseworksWriter {

    boolean createProfessor(Professor prof);

    boolean createStudent(Student student);

    // returns new event_id, 0 on failure
    int createEvent(int calendar_id, Event event, String prof_uni);
    boolean deleteEvent(int event_id, String prof_uni);

    // returns new calendar_id, 0 on failure
    int createCalendar(int course_id, Calendar cal, String prof_uni);
    boolean deleteCalendar(int calendar_id, String prof_uni);

    // returns new anncmnt_id, 0 on failure
    int createAnnouncement(int course_id, Announcement anncmnt, String prof_uni);
    boolean deleteAnnouncement(int anncmnt_id, String prof_uni);

    // returns new document_id, 0 on failure
    int createDocument(int event_id, Document doc, String prof_uni);
    boolean deleteDocument(int document_id, String prof_uni);

    // returns new message_id, 0 on failure
    int createMessage(int event_id, Message msg);

    // returns new course_id, 0 on failure
    int createCourse(Course course);
    boolean updateCourse(Course course);
    boolean deleteCourse(int course_id, String prof_uni);

    boolean enrollStudentInCourse(String uni, int course_id);
    boolean unEnrollStudentFromCourse(String uni, int course_id);

    // returns true if read status changed, false if unchanged or on failure
    boolean updateAnncmntRead(int anncmnt_id, String student_uni, boolean hasRead);
}
