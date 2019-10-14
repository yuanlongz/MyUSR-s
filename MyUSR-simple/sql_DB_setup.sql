DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
DROP TABLE IF EXISTS users		CASCADE;
DROP TABLE IF EXISTS service 	CASCADE;
DROP TABLE IF EXISTS item;

CREATE TABLE users(
	user_id 		VARCHAR(36) NOT NULL,
	name 			VARCHAR(50) NOT NULL,
	account 		VARCHAR(60),
	password 		VARCHAR(36),
	role 			VARCHAR(15) NOT NULL,
   
	PRIMARY KEY (user_id),
	UNIQUE(account)
);


CREATE TABLE service(
	service_id	VARCHAR (36) 	NOT NULL,
	user_id 	VARCHAR (36) 	NOT NULL,
	address		VARCHAR (255) 	NOT NULL,
	item_list	VARCHAR (2000),
	description	VARCHAR (2000),
	status		VARCHAR (20) NOT NULL,
	bill		VARCHAR (10) NOT NULL,
	PRIMARY KEY (service_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE item(
	name 		VARCHAR (50) NOT NULL,
	unit_price	VARCHAR (10) NOT NULL,
	
	PRIMARY KEY (name)
);

/*default user value*/
INSERT INTO users VALUES ('00001', 'Admin', 'admin@usr.au', 'admin', 'ADMIN');
INSERT INTO users VALUES ('00002', 'Customer', 'customer@gmail.com', 'customer'
, 'CUSTOMER');
/*default service value*/
INSERT INTO service VALUES ('00001', '00002', 'ogilvie Street', 'ipad:2,mac:1'
, 'pickup', 'REQUESTED','35');
/*default item value*/
INSERT INTO item VALUES ('mac', '10');
INSERT INTO item VALUES ('ipad', '5');
