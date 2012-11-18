package courseworks.model.comperators;

import java.util.Comparator;
import courseworks.model.Event;

public class EventComperator implements Comparator<Event> {
    public int compare(Event e1, Event e2) {
        return e1.start.compareTo(e2.start);
    }
}