package courseworks.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISqlHelper {
    Connection getConnection(String connString) throws SQLException;

    ResultSet executeQuery(String connString, String query) throws SQLException;
    ResultSet executeQuery(Connection conn, String query) throws SQLException;


}
