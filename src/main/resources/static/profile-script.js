$(document).ready(function() {

    let usernameId = document.querySelector("#username-field").innerHTML;

    $('#myTab a[href="#users"]').on('click', function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    $('#myTab a[href="#liked-movies"]').on('click', function (e) {
        e.preventDefault();
        getLikedMoviesInUserObject();
        $(this).tab('show');
    });

    $('#myTab a[href="#reviewed-movies"]').on('click', function (e) {
        e.preventDefault();
        console.log("test");
        $(this).tab('show');
    });

    function getLikedMoviesInUserObject() {
        fetch('/movies/favorites/' + usernameId).then(response => {
            response.json().then( data => {
                console.log(data);
            })
        })
    }

});