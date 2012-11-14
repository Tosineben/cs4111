package courseworks.sql;

import courseworks.model.*;

public class CourseworksWriter implements ICourseworksWriter {

    private ISqlHelper _helper;

    public CourseworksWriter() {
        _helper = new SqlHelper();
    }

    @Override
    public boolean createProfessor(Professor prof) {
        return false;
    }

    @Override
    public boolean updateProfessor(Professor prof) {
        return false;
    }

    @Override
    public boolean createStudent(Student student) {
        return false;
    }

    @Override
    public boolean updateStudent(Student student) {
        return false;
    }

}
