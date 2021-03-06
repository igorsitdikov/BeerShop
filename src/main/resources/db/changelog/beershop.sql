INSERT INTO beer (type, in_stock, name, description, alcohol, density, country, price)
VALUES ('светлое', true, 'Лидское', 'Лучшее пиво по бабушкиным рецептам', 5.0, 11.5, 'Республика Беларусь', 5.0),
       ('темное', true, 'Аливария', 'Пиво номер 1 в Беларуси', 4.6, 10.2, 'Республика Беларусь', 3.0),
       ('светлое осветлённое', true, 'Pilsner Urquell', 'непастеризованное', 4.2, 12.0, 'Чехия', 8.0);

INSERT INTO user (first_name, second_name, password, email, phone, user_role)
VALUES ('Иван', 'Иванов', '$2a$10$wbfi9Zi4pfkC0Y3Mp7r.fOc8po9kqc0T5.nMPTq65D/qgdKBdKAre', 'ivan.ivanov@mail.ru', '+375331234567', 0),
       ('Петр', 'Петров', '$2a$10$VvYBVKbr4xafUsgWdEHsxuOTnBhTFdzY9f1nm9p1j6x5j8TQhNZX6', 'petr.petrov@yandex.ru', '+375337654321', 0),
       ('Алексей', 'Алексеев', '$2a$10$A4HOZGs7d65Nqd1Wz2T/kO8Zt4GZOI6CTn/Vo0Ug3OLjJsVGafwM.', 'alex.alexeev@gmail.com', '+375333021232', 1);

INSERT INTO orders (user_id, processed, total, canceled)
VALUES (1, true, 25.0, false);

INSERT INTO customer_order (order_id, beer_id, amount)
VALUES (1, 1, 2),
       (1, 2, 5);