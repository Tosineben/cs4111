package courseworks;

import courseworks.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CourseManagementServlet")
public class CourseManagementServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter pw = new PrintWriter(response.getOutputStream());
        boolean success = false;

        // get params
        int course_id = Integer.parseInt(request.getParameter("course_id"));
        String addRemove = request.getParameter("type");

        // get stuff from session
        Student student = (Student)request.getSession().getAttribute(SessionKeys.logged_in_student);

        if (student != null) {
            if ("add".equals(addRemove)) {
                success = student.enrollInCourse(course_id);
            }
            else if ("remove".equals(addRemove)) {
                success = student.unEnrollFromCourse(course_id);
            }
        }

        if (success) {
            pw.write("/courseworks/enroll.jsp");
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
