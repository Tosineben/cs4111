package courseworks.sql;

import courseworks.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICourseworksReader {

    // returns all professors
    List<Professor> getProfessors();

    // returns all students
    List<Student> getStudents();

    // returns students enrolled in course
    List<Student> getStudentsForCourse(int course_id);

    // returns all courses
    List<Course> getCourses();

    // returns courses taught by professor
    List<Course> getCoursesForProfessor(String prof_uni);

    // returns courses student is enrolled in
    List<Course> getCoursesForStudent(String student_uni);

    // returns events for calendar
    List<Event> getEventsForCalendar(int calendar_id);

    // returns announcements for course
    List<Announcement> getAnnouncementsForCourse(int course_id);

    // returns student+time_read for students that have read announcement
    List<ReadAnnouncment> getStudentsReadForAnnouncment(int anncmnt_id);

    // returns calendars for course by name
    Map<String, Calendar> getCalendarsForCourse(int course_id);

    // returns a list of calendars for course
    List<Calendar> getCalendarListForCourse(int course_id);

    Map<Integer, List<Calendar>> getCalendarsForProf(String prof_uni);
    Map<Integer, Map<Integer, List<Event>>> getEventsForProf(String prof_uni);
    HashMap<Integer, HashMap<String, Integer>> getMessageCountByStudentByCourse(String prof_uni);
    Map<Integer, List<Announcement>> getAnnouncementsForProf(String prof_uni);
    Map<Integer, List<Document>> getDocumentsForProf(String prof_uni);

    // returns messages for event
    List<Message> getMessagesForEvent(int event_id);

    // returns documents for event by file path
    Map<String, Document> getDocumentsForEvent(int event_id);

    List<Event> getEventsForStudent(String student_uni);

    List<Announcement> getAnnouncementsForStudent(String student_uni);

}
