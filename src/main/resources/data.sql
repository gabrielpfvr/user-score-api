INSERT INTO users(id, name, email, password, age, phone_number, role, created_at)
VALUES(100, 'admin', 'admin@test.com', '$2a$10$o2mJHEunGB6wDyE0a5MLC.IjrIHcv.r/RJH0tab0V6OlX4e55Tfkq', 30, '11988776644', 'ADMIN', CURRENT_TIMESTAMP);

INSERT INTO address(zip_code, state, city, neighborhood, street, user_id)
VALUES('04794000', 'SP', 'São Paulo', 'Vila Gertrudes', 'Avenida das Nações Unidas', 100);

INSERT INTO score(score_value, user_id)
VALUES(980, 100);
