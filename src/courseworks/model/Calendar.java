package courseworks.model;

import java.util.ArrayList;
import java.util.List;

public class Calendar {

    public int calendar_id;
    public String name;

    public List<Event> getEvents() {
        return new ArrayList<Event>();
    }

}

