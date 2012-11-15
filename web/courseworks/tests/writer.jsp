<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Random" %>

<!-- methods here -->
<%!
    private void assert_bool(boolean b, String msg) throws Exception {
        if (!b) {
            throw new Exception(msg);
        }
    }
%>

<!-- variable declarations -->
<%
    ICourseworksReader rdr = new CourseworksReader();
    ICourseworksWriter wrt = new CourseworksWriter();
    Random rand = new Random();
%>

<!-- prof and student tests -->
<%
    final String newProfUni = "adq" + (rand.nextInt(8999) + 1000);
    final String newProfName = "pAlden " + rand.nextInt(9999);
    final String newStudUni = "adq" + (rand.nextInt(8999) + 1000);
    final String newStudentName = "sAlden " + rand.nextInt(9999);

    boolean true_1 = wrt.createProfessor(new Professor(){{uni = newProfUni; name = newProfName;}});
    assert_bool(true_1, "adding new prof failed");

    // adding new prof same uni fails
    boolean false_1 = wrt.createProfessor(new Professor(){{uni = newProfUni; name = newProfName;}});
    assert_bool(!false_1, "adding new prof same uni worked");

    // adding new prof bad uni fails
    boolean false_2 = wrt.createProfessor(new Professor(){{uni = "pc4"; name = newProfName;}});
    assert_bool(!false_2, "adding new prof bad uni worked");

    // adding new student works
    boolean true_2 = wrt.createStudent(new Student(){{uni = newStudUni; name = newStudentName;}});
    assert_bool(true_2, "adding new student failed");

    // adding new student same uni fails
    boolean false_3 = wrt.createStudent(new Student(){{uni = newStudUni; name = newStudentName;}});
    assert_bool(!false_3, "adding new student same uni worked");

    // adding new student bad uni fails
    boolean false_4 = wrt.createStudent(new Student(){{uni = "89ys"; name = newStudentName;}});
    assert_bool(!false_4, "adding new same bad worked");

    List<Professor> profs = rdr.getProfessors();
    List<Student> students = rdr.getStudents();
    boolean foundStudent = false;
    boolean foundProf = false;
    for (Professor p : profs) {
        foundProf = p.uni.equalsIgnoreCase(newProfUni);
    }
    for (Student s : students) {
        foundStudent = s.uni.equalsIgnoreCase(newStudUni);
    }

    assert_bool(foundProf, "didnt find prof");
    assert_bool(foundStudent, "didnt find student");
%>

<!-- announcement tests -->
<%
    //wrt.updateAnncmntRead();
    //wrt.createAnnouncement();

%>

<!-- calendar and event tests -->
<%
    //wrt.createCalendar();
    //wrt.createEvent();

%>

<!-- document and message tests -->
<%
    //wrt.createDocument();
    //wrt.createMessage();
%>

<!-- course tests -->
<%
    //wrt.createCourse();
    //wrt.enrollStudentInCourse();
%>

<html>
<head>
    <title>Sample</title>
    <!-- styles here -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.servletPath}/styles/bootstrap/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.servletPath}/styles/bootstrap/bootstrap-responsive.min.css" />
</head>
<body>

</body>
<!-- scripts here -->
<script type="text/javascript" src="${pageContext.request.servletPath}/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="${pageContext.request.servletPath}/scripts/bootstrap.js"></script>
</html>