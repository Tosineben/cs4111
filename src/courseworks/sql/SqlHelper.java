package courseworks.sql;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;

public class SqlHelper implements ISqlHelper {

    public Connection getConnection(String connString) throws SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setURL(connString);
        return ods.getConnection();
    }

    public ResultSet executeQuery(String connString, String query) throws SQLException {
        Connection conn = getConnection(connString);
        return executeQuery(conn, query);
    }

    public ResultSet executeQuery(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

}
