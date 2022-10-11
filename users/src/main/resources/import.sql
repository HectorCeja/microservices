INSERT INTO users(user_name, password, enabled, name, last_name, email) VALUES('mrceja', '12345', 1, 'hector', 'ceja', 'cja@gmail.com');
INSERT INTO users(user_name, password, enabled, name, last_name, email) VALUES('app', '12345', 1, 'a', 'pp', 'admin@gmail.com');
INSERT INTO users(user_name, password, enabled, name, last_name, email) VALUES('admin', '12345', 1, 'admin', 'admin', 'app@gmail.com');

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO user_roles(user_id, role_id) VALUES(1,1);
INSERT INTO user_roles(user_id, role_id) VALUES(2,2);
INSERT INTO user_roles(user_id, role_id) VALUES(2,1);