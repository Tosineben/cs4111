<%@ page import="courseworks.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String student_uni = request.getParameter("student_uni");
    String prof_uni = request.getParameter("prof_uni");

    // if no student or professor, redirect to homepage
    if (student_uni == null && prof_uni == null) {
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