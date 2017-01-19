DROP TABLE IF EXISTS product;


CREATE TABLE client
(
  clientname VARCHAR(40),
  apikey VARCHAR(40) UNIQUE
);

CREATE TABLE product
(
  webshop_user VARCHAR(255),
  name VARCHAR(255),
  category VARCHAR(255),
  default_price VARCHAR(255),
  quantity INTEGER

);



