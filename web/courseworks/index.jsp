<%@ page import="courseworks.sql.*" %>
<%@ page import="courseworks.model.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ICourseworksReader repo = new CourseworksReader();
    List<Student> students = repo.getStudents();
    List<Professor> profs = repo.getProfessors();

    String unis = "";
    String objs = "";
    for (Professor p : profs) {
        unis += "'" + p.uni + "',";
        objs += p.uni + ": { name:'" + p.name + "', type: 'Professor' },";
    }
    for (Student s : students) {
        unis += "'" + s.uni + "',";
        objs += s.uni + ": { name:'" + s.name + "', type: 'Student' },";
    }
    unis = unis.substring(0, unis.length() - 1);
    objs = objs.substring(0, objs.length() - 1);
%>

<html>
<head>
    <title>Courseworks</title>
    <!-- styles here -->
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/bootstrap/bootstrap-responsive.min.css" />
    <link type="text/css" rel="stylesheet" href="/styles/courseworks.css" />
    <style>

        .hero-wrapper {
            width: 540px;
            margin: 0 auto;
        }

        #signin-form {
            padding-top: 30px;
        }

        button[type="submit"] {
            padding-left: 10px;
        }

        ul.typeahead li.active span {
            color: white;
        }

    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="hero-wrapper">
            <h1>Courseworks</h1>
            <h1><small>Manage all of your courses in one place.</small></h1>
            <div class="row-fluid">
                <form class="form-horizontal" id="signin-form">
                    <div class="control-group">
                        <label class="control-label" for="signin-uni">Sign In:</label>
                        <div class="controls">
                            <input class="input-xlarge" id="signin-uni" type="text" placeholder="Enter your Columbia UNI...">
                        </div>
                    </div>
                </form>
            </div>
            <hr>
            <div><strong>New to Courseworks? Sign up Below!</strong></div>
            <hr>
            <div class="row-fluid">
                <form class="form-horizontal">
                    <div class="control-group">
                        <label class="control-label" for="signup-uni">UNI</label>
                        <div class="controls">
                            <input class="input-xlarge" type="text" id="signup-uni" name="uni" placeholder="UNI">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="signup-name">Name</label>
                        <div class="controls">
                            <input class="input-xlarge" type="text" name="name" id="signup-name" placeholder="Name">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="signup-type">Affiliation: </label>
                        <div class="controls">
                            <select id="signup-type" name="type">
                                <option>Professor</option>
                                <option>Student</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="submit" id="signup-submit" onclick="return false;" class="btn btn-primary">Sign Up</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
<!-- scripts here -->
<script type="text/javascript" src="/scripts/jquery-1.8.1.js"></script>
<script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
<script type="text/javascript">
    (function(){

        $(function(){

            var allDudes = {<%=objs%>};
            var allUnis = [<%=unis%>];

            $('#signin-uni').typeahead({
                source: allUnis,
                items: 12,
                updater: function(item){
                    var dude = allDudes[item];
                    if (dude) {
                        signin(item, dude.name, dude.type);
                    }
                    return '';
                },
                sorter: function(items) {
                    if (items.length == 0) {
                        items[0] = 'None';
                    }
                    return items;
                },
                highlighter: function(item) {
                    if (item == 'None') {
                        return '<span><b>No Results</b></span>';
                    }
                    var dude = allDudes[item];
                    return '<span><b>' + item + ' - ' + dude.name + '</b> - ' + dude.type + '</span>';
                }
            });

            $('#signup-submit').click(function(){
                signup($('#signup-uni').val(),
                       $('#signup-name').val(),
                       $('#signup-type').val());
            });

            $('#signout').click(function() {
                $.post('/courseworks/signin');
                window.location = '/courseworks';
            });

            function signin(uni, name, type) {
                $.ajax({
                    type: 'GET',
                    url: '/courseworks/signin',
                    data: {
                        uni: uni,
                        name: name,
                        type: type
                    },
                    success: function(resp){
                        window.location = resp;
                    }
                });
            }

            function signup(uni, name, type) {
                $.ajax({
                    type: 'POST',
                    url: '/courseworks/signin',
                    data: {
                        uni: uni,
                        name: name,
                        type: type
                    },
                    success: function(resp){
                        window.location = resp;
                    },
                    error: function() {
                        alert("Oops, something went wrong, sorry!");
                    }
                });
            }

        });
    })();
</script>
</html>