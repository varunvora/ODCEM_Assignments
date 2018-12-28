DROP database IF EXISTS ODCEM;
CREATE database ODCEM;
CONNECT ODCEM;

CREATE TABLE items(
    name varchar(50) PRIMARY KEY,
    type varchar(20),
    price double DEFAULT -1.0,
    quantity integer DEFAULT -1,
    tax double DEFAULT -1.0,
    totalPrice double DEFAULT -1.0,
    CHECK (type IN ("raw", "manufactured", "imported"))
);

INSERT INTO items (name, type, price, quantity)
VALUES
("laptop", "imported", 35000.00, 2),
("jute", "raw", 40.00, 50),
("steel", "imported", 400.00, 10),
("car", "manufactured", 500000.00, 1);
