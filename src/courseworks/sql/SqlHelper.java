package courseworks.sql;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;

public class SqlHelper implements ISqlHelper {

    private String _connString;

    public SqlHelper() {
        String username = "adq2101";
        String password = "sqlserverftw";
        String dbServer = "w4111b.cs.columbia.edu:1521/ADB";
        _connString = "jdbc:oracle:thin:" + username + "/" + password + "@//" + dbServer;
    }

    @Override
    public Connection getConnection() throws SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setURL(_connString);
        return ods.getConnection();
    }

    @Override
    public ResultSet executeQuery(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    @Override
    public void tryClose(ResultSet rset, Connection conn) {
        if (rset != null) {
            try {
                rset.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}