package courseworks.profcourses;

import courseworks.RequestType;
import courseworks.SessionKeys;
import courseworks.model.Event;
import courseworks.model.Professor;
import courseworks.sql.CourseworksWriter;
import courseworks.sql.ICourseworksWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "EventServlet")
public class EventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Professor prof = (Professor)request.getSession().getAttribute(SessionKeys.logged_in_prof);

        if (prof == null) {
            return;
        }

        RequestType.ReqType type = RequestType.getRequestType(request.getParameter("type"));

        final String r_calendar_id = request.getParameter("calendar_id");
        final String r_event_id = request.getParameter("event_id");
        final String r_title = request.getParameter("title");
        final String r_start = request.getParameter("start");
        final String r_end = request.getParameter("end");
        final String r_description = request.getParameter("description");
        final String r_location = request.getParameter("location");

        ICourseworksWriter wtr = new CourseworksWriter();

        boolean succeeded = false;

        if (type == RequestType.ReqType.Delete) {
            succeeded = wtr.deleteEvent(Integer.parseInt(r_event_id), prof.uni);
        }
        else {
            final Date startd = parseDate(r_start);
            final Date endd = parseDate(r_end);

            if (startd.getTime() != myDate.getTime() && endd.getTime() != myDate.getTime()) {
                if (type == RequestType.ReqType.Add) {
                    succeeded = wtr.createEvent(Integer.parseInt(r_calendar_id),
                            new Event() {{
                                title = r_title;
                                description = r_description;
                                location = r_location;
                                start = startd;
                                end = endd;
                            }}, prof.uni) > 0;
                }
                else if (type == RequestType.ReqType.Update) {
                    succeeded = wtr.updateEvent(new Event() {{
                        event_id = Integer.parseInt(r_event_id);
                        title = r_title;
                        description = r_description;
                        location = r_location;
                        start = startd;
                        end = endd;
                    }}, prof.uni);
                }
            }
        }

        if (!succeeded){
            response.sendError(500);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private Date myDate = new Date();

    private Date parseDate(String d) {
        try {
            return new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }
}
