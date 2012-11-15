package courseworks.sql;

import courseworks.model.*;

public interface ICourseworksWriter {

    // returns true on success, false on failure
    boolean createProfessor(Professor prof);

    // returns true on success, false on failure
    boolean createStudent(Student student);

    // returns new event_id
    int createEvent(int calendar_id, Event event);

    // returns new calendar_id
    int createCalendar(int course_id, Calendar cal);

    // returns new anncmnt_id
    int createAnnouncement(int course_id, Announcement anncmnt);

    // returns new document_id
    int createDocument(int event_id, Document doc);

    // returns new message_id
    int createMessage(int event_id, Message msg);

    // returns new course_id
    int createCourse(String prof_uni, Course course);

    // returns true on success, false on failure
    boolean enrollStudentInCourse(String uni, int course_id);

    // returns true on success, false on failure
    boolean updateAnncmntRead(int anncmnt_id, String student_uni, boolean hasRead);

}
