(function(){

    $(function(){

        $('#signout').click(function() {
            $.post('/courseworks/signin');
            window.location = '/courseworks';
        });

    });

})();
