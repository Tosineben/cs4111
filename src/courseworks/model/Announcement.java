package courseworks.model;

import courseworks.sql.*;

import java.util.Date;
import java.util.List;

public class Announcement {

    public int anncmnt_id;
    public String message;
    public Date time_posted;
    public int course_id;          // TODO: this does not identify anything
    public String author;         // TODO: this is professor name, not uni
    public Date time_read;        // TODO: no way to know what student is this for

    public boolean updateAnncmntRead(String student_uni, boolean hasRead) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.updateAnncmntRead(anncmnt_id, student_uni, hasRead);
    }

}
