package courseworks.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Announcement {

    public int anncmnt_id;
    public String message;
    public Date time_posted;

    public List<ReadAnnouncment> getStudentsRead() {
        return new ArrayList<ReadAnnouncment>();
    }

    public class ReadAnnouncment {
        public Student student;
        public Date time_read;
    }
}
