(function() {

    "use strict";

    document.querySelector("#movie-search").addEventListener("click", runQueryToMovieDB);

    fetch("https://api.themoviedb.org/3/discover/movie?api_key=49990b53c193c05b1ce005f26fbf50c1&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            let html = "";
            for (let i = 0; i  < data.results.length; i ++) {
                html += "<a href='/movies/" + data.results[i].id + "-" + stringConverter(data.results[i].title) + "'>";
                html += "<img src='https://image.tmdb.org/t/p/w185" + data.results[i].poster_path + "'>";
                html += "</a>";
            }
            document.querySelector("#currently-popular-movies").innerHTML = html;
        });


    const stringConverter = (urlSnippet) => {
        return urlSnippet.replace(/\s+/g, '-').toLowerCase()
    };


    function runQueryToMovieDB() {
        let movieQuery = document.querySelector("#movie-search-field").value;
        console.log(movieQuery);
        fetch("https://api.themoviedb.org/3/search/movie?api_key=49990b53c193c05b1ce005f26fbf50c1&language=en-US&query=" + movieQuery + "&page=1&include_adult=false")
            .then((response) => response.json())
            .then((data) => {
                let html = "";
                for (let i = 0; i  < data.results.length; i ++) {
                    html += "<a href='/movies/" + data.results[i].id + "-" + stringConverter(data.results[i].title) + "'>";
                    html += "<img src='https://image.tmdb.org/t/p/w185" + data.results[i].poster_path + "'>";
                    html += "</a>";
                }
                document.querySelector("#searched-movies").innerHTML = html;
            })
    }

})();