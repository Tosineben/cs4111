package courseworks.profcourses;

import courseworks.RequestType;
import courseworks.SessionKeys;
import courseworks.model.Document;
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
import java.util.Date;

@WebServlet(name = "DocumentServlet")
public class DocumentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Professor prof = (Professor)request.getSession().getAttribute(SessionKeys.logged_in_prof);

        if (prof == null) {
            return;
        }

        RequestType.ReqType type = RequestType.getRequestType(request.getParameter("type"));

        final String r_document_id = request.getParameter("document_id");
        final String r_event_id = request.getParameter("event_id");
        final String r_file_path = request.getParameter("file_path");

        ICourseworksWriter wtr = new CourseworksWriter();

        boolean succeeded = false;

        if (type == RequestType.ReqType.Delete) {
            succeeded = wtr.deleteDocument(Integer.parseInt(r_document_id), prof.uni);
        }
        else if (type == RequestType.ReqType.Add) {
            succeeded = wtr.createDocument(Integer.parseInt(r_event_id),
                    new Document() {{
                        file_path = r_file_path;
                    }}, prof.uni) > 0;
        }

        if (!succeeded){
            response.sendError(500);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
