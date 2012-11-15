package courseworks.model;

import courseworks.sql.*;

import java.util.Date;
import java.util.List;

public class Announcement {

    public int anncmnt_id;
    public String message;
    public Date time_posted;

    public List<ReadAnnouncment> getStudentsRead() {
        ICourseworksReader rdr = new CourseworksReader();
        return rdr.getStudentsReadForAnnouncment(anncmnt_id);
    }

    public boolean updateAnncmntRead(String student_uni, boolean hasRead) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.updateAnncmntRead(anncmnt_id, student_uni, hasRead);
    }

}
