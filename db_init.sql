GRANT ALL PRIVILEGES ON todolist.* TO 'saman'@'%' IDENTIFIED BY 'saman1234';

USE todolist;

CREATE TABLE "role_entity" (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO "role_entity" (name) VALUES ('USER');
