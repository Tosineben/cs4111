<%@ page import="Sample.HelloWorld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Sample</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css" />
  </head>
  <body>
    <h1>Bro</h1>
    <h3 class="message">
        <%=HelloWorld.getMessage()%>
    </h3>
  </body>
</html>