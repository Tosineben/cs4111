(function(){

    $(function(){

        $('#signout').click(function() {
            $.post('/courseworks/signin');
            window.location = '/courseworks';
        });

        $('#delete-user').click(function(){
            if ($(this).attr('class').indexOf('disabled') != -1) {
                return;
            }
            $.ajax({
                type: 'POST',
                url: '/courseworks/user',
                data: {
                    type: 'delete'
                },
                success: function(resp){
                    window.location = resp;
                },
                error: function() {
                    alert('Oops, we failed to delete your account, please try again');
                }
            });
        });

        $('#edit-user-submit').click(function(){
            var name = $('#edit-user-name').val();
            $.ajax({
                type: 'POST',
                url: '/courseworks/user',
                data: {
                    type: 'edit',
                    name: name
                },
                success: function(resp){
                    $('#modal-nav').modal('hide');
                    $('#user-name').text($("#edit-user-name").val());
                },
                error: function() {
                    alert('Oops, we failed to edit your name, please try again');
                }
            });
        });

        $('#delete-user').tooltip();

    });

})();