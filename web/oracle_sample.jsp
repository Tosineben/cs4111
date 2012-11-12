<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- import for JDBC -->
<%@ page import="java.sql.*"%>
<%@ page import="oracle.jdbc.pool.OracleDataSource"%>

<!-- database lookup -->
<%
    Connection conn = null;
    ResultSet rset = null;
    String error_msg = "";
    String username = "adq2101";
    String password = "passwordhere";
    String oracle_url = "jdbc:oracle:thin:" + username + "/" + password + "@//w4111b.cs.columbia.edu:1521/ADB";
    String query = "select uni, name from Professors";
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
                                  "<td>" + rset.getString("name") + "</td>");
                        out.print("</tr>");
                    }
                } catch (SQLException e) {
                    out.print(e.getMessage());
                }
            } else {
                out.print(error_msg);
            }

            if (conn != null) {
                conn.close();
            }
        %>
    </table>
</body>
</html>