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

@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter pw = new PrintWriter(response.getOutputStream());

        final String req_type = request.getParameter("type");
        final String req_name = request.getParameter("name");

        ICourseworksWriter writer = new CourseworksWriter();

        boolean succeeded = false;

        Student s = (Student)request.getSession().getAttribute(SessionKeys.logged_in_student);
        Professor p = (Professor)request.getSession().getAttribute(SessionKeys.logged_in_prof);

        String oldName = p == null ? (s == null ? null : s.name) : p.name;

        if ("edit".equals(req_type)) {
            if (p != null) {
                p.name = req_name;
                succeeded = writer.editProfessor(p);
                if (!succeeded) {
                    p.name = oldName;
                }
            }
            else if (s != null) {
                s.name = req_name;
                succeeded = writer.editStudent(s);
                if (!succeeded) {
                    s.name = oldName;
                }
            }
        }
        else if ("delete".equals(req_type)) {
            if (p != null) {
                succeeded = writer.deleteProfessor(p.uni);
            }
            else if (s != null) {
                succeeded = writer.deleteStudent(s.uni);
            }
            if (succeeded) {
                request.getSession().setAttribute(SessionKeys.logged_in_student, null);
                request.getSession().setAttribute(SessionKeys.logged_in_prof, null);
                pw.print("/courseworks");
            }
        }

        if (!succeeded) {
            response.sendError(500);
        }

        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
