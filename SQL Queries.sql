--Make sure to check if the wrong answers are equal to the right one before using them..

--Who directed the movie X?
SELECT title, director 
FROM movies
ORDER BY RAND()
LIMIT 1;

--Pick 3 random Directors
SELECT director FROM movies
ORDER BY RAND()
LIMIT 3;


--When was the movie X released?
SELECT title, year
FROM movies
ORDER BY RAND()
LIMIT 1;

--Pick 3 random years
SELECT year FROM movies
ORDER BY RAND()
LIMIT 3;
-- Or possibly just make up a few years near the answer?


--Which star (was/was not) in the movie X?
SELECT s.first_name, s.last_name, m.title
FROM stars s, stars_in_movies sm, movies m
WHERE (s.id = sm.star_id AND m.id = sm.movie_id)
ORDER BY RAND()
LIMIT 1;

--Pick 3 random stars
SELECT name
FROM stars
ORDER BY RAND()
LIMIT 3;


-- This query gives a list of how many stars where in each movie (possibly useful for figuring things out)
SELECT movies.title, count(stars.id)
FROM stars, stars_in_movies, movies
WHERE (stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id)
GROUP BY movies.id
ORDER BY RAND();
-- Movies with more than 2 stars
SELECT movies.id, movies.title
FROM stars, stars_in_movies, movies
WHERE (stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id)
GROUP BY movies.id
HAVING count(stars.id)>1
ORDER BY RAND();


-- In which movie did stars X and Y appear together
-- Find a movie with more than 2 stars
SELECT movies.id, movies.title
FROM stars, stars_in_movies, movies
WHERE (stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id)
GROUP BY movies.id
HAVING count(stars.id)>1
ORDER BY RAND()
LIMIT 1;

--Find names of stars
SELECT first_name, last_name
FROM stars, stars_in_movies
WHERE (stars.id = stars_in_movies.star_id) AND movie_id = XXX
LIMIT 2;

--Pick 3 random movies
SELECT title
FROM movies
ORDER BY RAND()
LIMIT 3;






