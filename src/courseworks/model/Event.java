package courseworks.model;

import courseworks.sql.*;

import java.util.*;

public class Event {

    public int event_id;
    public String title;
    public Date start;
    public Date end;
    public String description;
    public String location;
    public int course_id;  // TODO: this should not be on here
    public int calendar_id;       // TODO: same with this...

    public List<Message> getMessages() {
        ICourseworksReader rdr = new CourseworksReader();
        return rdr.getMessagesForEvent(event_id);
    }

    public Map<String, Document> getDocumentsByPath() {
        ICourseworksReader rdr = new CourseworksReader();
        return rdr.getDocumentsForEvent(event_id);
    }

    public int addMessage(Message msg) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.createMessage(event_id, msg);
    }

    public int addDocument(Document doc, String prof_uni) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.createDocument(event_id, doc, prof_uni);
    }
}


