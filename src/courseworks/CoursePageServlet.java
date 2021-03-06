package courseworks;

import courseworks.model.Announcement;
import courseworks.model.Message;
import courseworks.model.Student;
import courseworks.sql.CourseworksWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CoursePageServlet")
public class CoursePageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter pw = response.getWriter();
        boolean success = false;

        Student student = (Student)request.getSession().getAttribute(SessionKeys.logged_in_student);
        String type = request.getParameter("type");

        if(student != null){
            if("read".equals(type)){
                Announcement ancmt = new Announcement();
                ancmt.anncmnt_id = Integer.parseInt(request.getParameter("announcement_id"));
                success = ancmt.updateAnncmntRead(student.uni, true);
                if(success){
                    pw.print("worked");
                }
                else {
                    response.sendError(500);
                }

            }


        }


        pw.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
