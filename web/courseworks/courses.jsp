<%@ page import="courseworks.model.*" %>
<%@ page import="courseworks.sql.ICourseworksReader" %>
<%@ page import="courseworks.sql.CourseworksReader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String uni = request.getParameter("prof_uni");

    Professor current_prof = null;

    ICourseworksReader repo = new CourseworksReader();

    if (uni != null) {
        for (Professor prof : repo.getProfessors()) {
            if (prof.uni.equalsIgnoreCase(uni)) {
                current_prof = prof;
            }
        }
    }

    // if no professor, redirect to homepage
    if (current_prof == null) {
        response.sendRedirect("/courseworks/index.jsp");
    }
%>


<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>