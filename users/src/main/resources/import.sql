INSERT INTO users(user_name, password, enabled, name, last_name, email) VALUES('mrceja', '$2a$10$ucR6aiN4GJt98YiKgqn1V.Tn8SpKLQlSCYW17Kf8w5zxY/E5snqv2', 1, 'hector', 'ceja', 'cja@gmail.com');
INSERT INTO users(user_name, password, enabled, name, last_name, email) VALUES('app', '$2a$10$F2JdJWDTbTskXlLlbQTQtuOvvw5zrgZqKctrYwVqcMvLQWYZVIAhW', 1, 'a', 'pp', 'admin@gmail.com');
INSERT INTO users(user_name, password, enabled, name, last_name, email) VALUES('admin', '$2a$10$XYSqXhcnl7k1cz08yJo4NOTkiCNCkjaWvI3y9QBty7JkHgKKnYYbu', 1, 'admin', 'admin', 'app@gmail.com');

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO user_roles(user_id, role_id) VALUES(1,1);
INSERT INTO user_roles(user_id, role_id) VALUES(2,2);
INSERT INTO user_roles(user_id, role_id) VALUES(2,1);