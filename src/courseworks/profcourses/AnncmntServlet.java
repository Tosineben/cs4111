package courseworks.profcourses;

import courseworks.RequestType;
import courseworks.SessionKeys;
import courseworks.model.Announcement;
import courseworks.model.Professor;
import courseworks.sql.CourseworksWriter;
import courseworks.sql.ICourseworksWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AnncmntServlet")
public class AnncmntServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Professor prof = (Professor)request.getSession().getAttribute(SessionKeys.logged_in_prof);

        if (prof == null) {
            return;
        }

        RequestType.ReqType type = RequestType.getRequestType(request.getParameter("type"));

        final String r_anncmnt_id = request.getParameter("anncmnt_id");
        final String r_course_id = request.getParameter("course_id");
        final String r_message = request.getParameter("message");

        ICourseworksWriter wtr = new CourseworksWriter();

        boolean succeeded = false;

        if (type == RequestType.ReqType.Add) {
            succeeded = wtr.createAnnouncement(Integer.parseInt(r_course_id),
                    new Announcement() {{
                        message = r_message;
                    }}, prof.uni) > 0;
        }
        else if (type == RequestType.ReqType.Update) {
            succeeded = wtr.updateAnnouncement(new Announcement(){{
                anncmnt_id = Integer.parseInt(r_anncmnt_id);
                message = r_message;
            }}, prof.uni);
        }
        else if (type == RequestType.ReqType.Delete) {
            succeeded = wtr.deleteAnnouncement(Integer.parseInt(r_anncmnt_id), prof.uni);
        }

        if (!succeeded){
            response.sendError(500);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

