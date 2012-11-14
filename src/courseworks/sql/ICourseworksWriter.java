package courseworks.sql;

import courseworks.model.*;

public interface ICourseworksWriter {

    boolean createProfessor(Professor prof);

    boolean updateProfessor(Professor prof);

    boolean createStudent(Student student);

    boolean updateStudent(Student student);

}
