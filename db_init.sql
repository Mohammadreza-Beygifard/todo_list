GRANT ALL PRIVILEGES ON todolist.* TO 'saman'@'%' IDENTIFIED BY 'saman1234';

USE todolist;

CREATE TABLE "role_entity" (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO "role_entity" (name) VALUES ('ADMIN');
INSERT INTO "role_entity" (name) VALUES ('USER');

-- CREATE TABLE todo_table (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     user_id INT NOT NULL,
--     task VARCHAR(255) NOT NULL,
--     completed BOOLEAN NOT NULL,
--     FOREIGN KEY (user_id) REFERENCES user_table(id)
-- );