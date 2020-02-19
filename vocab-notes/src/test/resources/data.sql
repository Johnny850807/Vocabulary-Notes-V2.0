INSERT INTO member (id, first_name, last_name, age, email, password)
VALUES (1, 'Johnny', 'Pan', 23, 'johnny850807@gmail.com', 'hashed');

INSERT INTO dictionary (id, title, description, type, owner_id)
VALUES (1, 'TOEIC Level 1', 'The Toeic basic dictionary.', 'own', 1);

INSERT INTO wordgroup (id)
VALUES (1);

INSERT INTO word (id, name, synonyms, image_url)
VALUES (1, 'lease', '',
        'https://www.lawdonut.co.uk/sites/default/files/your-options-for-getting-out-of-a-lease-552568144.jpg'),
       (2, 'treaty', '',
        'https://static01.nyt.com/images/2018/12/15/opinion/15ArmsControl-edt/15ArmsControl-edt-articleLarge.jpg'),
       (3, 'stipulation', '',
        'http://www.hicounsel.com/wp-content/uploads/2017/07/maxresdefault.jpg');

INSERT INTO wordgroup_word (wordgroup_id, word_id)
VALUES (1, 1), (1, 2), (1, 3);

INSERT INTO dictionary_wordgroup (dictionary_id, wordgroup_id)
VALUES (1, 1);