Computer-Engineering-Capstone

A movie rental application:

Concept 1: Movie rental service I'm thinking of an inventory system/e-commerce. We will need a large dataset. The more the scope of data, the more features we can implement in the app

For the data: The Movie DB (themoviedb.org) is like IMDB, which contains movie information for released and upcoming movies. They offer free Rest API, https://developer.themoviedb.org. Initially, we scrape movie data using their API and store it in Firebase. The data we collect will include movie titles, descriptions, release dates, genres, cast and crew details, ratings, image file names, etc.

Here is how our app works:

    Users can sign up or log in to see a list of available movies from our Firebase database.
    The app will have features like sorting movies by genre, rating, and release date
    Clicking on a movie title reveals all the information about that movie.
    There will be a search function to locate specific movie titles.
    Users can rent movies for a fee, which varies depending on how long they want to keep the movie. (think of a pricing structure)
    We’ll limit the number of copies available for each movie
    Set rules for the maximum number of movies a user can rent.
    List user payment history and previous rental history.
    In the user account settings, user can update their personal information, such as their name, email, password, and account balance.
