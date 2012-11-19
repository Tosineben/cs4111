<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="courseworks.model.Calendar" %>

<!-- methods here -->
<%!
    private void assertTrue(boolean b, String msg) throws Exception {
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

    boolean shouldBeTrue = wrt.createProfessor(new Professor(){{uni = newProfUni; name = newProfName;}});
    assertTrue(shouldBeTrue, "adding new prof failed");

    // adding new prof same uni fails
    boolean shouldBeFalse = wrt.createProfessor(new Professor(){{uni = newProfUni; name = newProfName;}});
    assertTrue(!shouldBeFalse, "adding new prof same uni worked");

    // adding new prof bad uni fails
    shouldBeFalse = wrt.createProfessor(new Professor(){{uni = "pc4"; name = newProfName;}});
    assertTrue(!shouldBeFalse, "adding new prof bad uni worked");

    // adding new student works
    shouldBeTrue = wrt.createStudent(new Student(){{uni = newStudUni; name = newStudentName;}});
    assertTrue(shouldBeTrue, "adding new student failed");

    // adding new student same uni fails
    shouldBeFalse = wrt.createStudent(new Student(){{uni = newStudUni; name = newStudentName;}});
    assertTrue(!shouldBeFalse, "adding new student same uni worked");

    // adding new student bad uni fails
    shouldBeFalse = wrt.createStudent(new Student(){{uni = "89ys"; name = newStudentName;}});
    assertTrue(!shouldBeFalse, "adding new same bad worked");

    List<Professor> profs = rdr.getProfessors();
    List<Student> students = rdr.getStudents();
    Student tempNewStudent = null;
    Professor tempNewProf = null;
    for (Professor p : profs) {
        if(p.uni.equalsIgnoreCase(newProfUni)) {
            tempNewProf = p;
        }
    }
    for (Student s : students) {
        if(s.uni.equalsIgnoreCase(newStudUni)) {
            tempNewStudent = s;
        }
    }

    final Professor newProf = tempNewProf;
    final Student newStudent = tempNewStudent;

    assertTrue(newProf != null, "didnt find prof");
    assertTrue(newStudent != null, "didnt find student");
%>

<!-- course tests -->
<%
    // creating course works
    int newCourseId = wrt.createCourse(new Course(){{
        name = "Intro to Databases"; location = "411 IAB"; description = "Learn about databases."; course_number = "COMS W4111"; professor = newProf;
    }});
    assertTrue(newCourseId > 0, "adding course failed");

    // can enroll student
    shouldBeTrue = wrt.enrollStudentInCourse(newStudUni, newCourseId);
    assertTrue(shouldBeTrue, "enrolling student failed");

    // cannot enroll student twice
    shouldBeFalse = wrt.enrollStudentInCourse(newStudUni, newCourseId);
    assertTrue(!shouldBeFalse, "enrolled student in same course twice");
%>

<!-- announcement tests -->
<%
    // create new announcement
    int newAnncmntId = wrt.createAnnouncement(newCourseId, new Announcement(){{
        message = "Be warned, midterm exam is brutally difficult!";
    }});
    assertTrue(newAnncmntId > 0, "failed to create anncmnt");

    // student reads announcement
    shouldBeTrue = wrt.updateAnncmntRead(newAnncmntId, newStudUni, true);
    assertTrue(shouldBeTrue, "student failed to read anncmnt");

    // student cannot read announcement again
    shouldBeFalse = wrt.updateAnncmntRead(newAnncmntId, newStudUni, true);
    assertTrue(!shouldBeFalse, "student read anncmnt twice");

    // student marks unread
    shouldBeTrue = wrt.updateAnncmntRead(newAnncmntId, newStudUni, false);
    assertTrue(shouldBeTrue, "student cant mark unread");
%>

<!-- calendar and event tests -->
<%
    // create calendar
    int newCalendarId = wrt.createCalendar(newCourseId, new Calendar(){{
        name = "TA Office Hours";
    }});
    assertTrue(newCalendarId > 0, "failed to create calendar");

    // create event
    final java.util.Calendar startT = new GregorianCalendar(2012, java.util.Calendar.DECEMBER, 10, 16, 0);
    final java.util.Calendar endT = new GregorianCalendar(2012, java.util.Calendar.DECEMBER, 10, 18, 0);
    int newEventId = wrt.createEvent(newCalendarId, new Event(){{
        title = "John's Office Hours"; start = startT.getTime(); end = endT.getTime(); location = "Mudd TA Room"; description = "Weekly office hours, ask any questions";
    }});
    assertTrue(newEventId > 0, "failed to create event");

%>

<!-- document and message tests -->
<%
    // create document
    int newDocumentId = wrt.createDocument(newEventId, new Document(){{
        file_path = "C:/tmp/lecture.pdf";
    }});
    assertTrue(newDocumentId > 0, "failed to create document");

    // create message
    int newMessageId = wrt.createMessage(newEventId, new Message(){{
        message = "Are you going to review the lecture?"; author = newStudent;
    }});
    assertTrue(newMessageId > 0, "failed to create message");

%>

<html>
<head>
    <title>Sample</title>
    <!-- styles here -->
</head>
<body>
    <h1>All tests passed!</h1>
</body>
<!-- scripts here -->
</html>