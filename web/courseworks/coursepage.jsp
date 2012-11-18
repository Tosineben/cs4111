<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="courseworks.model.comperators.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    String student_uni = request.getParameter("student_uni");

    if (student_uni == null){
        response.sendRedirect("index.jsp");
    }
    ICourseworksReader rdr = new CourseworksReader();
    List<Course> courses = rdr.getCoursesForStudent(student_uni);


    List<Calendar> calendars = new ArrayList<Calendar>();
    List<Event> events = rdr.getEventsForStudent(student_uni);
    List<Announcement> announcements = rdr.getAnnouncementsForStudent(student_uni);

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
            <li id= "<%= course.course_number %>"><%=course.name%></li>
        <%}%>
    </ul>


    <div id="announcements">
        <h1>Announcements</h1>
        <% for(Announcement ancmt : announcements){ %>
            <div id= "<%= ancmt.anncmnt_id %>">
                <h3><%=ancmt.time_posted%></h3>
                <p><%= ancmt.message %></p>
            </div>
        <%}%>
    </div>

    <div id="Calendar">
        <h1>Events</h1>
        <% for(Event event : events){%>
            <div id="<%=event.event_id%>">
                <h2><%=event.title%></h2>
                <h4><%=event.start%> - <%=event.end%></h4>
                <p><%=event.description%></p>
            </div>


        <%}%>
    </div>







    </body>
</html>