-- -----------------------------------------------------
-- Fill in role table
-- -----------------------------------------------------
INSERT INTO role(name) VALUES('ROLE_SIMPLE');
INSERT INTO role(name) VALUES('ROLE_PREMIUM');


-- -----------------------------------------------------
-- Fill in user table
-- -----------------------------------------------------
INSERT INTO user(name, email, password, role_id, is_enabled) VALUES('Igor  Parada', 'igorParada@gmail.com', '1111',2, 1);
INSERT INTO user(name, email, password, role_id, is_enabled) VALUES('Roman Dyndyn', 'romanDyndyn@gmail.com', '2222',2, 1);
INSERT INTO user(name, email, password, role_id, is_enabled) VALUES('Pavlo Bendus', 'pavloBendus@gmail.com', '3333',2, 1);
INSERT INTO user(name, email, password, role_id, is_enabled) VALUES('Igor Kryviuk', 'igorKryviuk@gmail.com', '4444',2, 1);
INSERT INTO user(name, email, password, role_id, is_enabled) VALUES('Oleh Pochernin', 'olehPochernin@gmail.com', '5555',2, 1);
INSERT INTO user(name, email, password, role_id, is_enabled) VALUES('Vitaliy Malisevych', 'vitaliyMalisevych@gmail.com', '6666',2, 1);
INSERT INTO user(name, email, password, role_id, is_enabled) VALUES('Nazar Vynnyk', 'nazarVynnyk@gmail.com', '7777',2, 1);


-- -----------------------------------------------------
-- Fill in unit table
-- -----------------------------------------------------
INSERT INTO unit(name) VALUES('л');
INSERT INTO unit(name) VALUES('кг');
INSERT INTO unit(name) VALUES('шт');


-- -----------------------------------------------------
-- Fill in category table
-- -----------------------------------------------------
-- -----------------------------------------------------
-- for default user
-- -----------------------------------------------------
INSERT INTO category(name, user_id) VALUES('Продовольчі товари', NULL);
INSERT INTO category(name, user_id) VALUES('Техніка', NULL);
INSERT INTO category(name, user_id) VALUES('Електроніка', NULL);
INSERT INTO category(name, user_id) VALUES('Hi-Tech', NULL);
INSERT INTO category(name, user_id) VALUES('Промислові товари', NULL);
INSERT INTO category(name, user_id) VALUES('Побутова хімія', NULL);
-- -----------------------------------------------------
-- for user with id 1
-- -----------------------------------------------------
INSERT INTO category(name, user_id) VALUES('Продовольчі товари', 1);
INSERT INTO category(name, user_id) VALUES('Техніка', 1);
INSERT INTO category(name, user_id) VALUES('Телевізори', 1);
-- -----------------------------------------------------
-- for user with id 2
-- -----------------------------------------------------
INSERT INTO category(name, user_id) VALUES('Hi-Tech', 2);
INSERT INTO category(name, user_id) VALUES('Промислові товари', 2);
INSERT INTO category(name, user_id) VALUES('Побутова хімія', 2);
-- -----------------------------------------------------
-- for user with id 3
-- -----------------------------------------------------
INSERT INTO category(name, user_id) VALUES('Продовольчі товари', 3);
INSERT INTO category(name, user_id) VALUES('Техніка', 3);
-- -----------------------------------------------------
-- for user with id 4
-- -----------------------------------------------------
INSERT INTO category(name, user_id) VALUES('Електроніка', 4);
INSERT INTO category(name, user_id) VALUES('Hi-Tech', 4);
-- -----------------------------------------------------
-- for user with id 5
-- -----------------------------------------------------
INSERT INTO category(name, user_id) VALUES('Промислові товари', 5);
INSERT INTO category(name, user_id) VALUES('Телевізори та аксесуари', 5);
-- -----------------------------------------------------
-- for user with id 6
-- -----------------------------------------------------
INSERT INTO category(name, user_id) VALUES('Побутова хімія', 6);
INSERT INTO category(name, user_id) VALUES('Накопичувачі інформації', 6);


-- -----------------------------------------------------
-- Fill in product table
-- -----------------------------------------------------
-- -----------------------------------------------------
-- for default user
-- -----------------------------------------------------
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Олія', 'соняшникова, рафінована', NULL, NULL, 1, 1);
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Apple MacBook Pro 16', 'остання модель', NULL, NULL, 3, 1);
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Sony LCD', 'телевізор з FULL-HD розширенням', NULL, NULL, 3, 1);
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Google Glasses', 'розумні окуляри', NULL, NULL, 3, 1);
-- -----------------------------------------------------
-- for user with id 1
-- -----------------------------------------------------
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Олія', 'соняшникова, рафінована', 1, 7, 1, 1);
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Apple MacBook Pro 16', 'остання модель', 1, NULL, NULL, 1);
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Sony LCD', 'телевізор з FULL-HD розширенням', 1, 9, 3, 0);
-- -----------------------------------------------------
-- for user with id 2
-- -----------------------------------------------------
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Gala', Null, 2, 12, 3, 1);
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Apple MacBook Pro 16', 'передостання модель', 2, 10, 3, 1);
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('LG', 'телевізор з 4K розширенням', 2, 10, 3, 0);
INSERT INTO product(name, description, user_id, category_id, unit_id, is_enabled) VALUES('Persil', Null, 2, 10, 3, 1);


-- -----------------------------------------------------
-- Fill in store table
-- -----------------------------------------------------
INSERT INTO store(name, address, user_id) VALUES('Вопак', 'Галицька, 20', NULL);
INSERT INTO store(name, address, user_id) VALUES('Вопак', 'Галицька, 44', 1);
INSERT INTO store(name, address, user_id) VALUES('Ельдорадо', 'Вовчинецька, 40', 1);
INSERT INTO store(name, address, user_id) VALUES('MOYO', 'Вовчинецька, 40', 2);
INSERT INTO store(name, address, user_id) VALUES('Rozetka', 'Інтернет', 3);
INSERT INTO store(name, address, user_id) VALUES('Вопак', 'Галицька, 20', 4);


-- -----------------------------------------------------
-- Fill in store_products table
-- -----------------------------------------------------
INSERT INTO stores_products(product_id, store_id) VALUES(5, 2);
INSERT INTO stores_products(product_id, store_id) VALUES(6, 3);
INSERT INTO stores_products(product_id, store_id) VALUES(7, 3);
INSERT INTO stores_products(product_id, store_id) VALUES(9, 4);
INSERT INTO stores_products(product_id, store_id) VALUES(10, 4);
INSERT INTO stores_products(product_id, store_id) VALUES(11, 4);


-- -----------------------------------------------------
-- Fill in storages table
-- -----------------------------------------------------
INSERT INTO storage(user_id, product_id, amount, end_date) VALUES(1, 5, 5,null);
INSERT INTO storage(user_id, product_id, amount, end_date) VALUES(1, 6, 1,'2017-01-22');
INSERT INTO storage(user_id, product_id, amount, end_date) VALUES(1, 7, 2,'2017-01-30');
INSERT INTO storage(user_id, product_id, amount, end_date) VALUES(2, 8, 1,'2017-01-10');
INSERT INTO storage(user_id, product_id, amount, end_date) VALUES(2, 10, 5,'2017-01-18');
INSERT INTO storage(user_id, product_id, amount, end_date) VALUES(2, 11, 0,'2017-01-19');


-- -----------------------------------------------------
-- Fill in shopping_list table
-- -----------------------------------------------------
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(1, 5, 1);
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(1, 7, 1);
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(2, 11, 3);
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(2, 8, 1);


-- -----------------------------------------------------
-- Fill in cart table
-- -----------------------------------------------------
INSERT INTO cart(user_id, product_id, amount, store_id) VALUES(1, 6, 1, 3);
INSERT INTO cart(user_id, product_id, amount, store_id) VALUES(2, 11, 1, 4);


-- -----------------------------------------------------
-- Fill in history table
-- -----------------------------------------------------
INSERT INTO history(user_id, product_id, amount, used_date) VALUES(1, 5, 1, '2017-01-18');
INSERT INTO history(user_id, product_id, amount, used_date) VALUES(1, 5, 1, '2017-01-20');
INSERT INTO history(user_id, product_id, amount, used_date) VALUES(1, 5, 2, '2017-01-28');
