create table PRODUCT
(
    id           serial primary key,
    product_name varchar(100) NOT NULL,
    product_info varchar(100) NOT NULL,
    price        int NOT NULL,
    category_id  int
);