DROP TABLE IF EXISTS webshopuser;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS product;


CREATE TABLE client
(
  clientname VARCHAR(40),
  apikey VARCHAR(40) UNIQUE
);

CREATE TABLE webshopuser
(
  id SERIAL PRIMARY KEY NOT NULL,
  username VARCHAR(255)
);

CREATE TABLE product
(
  name VARCHAR(255),
  category VARCHAR(255),
  default_price VARCHAR(255),
  quantity INTEGER,
  user_id INT REFERENCES webshopuser(id)

);



