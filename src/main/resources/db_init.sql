--//genres
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


--//age restrictions
INSERT INTO age_restrictions (min_age) VALUES ('all');
INSERT INTO age_restrictions (min_age) VALUES ('6');
INSERT INTO age_restrictions (min_age) VALUES ('12');
INSERT INTO age_restrictions (min_age) VALUES ('15');
INSERT INTO age_restrictions (min_age) VALUES ('18');


--//ticket types
INSERT INTO ticket_types (name, description, is_available, price) VALUES ('normal', 'normal ticket', true, 20.50);
INSERT INTO ticket_types (name, description, is_available, price) VALUES ('students', 'ticket with students discount', true, 10);
INSERT INTO ticket_types (name, description, is_available, price) VALUES ('exclusive', 'exclusive ticket', false, 100);


--//carts
INSERT INTO carts () VALUES ();
INSERT INTO carts () VALUES ();
INSERT INTO carts () VALUES ();
INSERT INTO carts () VALUES ();
INSERT INTO carts () VALUES ();


--//admins
INSERT INTO admins (email, first_name, last_name, password, phone, role) VALUES ('admin@mail.com', 'Jan', 'Jakubowski', 'admin', '789456123', 'ROLE_ADMIN');


--//users
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active, cart_id) VALUES ('woj.wi@mail.com', 'Wojciech', 'Wiśniewski', 'user1', '789456123', 'ROLE_USER', true, 1);
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active, cart_id) VALUES ('nor.no@mail.com', 'Norbert', 'Nowak', 'user2', '789456456', 'ROLE_USER', true, 2);
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active, cart_id) VALUES ('krzy-ko@mail.com', 'Krzysztof', 'Kowal', 'user3', '789456753', 'ROLE_USER', true, 3);
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active, cart_id) VALUES ('kry-kru@mail.com', 'Krystian', 'Kruk', 'user4', null, 'ROLE_USER', true, 4);
INSERT INTO users (email, first_name, last_name, password, phone, role, is_active, cart_id) VALUES ('luc-la@mail.com', 'Lucjan', 'Lawenda', 'user5', '789456324', 'ROLE_USER', false, 5);


--//subscribers
INSERT INTO subscribers (email) VALUES ('nor.no@mail.com');
INSERT INTO subscribers (email) VALUES ('krzy-ko@mail.com');
INSERT INTO subscribers (email) VALUES ('guest-1@mail.com');
INSERT INTO subscribers (email) VALUES ('guest-2@mail.com');


--//contents & movies
INSERT INTO contents (title, duration, age_restr_id, short_description) VALUES ('The Conjuring', '112', 4, 'Paranormal investigators Ed and Lorraine Warren work to help a family terrorized by a dark presence in their farmhouse.');
INSERT INTO movies (id, is_premiere, rating_count, rating_sum) VALUES (1, false, 0, 0);
INSERT INTO contents (title, duration, age_restr_id, short_description) VALUES ('Scream', '106', 4, 'A year after the murder of her mother, a teenage girl is terrorized by a new killer, who targets the girl and her friends by using horror films as part of a deadly game.');
INSERT INTO movies (id, is_premiere, rating_count, rating_sum) VALUES (2, false, 2, 8);
INSERT INTO contents (title, duration, age_restr_id, short_description) VALUES ('Pretty Woman', '119', 3, 'A man in a legal but hurtful business needs an escort for some social events, and hires a beautiful prostitute he meets... only to fall in love.');
INSERT INTO movies (id, is_premiere, rating_count, rating_sum) VALUES (3, false, 3, 14);


--//genres in movies
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (1, 1);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (1, 5);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (2, 1);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (2, 3);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (3, 4);
INSERT INTO genres_in_movies (movie_id, genre_id) VALUES (3, 12);


--//movies rated by users
INSERT INTO movies_rated_by_users (user_id, movie_id) VALUES (1, 2);
INSERT INTO movies_rated_by_users (user_id, movie_id) VALUES (2, 2);
INSERT INTO movies_rated_by_users (user_id, movie_id) VALUES (1, 3);
INSERT INTO movies_rated_by_users (user_id, movie_id) VALUES (3, 3);
INSERT INTO movies_rated_by_users (user_id, movie_id) VALUES (4, 3);


--//movies to watch by users
INSERT INTO movies_to_watch_by_users (user_id, movie_id) VALUES (1, 1);
INSERT INTO movies_to_watch_by_users (user_id, movie_id) VALUES (2, 3);
INSERT INTO movies_to_watch_by_users (user_id, movie_id) VALUES (5, 1);


--//venues
INSERT INTO venues (name, rows_number, columns_number, is_active) VALUES ('Venue A', 3, 3, true)
INSERT INTO venues (name, rows_number, columns_number, is_active) VALUES ('Venue B', 2, 2, false)
INSERT INTO venues (name, rows_number, columns_number, is_active) VALUES ('Venue C', 2, 2, true)


--//seats
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', true, 1, 1, true, 1, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 1, 2, true, 1, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 1, 3, false, 1, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 2, 1, false, 1, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 2, 2, true, 1, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 2, 3, true, 1, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 3, 1, false, 1, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 3, 2, false, 1, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', true, 3, 3, false, 1, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('double', true, null, null, null, 1, 1, 2);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('double', false, null, null, null, 1, 5, 6);

INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 1, 1, true, 2, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 1, 2, true, 2, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 1, 3, false, 2, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', true, 2, 1, false, 2, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('double', true, null, null, null, 2, 1, 2);

INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 1, 1, false, 3, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', true, 1, 2, false, 3, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', false, 2, 1, false, 3, null, null);
INSERT INTO seats (seat_type, is_vip, seat_row, seat_col, seat_part, venue_id, left_seat_id, right_seat_id) VALUES ('single', true, 2, 2, false, 3, null, null);


--//shows
INSERT INTO shows (content_id, venue_id, start_date_time, end_date_time, break_after_in_minutes) VALUES (1, 1, '2024-12-15T17:10:00.001Z', '2024-12-15T19:20:00.000Z', 18);
INSERT INTO shows (content_id, venue_id, start_date_time, end_date_time, break_after_in_minutes) VALUES (1, 1, '2024-12-15T17:10:00.001Z', '2024-12-15T19:20:00.000Z', 18);
INSERT INTO shows (content_id, venue_id, start_date_time, end_date_time, break_after_in_minutes) VALUES (2, 1, '2024-12-15T19:20:00.001Z', '2024-12-15T21:30:00.000Z', 24);
INSERT INTO shows (content_id, venue_id, start_date_time, end_date_time, break_after_in_minutes) VALUES (1, 1, '2021-12-15T15:00:00.001Z', '2021-12-15T17:10:00.000Z', 18);
INSERT INTO shows (content_id, venue_id, start_date_time, end_date_time, break_after_in_minutes) VALUES (1, 1, '2021-12-15T15:00:00.001Z', '2021-12-15T17:10:00.000Z', 18);


--//tickets
INSERT INTO tickets (seat_id, show_id, ticket_type_id, is_paid, order_id) VALUES (3, 1, 1, false, null);
INSERT INTO tickets (seat_id, show_id, ticket_type_id, is_paid, order_id) VALUES (4, 1, 1, false, null);
INSERT INTO tickets (seat_id, show_id, ticket_type_id, is_paid, order_id) VALUES (7, 1, 1, false, null);
INSERT INTO tickets (seat_id, show_id, ticket_type_id, is_paid, order_id) VALUES (8, 1, 2, false, null);
INSERT INTO tickets (seat_id, show_id, ticket_type_id, is_paid, order_id) VALUES (9, 1, 2, false, null);
INSERT INTO tickets (seat_id, show_id, ticket_type_id, is_paid, order_id) VALUES (11, 1, 2, false, null);
INSERT INTO tickets (seat_id, show_id, ticket_type_id, is_paid, order_id) VALUES (2, 2, 1, false, null);


--//tickets in carts
INSERT INTO tickets_in_carts (cart_id, ticket_id) VALUES (1, 1);
INSERT INTO tickets_in_carts (cart_id, ticket_id) VALUES (1, 2);
INSERT INTO tickets_in_carts (cart_id, ticket_id) VALUES (2, 3);


--SELECT * FROM TICKETS;
--SELECT * FROM TICKETS_IN_CARTS;
--SELECT * FROM ORDERS;
--SELECT * FROM USERS;
