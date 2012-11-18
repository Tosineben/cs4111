<%@ page import="courseworks.SessionKeys" %>
<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Professor prof = (Professor)session.getAttribute(SessionKeys.logged_in_prof);

    if (prof == null) {
        response.sendRedirect("/courseworks/index.jsp");
        return;
    }

    List<Course> currentCourses = prof.getCourses();
%>

<html>
<head>
    <title>Course Management</title>
    <!-- styles here -->
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap-responsive.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/courseworks.css" />
    <style>

    </style>
</head>
<body>
    <div class="container-fluid">
        <h1>Course Management</h1>
        <hr>
        <div class="row-fluid">
            <h3>Your Courses</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th>Course</th>
                        <th>Name</th>
                        <th>Location</th>
                        <th>Enrolled Students</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Course c: currentCourses) {
                %>
                    <tr>
                        <td><%=c.course_number%></td>
                        <td><%=c.name%></td>
                        <td><%=c.location%></td>
                        <td><%=c.getStudents().size()%></td>
                        <td><a href="#" onclick="return false;" class="remove-course" data-course="<%=c.course_id%>">Delete</a></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <hr>
        <div class="row-fluid">
            <h3>New Course</h3>
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="newcourse-number">Number</label>
                    <div class="controls">
                        <input class="input-xlarge" type="text" id="newcourse-number" placeholder="COMS 4111">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="newcourse-name">Name</label>
                    <div class="controls">
                        <input class="input-xlarge" type="text" id="newcourse-name" placeholder="Intro to Databases">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="newcourse-location">Location</label>
                    <div class="controls">
                        <input class="input-xlarge" type="text" id="newcourse-location" placeholder="410 Mudd">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="newcourse-desc">Description</label>
                    <div class="controls">
                        <textarea id="newcourse-desc" class="input-xlarge" rows="5" placeholder="Introduction to all things databases, from relational algebra to query optimization. Focuses on relational databases, specificall oracle."></textarea>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" id="newcourse-submit" onclick="return false;" class="btn btn-primary">Create Course</button>
                </div>
            </form>
        </div>
    </div>
</body>
<!-- scripts here -->
<script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
<script type="text/javascript">
    (function(){

        $(function(){

            $('#newcourse-submit').click(function(){
                var course_number = $('#newcourse-number').val();
                var course_name = $('#newcourse-name').val();
                var location = $('#newcourse-location').val();
                var description = $('#newcourse-desc').val();
                addCourse(course_number, course_name, location, description);
            });

            $('.remove-course').click(function(){
                var course_id = $(this).data('course');
                removeCourse(course_id);
            });

        });

        function removeCourse(course_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/coursemgmt/professor',
                data: {
                    type: 'remove',
                    course_id: course_id
                },
                success: function(resp){
                    window.location = resp;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function addCourse(course_number, course_name, location, description) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/coursemgmt/professor',
                data: {
                    type: 'add',
                    course_number: course_number,
                    course_name: course_name,
                    location: location,
                    description: description
                },
                success: function(resp){
                    window.location = resp;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

    })();
</script>
</html>