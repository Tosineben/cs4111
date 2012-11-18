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


<html>
    <head>
        <title></title>
    </head>


    <body>

    <ul id="course_list">
        <% for(Course course : courses){ %>
            <li class= "<%= course.course_number.replaceAll("\\s","") %>"><%=course.name%></li>
        <%}%>
    </ul>


    <div id="announcements">
        <h1>Announcements</h1>
        <% for(Announcement ancmt : announcements){ %>
            <div class="<%=ancmt.course_number.replaceAll("\\s","")%> <%= ancmt.time_read == null ? "unread" : "read" %>" id= "<%= ancmt.anncmnt_id %>">
                <h3><%=ancmt.time_posted%></h3>
                <p><%= ancmt.message %></p>
                <p><%= ancmt.author %></p>
            </div>
        <%}%>
    </div>

    <div id="Calendar">
        <h1>Events</h1>
        <% for(Event event : events){%>
            <div class="<%=event.course_number.replaceAll("\\s","")%> cal<%=event.calendar_id%>" id="<%=event.event_id%>">
                <h2><%=event.title%></h2>
                <h4><%=event.start%> - <%=event.end%></h4>
                <p><%=event.description%></p>
            </div>


        <%}%>
    </div>







    </body>
</html>