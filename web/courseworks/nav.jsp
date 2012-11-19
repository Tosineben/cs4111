<%@ page import="courseworks.model.*" %>
<%@ page import="courseworks.SessionKeys" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Student s = (Student)session.getAttribute(SessionKeys.logged_in_student);
    Professor p = (Professor)session.getAttribute(SessionKeys.logged_in_prof);

    // no nav if no one is logged in
    if (s == null && p == null) {
        return;
    }

    String name = s != null ? s.name : p.name;
%>

<div class="navbar navbar-inverse">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="#">Courseworks</a>
            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav">
                    <li class="${param.calendar}">
                        <a href="/courseworks/coursepage.jsp">Calendar</a>
                    </li>
                    <li class=${param.courseMgmt}>
                        <a href="/courseworks/coursemgmt/student.jsp">Courses</a>
                    </li>
                </ul>
                <ul class="nav pull-right">
                    <p class="navbar-text pull-left" id="user-name"><%=name%></p>
                    <li>
                        <a href="#modal-nav" data-toggle="modal">
                            <i class="icon-wrench icon-white"></i>
                        </a>
                    </li>
                    <li class="divider-vertical"></li>
                    <li>
                        <a href="#" onclick="return false;" id="signout">Sign Out</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="modal hide fade" id="modal-nav">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h3>Edit Account</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal">
            <div class="control-group">
                <label class="control-label" for="edit-user-name">Name</label>
                <div class="controls">
                    <input type="text" id="edit-user-name" class="input-xlarge" value="<%=name%>" />
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary" id="edit-user-submit" onclick="return false;">Submit</button>
                <a class="btn btn-danger ${param.deleteDisabled}" style="margin-left:10px;" rel="tooltip" title="${param.deleteTitle}" id="delete-user" href="#" onclick="return false"><i class="icon-trash icon-white"></i> Delete Account</a>
            </div>
        </form>
    </div>
</div>