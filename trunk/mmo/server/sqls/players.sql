CREATE TABLE IF NOT EXISTS Players (
 ID decimal(11,0) not null default 0,
 account varchar(45) not null,
 name varchar(16) not null,
 speed decimal(11,0) not null default 1,
 defense decimal(11,0) not null default 1,
 `force` decimal(11,0) not null default 1,
 maxHp decimal(11,0) not null default 1,
 hp decimal(11,0) not null default 1,
 `level` decimal(10,0) not null default 1,
 x decimal(11,0) not null default 0,
 y decimal(11,0) not null default 0,
 heading decimal(6,0) not null default 0,
 `exp` decimal(20,0) not null default 0,
 model decimal(1,0) not null,

PRIMARY KEY(ID));
