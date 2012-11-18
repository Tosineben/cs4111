package courseworks.model.comperators;

import courseworks.model.Announcement;

import java.util.Comparator;

public class AnnouncementComperator implements Comparator<Announcement> {
    public int compare(Announcement a1, Announcement a2) {
        return a1.time_posted.compareTo(a2.time_posted);
    }
}