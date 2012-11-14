<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Student current_student = null;
    String student_uni = request.getParameter("student_uni");

    ICourseworksReader repo = new CourseworksReader();
    List<Student> students = repo.getStudents();

    if (student_uni != null) {
        for (Student student : students) {
            if (student.uni.equalsIgnoreCase(student_uni)) {
                current_student = student;
            }
        }
    }
%>

<html>
<head>
    <title>Home Page</title>
</head>
<body>

    <h1>All Students (debug table)</h1>
    <table>
        <tr>
            <td>UNI</td>
            <td>NAME</td>
        </tr>
        <tr>
            <td><b>----------</b></td>
            <td><b>----------</b></td>
        </tr>
        <%
            for(Student student : students) {
                out.print("<tr>");
                out.print("<td>" + student.uni + "</td>" +
                        "<td>" + student.name + "</td>");
                out.print("</tr>");
            }
        %>
    </table>

    <%
    if (current_student == null) {
    %>
        <h1>Choose a student</h1>

        <div>
            <ul>
                <%
                for (Student student : students) {
                %>
                    <li>
                        <a href="home.jsp?student_uni=<%=student.uni%>"><%=student.name%></a>
                    </li>
                <%
                }
                %>
            </ul>
        </div>

    <%
    } else {
    %>

        <h1>Courses for <%=current_student.name%></h1>

        <table>
            <tr>
                <td>COURSE ID</td>
                <td>COURSE NUMBER</td>
            </tr>
            <tr>
                <td><b>----------</b></td>
                <td><b>----------</b></td>
            </tr>
            <%
                for(Course course : current_student.getCourses()) {
                    out.print("<tr>");
                    out.print("<td>" + course.course_id + "</td>" +
                            "<td>" + course.course_number + "</td>");
                    out.print("</tr>");
                }
            %>
        </table>

    <%
    }
    %>

</body>
</html>