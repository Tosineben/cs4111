package courseworks.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISqlHelper {

    Connection getConnection() throws SQLException;

    ResultSet executeQuery(Connection conn, String query) throws SQLException;

    void tryClose(ResultSet rset, Connection conn);

}
