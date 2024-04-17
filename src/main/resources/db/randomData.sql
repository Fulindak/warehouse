H2:
-- product_type
INSERT INTO product_type (id, name)
SELECT
    next value for product_type_seq AS id,
    CONCAT('Type_', CAST(CAST(RANDOM() * 1000000 AS INT) AS VARCHAR)) AS name
FROM system_range(1, 100); -- количество записей

-- product
INSERT INTO product (id, name, article, quantity, price, update_at, create_at)
SELECT
    UUID() AS id,
    CONCAT('Product_', CAST(CAST(RANDOM() * 100 AS INT) AS VARCHAR)) AS name,
    CONCAT('Article_', CAST(CAST(RANDOM() * 100 AS INT) AS VARCHAR)) AS article,
    CAST(RANDOM() * 1000 AS INT) AS quantity,
    CAST(RANDOM() * 100000 AS BIGINT) AS price,
    CURRENT_TIMESTAMP AS update_at,
    CURRENT_TIMESTAMP AS create_at
FROM system_range(1, 10000); -- количество записей

-- product_product_type
INSERT INTO product_product_type (product_id, product_type_id)
SELECT
    p.id,
    pt.id
FROM
    product p,
    product_type pt
ORDER BY RANDOM()
LIMIT 10000; ---количество записей

PostgreSQL:
-- product_type
INSERT INTO product_type (id, name)
SELECT
    NEXTVAL('product_type_seq') AS id,
    CONCAT('Type_', CAST(CAST(RANDOM() * 1000000 AS INT) AS VARCHAR)) AS name
FROM generate_series(1, 100); -- количество записей

-- product
INSERT INTO product (id, name, article, quantity, price, update_at, create_at)
SELECT
    GEN_RANDOM_UUID() AS id,
    CONCAT('Product_', ROW_NUMBER() OVER (ORDER BY RANDOM())) AS name,
    SUBSTRING(MD5(RANDOM()::TEXT), 1, 12) AS article,
    CAST(RANDOM() * 1000 AS INT) AS quantity,
    CAST(RANDOM() * 100000 AS BIGINT) AS price,
    CURRENT_TIMESTAMP AS update_at,
    CURRENT_TIMESTAMP AS create_at
FROM generate_series(1, 100000); -- количество записей

-- product_product_type 
INSERT INTO product_product_type (product_id, product_type_id)
SELECT
    p.id,
    pt.id
FROM
    product p,
    product_type pt
ORDER BY RANDOM()
LIMIT 100000; -- количество записей
