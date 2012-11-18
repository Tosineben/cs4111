<%@ page import="courseworks.model.Student" %>
<%@ page import="courseworks.SessionKeys" %>
<%@ page import="courseworks.sql.ICourseworksReader" %>
<%@ page import="courseworks.sql.CourseworksReader" %>
<%@ page import="courseworks.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Student student = (Student)session.getAttribute(SessionKeys.logged_in_student);

    if (student == null) {
        response.sendRedirect("/courseworks/index.jsp");
        return;
    }

    ICourseworksReader rdr = new CourseworksReader();

    List<Course> courses = rdr.getCourses();
    HashSet<Integer> enrollend_course_ids = new HashSet<Integer>();

    for (Course c : rdr.getCoursesForStudent(student.uni)) {
        enrollend_course_ids.add(c.course_id);
    }

%>

<html>
<head>
    <title>Enroll in a Course</title>
    <!-- styles here -->
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap-responsive.min.css" />
    <style>

        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

    </style>
</head>
<body>
    <h2>Courses</h2>
    <table>
        <tr>
            <td>Course Number</td>
            <td>Name</td>
            <td>Professor</td>
            <td>Location</td>
            <td>Description</td>
        </tr>
        <tr>
            <td><b>----------</b></td>
            <td><b>----------</b></td>
            <td><b>----------</b></td>
            <td><b>----------</b></td>
            <td><b>----------</b></td>
        </tr>
        <%
            for (Course course: courses) {
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
</body>
<!-- scripts here -->
<script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
<script type="text/javascript">
    (function(){

        $(function(){

        });

    });
</script>
</html>