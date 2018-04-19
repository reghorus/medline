CREATE TABLE part
(
  part_name     VARCHAR(36),
  vendor        VARCHAR(36),
  part_number   VARCHAR(36),
  quantity      INTEGER,
  date_received DATE,
  date_shipped  DATE
);

INSERT INTO part (
  part_name, vendor, part_number, quantity, date_received, date_shipped)
VALUES ('1part name1', '1vendor1', '1part_number1', 1, '2013-06-01', '2014-06-01');

INSERT INTO part (
  part_name, vendor, part_number, quantity, date_received, date_shipped)
VALUES ('2part name2', '2vendor2', '2part_number2', 2, '2014-06-01', '2015-06-01');

INSERT INTO part (
  part_name, vendor, part_number, quantity, date_received, date_shipped)
VALUES ('3part name3', '3vendor3', '3part_number3', 3, '2015-03-01', '2015-05-01');

INSERT INTO part (
  part_name, vendor, part_number, quantity, date_received, date_shipped)
VALUES ('4part name4', '4vendor4', '4part_number4', 4, '2016-06-01', '2017-06-01');
	
	