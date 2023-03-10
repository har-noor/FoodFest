
insert into SEC_User (userName, encryptedPassword, age, city, num, ENABLED, role)
values ('Jon', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 18, 'Bramption', '342-994-9392', 1, 'Vendor');

insert into SEC_User (userName, encryptedPassword, age, city, num, ENABLED, role)
values ('userOne', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 20, 'Missisauga', '833-234-8484', 1, 'Guest');

insert into SEC_User (userName, encryptedPassword, age, city, num, ENABLED, role)
values ('userTwo', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 20, 'Missisauga', '833-234-8484', 1, 'Guest');

insert into SEC_User (userName, encryptedPassword, age, city, num, ENABLED, role)
values ('userThree', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 20, 'Missisauga', '833-234-8484', 1, 'Guest');

insert into SEC_User (userName, encryptedPassword, age, city, num, ENABLED, role)
values ('userFour', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 20, 'Missisauga', '833-234-8484', 1, 'Guest');

insert into SEC_User (userName, encryptedPassword, age, city, num, ENABLED, role)
values ('userFive', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 20, 'Missisauga', '833-234-8484', 1, 'Guest');

INSERT INTO tickets (userName, eventDay, role, price, quantity) VALUES
('userOne', 'Friday', 'Guest', 50.00, 1),
('userTwo', 'Sunday', 'Guest', 50.00, 1),
('userThree', 'Friday', 'Guest', 50.00, 2),
('userFour', 'Saturday', 'Guest', 50.00, 2),
('userFive', 'Sunday', 'Guest', 50.00, 1),
('userOne', 'Sunday', 'Guest', 50.00, 3),
('userTwo', 'Friday', 'Guest', 50.00, 4),
('userThree', 'Saturday', 'Guest', 50.00, 1),
('userFour', 'Friday', 'Guest', 50.00, 7),
('userFive', 'Saturday', 'Guest', 50.00, 3);


insert into sec_role (roleName)
values ('ROLE_VENDOR');
 
insert into sec_role (roleName)
values ('ROLE_GUEST');

insert into user_role (userId, roleId)
values (1, 1);
 
insert into user_role (userId, roleId)
values (2, 2);
 
