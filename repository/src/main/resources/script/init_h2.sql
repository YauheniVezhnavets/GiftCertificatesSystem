CREATE TABLE gift_certificate
(
    id bigserial,
    name                varchar(256)   NOT NULL,
    description         text           NOT NULL,
    price               decimal(10, 0) NOT NULL,
    duration            int            NOT NULL,
    create_date         datetime       DEFAULT NULL,
    last_update_date    datetime       DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id   bigserial,
    name varchar(256) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE gift_certificate_tag(
    id bigserial,
	gift_certificate_id bigint NOT NULL,
	tag_id bigint NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_gift_certificte_id FOREIGN KEY (gift_certificate_id) references gift_certificate(id) on delete cascade,
	CONSTRAINT fk_tag_id FOREIGN KEY (tag_id) references tag(id)on delete cascade

);

CREATE TABLE if not exists users (
	id bigserial,
	first_name VARCHAR(50)     NOT NULL ,
	last_name  VARCHAR(50)     NOT NULL ,
	email VARCHAR(50)          NOT NULL ,
	password VARCHAR(50)       NOT NULL ,
	PRIMARY KEY (id)
);

CREATE TABLE if not exists orders (
	id bigserial,
	cost numeric (10,2)            NOT NULL,
	purchase_date       timestamp   NOT NULL,
	PRIMARY KEY (id),
	user_id integer not null,
    certificate_id integer not null,
    foreign key(user_id) references users(id),
    foreign key(certificate_id) references gift_certificate(id)
);