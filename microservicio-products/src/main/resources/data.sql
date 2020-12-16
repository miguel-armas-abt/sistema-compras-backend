INSERT INTO tb_categories (id, name) VALUES (1, 'zapatos');
INSERT INTO tb_categories (id, name) VALUES (2, 'libros');
INSERT INTO tb_categories (id, name) VALUES (3, 'elecrodomesticos');

INSERT INTO tb_products (id, name, description, stock, price, status, create_at, category_id)
VALUES (1, 'zapatillas adidas', 'cloudfoam ultimate', 5, 178.89, 'CREATED', '2018-09-05', 1);

INSERT INTO tb_products (id, name, description, stock, price, status, create_at, category_id)
VALUES (2, 'zapataillas nike', 'skater j40', 4, 12.5, 'CREATED', '2018-09-05', 1);

INSERT INTO tb_products (id, name, description, stock, price, status, create_at, category_id)
VALUES (3, 'Backed con Springboot', 'Microservicios', 12, 40.06, 'CREATED', '2018-09-05', 2);