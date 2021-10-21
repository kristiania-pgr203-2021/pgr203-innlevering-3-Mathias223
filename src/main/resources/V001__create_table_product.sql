create table product
(
    id            serial primary key,
    product_name  varchar(100),
    category_type varchar(100),
    price         int
);