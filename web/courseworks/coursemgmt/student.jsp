<%@ page import="courseworks.SessionKeys" %>
<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Student student = (Student)session.getAttribute(SessionKeys.logged_in_student);

    if (student == null) {
        if (session.getAttribute(SessionKeys.logged_in_prof) != null) {
            response.sendRedirect("/courseworks/coursemgmt/prof.jsp");
        }
        else {
            response.sendRedirect("/courseworks");
        }
        return;
    }

    ICourseworksReader rdr = new CourseworksReader();

    List<Course> notEnrolled = new ArrayList<Course>();
    List<Course> enrolled = student.getCourses();

    HashSet<Integer> enrollend_course_ids = new HashSet<Integer>();
    for (Course c : enrolled) {
        enrollend_course_ids.add(c.course_id);
    }
    for (Course c : rdr.getCourses()) {
        if (!enrollend_course_ids.contains(c.course_id)) {
            notEnrolled.add(c);
        }
    }
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
    <jsp:include page="../nav.jsp">
        <jsp:param name="courseMgmt" value="active" />
    </jsp:include>
    <div class="container-fluid">
        <h1>Course Management</h1>
        <hr>
        <div class="row-fluid">
            <h3>Enrolled Courses</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th>Course</th>
                        <th>Name</th>
                        <th>Professor</th>
                        <th>More Info</th>
                        <th>Enroll</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Course c: enrolled) {
                %>
                    <tr>
                        <td><%=c.course_number%></td>
                        <td><%=c.name%></td>
                        <td><%=c.professor.name%> (<%=c.professor.uni%>)</td>
                        <td><a href="#model-<%=c.course_id%>" data-toggle="modal">More Info</a></td>
                        <td><a href="#" onclick="return false;" class="remove-course" data-course="<%=c.course_id%>">Un-enroll</a></td>
                    </tr>

                    <div class="modal hide fade" id="model-<%=c.course_id%>">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h3><%=c.course_number%></h3>
                        </div>
                        <div class="modal-body">
                            <p><b>Professor: </b><%=c.professor.name%> (<%=c.professor.uni%>)</p>
                            <p><b>Location: </b><%=c.location%></p>
                            <p><b>Description: </b><%=c.description%></p>
                        </div>
                    </div>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <hr>
        <div class="row-fluid">
            <h3>Other Courses</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th>Course</th>
                        <th>Name</th>
                        <th>Professor</th>
                        <th>More Info</th>
                        <th>Enroll</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Course c: notEnrolled) {
                %>
                    <tr>
                        <td><%=c.course_number%></td>
                        <td><%=c.name%></td>
                        <td><%=c.professor.name%> (<%=c.professor.uni%>)</td>
                        <td><a href="#model-<%=c.course_id%>" data-toggle="modal">More Info</a></td>
                        <td><a href="#" onclick="return false;" class="add-course" data-course="<%=c.course_id%>">Enroll</a></td>
                    </tr>

                    <div class="modal hide fade" id="model-<%=c.course_id%>">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h3><%=c.course_number%></h3>
                        </div>
                        <div class="modal-body">
                            <p><b>Professor: </b><%=c.professor.name%> (<%=c.professor.uni%>)</p>
                            <p><b>Location: </b><%=c.location%></p>
                            <p><b>Description: </b><%=c.description%></p>
                        </div>
                    </div>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</body>
<!-- scripts here -->
<script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="/scripts/nav.js"></script>
<script type="text/javascript">
    (function(){

        $(function(){

            $('.add-course').click(function(){
                var course_id = $(this).data('course');
                enrollInCourse(course_id);
            });

            $('.remove-course').click(function(){
                var course_id = $(this).data('course');
                unEnrollFromCourse(course_id);
            });

        });

        function unEnrollFromCourse(course_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/coursemgmt/student',
                data: {
                    course_id: course_id,
                    type: 'remove'
                },
                success: function(resp){
                    window.location = resp;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function enrollInCourse(course_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/coursemgmt/student',
                data: {
                    course_id: course_id,
                    type: 'add'
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