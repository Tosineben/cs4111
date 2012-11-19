<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="courseworks.model.comperators.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="courseworks.SessionKeys" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    //Student student = (Student)session.getAttribute(SessionKeys.logged_in_student);

    //TODO remove test code, replace with session
    Student student = new Student();
    student.uni = "vs2411";

    if (student == null){
        response.sendRedirect("index.jsp");
    }
    ICourseworksReader rdr = new CourseworksReader();
    List<Course> courses = rdr.getCoursesForStudent(student.uni);


    List<Event> events = rdr.getEventsForStudent(student.uni);
    List<Announcement> announcements = rdr.getAnnouncementsForStudent(student.uni);

    Collections.sort((List<Announcement>) announcements, new AnnouncementComperator());
    Collections.sort((List<Event>) events, new EventComperator());
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title></title>
        <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap.min.css" />
        <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap-responsive.min.css" />
        <link type="text/css" rel="stylesheet" href="/styles/courseworks.css"/>
    </head>


    <body>

    <jsp:include page="nav.jsp">
        <jsp:param name="calendar" value="active" />
    </jsp:include>


    <ul class="nav nav-tabs nav-stacked">
        <% for(Course course : courses){ %>
            <li class= "<%= course.course_number.replaceAll("\\s","") %>"> <a href="#"><%=course.name%></a></li>
        <%}%>
    </ul>


    <div id="announcements">
        <h1>Announcements</h1>
        <% for(Announcement ancmt : announcements){ %>
            <div class="announcement <%=ancmt.course_number.replaceAll("\\s","")%> <%= ancmt.time_read == null ? "unread" : "read" %>" id= "<%= ancmt.anncmnt_id %>">
                <em><%=ancmt.time_posted%></em>
                <p><%= ancmt.message %></p>
                <p><%= ancmt.author %></p>
            </div>
        <%}%>
    </div>

    <div id="Calendar">
        <h1>Events</h1>
        <%
            List<Message> messages;

            for(Event event : events){
                messages = rdr.getMessagesForEvent(event.event_id);


        %>

            <div class="event <%=event.course_number.replaceAll("\\s","")%> cal<%=event.calendar_id%>" id="<%=event.event_id%>">
                <h3><%=event.title%></h3>
                <em><%=event.start%> - <%=event.end%></em>
                <p><%=event.description%></p>
                <div class="message-container">
                    <%for(Message msg : messages){%>
                    <div class="message">
                        <%=msg.message%>
                    </div>

                    <%}%>
                </div>
            </div>


        <%}%>
    </div>







    </body>

    <!-- scripts here -->
    <script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="/scripts/nav.js"></script>

</html>