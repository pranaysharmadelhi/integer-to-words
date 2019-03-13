CREATE TABLE numbering_system (
  id int NOT NULL PRIMARY KEY,
  number_system VARCHAR(255) NOT NULL
);


CREATE TABLE scales (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  no_of_zeros_start int NOT NULL,
  numbering_system_id int NOT NULL,
  max_length int NOT NULL,
  word_in_english VARCHAR(255) NOT NULL
);

CREATE TABLE tens (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  number int NOT NULL,
  word_in_english VARCHAR(255) NOT NULL
);



CREATE TABLE small_numbers (
  id int NOT NULL AUTO_INCREMENT  PRIMARY KEY,
  number int NOT NULL,
  word_in_english VARCHAR(255) NOT NULL
);



INSERT INTO numbering_system (id, number_system) VALUES
(1, 'Western'),
(2, 'Indian');

INSERT INTO scales (numbering_system_id, no_of_zeros_start, max_length, word_in_english) VALUES
(1, 0, 3, ''),
(1, 3, 3, 'thousand'),
(1, 6, 3, 'million'),
(1, 9, 3, 'billion'),
(1, 12, 3, 'trillion'),
(1, 15, 3, 'quadrillion'),
(1, 18, 3, 'quintillion'),
(1, 21, 3, 'sextillion'),
(1, 24, 3, 'septillion');


INSERT INTO scales (numbering_system_id, no_of_zeros_start, max_length, word_in_english) VALUES
(2, 0, 3, ''),
(2, 3, 2, 'thousand'),
(2, 5, 2, 'lack'),
(2, 7, 10, 'crore');


INSERT INTO tens (number, word_in_english) VALUES
(20, 'twenty'),
(30, 'thirty'),
(40, 'forty'),
(50, 'fifty'),
(60, 'sixty'),
(70, 'seventy'),
(80, 'eighty'),
(90, 'ninety');

INSERT INTO small_numbers (number, word_in_english) VALUES
(0, 'zero'),
(1, 'one'),
(2, 'two'),
(3, 'three'),
(4, 'four'),
(5, 'five'),
(6, 'six'),
(7, 'seven'),
(8, 'eight'),
(9, 'nine'),
(10, 'ten'),
(11, 'eleven'),
(12, 'twelve'),
(13, 'thirteen'),
(14, 'fourteen'),
(15, 'fifteen'),
(16, 'sixteen'),
(17, 'seventeen'),
(18, 'eighteen'),
(19, 'nineteen');
