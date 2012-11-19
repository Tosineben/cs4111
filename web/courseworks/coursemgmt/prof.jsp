<%@ page import="courseworks.SessionKeys" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="courseworks.sql.ICourseworksReader" %>
<%@ page import="courseworks.sql.CourseworksReader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Professor prof = (Professor)session.getAttribute(SessionKeys.logged_in_prof);

    if (prof == null) {
        if (session.getAttribute(SessionKeys.logged_in_student) != null) {
            response.sendRedirect("/courseworks/coursemgmt/student.jsp");
        }
        else {
            response.sendRedirect("/courseworks");
        }
        return;
    }

    List<Course> currentCourses = prof.getCourses();

    ICourseworksReader rdr = new CourseworksReader();
    HashMap<Integer, HashMap<String, Integer>> msgCntByStudentByCourse = rdr.getMessageCountByStudentByCourse(prof.uni);

    HashMap<Integer, List<Student>> studentsByCourse = new HashMap<Integer, List<Student>>();
    for (Course c : currentCourses) {
        studentsByCourse.put(c.course_id, c.getStudents());
    }
%>

<html>
<head>
    <title>Course Management</title>
    <!-- styles here -->
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap-responsive.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/courseworks.css" />
    <style>

    </style>
</head>
<body>
    <jsp:include page="../nav.jsp">
        <jsp:param name="courseMgmt" value="active" />
    </jsp:include>
    <div class="container-fluid">
        <h1>Course Management</h1>
        <hr>
        <div class="row-fluid">
            <h3>Your Courses</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th>Course</th>
                        <th>Name</th>
                        <th>Location</th>
                        <th>Enrolled Students</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Course c: currentCourses) {
                    int numStudents = studentsByCourse.get(c.course_id).size();
                %>
                    <tr>
                        <td><%=c.course_number%></td>
                        <td><%=c.name%></td>
                        <td><%=c.location%></td>
                        <% if (numStudents == 0) { %>
                            <td>0</td>
                        <% } else { %>
                            <td><a href="#modal-students-<%=c.course_id%>" data-toggle="modal"><%=numStudents%></a></td>
                        <% } %>
                        <td><a href="#modal-<%=c.course_id%>" data-toggle="modal">Edit</a></td>
                        <td><a href="#" onclick="return false;" class="remove-course" data-course="<%=c.course_id%>">Delete</a></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
            <%
                for (Course c: currentCourses) {
                    HashMap<String, Integer> msgCntByStudent = msgCntByStudentByCourse.get(c.course_id);
                    if (msgCntByStudent == null) {
                        msgCntByStudent = new HashMap<String, Integer>();
                    }
            %>
                <div class="modal hide fade" id="modal-students-<%=c.course_id%>">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h3><%=c.course_number%> - <%=c.name%></h3>
                    </div>
                    <div class="modal-body">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Student</th>
                                <th>Uni</th>
                                <th>Messages Posted</th>
                            </tr>
                            </thead>
                            <tbody>
                            <% for (Student s: studentsByCourse.get(c.course_id)) {
                                Integer msgCount = msgCntByStudent.get(s.uni);
                            %>
                            <tr>
                                <td><%=s.name%></td>
                                <td><%=s.uni%></td>
                                <td><%=(msgCount == null ? 0 : msgCount)%></td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal hide fade" id="modal-<%=c.course_id%>">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h3>Edit Course</h3>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="control-group">
                                <label class="control-label" for="edit-number-<%=c.course_id%>">Number</label>
                                <div class="controls">
                                    <input class="input-xlarge" type="text" id="edit-number-<%=c.course_id%>" value="<%=c.course_number%>">
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="edit-name-<%=c.course_id%>">Name</label>
                                <div class="controls">
                                    <input class="input-xlarge" type="text" id="edit-name-<%=c.course_id%>" value="<%=c.name%>">
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="edit-location-<%=c.course_id%>">Location</label>
                                <div class="controls">
                                    <input class="input-xlarge" type="text" id="edit-location-<%=c.course_id%>" value="<%=c.location%>">
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="edit-desc-<%=c.course_id%>">Description</label>
                                <div class="controls">
                                    <textarea id="edit-desc-<%=c.course_id%>" class="input-xlarge" rows="5"><%=c.description%></textarea>
                                </div>
                            </div>
                            <div class="form-actions">
                                <button type="submit" class="btn btn-primary edit-submit" data-courseid="<%=c.course_id%>" onclick="return false;">Submit</button>
                                <button class="btn" data-dismiss="modal">Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            <%
                }
            %>
        </div>
        <hr>
        <div class="row-fluid">
            <h3>New Course</h3>
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="newcourse-number">Number</label>
                    <div class="controls">
                        <input class="input-xlarge" type="text" id="newcourse-number" placeholder="COMS 4111">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="newcourse-name">Name</label>
                    <div class="controls">
                        <input class="input-xlarge" type="text" id="newcourse-name" placeholder="Intro to Databases">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="newcourse-location">Location</label>
                    <div class="controls">
                        <input class="input-xlarge" type="text" id="newcourse-location" placeholder="410 Mudd">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="newcourse-desc">Description</label>
                    <div class="controls">
                        <textarea id="newcourse-desc" class="input-xlarge" rows="5" placeholder="Introduction to all things databases, from relational algebra to query optimization. Focuses on relational databases, specificall oracle."></textarea>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" id="newcourse-submit" onclick="return false;" class="btn btn-primary">Create Course</button>
                </div>
            </form>
        </div>
    </div>
</body>
<!-- scripts here -->
<script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="/scripts/navigation.js"></script>
<script type="text/javascript">
    (function(){

        $(function(){

            $('#newcourse-submit').click(function(){
                var course_number = $('#newcourse-number').val();
                var course_name = $('#newcourse-name').val();
                var location = $('#newcourse-location').val();
                var description = $('#newcourse-desc').val();
                postCourse('add', null, course_number, course_name, location, description);
            });

            $('.edit-submit').click(function(){
                var course_id = $(this).data('courseid');
                var course_number = $('#edit-number-' + course_id).val();
                var course_name = $('#edit-name-' + course_id).val();
                var location = $('#edit-location-' + course_id).val();
                var description = $('#edit-desc-' + course_id).val();
                postCourse('edit', course_id, course_number, course_name, location, description);
            });

            $('.remove-course').click(function(){
                var course_id = $(this).data('course');
                removeCourse(course_id);
            });

        });

        function removeCourse(course_id) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/coursemgmt/professor',
                data: {
                    type: 'remove',
                    course_id: course_id
                },
                success: function(resp){
                    window.location = resp;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

        function postCourse(type, course_id, course_number, course_name, location, description) {
            $.ajax({
                type: 'POST',
                url: '/courseworks/coursemgmt/professor',
                data: {
                    type: type,
                    course_id: course_id,
                    course_number: course_number,
                    course_name: course_name,
                    location: location,
                    description: description
                },
                success: function(resp){
                    window.location = resp;
                },
                error: function() {
                    alert('Oops, we failed to add this course, please try again');
                }
            });
        }

    })();
</script>
</html>