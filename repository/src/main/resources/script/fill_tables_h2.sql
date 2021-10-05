insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('Golden_Coffee', 'Taste our best coffee.', 30.00, 30, '2021-08-31T03:12:15Z', '2021-08-31T03:12:15Z');
insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('Bate', 'You can visit all home matches in season ', 100, 270, '2021-08-30T03:12:15.156Z', '2021-08-30T03:12:15.156Z');
insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('Friends_bar', 'You can selebrate your birhday in our bar', 1000.00, 10, '2021-08-29T03:12:15.156Z', '2021-08-29T03:12:15.156Z');
insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('Silver_screen', 'You can watch new movies in our cinema', 20.00, 45, '2021-08-28T03:12:15.156Z', '2021-08-28T03:12:15.156Z');
insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('Bershka', 'This certificate for buying our new collection', 100.00, 90, '2021-08-29T03:12:15.156Z', '2021-08-29T03:12:15.156Z');
insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('Yandex_music', 'You can gift a subscription for 6 months', 50.00, 180, '2021-08-30T03:12:15.156Z', '2021-08-30T03:12:15.156Z');
insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('iTunes_gift_card', 'Apple Gift Cards are solely for the purchase of goods and services from the Apple Store, located in iTunes.',
70.00, 50, '2021-08-31T03:12:15.156Z', '2021-08-31T03:12:15.156Z');



insert into tag(name)
values ('Relax');
insert into tag(name)
values ('Music');
insert into tag(name)
values ('Movie');
insert into tag(name)
values ('Shopping');
insert into tag(name)
values ('Coffee');
insert into tag(name)
values ('Sport');


insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (1, 1);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (1, 5);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (2, 1);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (2, 6);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (3, 1);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (3, 2);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (3, 5);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (4, 1);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (4, 3);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (5, 1);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (5, 4);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (6, 2);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (6, 4);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (7, 2);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (7, 4);


insert into users (first_name, last_name, email, password) values ('Ivan', 'Ivanov', 'ivan@mail.ru', 'ivan');
insert into users (first_name, last_name, email, password) values ('Petr', 'Petrov', 'petr@mail.ru', 'petr');
insert into users (first_name, last_name, email, password) values ('Semen', 'Semenov', 'semen@mail.ru', 'semen');
insert into users (first_name, last_name, email, password) values ('Andrey', 'Andreev', 'andrey@mail.ru', 'andrey');
insert into users (first_name, last_name, email, password) values ('Nikolay', 'Nikolaev', 'nikolay@mail.ru', 'nikolay');


INSERT INTO user_order (cost, purchase_date, user_id, certificate_id) VALUES (100, '2021-09-23T03:12:15.156Z', 1, 2);
INSERT INTO user_order (cost, purchase_date, user_id, certificate_id) VALUES (100, '2021-09-24T03:12:15.156Z', 1, 5);
INSERT INTO user_order (cost, purchase_date, user_id, certificate_id) VALUES (1000, '2021-09-25T03:12:15.156Z', 2, 3);
INSERT INTO user_order (cost, purchase_date, user_id, certificate_id) VALUES (30, '2021-09-20T03:12:15.156Z', 3, 1);
INSERT INTO user_order (cost, purchase_date, user_id, certificate_id) VALUES (50, '2021-09-22T03:12:15.156Z', 4, 6);
INSERT INTO user_order (cost, purchase_date, user_id, certificate_id) VALUES (20, '2021-09-21T03:12:15.156Z', 5, 4);
INSERT INTO user_order (cost, purchase_date, user_id, certificate_id) VALUES (70, '2021-09-25T03:12:15.156Z', 5, 7);


