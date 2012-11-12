package courseworks;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import oracle.jdbc.pool.OracleDataSource;

@WebServlet(name = "OracleServlet")
public class OracleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String conn_string;
    private Connection conn;

    public OracleServlet() {
        super();
        String username = "adq2101";
        String password = "sqlserverftw";
        conn_string = "jdbc:oracle:thin:" + username + "/" + password + "@//w4111b.cs.columbia.edu:1521/ADB";
    }

    public OracleServlet(String username, String password) {
        super();
        conn_string = "jdbc:oracle:thin:" + username + "/" + password + "@//w4111b.cs.columbia.edu:1521/ADB";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // no op
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = new PrintWriter(response.getOutputStream());
        Map<String, String[]> paramMap = request.getParameterMap();
        try {
            if (conn == null) {
                // Create a OracleDataSource instance and set URL
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(conn_string);
                conn = ods.getConnection();
            }
            String query = "select uni, name from Students";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            response.setContentType("text/html");
            pw.println("<html>");
            pw.println("<head><title>Employee Table Servlet</title></head>");
            pw.println("<H1>Show EMP Table Data <BR></H1>");
            pw.println("<body><BR>");
            while (rset.next()) {
                pw.println (rset.getString("uni") + "<BR>");
            }
            pw.println("</body></html>");
        } catch (SQLException e) {
            pw.println(e.getMessage());
        }

        pw.close();
    }
}
