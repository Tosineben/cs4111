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

    List<Event> events = new ArrayList<Event>();

    for (Map<Integer, List<Event>> tmp: eventsByCalByCourse.values()) {
        for(List<Event> tmp2: tmp.values()) {
            for(Event ev: tmp2) {
                events.add(ev);
            }
        }
    }

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
                            <a href="#tab-<%=c.course_id%>" data-toggle="tab"><%=c.name%></a>
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
                                <ul class="nav nav-pills">
                                    <li><a href="#modal-anncmnts-<%=c.course_id%>" data-toggle="modal">Manage Announcements</a></li>
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

                                <div class="prof-calendar" style="padding-top:20px;">

                                    <h3>
                                        <span id="cal-name-<%=ca.calendar_id%>"><%=ca.name%></span>
                                        <a href="#modal-edit-calendar-<%=ca.calendar_id%>" data-toggle="modal">
                                            <i class="icon-pencil"></i>
                                        </a>
                                    </h3>

                                    <% if(!eventsByCalByCourse.get(c.course_id).get(ca.calendar_id).isEmpty()) { %>
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th style="min-width:200px;">Title</th>
                                                    <th style="min-width:200px;">Start</th>
                                                    <th style="min-width:200px;">End</th>
                                                    <th style="min-width:200px;">Location</th>
                                                    <th style="min-width:50px;">Edit</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <% for(Event ev : eventsByCalByCourse.get(c.course_id).get(ca.calendar_id)) { %>
                                            <tr id="event-row-<%=ev.event_id%>">
                                                <td><%=ev.title%></td>
                                                <td><%=anncmntDateFmt.format(ev.start)%></td>
                                                <td><%=anncmntDateFmt.format(ev.end)%></td>
                                                <td><%=ev.location%></td>
                                                <td>
                                                    <a href="#modal-event-<%=ev.event_id%>" data-toggle="modal">
                                                        <i class="icon-pencil"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                            <% } %>
                                            </tbody>
                                        </table>
                                    <% } %>

                                    <div>
                                        <a href="#modal-add-event-<%=ca.calendar_id%>" data-toggle="modal">
                                            Add Event <%=ca.name%>
                                        </a>
                                    </div>

                                </div>

                                <div class="modal hide fade" id="modal-add-event-<%=ca.calendar_id%>">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h3>New Event</h3>
                                    </div>
                                    <div class="modal-body">
                                        <form class="form-horizontal">
                                            <div class="control-group">
                                                <label class="control-label" for="add-event-title-<%=ca.calendar_id%>">Title</label>
                                                <div class="controls">
                                                    <input class="input-xlarge" type="text" id="add-event-title-<%=ca.calendar_id%>" placeholder="John's Office Hours">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label" for="add-event-start-<%=ca.calendar_id%>">Start</label>
                                                <div class="controls">
                                                    <input class="input-xlarge" type="text" id="add-event-start-<%=ca.calendar_id%>" placeholder="12/10/2012 10:00 AM">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label" for="add-event-end-<%=ca.calendar_id%>">End</label>
                                                <div class="controls">
                                                    <input class="input-xlarge" type="text" id="add-event-end-<%=ca.calendar_id%>" placeholder="12/10/2012 11:30 AM">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label" for="add-event-location-<%=ca.calendar_id%>">Location</label>
                                                <div class="controls">
                                                    <input class="input-xlarge" type="text" id="add-event-location-<%=ca.calendar_id%>" placeholder="Mudd TA Room">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label" for="edit-event-desc-<%=ca.calendar_id%>">Description</label>
                                                <div class="controls">
                                                    <textarea id="edit-event-desc-<%=ca.calendar_id%>" class="input-xlarge" rows="5" placeholder="We will review this week's lecture notes and return your midterm exams."></textarea>
                                                </div>
                                            </div>
                                            <div class="form-actions">
                                                <button type="submit" class="btn btn-primary add-event-submit" data-calendarid="<%=ca.calendar_id%>" onclick="return false;">Submit</button>
                                                <button class="btn" data-dismiss="modal">Cancel</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

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

                            <% } %>

                        </div>
                    <% } %>
                </div>
            </div>

            <% for(Event ev : events) { %>

                <div class="modal hide fade" id="modal-event-<%=ev.event_id%>">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h3>Edit Event</h3>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="control-group">
                                <label class="control-label" for="edit-event-title-<%=ev.event_id%>">Title</label>
                                <div class="controls">
                                    <input class="input-xlarge" type="text" id="edit-event-title-<%=ev.event_id%>" value="<%=ev.title%>">
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="edit-event-start-<%=ev.event_id%>">Start</label>
                                <div class="controls">
                                    <input class="input-xlarge" type="text" id="edit-event-start-<%=ev.event_id%>" value="<%=anncmntDateFmt.format(ev.start)%>">
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="edit-event-end-<%=ev.event_id%>">End</label>
                                <div class="controls">
                                    <input class="input-xlarge" type="text" id="edit-event-end-<%=ev.event_id%>" value="<%=anncmntDateFmt.format(ev.end)%>">
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="edit-event-location-<%=ev.event_id%>">Location</label>
                                <div class="controls">
                                    <input class="input-xlarge" type="text" id="edit-event-location-<%=ev.event_id%>" value="<%=ev.location%>">
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="edit-event-desc-<%=ev.event_id%>">Description</label>
                                <div class="controls">
                                    <textarea id="edit-event-desc-<%=ev.event_id%>" class="input-xlarge" rows="5"><%=ev.description%></textarea>
                                </div>
                            </div>
                            <div class="form-actions">
                                <button type="submit" class="btn btn-primary edit-event-submit" data-eventid="<%=ev.event_id%>" onclick="return false;">Submit</button>
                                <a class="btn btn-danger delete-event" style="margin-left:10px;" data-eventid="<%=ev.event_id%>" href="#" onclick="return false"><i class="icon-trash icon-white"></i> Delete Event</a>
                            </div>
                        </form>
                    </div>
                </div>

            <% } %>

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
                                            <i class="icon-check"></i>
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
                                        <i class="icon-check"></i>
                                    </a>
                                    <a href="#" onclick="return false;" class="add-anncmnt-cancel">
                                        <i class="icon-ban-circle"></i>
                                    </a>
                                </td>
                            </tr>
                        </table>
                        <div>
                            <a href="#" onclick="return false;" class="add-anncmnt" data-course-id="<%=c.course_id%>">
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
<script type="text/javascript">
    (function(){

        $(function(){
            var firstTab = $($('.courseTab')[0]);
            if (firstTab.length > 0) {
                firstTab.children('a').click();
            }

            $('.edit-event-submit').click(function(){
                var event_id = $(this).data('eventid');
                var title = $('#edit-event-title-' + event_id).val();
                var start = $('#edit-event-start-' + event_id).val();
                var end = $('#edit-event-end-' + event_id).val();
                var location = $('#edit-event-location-' + event_id).val();
                var desc = $('#edit-event-desc-' + event_id).val();
                updateEvent(event_id, title, start, end, desc, location);
            });

            $('.add-event-submit').click(function() {
                var calendar_id = $(this).data('calendarid');
                var title = $('#add-event-title-' + calendar_id).val();
                var start = $('#add-event-start-' + calendar_id).val();
                var end = $('#add-event-end-' + calendar_id).val();
                var location = $('#add-event-location-' + calendar_id).val();
                var desc = $('#add-event-desc-' + calendar_id).val();
                addEvent(calendar_id, title, start, end, desc, location);
            });

            $('.delete-event').click(function(){
                var event_id = $(this).data('eventid');
                deleteEvent(event_id);
            });

            $('#show-future-events').click(function () {
                $($(this).data('target')).toggle('slow');
            });

            $('#show-past-events').click(function () {
                $($(this).data('target')).toggle('slow');
            });

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
                    alert('We were unable to save this event. Please enter dates in the format 12/25/2012 11:45 am.');
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
                    $('#event-row-' + event_id).remove();
                    window.location = window.location;
                },
                error: function() {
                    alert('Oops, we could not delete this event, please try again');
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
                    alert('We were unable to save this event. Please enter dates in the format 12/25/2012 11:45 am.');
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