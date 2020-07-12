

DROP TABLE IF EXISTS users;

CREATE TABLE users(
  user_name VARCHAR(20) PRIMARY KEY,
  first_name VARCHAR(20),
  last_name VARCHAR(20) ,
  career VARCHAR(200) DEFAULT NULL,
  password VARCHAR(200) NOT NULL,
);

DROP TABLE IF EXISTS usertoken;

CREATE TABLE usertoken(
   user_name VARCHAR(20) PRIMARY KEY,
   token VARCHAR (500) DEFAULT NULL
);