<%@ page import="courseworks.model.*" %>
<%@ page import="courseworks.sql.ICourseworksReader" %>
<%@ page import="courseworks.sql.CourseworksReader" %>
<%@ page import="courseworks.SessionKeys" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Professor prof = (Professor)session.getAttribute(SessionKeys.logged_in_prof);

    if (prof == null) {
        response.sendRedirect("/courseworks/index.jsp");
        return;
    }

    ICourseworksReader rdr = new CourseworksReader();

    List<Course> courses = rdr.getCoursesForProfessor(prof.uni);
%>


<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>