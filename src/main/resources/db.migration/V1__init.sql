CREATE TABLE IF NOT EXISTS product
(
    id uuid NOT NULL,
    name varchar(256),
    article varchar(12) UNIQUE NOT NULL,
    quantity integer NOT NULL,
    price bigint NOT NULL,
    update_at timestamp,
    create_at timestamp,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product_type
(
    id bigint NOT NULL,
    name varchar(256) UNIQUE NOT NULL,
    CONSTRAINT product_type_pkey PRIMARY KEY (id)
);

create sequence if not exists product_type_seq
    start 1 increment 20;

CREATE TABLE IF NOT EXISTS product_product_type
(
    product_id uuid NOT NULL,
    product_type_id bigint NOT NULL,
    CONSTRAINT fk_product
        FOREIGN KEY(product_id)
            REFERENCES product(id),
    CONSTRAINT fk_product_type
        FOREIGN KEY(product_type_id)
            REFERENCES product_type(id)
);
