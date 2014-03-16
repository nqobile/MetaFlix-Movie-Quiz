--Make sure to check if the wrong answers are equal to the right one before using them..

--Who directed the movie X?
SELECT title, director 
FROM movies
ORDER BY RANDOM()
LIMIT 1;

--Pick 3 random Directors
SELECT director FROM movies
ORDER BY RANDOM()
LIMIT 3;


--When was the movie X released?
SELECT title, year
FROM movies
ORDER BY RANDOM()
LIMIT 1;

--Pick 3 random years
SELECT year FROM movies
ORDER BY RANDOM()
LIMIT 3;
-- Or possibly just make up a few years near the answer?


--Which star (was/was not) in the movie X?
SELECT s.first_name, s.last_name, m.title
FROM stars s, stars_in_movies sm, movies m
WHERE (s.id = sm.star_id AND m.id = sm.movie_id)
ORDER BY RANDOM()
LIMIT 1;

--Pick 3 random stars
SELECT name
FROM stars
ORDER BY RANDOM()
LIMIT 3;


-- This query gives a list of how many stars where in each movie (possibly useful for figuring things out)
SELECT movies.title, count(stars.id)
FROM stars, stars_in_movies, movies
WHERE (stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id)
GROUP BY movies.id
ORDER BY RANDOM();
-- Movies with more than 2 stars
SELECT movies.id, movies.title
FROM stars, stars_in_movies, movies
WHERE (stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id)
GROUP BY movies.id
HAVING count(stars.id)>1
ORDER BY RANDOM();


-- In which movie did stars X and Y appear together
-- Find a movie with more than 2 stars
SELECT movies.id, movies.title
FROM stars, stars_in_movies, movies
WHERE (stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id)
GROUP BY movies.id
HAVING count(stars.id)>1
ORDER BY RANDOM()
LIMIT 1;

--Find names of stars
SELECT first_name, last_name
FROM stars, stars_in_movies
WHERE (stars.id = stars_in_movies.star_id) AND movie_id = XXX
ORDER BY RANDOM()
LIMIT 2;

--Pick 3 random movies
SELECT title
FROM movies
ORDER BY RANDOM()
LIMIT 3;


--Who directed the star X (in the year Y)?
--Pick a random star who has been in a movie
SELECT s.id, s.first_name, s.last_name
FROM stars s, stars_in_movies sm
WHERE s.id=sm.star_id
ORDER BY RANDOM()
LIMIT 1;

--Pick a director that has directed that star
SELECT m.director, m.title
FROM stars s, movies m, stars_in_movies sm
WHERE s.id=sm.star_id AND m.id=sm.movie_id AND s.id= XXX
ORDER BY RANDOM()
LIMIT 1;

--Pick 3 more random directors
SELECT director
FROM movies
ORDER BY RANDOM()
LIMIT 3;


--Which star appears in both movies X and Y?
--Pick a star who has been in 2 movies
SELECT *
FROM stars_in_movies
GROUP BY star_id
HAVING count(movie_id) > 1
ORDER BY RANDOM()
LIMIT 1;

--Get the names of 2 movies they've been in
SELECT m.title
FROM stars_in_movies sm, movies m
WHERE sm.movie_id = m.id AND sm.star_id=XXX
ORDER BY RANDOM()
LIMIT 2;

--Pick 3 random stars
SELECT first_name, last_name
FROM stars
ORDER BY RANDOM()
LIMIT 3;


--Which star did not appear in the same movie with the star X?
-- Pick a star who has been in a movie with at least 3 other stars
SELECT movies.id
FROM stars, stars_in_movies, movies
WHERE (stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id)
GROUP BY movies.id
HAVING count(stars.id)>3
ORDER BY RANDOM()
LIMIT 1;

SELECT star_id
FROM stars_in_movies
WHERE movie_id= XXX
--Use the 3 others from the list as the incorrect answers
--Pick one star who hasn't been in a movie with that star
SELECT first_name, last_name
FROM stars
ORDER BY RANDOM()
LIMIT 1;


