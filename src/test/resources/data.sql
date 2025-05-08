DROP TABLE IF EXISTS product;
--DROP SEQUENCE IF EXISTS PRODUCT_SEQ;



CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    brand_name VARCHAR(50),
    price DOUBLE,
    currency VARCHAR(3),
    description VARCHAR(100)
);

--CREATE SEQUENCE PRODUCT_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

INSERT INTO product ( brand_name, price, currency, description) VALUES
( 'BrandA', 19.99, 'EUR', 'High-quality product from BrandA.'),
( 'BrandB', 25.50, 'EUR', 'Stylish and durable item from BrandB.'),
( 'BrandC', 15.00, 'EUR', 'Affordable choice from BrandC.'),
( 'BrandD', 99.99, 'EUR', 'Premium product designed by BrandD.'),
( 'BrandE', 40.00, 'EUR', 'Innovative solution by BrandE.'),
( 'BrandF', 7.49, 'EUR', 'Budget-friendly item by BrandF.'),
( 'BrandG', 150.00, 'EUR', 'Luxury product from BrandG.'),
( 'BrandH', 30.25, 'EUR', 'Performance-driven item by BrandH.'),
( 'BrandI', 22.99, 'EUR', 'Versatile option by BrandI.'),
( 'BrandJ', 5.00, 'EUR', 'Basic product from BrandJ.');