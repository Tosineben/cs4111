package courseworks.sql;

public final class ReaderQueries {

    public static final String GET_PROFESSORS =
            "select p.uni, p.name " +
            "from Professors p";

    public static final String GET_STUDENTS =
            "select s.uni, s.name " +
            "from Students s ";

    public static final String GET_STUDENTS_FOR_COURSE =
            GET_STUDENTS +
            "inner join Enrollment e on e.uni = s.uni " +
            "where e.course_id = :course_id";

    public static final String GET_COURSES =
            "select c.course_id, c.courseNumber, c.name, c.location, c.description, c.uni, p.name as prof_name " +
            "from Courses c " +
            "inner join Professors p on p.uni = c.uni ";

    public static final String GET_COURSES_FOR_PROF =
            GET_COURSES +
            "where c.uni = :uni";

    public static final String GET_COURSES_FOR_STUDENT =
            GET_COURSES +
            "inner join Enrollment e on e.course_id = c.course_id " +
            "where e.uni = :uni";


}
