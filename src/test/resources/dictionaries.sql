

INSERT INTO dictionary (id, title, description, type)
VALUES (1, 'a', 'a', 'PUBLIC'),
       (2, 'b', 'b', 'PUBLIC'),
       (3, 'c', 'c', 'PUBLIC'),
       (4, 'd', 'd', 'PUBLIC'),
       (5, 'e', 'e', 'PUBLIC'),
       (6, 'f', 'f', 'PUBLIC'),
       (7, 'g', 'g', 'PUBLIC'),
       (8, 'h', 'h', 'PUBLIC'),
       (9, 'i', 'i', 'PUBLIC'),
       (10, 'j', 'j', 'PUBLIC'),
       (11, 'k', 'k', 'PUBLIC'),
       (12, 'l', 'l', 'PUBLIC'),
       (13, 'm', 'm', 'PUBLIC');

INSERT INTO member (id, first_name, last_name, age, email, password, role)
VALUES (1, 'Owner', 'Test', 15, 'owner@gmail.com', 'hashed', 'MEMBER');


INSERT INTO dictionary(id, title, description, type, owner_id)
VALUES (14, 'n', 'n', 'OWN', 1),
       (15, 'o', 'o', 'OWN', 1),
       (16, 'p', 'p', 'OWN', 1),
       (17, 'q', 'q', 'OWN', 1),
       (18, 'r', 'r', 'OWN', 1),
       (19, 's', 's', 'OWN', 1),
       (20, 't', 't', 'OWN', 1),
       (21, 'u', 'u', 'OWN', 1),
       (22, 'v', 'v', 'OWN', 1),
       (23, 'w', 'w', 'OWN', 1),
       (24, 'x', 'x', 'OWN', 1),
       (25, 'y', 'y', 'OWN', 1),
       (26, 'z', 'z', 'OWN', 1)
