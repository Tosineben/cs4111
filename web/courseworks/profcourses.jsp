<%@ page import="courseworks.SessionKeys" %>
<%@ page import="courseworks.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Professor prof = (Professor)session.getAttribute(SessionKeys.logged_in_prof);

    if (prof == null) {
        if (session.getAttribute(SessionKeys.logged_in_student) != null) {
            response.sendRedirect("/courseworks/coursepage.jsp");
        }
        else {
            response.sendRedirect("/courseworks");
        }
        return;
    }
    SimpleDateFormat anncmntDateFmt = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    ICourseworksReader rdr = new CourseworksReader();
    Map<Integer, List<Announcement>> anncmntsByCourse = rdr.getAnnouncementsForProf(prof.uni);
    Map<Integer, Map<Integer, List<Event>>> eventsByCalByCourse = rdr.getEventsForProf(prof.uni);
    Map<Integer, List<Calendar>> calendarsByCourse = rdr.getCalendarsForProf(prof.uni);
    List<Course> currentCourses = prof.getCourses();

    String deleteDisabled = null;
    String deleteTitle = null;

    if (currentCourses.size() > 0) {
        deleteDisabled = "disabled";
        deleteTitle = "You cannot delete your account because you have active courses.";
    }
%>

<html>
<head>
    <title>Professor Courses</title>
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap-responsive.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/courseworks.css"/>
    <style>

    </style>
</head>
<body>
    <jsp:include page="nav.jsp">
        <jsp:param name="calendar" value="active" />
        <jsp:param name="deleteDisabled" value="<%=deleteDisabled%>" />
        <jsp:param name="deleteTitle" value="<%=deleteTitle%>" />
    </jsp:include>
    <div class="container-fluid">
        <h1>Calendars, Events and Announcements</h1>
        <hr>
        <% if (currentCourses.isEmpty()) { %>

            <p class="lead">
                It looks like you don't teach any courses yet.
                You can add the courses you teach <a href="/courseworks/coursemgmt/prof.jsp">here</a>.
            </p>

        <% } else { %>

        <div class="row-fluid">
            <div class="tabbable tabs-left">
                <ul class="nav nav-tabs">
                    <% for(Course c : currentCourses) { %>
                        <li class="courseTab">
                            <a href="#tab-<%=c.course_id%>" data-toggle="tab">
                                <%=c.name%><i class="icon-chevron-right pull-right"></i>
                            </a>
                        </li>
                    <% } %>
                </ul>
                <div class="tab-content">
                    <% for(Course c : currentCourses) {
                        if (!calendarsByCourse.containsKey(c.course_id)) {
                            calendarsByCourse.put(c.course_id, new ArrayList<Calendar>());
                        }
                    %>
                        <div class="tab-pane" id="tab-<%=c.course_id%>">

                            <div class="pull-right">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="#modal-anncmnts-<%=c.course_id%>" data-toggle="modal">Announcements</a></li>
                                    <li><a href="#modal-add-calendar-<%=c.course_id%>" data-toggle="modal">Add Calendar</a></li>
                                </ul>
                            </div>

                            <% for(Calendar ca : calendarsByCourse.get(c.course_id)) {
                                if (!eventsByCalByCourse.containsKey(c.course_id)) {
                                    eventsByCalByCourse.put(c.course_id, new HashMap<Integer, List<Event>>());
                                }
                                if (!eventsByCalByCourse.get(c.course_id).containsKey(ca.calendar_id)) {
                                    eventsByCalByCourse.get(c.course_id).put(ca.calendar_id, new ArrayList<Event>());
                                }
                            %>

                                <div class="modal hide fade" id="modal-edit-calendar-<%=ca.calendar_id%>">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h3>Edit Calendar for <%=c.course_number%></h3>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal">
                                        <div class="control-group">
                                            <label class="control-label" for="edit-calendar-name-<%=ca.calendar_id%>">Name</label>
                                            <div class="controls">
                                                <input class="input-xlarge" type="text" id="edit-calendar-name-<%=ca.calendar_id%>" value="<%=ca.name%>">
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <button type="submit" class="btn btn-primary edit-calendar-submit" data-calendarid="<%=ca.calendar_id%>" onclick="return false;">Submit</button>
                                            <a class="btn btn-danger delete-calendar" style="margin-left:10px;" data-calendarid="<%=ca.calendar_id%>" href="#" onclick="return false"><i class="icon-trash icon-white"></i> Delete Calendar</a>
                                        </div>
                                    </form>
                                </div>
                            </div>

                                <div>
                                    <h3>
                                        <span id="cal-name-<%=ca.calendar_id%>"><%=ca.name%></span>
                                        <a href="#modal-edit-calendar-<%=ca.calendar_id%>" data-toggle="modal">
                                            <i class="icon-pencil"></i>
                                        </a>
                                    </h3>

                                </div>

                                <% for(Event ev : eventsByCalByCourse.get(c.course_id).get(ca.calendar_id)) { %>

                                <% } %>

                            <% } %>

                        </div>
                    <% } %>
                </div>
            </div>

            <%
            for(Course c : currentCourses) {
                if (!anncmntsByCourse.containsKey(c.course_id)) {
                    anncmntsByCourse.put(c.course_id, new ArrayList<Announcement>());
                }
            %>
                <div class="modal hide fade" id="modal-anncmnts-<%=c.course_id%>">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h3>Announcements for <%=c.name%> (<%=c.course_number%>)</h3>
                    </div>
                    <div class="modal-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th style="min-width:200px;">Date Posted</th>
                                    <th style="min-width:200px;">Message</th>
                                    <th style="min-width:50px;">Edit</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for (Announcement a: anncmntsByCourse.get(c.course_id)) { %>
                                <tr>
                                    <td><%=anncmntDateFmt.format(a.time_posted)%></td>
                                    <td>
                                        <span id="anncmnt-msg-old-<%=a.anncmnt_id%>"><%=a.message%></span>
                                        <textarea rows="5" style="display:none;" id="anncmnt-msg-new-<%=a.anncmnt_id%>"><%=a.message%></textarea>
                                    </td>
                                    <td data-anncmnt-id="<%=a.anncmnt_id%>">
                                        <a href="#" onclick="return false;" class="edit-anncmnt">
                                            <i class="icon-pencil"></i>
                                        </a>
                                        <a href="#" onclick="return false;" class="delete-anncmnt">
                                            <i class="icon-trash"></i>
                                        </a>
                                        <a href="#" onclick="return false;" class="edit-anncmnt-ok" style="display:none;">
                                            <i class="icon-ok-circle"></i>
                                        </a>
                                    </td>
                                </tr>
                            <% } %>
                            </tbody>
                            <tr style="display:none;" id="add-anncmnt-<%=c.course_id%>">
                                <td></td>
                                <td>
                                    <textarea rows="5" id="anncmnt-add-msg-<%=c.course_id%>"></textarea>
                                </td>
                                <td>
                                    <a href="#" onclick="return false;" class="add-anncmnt-ok" data-course-id="<%=c.course_id%>">
                                        <i class="icon-ok-circle"></i>
                                    </a>
                                    <a href="#" onclick="return false;" class="add-anncmnt-cancel">
                                        <i class="icon-ban-circle"></i>
                                    </a>
                                </td>
                            </tr>
                        </table>
                        <div>
                            <a href="#" onclick="return false;" class="add-anncmnt" data-course-id="<%=c.course_id%>">
                                <i class="icon-check"></i>
                                Add New Announcement
                            </a>
                        </div>
                    </div>
                </div>
                <div class="modal hide fade" id="modal-add-calendar-<%=c.course_id%>">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h3>New Calendar for <%=c.course_number%></h3>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="control-group">
                                <label class="control-label" for="add-calendar-name-<%=c.course_id%>">Name</label>
                                <div class="controls">
                                    <input class="input-xlarge" type="text" id="add-calendar-name-<%=c.course_id%>">
                                </div>
                            </div>
                            <div class="form-actions">
                                <button type="submit" class="btn btn-primary add-calendar-submit" data-courseid="<%=c.course_id%>" onclick="return false;">Submit</button>
                                <button class="btn" data-dismiss="modal">Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            <% } %>

        </div>

        <% } %>

    </div>
</body>
<!-- scripts here -->
<script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="/scripts/navigation.js"></script>
<script type="text/javascript" src="/scripts/moment.js"></script>
<script type="text/javascript">
    (function(){

        $(function(){
            var firstTab = $($('.courseTab')[0]);
            if (firstTab.length > 0) {
                firstTab.children('a').click();
            }

            $('.add-calendar-submit').click(function(){
                var course = $(this).data('courseid');
                var name = $('#add-calendar-name-' + course).val();
                addCalendar(course, name);
            });

            $('.edit-calendar-submit').click(function(){
                var cal = $(this).data('calendarid');
                var name = $('#edit-calendar-name-' + cal).val();
                updateCalendar(cal, name);
            });

            $('.delete-calendar').click(function(){
                var cal = $(this).data('calendarid');
                deleteCalendar(cal);
            });

            $('.edit-anncmnt').click(function(){
                var anncmnt_id = $(this).parent().data('anncmnt-id');
                $("#anncmnt-msg-new-" + anncmnt_id).show();
                $("#anncmnt-msg-old-" + anncmnt_id).hide();
                $(this).hide();
                $(this).parent().children('.delete-anncmnt').hide();
                $(this).parent().children('.edit-anncmnt-ok').show();
            });

            $('.edit-anncmnt-ok').click(function(){
                var anncmnt_id = $(this).parent().data('anncmnt-id');
                var msg = $("#anncmnt-msg-new-" + anncmnt_id).val();
                updateAnnouncement(anncmnt_id, msg);
            })

            $('.delete-anncmnt').click(function(){
                if (confirm('Are you sure you want to delete this announcement?')) {
                    deleteAnnouncement($(this).parent().data('anncmnt-id'));
                }
            })

            $('.add-anncmnt').click(function(){
                var course_id = $(this).data('course-id');
                $('#add-anncmnt-' + course_id).show();
            });

            $('.add-anncmnt-ok').click(function(){
                var course_id = $(this).data('course-id');
                var msg = $("#anncmnt-add-msg-" + course_id).val();
                addAnnouncment(course_id, msg);
            });

            $('.add-anncmnt-cancel').click(function(){
                $(this).parent().parent().hide();
            });

        });

        // dates must be in format yyyy/MM/dd HH:mm:ss

        function addAnnouncment(course_id, message) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/anncmnt',
                data: {
                    type: 'add',
                    course_id: course_id,
                    message: message
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this announcement, please try again');
                }
            });
        }

        function deleteAnnouncement(anncmnt_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/anncmnt',
                data: {
                    type: 'delete',
                    anncmnt_id: anncmnt_id
                },
                success: function(){
                    var row = $('td[data-anncmnt-id="'+anncmnt_id+'"]');
                    row.parent().remove();
                },
                error: function() {
                    alert('Oops, we failed to delete this announcement, please try again');
                }
            });
        }

        function updateAnnouncement(anncmnt_id, message) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/anncmnt',
                data: {
                    type: 'update',
                    anncmnt_id: anncmnt_id,
                    message: message
                },
                success: function() {
                    $("#anncmnt-msg-new-" + anncmnt_id).hide();
                    $("#anncmnt-msg-old-" + anncmnt_id).show();
                    $("#anncmnt-msg-old-" + anncmnt_id).text(message);
                    var row = $('td[data-anncmnt-id="'+anncmnt_id+'"]');
                    row.children('.delete-anncmnt').show();
                    row.children('.edit-anncmnt').show();
                    row.children('.edit-anncmnt-ok').hide();
                },
                error: function() {
                    alert('Oops, we failed to update this announcement, please try again');
                }
            });
        }

        function addEvent(calendar_id, title, start, end, description, location) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/event',
                data: {
                    type: 'add',
                    calendar_id: calendar_id,
                    title: title,
                    start: start,
                    end: end,
                    description: description,
                    location: location
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function deleteEvent(event_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/event',
                data: {
                    type: 'delete',
                    event_id: event_id
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function updateEvent(event_id, title, start, end, description, location) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/event',
                data: {
                    type: 'update',
                    event_id: event_id,
                    title: title,
                    start: start,
                    end: end,
                    description: description,
                    location: location
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function addCalendar(course_id, name) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/calendar',
                data: {
                    type: 'add',
                    course_id: course_id,
                    name: name
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, looks like you have already used that name for a different calendar.');
                }
            });
        }

        function deleteCalendar(calendar_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/calendar',
                data: {
                    type: 'delete',
                    calendar_id: calendar_id
                },
                success: function(){
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we failed to delete this calendar, please try again');
                }
            });
        }

        function updateCalendar(calendar_id, name) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/profcourses/calendar',
                data: {
                    type: 'update',
                    calendar_id: calendar_id,
                    name: name
                },
                success: function(){
                    $('#cal-name-' + calendar_id).html(name);
                    $('#modal-edit-calendar-' + calendar_id).modal('hide')
                },
                error: function() {
                    alert('Oops, looks like you have already used that name for a different calendar.');
                }
            });
        }

    })();
</script>
</html>