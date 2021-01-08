DROP TABLE IF EXISTS track;
DROP TABLE IF EXISTS accounts;

CREATE TABLE track(id int(4) primary key, name varchar(255));
CREATE TABLE accounts(username varchar(255) primary key, password varchar(255));

INSERT INTO track values (1,'abc');
INSERT INTO track values (2,'bcd');

INSERT INTO accounts values ('one','onepass');
INSERT INTO accounts values ('two','twopass');

