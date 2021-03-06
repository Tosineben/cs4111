<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="courseworks.model.comperators.*" %>
<%@ page import="courseworks.SessionKeys" %>
<%@ page import="java.util.*" %>
<%@ page import="courseworks.model.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Student student = (Student)session.getAttribute(SessionKeys.logged_in_student);

    if (student == null) {
        if (session.getAttribute(SessionKeys.logged_in_prof) != null) {
            response.sendRedirect("/courseworks/profcourses.jsp");
        }
        else {
            response.sendRedirect("/courseworks");
        }
        return;
    }

    ICourseworksReader rdr = new CourseworksReader();
    List<Course> courses = rdr.getCoursesForStudent(student.uni);

    for(Course course : courses){
           course.calendars = rdr.getCalendarListForCourse(course.course_id);
    }

    SimpleDateFormat dateFmt = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    Map<Integer, Map<String, Document>> docsByEvent = rdr.getDocumentsForStudent(student.uni);
    Map<Integer, List<Message>> msgsByEvent = rdr.getMessagesForStudent(student.uni);

    List<Event> events = rdr.getEventsForStudent(student.uni);
    List<Announcement> announcements = rdr.getAnnouncementsForStudent(student.uni);

    Collections.sort(announcements, new AnnouncementComperator());
    Collections.sort(events, new EventComperator());
    Map<Integer, Course> course_id_to_course = new HashMap<Integer, Course>();
    Map<Integer, Calendar> cal_id_to_cal = new HashMap<Integer, Calendar>();
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title></title>
        <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap.min.css" />
        <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap-responsive.min.css" />
        <link type="text/css" rel="stylesheet" href="/styles/courseworks.css"/>
    </head>

    <body>

    <jsp:include page="nav.jsp">
        <jsp:param name="calendar" value="active" />
    </jsp:include>

    <div class="wrapper">

        <div class="row-fluid">

            <% if(!courses.isEmpty()) { %>

            <div class="span3">
                <ul class="nav nav-list well">
                    <% for(Course course : courses){
                        course_id_to_course.put(course.course_id, course);
                    %>

                        <li class= "nav-header c<%= course.course_id%> <%= course.course_number.replaceAll("\\s","")%>"><%=course.name%></li>
                        <%for (Calendar cal : course.calendars){
                        cal_id_to_cal.put(cal.calendar_id, cal);%>
                        <li class="active"><a class="cal<%=cal.calendar_id%>" href="#"><%=cal.name%></a></li>
                        <%}%>
                    <%}%>
                </ul>
            </div>

            <div class="span9">
            <div id="announcements">
                <h1>Announcements</h1>
                <% for(Announcement ancmt : announcements){
                    if(ancmt.time_read == null){%>
                    <div class="announcement c<%=ancmt.course_id%>" >
                        <div class="announcement-title"> <%=course_id_to_course.get(ancmt.course_id).course_number%>: <%= ancmt.author %>  <button type="button" class="close anc" data-id="<%= ancmt.anncmnt_id %>">×</button></div>
                        <div class="announcement-content">
                            <p><%= ancmt.message %></p>
                        </div>
                    </div>
                <%}else{%>
                <div class="announcement old hidden c<%=ancmt.course_id%>" >
                    <div class="announcement-title"> <%=course_id_to_course.get(ancmt.course_id).course_number%>: <%= ancmt.author %></div>
                    <div class="announcement-content">
                        <p><%= ancmt.message %></p>
                    </div>
                </div>
                <%}}%>

                <% if(!announcements.isEmpty()) { %>
                    <button class="btn btn-large btn-block" id="ancmt-toggle" type="button">Toggle Read Announcements</button>
                <% }%>
            </div>

            <div id="Calendar" style="padding-top:40px;">
                <h1>Events</h1>
                <%
                    List<Message> messages;
                    Map<String, Document> documents;
                    for(Event event : events){
                        messages = msgsByEvent.containsKey(event.event_id) ? msgsByEvent.get(event.event_id) : new ArrayList<Message>();
                        documents = docsByEvent.containsKey(event.event_id) ? docsByEvent.get(event.event_id) : new HashMap<String, Document>();
                 %>

                    <div class="event cal<%=event.calendar_id%>">
                        <a href="#<%=event.event_id%>" role="button" class="btn btn-small calbtn" data-toggle="modal"><i class="icon-calendar"></i></a>
                        <h4><%=course_id_to_course.get(event.course_id).course_number%>: <%=cal_id_to_cal.get(event.calendar_id).name%></h4>
                        <h4 class="inline"><%=event.title%></h4> <p><%=dateFmt.format(event.start)%> - <%=dateFmt.format(event.end)%></p>

                        <p> Location: <%=event.location%></p>
                    </div>

                        <!-- Modal -->
                        <div id="<%=event.event_id%>" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h3 id="myModalLabel"><%=event.title%></h3>
                            </div>
                            <br>
                            <div class="modal-body">
                                <p><strong>Description: </strong> <%=event.description%></p>
                                <br>
                                <p><strong>Time: </strong> <%=dateFmt.format(event.start)%> to <%=dateFmt.format(event.end)%> </p>
                                <p><strong>Location:</strong> <%=event.location%></p>
                                <br>

                                <%if(!documents.isEmpty()){%>
                                    <h4>Documents:</h4>
                                    <%for(String key: documents.keySet()){%>
                                       <ul>
                                           <li><a href="<%=key%>"><%=key%></a></li>
                                       </ul>
                                <%}} %>



                                 <%if(!messages.isEmpty()){%>
                                    <h4>Messages:</h4>
                                        <%for(Message msg : messages){%>
                                        <dl class="dl-horizontal">
                                            <dt><%=msg.author.name%>:</dt>
                                            <dd><%=msg.message%></dd>
                                        </dl><%}%>
                                <%}%>

                                <form class="add-message">
                                    <input type="hidden" name="event_id" value="<%=event.event_id%>"/>
                                    <textarea class="input-block-level" name="message" rows="3"></textarea>
                                    <button type="submit" class="btn btn-block">Add a Message</button>
                                </form>

                            </div>
                            <div class="modal-footer">
                                <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
                            </div>
                        </div>
                <%}%>
            </div>
          </div>

            <% } else { %>

            <p class="lead">
                It looks like you aren't enrolled in any courses yet.
                You can add courses <a href="/courseworks/coursemgmt/student.jsp">here</a>.
            </p>

            <% } %>

        </div>
   </div>


 </body>



    <!-- scripts here -->
    <script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="/scripts/navigation.js"></script>
    <script type="text/javascript" src="/scripts/jquery.form.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){

            $('.nav.nav-list a').click(function(){
                var cur = $(this);
             $('.event.' + cur.attr("class").split(" ")[0]).fadeToggle("slow", "linear");
                cur.parent().toggleClass('active');
            });

            $('.add-message').ajaxForm({
                url: '/courseworks/message',
                type: 'post',
                success: function() {
                    window.location = window.location.href.split('#')[0];
                }
            });

            $(".close.anc").click(function(){
                var anc = $(this);
                var ancmt_id = anc.data("id");

                $.ajax({
                    type: 'POST',
                    url: '/courseworks/coursepage',
                    data: {
                        announcement_id: ancmt_id,
                        type: 'read'
                    },
                    success: function(){
                        anc.parent().parent().toggleClass("old hidden");
                        anc.remove();
                    },
                    error: function(){
                        alert('Sorry, we couldn\'t mark it read')
                    }
                });
            });

            $("#ancmt-toggle").click(function(){
                $(".announcement.old").toggleClass("hidden");
            });

        });

    </script>

</html>