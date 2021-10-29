CREATE TABLE if not exists gift_certificate (
	id bigserial,
	name VARCHAR(100)           NOT NULL ,
	description text           NOT NULL,
	price numeric (10,2)       NOT NULL,
	duration int              NOT NULL,
	create_date       timestamp   NOT NULL,
	last_update_date  timestamp   NOT NULL,
	is_active boolean          DEFAULT 'TRUE' NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE if not exists tag (
	id bigserial,
	name VARCHAR(100)          NOT NULL ,
	is_active boolean          DEFAULT 'TRUE' NOT NULL,
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
	email VARCHAR(50)          UNIQUE NOT NULL,
	password VARCHAR(100)       NOT NULL ,
	is_active boolean          DEFAULT 'TRUE' NOT NULL,
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

CREATE TABLE if not exists roles (
	id bigserial,
	role VARCHAR(10)          NOT NULL ,
	PRIMARY KEY (id)
);


CREATE TABLE if not exists user_role (
	id bigserial NOT NULL,
	user_id bigint NOT NULL,
	role_id bigint NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_user_id FOREIGN KEY (user_id) references users(id) ON DELETE CASCADE,
	CONSTRAINT fk_role_id FOREIGN KEY (role_id) references roles(id) ON DELETE CASCADE
);

CREATE TABLE if not exists permissions (
	id bigserial,
	permission VARCHAR(30)          NOT NULL ,
	PRIMARY KEY (id)
);

CREATE TABLE if not exists role_permission (
	id bigserial NOT NULL,
	role_id bigint NOT NULL,
	permission_id bigint NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_role_id FOREIGN KEY (role_id) references roles(id) ON DELETE CASCADE,
	CONSTRAINT fk_permission_id FOREIGN KEY (permission_id) references permissions(id) ON DELETE CASCADE
);


