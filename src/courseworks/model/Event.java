package courseworks.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Event {
    public int event_id;
    public String title;
    public Date start;
    public Date end;
    public String description;
    public String location;

    public List<Message> messages;
    public Map<String, Document> docs_by_path;
}

