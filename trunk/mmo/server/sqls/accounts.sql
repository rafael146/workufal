CREATE TABLE IF NOT EXISTS Accounts(
 ID VARCHAR(25) NOT NULL,
 pass VARCHAR(45) NOT NULL,
 last_ip VARCHAR(15) NOT NULL DEFAULT '0.0.0.0',
 PRIMARY KEY(ID));
