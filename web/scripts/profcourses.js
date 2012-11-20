(function(){

    $(function(){
        var firstTab = $($('.courseTab')[0]);
        if (firstTab.length > 0) {
            firstTab.children('a').click();
        }

        $('.add-document').click(function(){
            var event_id = $(this).data('eventid');
            $('#add-document-' + event_id).show();
        });

        $('.add-document-cancel').click(function(){
            $(this).parent().parent().hide();
        });

        $('.delete-document').click(function(){
            var docid = $(this).data('documentid');
            deleteDocument(docid);
        });

        $('.add-document-ok').click(function(){
            var event_id = $(this).data('eventid');
            var file_path = $('#add-document-filepath-' + event_id).val();
            addDocument(event_id, file_path);
        });

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

    function addDocument(event_id, file_path) {
        $.ajax({
            type: 'POST',
            url: '/courseworks/profcourses/document',
            data: {
                type: 'add',
                event_id: event_id,
                file_path: file_path
            },
            success: function(){
                window.location = window.location;
            },
            error: function() {
                alert('Oops, looks like you have already uploaded that document.');
            }
        });
    }

    function deleteDocument(document_id) {
        $.ajax({
            type: 'POST',
            url: '/courseworks/profcourses/document',
            data: {
                type: 'delete',
                document_id: document_id
            },
            success: function(){
                $('#document-row-' + document_id).remove();
            },
            error: function() {
                alert('Oops, we were unable to delete that document, please try again.');
            }
        });
    }

})();
