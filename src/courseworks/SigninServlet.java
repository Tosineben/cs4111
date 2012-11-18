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

        String uni = request.getParameter("uni");
        String type = request.getParameter("type");
        String name = request.getParameter("name");

        ICourseworksWriter writer = new CourseworksWriter();

        if ("Professor".equals(type)) {
            Professor p = new Professor();
            p.uni = uni;
            p.name = name;

            if (writer.createProfessor(p)) {
                pw.print("/courseworks/courses.jsp?prof_uni=" + uni);
                pw.close();
                return;
            }
        }
        else if ("Student".equals(type)) {
            Student s = new Student();
            s.uni = uni;
            s.name = name;

            if (writer.createStudent(s)) {
                pw.print("/courseworks/coursepage.jsp?student_uni=" + uni);
                pw.close();
                return;
            }
        }

        // something failed
        response.sendError(500);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uni = request.getParameter("uni");
        String type = request.getParameter("type");

        PrintWriter pw = new PrintWriter(response.getOutputStream());

        if ("Professor".equals(type)) {
            pw.print("/courseworks/courses.jsp?prof_uni=" + uni);
        }
        else if ("Student".equals(type)) {
            pw.print("/courseworks/coursepage.jsp?student_uni=" + uni);
        }
        else {
            pw.print("/courseworks/index.jsp");
        }

        pw.close();
    }

}
