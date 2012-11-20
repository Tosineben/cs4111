package courseworks.profcourses;

import courseworks.RequestType;
import courseworks.SessionKeys;
import courseworks.model.Calendar;
import courseworks.model.Professor;
import courseworks.sql.CourseworksWriter;
import courseworks.sql.ICourseworksWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CalendarServlet")
public class CalendarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Professor prof = (Professor)request.getSession().getAttribute(SessionKeys.logged_in_prof);

        if (prof == null) {
            return;
        }

        RequestType.ReqType type = RequestType.getRequestType(request.getParameter("type"));

        final String r_calendar_id = request.getParameter("calendar_id");
        final String r_course_id = request.getParameter("course_id");
        final String r_name = request.getParameter("name");

        ICourseworksWriter wtr = new CourseworksWriter();

        boolean succeeded = false;

        if (type == RequestType.ReqType.Add) {
            succeeded = wtr.createCalendar(Integer.parseInt(r_course_id),
                    new Calendar() {{
                        name = r_name;
                    }}, prof.uni) > 0;
        }
        else if (type == RequestType.ReqType.Update) {
            succeeded = wtr.updateCalendar(new Calendar() {{
                calendar_id = Integer.parseInt(r_calendar_id);
                name = r_name;
            }}, prof.uni);
        }
        else if (type == RequestType.ReqType.Delete) {
            succeeded = wtr.deleteCalendar(Integer.parseInt(r_calendar_id), prof.uni);
        }

        if (!succeeded){
            response.sendError(500);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
