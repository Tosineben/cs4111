<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>

<%
    ICourseworksWriter wrt = new CourseworksWriter();

%>

<html>
<head>
    <title>Sample</title>
    <!-- styles here -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.servletPath}/styles/bootstrap/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.servletPath}/styles/bootstrap/bootstrap-responsive.min.css" />
</head>
<body>

</body>
<!-- scripts here -->
<script type="text/javascript" src="${pageContext.request.servletPath}/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="${pageContext.request.servletPath}/scripts/bootstrap.js"></script>
</html>