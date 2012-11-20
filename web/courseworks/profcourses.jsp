<%@ page import="courseworks.SessionKeys" %>
<%@ page import="courseworks.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="courseworks.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Professor prof = (Professor)session.getAttribute(SessionKeys.logged_in_prof);

    if (prof == null) {
        if (session.getAttribute(SessionKeys.logged_in_student) != null) {
            response.sendRedirect("/courseworks/coursepage.jsp");
        }
        else {
            response.sendRedirect("/courseworks");
        }
        return;
    }

    ICourseworksReader rdr = new CourseworksReader();

    List<Course> currentCourses = prof.getCourses();

    String deleteDisabled = null;
    String deleteTitle = null;

    if (currentCourses.size() > 0) {
        deleteDisabled = "disabled";
        deleteTitle = "You cannot delete your account because you have active courses.";
    }
%>

<html>
<head>
    <title>Professor Courses</title>
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap-responsive.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/courseworks.css"/>
    <style>

    </style>
</head>
<body>
    <jsp:include page="nav.jsp">
        <jsp:param name="calendar" value="active" />
        <jsp:param name="deleteDisabled" value="<%=deleteDisabled%>" />
        <jsp:param name="deleteTitle" value="<%=deleteTitle%>" />
    </jsp:include>
    <div class="container-fluid">
        <h1>Course Management</h1>
        <hr>
        <div class="row-fluid">
            <h3>Your Courses</h3>
        </div>
        <hr>
    </div>
</body>
<!-- scripts here -->
<script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="/scripts/navigation.js"></script>
<script type="text/javascript" src="/scripts/moment.js"></script>
<script type="text/javascript">
    (function(){

        $(function(){

        });

        // dates must be in format yyyy/MM/dd HH:mm:ss

        function addAnnouncment(course_id, message) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/anncmnt',
                data: {
                    type: 'add',
                    course_id: course_id,
                    message: message
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function deleteAnnouncement(anncmnt_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/anncmnt',
                data: {
                    type: 'delete',
                    anncmnt_id: anncmnt_id
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function updateAnnouncement(anncmnt_id, message) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/anncmnt',
                data: {
                    type: 'update',
                    anncmnt_id: anncmnt_id,
                    message: message
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function addEvent(calendar_id, title, start, end, description, location) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/event',
                data: {
                    type: 'add',
                    calendar_id: calendar_id,
                    title: title,
                    start: start,
                    end: end,
                    description: description,
                    location: location
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function deleteEvent(event_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/event',
                data: {
                    type: 'delete',
                    event_id: event_id
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function updateEvent(event_id, title, start, end, description, location) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/event',
                data: {
                    type: 'update',
                    event_id: event_id,
                    title: title,
                    start: start,
                    end: end,
                    description: description,
                    location: location
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function addCalendar(course_id, name) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/calendar',
                data: {
                    type: 'add',
                    course_id: course_id,
                    name: name
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function deleteCalendar(calendar_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/calendar',
                data: {
                    type: 'delete',
                    calendar_id: calendar_id
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function updateCalendar(calendar_id, name) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/calendar',
                data: {
                    type: 'update',
                    name: name
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

    })();
</script>
</html>