insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('Golden_Coffee', 'Taste our best coffee.', 30.00, 30, '2021-08-31T03:12:15.156Z', '2021-08-31T03:12:15.156Z');
insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('Bate', 'You can visit all home matches in season ', 100, 270, '2021-08-30T03:12:15.156Z', '2021-08-30T03:12:15.156Z');


insert into tag(name)
values ('Relax');
insert into tag(name)
values ('Music');



insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (1, 1);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (1, 2);
insert into gift_certificate_tag (gift_certificate_id, tag_id)
values (2, 1);


insert into users (first_name, last_name, email, password) values ('Ivan', 'Ivanov', 'ivan@mail.ru', 'ivan');
insert into users (first_name, last_name, email, password) values ('Petr', 'Petrov', 'petr@mail.ru', 'petr');
insert into users (first_name, last_name, email, password) values ('Semen', 'Semenov', 'semen@mail.ru', 'semen');
insert into users (first_name, last_name, email, password) values ('Andrey', 'Andreev', 'andrey@mail.ru', 'andrey');
insert into users (first_name, last_name, email, password) values ('Nikolay', 'Nikolaev', 'nikolay@mail.ru', 'nikolay');


