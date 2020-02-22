DROP TABLE IF EXISTS wordgroup_word;
DROP TABLE IF EXISTS dictionary_wordgroup;
DROP TABLE IF EXISTS word, wordgroup, dictionary;
DROP TABLE IF EXISTS member;

CREATE TABLE member
(
  id         INTEGER PRIMARY KEY AUTO_INCREMENT,
  first_name NVARCHAR(18) NOT NULL,
  last_name  NVARCHAR(18) NOT NULL,
  age        TINYINT      NOT NULL,
  email      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(128) NOT NULL,
  exp        INTEGER                  DEFAULT 0,
  level      INTEGER                  DEFAULT 1,
  role       ENUM ('member', 'admin') DEFAULT 'member'
);

CREATE TABLE dictionary
(
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  title       NVARCHAR(80)           NOT NULL,
  description NVARCHAR(300)          NOT NULL,
  type        ENUM ('public', 'own') NOT NULL,
  owner_id    INTEGER,
  FOREIGN KEY (owner_id) REFERENCES member (id) ON DELETE CASCADE,
  CHECK (type = 'public' AND owner_id IS NULL
    OR type = 'own' AND owner_id IS NOT NULL)
);

CREATE TABLE wordgroup
(
  id    INTEGER PRIMARY KEY AUTO_INCREMENT,
  title NVARCHAR(50) UNIQUE
);

CREATE TABLE word
(
  id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  name      VARCHAR(30) UNIQUE,
  synonyms  VARCHAR(80) DEFAULT '' COMMENT 'a list of synonym separated by comma',
  image_url VARCHAR(1000)
);

CREATE TABLE dictionary_wordgroup
(
  dictionary_id INTEGER,
  wordgroup_id  INTEGER,
  CONSTRAINT PK_dictionary_wordgroup PRIMARY KEY (dictionary_id, wordgroup_id),
  FOREIGN KEY (dictionary_id) REFERENCES dictionary (id),
  FOREIGN KEY (wordgroup_id) REFERENCES wordgroup (id)
);

CREATE TABLE wordgroup_word
(
  wordgroup_id INTEGER,
  word_id      INTEGER,
  CONSTRAINT PK_wordgroup_word PRIMARY KEY (wordgroup_id, word_id),
  FOREIGN KEY (wordgroup_id) REFERENCES wordgroup (id),
  FOREIGN KEY (word_id) REFERENCES word (id)
);



