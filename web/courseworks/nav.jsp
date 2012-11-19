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
                    <p class="navbar-text pull-left"><%=name%></p>
                    <li class="divider-vertical"></li>
                    <li>
                        <a href="#" onclick="return false;" id="signout">Sign Out</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>