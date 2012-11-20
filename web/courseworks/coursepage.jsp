<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="courseworks.model.comperators.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="courseworks.SessionKeys" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    Student student = (Student)session.getAttribute(SessionKeys.logged_in_student);

    if (student == null) {
        if (session.getAttribute(SessionKeys.logged_in_prof) != null) {
            response.sendRedirect("/courseworks/coursemgmt/prof.jsp");
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


    List<Event> events = rdr.getEventsForStudent(student.uni);
    List<Announcement> announcements = rdr.getAnnouncementsForStudent(student.uni);

    Collections.sort(announcements, new AnnouncementComperator());
    Collections.sort(events, new EventComperator());
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
            <div class="span3">
                <ul class="nav nav-list well">
                    <% for(Course course : courses){
                        String course_id = Integer.toString(course.course_id);
                    %>

                        <li class= "nav-header c<%= course_id%>"><%=course.name%></li>
                        <%for (Calendar cal : course.calendars){%>
                        <li><a class="cal<%=cal.calendar_id%>" href="#"><%=cal.name%></a></li>
                        <%}%>
                    <%}%>
                </ul>
            </div>

            <div class="span9">
            <div id="announcements">
                <h1>Announcements</h1>
                <% for(Announcement ancmt : announcements){
                    if(ancmt.time_read == null){%>
                    <div class="announcement <%=ancmt.course_number.replaceAll("\\s","")%>" >
                        <div class="announcement-title"><%= ancmt.author %>  <button type="button" class="close anc" data-id="<%= ancmt.anncmnt_id %>">×</button></div>
                        <div class="announcement-content">
                            <p><%= ancmt.message %></p>
                        </div>
                    </div>
                <%}}%>
            </div>

            <div id="Calendar">
                <h1>Events</h1>
                <%
                    List<Message> messages;
                    for(Event event : events){
                        messages = rdr.getMessagesForEvent(event.event_id);
                 %>

                    <div class="event cal<%=event.calendar_id%>">
                        <a href="#<%=event.event_id%>" role="button" class="btn btn-small .pull-right" data-toggle="modal"><i class="icon-calendar"></i></a>
                        <h4 class="inline"><%=event.title%></h4> <p><%=event.start%> - <%=event.end%></p>
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
                                <p><%=event.description%></p>
                                <%for(Message msg : messages){%>
                                <dl class="dl-horizontal">
                                    <dt><%=msg.author.name%>:</dt>
                                    <dd><%=msg.message%></dd>
                                </dl><%}%>
                            </div>
                            <div class="modal-footer">
                                <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
                            </div>
                        </div>
                <%}%>
            </div>
          </div>
        </div>
   </div>


 </body>



    <!-- scripts here -->
    <script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="/scripts/navigation.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){

            $('.nav.nav-list a').click(function(){
                var cur = $(this);
             $('.event.' + cur.attr("class")).fadeToggle("slow", "linear");
            });

            $(".close.anc").click(function(){
                var ancmt_id = $(this).data("id");

                $.ajax({
                    type: 'POST',
                    url: '/courseworks/coursepage',
                    data: {
                        announcement_id: ancmt_id,
                        action: 'read'
                    },
                    success: function(){
                        $(this).parent().parent().hide();
                    },
                    error: function(){
                        alert('Sorry, we couldn\'t mark it read')
                    }
                });





            });
        });

    </script>

</html>