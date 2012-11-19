package courseworks.coursemgmt;

import courseworks.SessionKeys;
import courseworks.model.Course;
import courseworks.model.Professor;
import courseworks.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ProfServlet")
public class ProfServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter pw = new PrintWriter(response.getOutputStream());
        boolean success = false;

        // get stuff from session
        final Professor prof = (Professor)request.getSession().getAttribute(SessionKeys.logged_in_prof);

        // get params
        final String type = request.getParameter("type");
        final String id = request.getParameter("course_id");
        final String number = request.getParameter("course_number");
        final String course_name = request.getParameter("course_name");
        final String loc = request.getParameter("location");
        final String desc = request.getParameter("description");

        if (prof != null) {
            if ("add".equals(type)) {
                Course course = new Course(){{
                    course_number = number;
                    name = course_name;
                    location = loc;
                    description = desc;
                }};
                success = prof.addCourse(course) > 0;
            }
            else if ("edit".equals(type)) {
                Course course = new Course(){{
                    course_id = Integer.parseInt(id);
                    course_number = number;
                    name = course_name;
                    location = loc;
                    description = desc;
                    professor = prof;
                }};
                success = prof.updateCourse(course);
            }
            else if ("remove".equals(type)) {
                success = prof.removeCourse(Integer.parseInt(id));
            }
        }

        if (success) {
            pw.write("/courseworks/coursemgmt/prof.jsp");
        }
        else {
            response.sendError(500);
        }

        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // not implemented
    }

}
