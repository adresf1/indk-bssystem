CREATE SCHEMA WareHouse;

SET search_path To WareHouse;

CREATE DOMAIN name varchar(255);
CREATE DOMAIN ID varchar(32);
CREATE DOMAIN category INT;
CREATE DOMAIN productDescription VARCHAR(255);
CREATE DOMAIN productionDate varchar(16);
CREATE DOMAIN expirationDate varchar(16);
CREATE DOMAIN barcode BIGINT;
CREATE DOMAIN price DECIMAL(5,2);
CREATE DOMAIN quantity DECIMAL(5,2);
CREATE DOMAIN lowStock DECIMAL(5,2);
CREATE DOMAIN unitType varchar(10);

CREATE DOMAIN database varchar(255);
CREATE DOMAIN country varchar(50);

CREATE TABLE products(
        products_ID ID primary key NOT NULL ,
        products_name name,
        products_category category,
        products_productDescription productDescription,
        products_productionDate productionDate,
        products_expirationDate expirationDate,
        products_barcode barcode,
        products_price price,
        products_quantity quantity,
        products_lowStock lowStock,
        products_unitType unitType
);

--DROP TABLE products;

CREATE TABLE organic(
    organic_ID ID primary key NOT NULL,
    organic_database database,
    organic_description productDescription,
    organic_certificationDate productionDate,
    organic_expirationDate expirationDate,
    organic_originCountry country,
    organic_organization name,
    organic_productsID ID,
    FOREIGN KEY (organic_productsID) REFERENCES products(products_ID)
);

CREATE TABLE batchNumber(
    batch_ID ID primary key NOT NULL,
    batch_database database,
    batch_description productDescription,
    batch_originCountry country,
    batch_organization name,
    batch_productsID ID,
    FOREIGN KEY (batch_productsID) REFERENCES products(products_ID)
);