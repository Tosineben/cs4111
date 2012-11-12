package courseworks.sql;

import courseworks.model.*;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.*;

public class CourseworksRepository implements ICourseworksRepository {

    private String _connString;
    private ISqlHelper _helper;

    public CourseworksRepository(String connString) {
        _connString = connString;
        _helper = new SqlHelper();
    }

    public List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<Student>();

        ResultSet rset = _helper.executeQuery(_connString, Queries.GET_STUDENTS);

        while (rset.next()) {
            Student student = new Student();
            student.uni = rset.getString("uni");
            student.name = rset.getString("name");

            students.add(student);
        }

        return students;
    }

    private static class Queries {
        public static final String GET_STUDENTS = "select uni, name from Students";

    }
}
