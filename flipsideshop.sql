ALTER TABLE IF EXISTS Login DROP CONSTRAINT IF EXISTS fk_user_id;
ALTER TABLE IF EXISTS Product DROP CONSTRAINT IF EXISTS fk_supplier_id;
ALTER TABLE IF EXISTS Product DROP CONSTRAINT IF EXISTS fk_product_category;
ALTER TABLE IF EXISTS Cart DROP CONSTRAINT IF EXISTS fk_user_id;
ALTER TABLE IF EXISTS Cart DROP CONSTRAINT IF EXISTS fk_product_id;
ALTER TABLE IF EXISTS Contact_info DROP CONSTRAINT IF EXISTS fk_user_id;
ALTER TABLE IF EXISTS Address DROP CONSTRAINT IF EXISTS fk_user_id;
ALTER TABLE IF EXISTS Orders DROP CONSTRAINT IF EXISTS fk_user_id;
ALTER TABLE IF EXISTS Ordered_items DROP CONSTRAINT IF EXISTS fk_order_id; --ordered_items_order_id_fkey;
ALTER TABLE IF EXISTS Ordered_items DROP CONSTRAINT IF EXISTS fk_product_id; --ordered_items_product_id_fkey;




DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
  user_id serial PRIMARY KEY,
  status varchar
);

DROP TABLE IF EXISTS Login;
CREATE TABLE Login (
  login_id serial PRIMARY KEY,
  user_name varchar(20) NOT NULL,
  password varchar NOT NULL,
  user_id integer,
  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

DROP TABLE IF EXISTS Product_category;
CREATE TABLE Product_category (
  category_id serial PRIMARY KEY ,
  name varchar(20),
  description varchar,
  department varchar
);

DROP TABLE IF EXISTS Supplier;
CREATE TABLE Supplier (
  supplier_id serial PRIMARY KEY,
  name varchar,
  description varchar
);

DROP TABLE IF EXISTS Product;
CREATE TABLE Product (
  product_id serial PRIMARY KEY,
  supplier_id integer,
  name varchar NOT NULL,
  description varchar,
  price decimal(6,2) NOT NULL,
  default_currency varchar(3) NOT NULL,
  product_category integer,
  CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id),
  CONSTRAINT fk_product_category FOREIGN KEY (product_category) REFERENCES Product_category(category_id)
);

DROP TABLE IF EXISTS Cart;
CREATE TABLE Cart (
  cart_id serial PRIMARY KEY,
  user_id integer,
  product_id integer,

  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(user_id),
  CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

DROP TABLE IF EXISTS Contact_info;
CREATE TABLE Contact_info (
  contact_id serial PRIMARY KEY,
  user_id integer,
  first_name varchar,
  last_name varchar,
  email varchar(255),
  phone varchar(15),
 CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

DROP TABLE IF EXISTS Address;
CREATE TABLE Address (
    address_id serial PRIMARY KEY,
    user_id integer,
    country varchar(50),
    state varchar(20),
    city varchar(50),
    street varchar(100),
    postal_code integer,
    street_num integer,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


DROP TABLE IF EXISTS Orders;
CREATE TABLE Orders (
  order_id serial PRIMARY KEY,
  user_id integer,
  payment_method varchar,
  status varchar,
  order_time date,
  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


DROP TABLE IF EXISTS Ordered_items;
CREATE TABLE Ordered_items (
  ordered_items_id serial PRIMARY KEY,
  order_id integer,
  product_id integer,
  quantity integer,
  CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES Orders (order_id) ON DELETE CASCADE,
  CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES Product(product_id) ON DELETE CASCADE
);

