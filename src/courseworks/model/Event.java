package courseworks.model;

import java.util.*;

public class Event {

    public int event_id;
    public String title;
    public Date start;
    public Date end;
    public String description;
    public String location;

    public List<Message> getMessages() {
        return new ArrayList<Message>();
    }

    public Map<String, Document> getDocumentsByPath() {
        return new HashMap<String, Document>();
    }

}

