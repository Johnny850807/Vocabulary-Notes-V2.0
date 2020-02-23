/*
 *  Copyright 2020 johnny850807 (Waterball)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

INSERT INTO member (id, first_name, last_name, age, email, password, role)
VALUES (1, 'Johnny', 'Pan', 23, 'johnny850807@gmail.com', 'hashed', 'MEMBER');

INSERT INTO dictionary (id, title, description, type, owner_id)
VALUES (1, 'TOEIC Level 1', 'The Toeic basic dictionary.', 'OWN', 1);

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

