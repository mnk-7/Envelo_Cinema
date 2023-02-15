//genres
INSERT INTO genres (name) VALUES ('horror');
INSERT INTO genres (name) VALUES ('thriller');
INSERT INTO genres (name) VALUES ('action');
INSERT INTO genres (name) VALUES ('comedy');
INSERT INTO genres (name) VALUES ('drama');
INSERT INTO genres (name) VALUES ('documentary');
INSERT INTO genres (name) VALUES ('adventure');
INSERT INTO genres (name) VALUES ('historical');
INSERT INTO genres (name) VALUES ('fantasy');
INSERT INTO genres (name) VALUES ('science fiction');
INSERT INTO genres (name) VALUES ('musical');
INSERT INTO genres (name) VALUES ('romance');
INSERT INTO genres (name) VALUES ('western');


//age restrictions
INSERT INTO age_restrictions (min_age) VALUES ('all');
INSERT INTO age_restrictions (min_age) VALUES ('6');
INSERT INTO age_restrictions (min_age) VALUES ('12');
INSERT INTO age_restrictions (min_age) VALUES ('15');
INSERT INTO age_restrictions (min_age) VALUES ('18');


//ticket types
INSERT INTO ticket_types (name, description, is_available, price) VALUES ('normal', 'normal ticket', true, 20.50);
INSERT INTO ticket_types (name, description, is_available, price) VALUES ('students', 'ticket with students discount', true, 10);
INSERT INTO ticket_types (name, description, is_available, price) VALUES ('exclusive', 'exclusive ticket', false, 100);


//admins
INSERT INTO admins (email, first_name, last_name, password, phone, role) VALUES ('admin@mail.com', 'Jan', 'Jakubowski', 'admin', '789456123', 'ROLE_ADMIN');


//users
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active) VALUES ('woj.wi@mail.com', 'Wojciech', 'Wiśniewski', 'user1', '789456123', 'ROLE_USER', true);
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active) VALUES ('nor.no@mail.com', 'Norbert', 'Nowak', 'user2', '789456456', 'ROLE_USER', true);
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active) VALUES ('krzy-ko@mail.com', 'Krzysztof', 'Kowal', 'user3', '789456753', 'ROLE_USER', true);
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active) VALUES ('kry-kru@mail.com', 'Krystian', 'Kruk', 'user4', null, 'ROLE_USER', true);
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active) VALUES ('luc-la@mail.com', 'Lucjan', 'Lawenda', 'user5', '789456324', 'ROLE_USER', false);


//contents & movies
INSERT INTO contents (title, duration, age_restr_id, short_description) VALUES ('The Conjuring', '112', 4, 'Paranormal investigators Ed and Lorraine Warren work to help a family terrorized by a dark presence in their farmhouse.');
INSERT INTO movies (id, is_premiere, rating_count, rating_sum) VALUES (1, false, 0, 0);
INSERT INTO contents (title, duration, age_restr_id, short_description) VALUES ('Scream', '106', 4, 'A year after the murder of her mother, a teenage girl is terrorized by a new killer, who targets the girl and her friends by using horror films as part of a deadly game.');
INSERT INTO movies (id, is_premiere, rating_count, rating_sum) VALUES (2, false, 5, 23);
INSERT INTO contents (title, duration, age_restr_id, short_description) VALUES ('Pretty Woman', '119', 3, 'A man in a legal but hurtful business needs an escort for some social events, and hires a beautiful prostitute he meets... only to fall in love.');
INSERT INTO movies (id, is_premiere, rating_count, rating_sum) VALUES (3, false, 12, 60);


//genres in movies
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (1, 1);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (1, 5);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (2, 1);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (2, 3);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (3, 4);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (3, 12);
