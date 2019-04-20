(function() {

    "use script";

    if (document.querySelector("#username-field") == null) {
        removeElementsIfUserIsNotLoggedIn();
    }

    let usernameId = document.querySelector("#username-field").innerHTML;
    let movieId = document.querySelector("#movie-id").innerHTML;
    let userPreferredGenre = document.querySelector("#preferred-genre").innerHTML;
    let movieGenre = document.querySelector("#movie-genre").innerHTML;
    let userHorrorPointValue = document.querySelector("#horror-points").innerHTML;
    let userDramaPointValue = document.querySelector("#drama-points").innerHTML;
    let userComedyPointValue = document.querySelector("#comedy-points").innerHTML;
    let userSciFiPointValue = document.querySelector("#scifi-points").innerHTML;
    let userRomancePointValue = document.querySelector("#romance-points").innerHTML;
    let userMiscPointValue = document.querySelector("#misc-points").innerHTML;
    let actualUserPointValue = 0;
    let movieGenreMatch = false;
    // let movieExistsInUserFavorites = false;
    // let movieExistsInUserRatings = false;


    retrievePointValueOffUserObject(movieGenre);

    if (userPreferredGenre === movieGenre) {
        movieGenreMatch = true;
    }


    // checkIfMovieExistsInUserFavorites();
    // checkIfMovieExistsInUserRatings();

    //need to refactor the below functions to construct the rating and favorite buttons after the timeout concludes

    // setTimeout(function() {
    //     if (movieExistsInUserFavorites) {
    //         removeFavoritesButtonFromPage();
    //     }
    // }, 250);

    // setTimeout(function() {
    //     if (movieExistsInUserRatings) {
    //         removeRatingElementsFromPage();
    //     }
    // }, 250);

    // FAVORITES PANEL

    if (document.querySelector("#favorites-panel").contains(document.querySelector("#delete-from-favorites"))) {
        document.querySelector("#delete-from-favorites").addEventListener("click", deleteMovieFromFavorites);
    }

    if (document.querySelector("#favorites-panel").contains(document.querySelector("#add-to-favorites"))) {
        document.querySelector("#add-to-favorites").addEventListener("click", updateMovieListWithPatchRequest);
    }

    // RATINGS PANEL

    if (document.querySelector("#review-panel").contains(document.querySelector("#one-star"))) {
        document.querySelector("#one-star").addEventListener("click", updateOneStarMovieRatingWithPatchRequest);
        document.querySelector("#two-star").addEventListener("click", updateTwoStarMovieRatingWithPatchRequest);
        document.querySelector("#three-star").addEventListener("click", updateThreeStarMovieRatingWithPatchRequest);
        document.querySelector("#four-star").addEventListener("click", updateFourStarMovieRatingWithPatchRequest);
        document.querySelector("#five-star").addEventListener("click", updateFiveStarMovieRatingWithPatchRequest);
    }

    if (document.querySelector("#review-panel").contains(document.querySelector("#remove-rating"))) {
        document.querySelector("#remove-rating").addEventListener("click", removeRatingFromRatingsList);
    }

    function retrievePointValueOffUserObject (movieGenre) {
        if (movieGenre === "horror") {
            actualUserPointValue = parseInt(userHorrorPointValue);
        } else if (movieGenre === "drama" || movieGenre === "crime") {
            actualUserPointValue = parseInt(userDramaPointValue);
        } else if (movieGenre === "comedy") {
            actualUserPointValue = parseInt(userComedyPointValue);
        } else if (movieGenre === "science fiction" || movieGenre === "fantasy") {
            actualUserPointValue = parseInt(userSciFiPointValue);
        } else if (movieGenre === "romance") {
            actualUserPointValue = parseInt(userRomancePointValue);
        } else {
            actualUserPointValue = parseInt(userMiscPointValue);
        }
    }

    function updateMovieListWithPatchRequest(event) {
        event.preventDefault();
        fetch("/api/v1/" + usernameId + "/add/" + movieId, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' }
        }).then(removeFavoritesButtonFromPage)
            .catch(alertUserOfFailedFavoritesFetch);
    }

    function updateOneStarMovieRatingWithPatchRequest(event) {
        event.preventDefault();
        fetch("/api/v1/" + usernameId + "/" + movieId + "/rating/" + movieGenreMatch, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "totalPossiblePoints": 100,
                "totalActualPoints": 20,
                "totalPossibleGenrePoints": 100,
                "totalActualGenrePoints": 20,
                "totalPossibleWeightedPoints": actualUserPointValue,
                "totalActualWeightedPoints": actualUserPointValue * 0.2
            })
        }).then(removeRatingElementsFromPage)
            .catch(alertUserOfFailedRatingFetch);

    }

    function updateTwoStarMovieRatingWithPatchRequest(event) {
        event.preventDefault();
        fetch("/api/v1/" + usernameId + "/" + movieId + "/rating/" + movieGenreMatch, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "totalPossiblePoints": 100,
                "totalActualPoints": 40,
                "totalPossibleGenrePoints": 100,
                "totalActualGenrePoints": 40,
                "totalPossibleWeightedPoints": actualUserPointValue,
                "totalActualWeightedPoints": actualUserPointValue * 0.4
            })
        }).then(removeRatingElementsFromPage)
            .catch(alertUserOfFailedRatingFetch);
    }

    function updateThreeStarMovieRatingWithPatchRequest(event) {
        event.preventDefault();
        fetch("/api/v1/" + usernameId + "/" + movieId + "/rating/" + movieGenreMatch, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "totalPossiblePoints": 100,
                "totalActualPoints": 60,
                "totalPossibleGenrePoints": 100,
                "totalActualGenrePoints": 60,
                "totalPossibleWeightedPoints": actualUserPointValue,
                "totalActualWeightedPoints": actualUserPointValue * 0.6
            })
        }).then(removeRatingElementsFromPage)
            .catch(alertUserOfFailedRatingFetch);
    }

    function updateFourStarMovieRatingWithPatchRequest(event) {
        event.preventDefault();
        fetch("/api/v1/" + usernameId + "/" + movieId + "/rating/" + movieGenreMatch, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "totalPossiblePoints": 100,
                "totalActualPoints": 80,
                "totalPossibleGenrePoints": 100,
                "totalActualGenrePoints": 80,
                "totalPossibleWeightedPoints": actualUserPointValue,
                "totalActualWeightedPoints": actualUserPointValue * 0.8
            })
        }).then(removeRatingElementsFromPage)
            .catch(alertUserOfFailedRatingFetch);
    }

    function updateFiveStarMovieRatingWithPatchRequest(event) {
        event.preventDefault();
        fetch("/api/v1/" + usernameId + "/" + movieId + "/rating/" + movieGenreMatch, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "totalPossiblePoints": 100,
                "totalActualPoints": 100,
                "totalPossibleGenrePoints": 100,
                "totalActualGenrePoints": 100,
                "totalPossibleWeightedPoints": actualUserPointValue,
                "totalActualWeightedPoints": actualUserPointValue
            })
        }).then(removeRatingElementsFromPage)
            .catch(alertUserOfFailedRatingFetch);
    }

    // function checkIfMovieExistsInUserFavorites() {
    //     fetch(usernameId + "/get/" + movieId).then((response) => {
    //         response.json().then(data  => {
    //             movieExistsInUserFavorites = data;
    //         })
    //     })
    // }

    // function checkIfMovieExistsInUserRatings() {
    //     fetch(usernameId + "/get/" + movieId + "/rating").then(response => {
    //         response.json().then(data => {
    //             movieExistsInUserRatings = data;
    //         })
    //     })
    // }

    function deleteMovieFromFavorites() {
        console.log("hello");
        fetch("/movies/" + usernameId + "/delete/" + movieId, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' }
        }).then(alertUserMovieDeletedFromFavorites)
            .catch(alertUserMovieFailedToDelete);
    }

    function removeRatingFromRatingsList() {
        fetch("/movies/" + usernameId + "/delete/rating/" + movieId, {
            method: 'DELETE',
            headers: {'Content-Type': 'application/json'}
        }).then(alertUserRatingWasRemoved)
            .catch(alertUserRatingWasNotRemoved);
    }

    function removeElementsIfUserIsNotLoggedIn() {
        document.querySelector("#review-panel").innerHTML = "<p>Please log in to review this movie</p>";
        document.querySelector("#favorites-panel").innerHTML = "<p>Please log in to add to favorites.</p>";

    }

    function removeRatingElementsFromPage() {
        document.querySelector("#review-panel").innerHTML = "<p>Rating recorded.</p>";
    }

    function alertUserRatingWasRemoved() {
        document.querySelector("#review-panel").innerHTML = "<p>Rating removed.</p>";
    }

    function alertUserRatingWasNotRemoved() {
        document.querySelector("#review-panel").innerHTML = "<p>Rating failed to remove. Try again.</p>";
    }

    function alertUserOfFailedRatingFetch() {
        document.querySelector("#review-warning").innerHTML = "<p>Rating failed. Try again.</p>";
    }

    function removeFavoritesButtonFromPage() {
        document.querySelector("#favorites-panel").innerHTML = "<p>Movie added to favorites list.</p>";
    }

    function alertUserOfFailedFavoritesFetch() {
        document.querySelector("#favorites-warning").innerHTML = "<p>Movie failed to add. Try again</p>";
    }

    function alertUserMovieDeletedFromFavorites() {
        document.querySelector("#favorites-panel").innerHTML ="<p>Movie deleted from favorites</p>";
    }

    function alertUserMovieFailedToDelete() {
        document.querySelector("#favorites-panel").innerHTML ="<p>Movie failed to delete. Try again.</p>";
    }


})();
