<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>

<%
    ICourseworksReader rdr = new CourseworksReader();

    List<Student> students = rdr.getStudents();
    List<Professor> profs = rdr.getProfessors();
    List<Course> courses = rdr.getCourses();

    Student student = students.get(0);
    Professor prof = profs.get(0);
    Course course = courses.get(0);

    List<Course> coursesForStudent = rdr.getCoursesForStudent(student.uni);
    List<Course> coursesForProf = rdr.getCoursesForProfessor(prof.uni);
    List<Student> studentsForCourse = rdr.getStudentsForCourse(course.course_id);
    List<Announcement> anncmnts = rdr.getAnnouncementsForCourse(course.course_id);
    Map<String, Calendar> calendars = rdr.getCalendarsForCourse(course.course_id);





    Announcement anncmnt = anncmnts.get(0);
    Calendar calendar = (Calendar)calendars.values().toArray()[0];

    List<ReadAnnouncment> readAnncmnts = rdr.getStudentsReadForAnnouncment(anncmnt.anncmnt_id);
    List<Event> events = rdr.getEventsForCalendar(calendar.calendar_id);

    Event event = events.get(0);

    Map<String, Document> docs = rdr.getDocumentsForEvent(event.event_id);
    List<Message> msgs = rdr.getMessagesForEvent(event.event_id);
%>

<html>
<head>
    <title>Sample</title>
    <!-- styles here -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.servletPath}/styles/bootstrap/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.servletPath}/styles/bootstrap/bootstrap-responsive.min.css" />
</head>
<body>

</body>
<!-- scripts here -->
<script type="text/javascript" src="${pageContext.request.servletPath}/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="${pageContext.request.servletPath}/scripts/bootstrap.js"></script>
</html>