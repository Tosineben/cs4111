<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.sql.*"%>
<%@ page import="oracle.jdbc.pool.OracleDataSource"%>

<!-- database lookup -->
<%
    Connection conn = null;
    ResultSet rset = null;
    String error_msg = "";
    String oracle_url = "jdbc:oracle:thin:adq2101/sqlserverftw@//w4111b.cs.columbia.edu:1521/ADB";
    String query = "select uni, course_id from Enrollment";
    try {
        OracleDataSource ods = new OracleDataSource();
        ods.setURL(oracle_url);
        conn = ods.getConnection();
        Statement stmt = conn.createStatement();
        rset = stmt.executeQuery(query);
    } catch (SQLException e) {
        error_msg = e.getMessage();
    }
%>

<html>
<head>
    <title>Employee Table JSP Sample</title>
    <!-- styles -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.servletPath}/styles/courseworks.css" />
</head>
<body>
    <h2>Employee Table</h2>
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
            if (rset != null) {
                try {
                    while (rset.next()) {
                        out.print("<tr>");
                        out.print("<td>" + rset.getString("uni") + "</td>" +
                                  "<td>" + rset.getString("course_id") + "</td>");
                        out.print("</tr>");
                    }
                } catch (SQLException e) {
                    out.print(e.getMessage());
                }
            } else {
                out.print(error_msg);
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>
    </table>
</body>
<!-- scripts -->
<script type="text/javascript" src="${pageContext.request.servletPath}/scripts/courseworks.js"></script>
</html>