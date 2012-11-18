<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    ICourseworksReader rdr = new CourseworksReader();
    List<Student> students = rdr.getStudents();
    List<Professor> profs = rdr.getProfessors();
    List<Course> courses = rdr.getCourses();
%>

<html>
<head>
    <title>Sample</title>
    <!-- styles here -->
</head>
<body>

<div id="students">
    <h1>Students</h1>
    <%
        for (Student student: students) {
    %>
        <div><%=student.name%> (<%=student.uni%>)</div>
        <h2>Courses</h2>
        <table>
            <tr>
                <td>course id</td>
                <td>course num</td>
                <td>name</td>
                <td>prof</td>
                <td>location</td>
                <td>desc</td>
            </tr>
            <tr>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
            </tr>
            <%
                for (Course course: rdr.getCoursesForStudent(student.uni)) {
            %>
                <tr>
                    <td><%=course.course_id%></td>
                    <td><%=course.course_number%></td>
                    <td><%=course.name%></td>
                    <td><%=course.professor.name%> (<%=course.professor.uni%>)</td>
                    <td><%=course.location%></td>
                    <td><%=course.description.substring(0, 10)%></td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>
</div>
<br/>
<div id="profs">
    <h1>Profs</h1>
    <%
        for (Professor prof: profs) {
    %>
        <div><%=prof.name%>(<%=prof.uni%>)</div>
        <h2>Courses</h2>
        <table>
            <tr>
                <td>course id</td>
                <td>course num</td>
                <td>name</td>
                <td>prof</td>
                <td>location</td>
                <td>desc</td>
            </tr>
            <tr>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
            </tr>
            <%
                for (Course course: rdr.getCoursesForProfessor(prof.uni)) {
            %>
            <tr>
                <td><%=course.course_id%></td>
                <td><%=course.course_number%></td>
                <td><%=course.name%></td>
                <td><%=course.professor.name%> (<%=course.professor.uni%>)</td>
                <td><%=course.location%></td>
                <td><%=course.description.substring(0, 10)%></td>
            </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>
</div>
<br/>
<div id="courses">
    <h1>Courses</h1>
    <%
        for (Course course: courses) {
    %>
        <h2><%=course.course_number%> (<%=course.course_id%>)</h2>
        <h3>Students</h3>
        <ul>
            <%
                for (Student student : rdr.getStudentsForCourse(course.course_id)) {
            %>
                <li><%=student.name%> (<%=student.uni%>)</li>
            <%
                }
            %>
        </ul>

        <h3>Announcements</h3>
        <table>
            <tr>
                <td>anncmnt id</td>
                <td>message</td>
                <td>time posted</td>
                <td>students read</td>
            </tr>
            <tr>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
            </tr>
            <%
                for (Announcement anncmnt: rdr.getAnnouncementsForCourse(course.course_id)) {
            %>
                <tr>
                    <td><%=anncmnt.anncmnt_id%></td>
                    <td><%=anncmnt.message%></td>
                    <td><%=anncmnt.time_posted%></td>
                    <td>
                    <%
                        for(ReadAnnouncment ra: rdr.getStudentsReadForAnnouncment(anncmnt.anncmnt_id)) {
                            out.print(ra.student.uni + "(" + ra.time_read + "), ");
                        }
                    %>
                    </td>
                </tr>
            <%
                }
            %>
        </table>

        <h3>Calendars</h3>
        <% List<Event> allEvents = new ArrayList<Event>(); %>
        <ul>
            <%
                for (Calendar cal : rdr.getCalendarsForCourse(course.course_id).values()) {
                    allEvents.addAll(rdr.getEventsForCalendar(cal.calendar_id));
            %>
                <li><%=cal.name%> (<%=cal.calendar_id%>)</li>
            <%
                }
            %>
        </ul>

        <h3>Events</h3>

        <table>
            <tr>
                <td>Event id</td>
                <td>Title</td>
                <td>Start</td>
                <td>End</td>
                <td>Location</td>
                <td>Description</td>
                <td>Document Paths</td>
                <td>Message Ids</td>
            </tr>
            <tr>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
            </tr>
            <%
                for (Event event : allEvents) {
            %>
            <tr>
                <td><%=event.event_id%></td>
                <td><%=event.title%></td>
                <td><%=event.start%></td>
                <td><%=event.end%></td>
                <td><%=event.location%></td>
                <td><%=event.description.substring(0, 10)%></td>
                <td>
                    <% for (Document doc : rdr.getDocumentsForEvent(event.event_id).values()) {
                        out.print(doc.file_path + ", ");
                    }
                    %>
                </td>
                <td>
                    <% for (Message message : rdr.getMessagesForEvent(event.event_id)) {
                        out.print(message.message_id + ", ");
                    }
                    %>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>
</div>

</body>
<!-- scripts here -->
</html>