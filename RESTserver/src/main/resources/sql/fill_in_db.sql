INSERT INTO user(name, email, password, token, is_enabled) VALUES('Vasia Pupkin', 'vasiapupkin@gmail.com', '1111', 'qljbelkjbi2pughiu2h23ui2oi3', 1);
INSERT INTO user(name, email, password, token, is_enabled) VALUES('Roman Dyndyn', 'romandyndyn@gmail.com', '2222', 'qasdflnsdafsfhiu2hfd3ui2oi3', 1);
INSERT INTO user(name, email, password, token, is_enabled) VALUES('Pavlo Bendus', 'pavlobendus@gmail.com', '3333', 'bgfbfgbfgbgpughiu2h23ui2oi3', 1);

INSERT INTO unit(name) VALUES('л');
INSERT INTO unit(name) VALUES('кг');
INSERT INTO unit(name) VALUES('шт');

INSERT INTO category(name, user_id) VALUES('Продовольчі товари', 0);
INSERT INTO category(name, user_id) VALUES('Техніка', 1);
INSERT INTO category(name, user_id) VALUES('Електроніка', 2);
INSERT INTO category(name, user_id) VALUES('Hi-Tech', 3);

INSERT INTO product(name, description, user_id, category_id, unit_id) VALUES('Олія', 'соняшникова, рафінована', 0, 1, 1);
INSERT INTO product(name, description, user_id, category_id, unit_id) VALUES('Apple MacBook Pro 16', 'остання модель', 1, 2, 3);
INSERT INTO product(name, description, user_id, category_id, unit_id) VALUES('Sony LCD', 'телевізор з FULL-HD розширенням', 2, 3, 3);
INSERT INTO product(name, description, user_id, category_id, unit_id) VALUES('Google Glasses', 'розумні окуляри', 3, 4, 3);

INSERT INTO store(name, address, user_id) VALUES('Вопак', 'Галицька, 20', 0);
INSERT INTO store(name, address, user_id) VALUES('Ельдорадо', 'Вовчинецька, 40', 1);
INSERT INTO store(name, address, user_id) VALUES('MOYO', 'Вовчинецька, 40', 2);
INSERT INTO store(name, address, user_id) VALUES('Rozetka', 'Інтернет', 3);

INSERT INTO stores_products(product_id, store_id) VALUES(1, 1);
INSERT INTO stores_products(product_id, store_id) VALUES(2, 2);
INSERT INTO stores_products(product_id, store_id) VALUES(3, 3);
INSERT INTO stores_products(product_id, store_id) VALUES(4, 4);

INSERT INTO storage(user_id, product_id, amount) VALUES(1, 1, 5);
INSERT INTO storage(user_id, product_id, amount) VALUES(2, 1, 3);
INSERT INTO storage(user_id, product_id, amount) VALUES(3, 4, 1);
INSERT INTO storage(user_id, product_id, amount) VALUES(2, 3, 1);
INSERT INTO storage(user_id, product_id, amount) VALUES(3, 2, 2);
INSERT INTO storage(user_id, product_id, amount) VALUES(1, 2, 1);

INSERT INTO shopping_list(user_id, product_id, amount) VALUES(1, 3, 0);
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(2, 2, 0);
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(2, 4, 0);
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(3, 1, 1);

INSERT INTO cart(user_id, product_id, amount, store_id) VALUES(1, 4, 0, 4);
INSERT INTO cart(user_id, product_id, amount, store_id) VALUES(3, 3, 0, 3);

INSERT INTO analytics(user_id, product_id, amount, used_date) VALUES(1, 1, 1, '2017-01-18');

INSERT INTO forecast(user_id, product_id, end_date) VALUES(1, 1, '2017-01-23');