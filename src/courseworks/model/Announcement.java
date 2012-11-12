package courseworks.model;

import java.util.Date;
import java.util.List;

public class Announcement {
    public int anncmnt_id;
    public String message;
    public Date time_posted;

    public List<ReadAnnouncment> students_read;

    public class ReadAnnouncment {
        public Student student;
        public Date time_read;
    }
}
