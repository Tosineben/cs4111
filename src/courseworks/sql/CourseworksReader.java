package courseworks.sql;

import courseworks.model.*;
import java.sql.*;
import java.util.*;

public class CourseworksReader implements ICourseworksReader {

    private ISqlHelper _helper;

    public CourseworksReader() {
        _helper = new SqlHelper();
    }

    @Override
    public List<Professor> getProfessors() {
        List<Professor> profs = new ArrayList<Professor>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, ReadQueries.GET_PROFESSORS);

            while (rset.next()) {
                Professor prof = new Professor();
                prof.uni = rset.getString("uni");
                prof.name = rset.getString("name");
                profs.add(prof);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return profs;
    }

    @Override
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<Student>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, ReadQueries.GET_STUDENTS);

            while (rset.next()) {
                Student student = new Student();
                student.uni = rset.getString("uni");
                student.name = rset.getString("name");
                students.add(student);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return students;
    }

    @Override
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<Course>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, ReadQueries.GET_COURSES);
            courses = parseCourses(rset);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return courses;
    }

    @Override
    public List<Course> getCoursesForProfessor(String uni) {
        List<Course> courses = new ArrayList<Course>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            rset = _helper.executeQuery(conn, ReadQueries.GET_COURSES_FOR_PROF);
            courses = parseCourses(rset);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return courses;
    }

    @Override
    public List<Course> getCoursesForStudent(String uni) {
        List<Course> courses = new ArrayList<Course>();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = _helper.getConnection();
            CallableStatement stmt = conn.prepareCall(ReadQueries.GET_COURSES_FOR_STUDENT);
            stmt.setString("uni", uni);
            rset = stmt.executeQuery();
            courses = parseCourses(rset);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            _helper.tryClose(rset, conn);
        }

        return courses;
    }

    private List<Course> parseCourses(ResultSet rset) throws SQLException {
        List<Course> courses = new ArrayList<Course>();
        while (rset.next()) {
            Course course = new Course();
            course.course_id = rset.getInt("course_id");
            course.course_number = rset.getString("courseNumber");
            course.description = rset.getString("description");
            course.location = rset.getString("location");
            course.name = rset.getString("name");
            course.professor = new Professor();
            course.professor.uni = rset.getString("uni");
            course.professor.name = rset.getString("prof_name");
            courses.add(course);
        }
        return courses;
    }

}
