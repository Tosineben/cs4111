package courseworks;

import courseworks.model.Professor;
import courseworks.model.Student;
import courseworks.sql.CourseworksWriter;
import courseworks.sql.ICourseworksWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "SigninServlet")
public class SigninServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter pw = new PrintWriter(response.getOutputStream());

        final String req_uni = request.getParameter("uni");
        final String req_type = request.getParameter("type");
        final String req_name = request.getParameter("name");

        ICourseworksWriter writer = new CourseworksWriter();

        boolean succeeded = false;

        clearLoggedInSession(request);

        if ("professor".equals(req_type)) {
            Professor p = new Professor(){{name=req_name; uni=req_uni;}};

            succeeded = writer.createProfessor(p);

            if (succeeded) {
                pw.print("/courseworks/profcourses.jsp");
                request.getSession().setAttribute(SessionKeys.logged_in_prof, p);
            }
        }
        else if ("student".equals(req_type)) {
            Student s = new Student(){{name=req_name; uni=req_uni;}};

            succeeded = writer.createStudent(s);

            if (succeeded) {
                pw.print("/courseworks/coursepage.jsp");
                request.getSession().setAttribute(SessionKeys.logged_in_student, s);
            }
        }

        if (!succeeded){
            response.sendError(500);
        }

        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String req_uni = request.getParameter("uni");
        final String req_name = request.getParameter("name");
        final String req_type = request.getParameter("type");

        PrintWriter pw = new PrintWriter(response.getOutputStream());

        clearLoggedInSession(request);

        if ("professor".equals(req_type)) {
            pw.print("/courseworks/profcourses.jsp");
            request.getSession().setAttribute(SessionKeys.logged_in_prof, new Professor(){{name=req_name; uni=req_uni;}});
        }
        else if ("student".equals(req_type)) {
            pw.print("/courseworks/coursepage.jsp");
            request.getSession().setAttribute(SessionKeys.logged_in_student, new Student(){{name=req_name; uni=req_uni;}});
        }
        else {
            pw.print("/courseworks");
        }

        pw.close();
    }

    private void clearLoggedInSession(HttpServletRequest request) {
        request.getSession().setAttribute(SessionKeys.logged_in_student, null);
        request.getSession().setAttribute(SessionKeys.logged_in_prof, null);
    }

}
