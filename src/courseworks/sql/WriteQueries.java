package courseworks.sql;

public final class WriteQueries {

    public static final String INSERT_PROFESSOR =
            "insert into Professors (uni, name) " +
            "values :uni, :name";

    public static final String INSERT_STUDENT =
            "insert into Students (uni, name) " +
            "values :uni, :name";

}
