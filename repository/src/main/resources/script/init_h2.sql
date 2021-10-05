CREATE TABLE if not exists gift_certificate (
	id bigserial,
	name VARCHAR(100)           NOT NULL ,
	description text           NOT NULL,
	price numeric (10,2)       NOT NULL,
	duration int              NOT NULL,
	create_date       timestamp   NOT NULL,
	last_update_date  timestamp   NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE if not exists tag (
	id bigserial,
	name VARCHAR(100)          NOT NULL ,
	PRIMARY KEY (id)
);


CREATE TABLE if not exists gift_certificate_tag(
	id bigserial NOT NULL,
	gift_certificate_id bigint NOT NULL,
	tag_id bigint NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_gift_certificte_id FOREIGN KEY (gift_certificate_id) references gift_certificate(id) ON DELETE CASCADE,
	CONSTRAINT fk_tag_id FOREIGN KEY (tag_id) references tag(id) ON DELETE CASCADE
);

CREATE TABLE if not exists users (
	id bigserial,
	first_name VARCHAR(50)     NOT NULL ,
	last_name  VARCHAR(50)     NOT NULL ,
	email VARCHAR(50)          NOT NULL ,
	password VARCHAR(50)       NOT NULL ,
	PRIMARY KEY (id)
);

CREATE TABLE if not exists user_order (
	id bigserial,
	cost numeric (10,2)            NOT NULL,
	purchase_date       timestamp   NOT NULL,
	PRIMARY KEY (id),
	user_id bigint not null,
    certificate_id bigint not null,
    foreign key(user_id) references users(id) ON DELETE CASCADE,
    foreign key(certificate_id) references gift_certificate(id) ON DELETE CASCADE
);

