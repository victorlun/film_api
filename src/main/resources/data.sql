--Marvel franchise and movies
INSERT INTO franchise (name, description) VALUES ('Marvel Cinematic Universe', 'an American media franchise and shared universe centered on a series of superhero films produced by Marvel Studios. The films are based on characters that appear in American comic books published by Marvel Comics.');
INSERT INTO movie (franchise_id, title, genre, release_year, director, picture, trailer) VALUES (1,'The Avengers', 'Action, Superhero, Sci-fi', '2012', 'Joss Whedon', 'https://upload.wikimedia.org/wikipedia/en/8/8a/The_Avengers_%282012_film%29_poster.jpg', 'https://www.youtube.com/watch?v=hA6hldpSTF8');
INSERT INTO movie (franchise_id, title, genre, release_year, director, picture, trailer) VALUES (1,'Iron Man', 'Action, Superhero, Sci-fi', '2008', 'Jon Favreau', 'https://en.wikipedia.org/wiki/Iron_Man_(2008_film)#/media/File:Iron_Man_(2008_film)_poster.jpg', 'https://www.youtube.com/watch?v=8ugaeA-nMTc');
INSERT INTO movie (franchise_id, title, genre, release_year, director, picture, trailer) VALUES (1, 'Spider-Man: Homecoming', 'Action, Superhero, Sci-fi', '2017', 'Jon Watts', 'https://upload.wikimedia.org/wikipedia/en/f/f9/Spider-Man_Homecoming_poster.jpg', 'https://www.youtube.com/watch?v=DiTECkLZ8HM');

--Harry Potter franchise and movies
INSERT INTO franchise (name, description) VALUES ('Harry Potter', 'a film series based on the eponymous novels by J. K. Rowling. The series is produced and distributed by Warner Bros.');
INSERT INTO movie (franchise_id, title, genre, release_year, director, picture, trailer) VALUES (2, 'Harry Potter and the Philosophers Stone', 'Fantasy, Adventure, Family', '2001', 'Chris Columbus', 'https://upload.wikimedia.org/wikipedia/en/7/7a/Harry_Potter_and_the_Philosopher%27s_Stone_banner.jpg', 'https://www.youtube.com/watch?v=mNgwNXKBEW0');
INSERT INTO movie (franchise_id, title, genre, release_year, director, picture, trailer) VALUES (2, 'Harry Potter and the Prisoner of Azkaban', 'Fantasy, Adventure', '2004', 'Alfonso Cuarón', 'https://upload.wikimedia.org/wikipedia/en/b/bc/Prisoner_of_azkaban_UK_poster.jpg', 'https://www.youtube.com/watch?v=lAxgztbYDbs');
INSERT INTO movie (franchise_id, title, genre, release_year, director, picture, trailer) VALUES (2, 'Harry Potter and the Chamber of Secrets ', 'Fantasy, Adventure, Family', '2002', 'Chris Columbus', 'https://upload.wikimedia.org/wikipedia/en/c/c0/Harry_Potter_and_the_Chamber_of_Secrets_movie.jpg', 'https://www.youtube.com/watch?v=jBltxS8HfQ4');

--Winnie the pooh franchise and movies
INSERT INTO franchise (name, description) VALUES ('Winnie the Pooh', 'a film series based on a series of childrens books written by David Benedictus. Through their many adventures, a teddy bear and his friends learn why friendship is the most important thing.');
INSERT INTO movie (franchise_id, title, genre, release_year, director, picture, trailer) VALUES (3, 'The Tigger Movie', 'Adventure, Family', '2000', 'Jun Falkenstein', 'https://upload.wikimedia.org/wikipedia/en/1/13/The_Tigger_Movie_film.jpg', 'https://www.youtube.com/watch?v=zSA6pZjoFQg');
INSERT INTO movie (franchise_id, title, genre, release_year, director, picture, trailer) VALUES (3, 'Piglet''s Big Movie', 'Fantasy, Adventure, Family', '2003', 'Francis Glebas', 'https://upload.wikimedia.org/wikipedia/en/d/d1/Piglets_big_movie_teaser.jpg', 'https://www.youtube.com/watch?v=wgbU7gfpxTw');
INSERT INTO movie (franchise_id, title, genre, release_year, director, picture, trailer) VALUES (3, 'Christopher Robin', 'Fantasy, Adventure, Family', '2018', 'Marc Forster', 'https://upload.wikimedia.org/wikipedia/en/a/a9/Christopher_Robin_poster.png', 'https://www.youtube.com/watch?v=0URpDxIjZrQ');

--Characters Marvel
INSERT INTO character (full_name, alias, gender, url_photo) VALUES ('Tony Stark', 'Iron Man', 'male', 'https://insidethemagic.net/wp-content/uploads/2021/05/Tony-Stark-As-Iron-Man-e1671739595224.jpeg');
INSERT INTO character (full_name, alias, gender, url_photo) VALUES ('Peter Parker', 'Spider-man', 'male', 'https://upload.wikimedia.org/wikipedia/en/0/0f/Tom_Holland_as_Spider-Man.jpg');
INSERT INTO character (full_name, alias, gender, url_photo) VALUES ('Thor Odensson', 'NaN', 'male', 'https://upload.wikimedia.org/wikipedia/en/3/3c/Chris_Hemsworth_as_Thor.jpg');

--Characters Harry Potter
INSERT INTO character (full_name, alias, gender, url_photo) VALUES ('Harry Potter', 'The boy who lived', 'male', 'https://static.wikia.nocookie.net/hpbok/images/1/12/Harrypotter1.jpg/revision/latest?cb=20100726152215&path-prefix=sv');
INSERT INTO character (full_name, alias, gender, url_photo) VALUES ('Sirius Black', 'Padfoot', 'male', 'https://upload.wikimedia.org/wikipedia/en/6/6b/Sirius_Black.jpeg');
INSERT INTO character (full_name, alias, gender, url_photo) VALUES ('Arthur Weasley', 'NaN', 'male', 'https://i.pinimg.com/564x/68/4a/8c/684a8c85be17096b3b83f9330e937210.jpg');

--Characters Winnie the Pooh
INSERT INTO character (full_name, alias, gender, url_photo) VALUES ('Winnie the Pooh', 'Pooh Bear', 'male', 'https://upload.wikimedia.org/wikipedia/en/1/10/Winniethepooh.png');
INSERT INTO character (full_name, alias, gender, url_photo) VALUES ('Rabbit', 'NaN', 'male', 'https://upload.wikimedia.org/wikipedia/en/e/e9/Rabbitpooh.jpg');
INSERT INTO character (full_name, alias, gender, url_photo) VALUES ('Christoffer Robin', 'NaN', 'male', 'https://kulturbloggen.com/wp-content/uploads/2018/10/christofferrobinvuxen.jpg');

--Junction table - Movie to Character
--Tony Stark
INSERT INTO characters_movies (character_id, movie_id) VALUES (1,1);
INSERT INTO characters_movies (character_id, movie_id) VALUES (1,2);
INSERT INTO characters_movies (character_id, movie_id) VALUES (1,3);

--Peter Parker
INSERT INTO characters_movies (character_id, movie_id) VALUES (2,1);
INSERT INTO characters_movies (character_id, movie_id) VALUES (2,3);

--Thor Odensson
INSERT INTO characters_movies (character_id, movie_id) VALUES (3,1);

--Harry Potter
INSERT INTO characters_movies (character_id, movie_id) VALUES (4,4);
INSERT INTO characters_movies (character_id, movie_id) VALUES (4,5);
INSERT INTO characters_movies (character_id, movie_id) VALUES (4,6);

--Sirius Black
INSERT INTO characters_movies (character_id, movie_id) VALUES (5,5);

--Arthur Weasley
INSERT INTO characters_movies (character_id, movie_id) VALUES (6,5);
INSERT INTO characters_movies (character_id, movie_id) VALUES (6,6);

--Winnie the Pooh
INSERT INTO characters_movies (character_id, movie_id) VALUES (7,7);
INSERT INTO characters_movies (character_id, movie_id) VALUES (7,8);
INSERT INTO characters_movies (character_id, movie_id) VALUES (7,9);

--Rabbit
INSERT INTO characters_movies (character_id, movie_id) VALUES (8,7);
INSERT INTO characters_movies (character_id, movie_id) VALUES (8,8);
INSERT INTO characters_movies (character_id, movie_id) VALUES (8,9);

--Christoffer Robin
INSERT INTO characters_movies (character_id, movie_id) VALUES (9,9);









--Harry Potter