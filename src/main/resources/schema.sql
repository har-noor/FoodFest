  create table SEC_USER
(
  userId            BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userName          VARCHAR(128) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  age int, 
  city VARCHAR(50),
  num VARCHAR(100),
  ENABLED           BIT NOT NULL,
  role              VARCHAR (20)
);


create table SEC_ROLE
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
);


create table USER_ROLE
(
  ID      BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);


CREATE TABLE tickets 
( id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  userName VARCHAR(128), 
  eventDay VARCHAR(10), 
  role VARCHAR(20),
  price DECIMAL(5,2),
  quantity int
);

alter table USER_ROLE
  add constraint USER_ROLE_UK unique (userId, roleId);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (userId)
  references SEC_USER (userId);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (roleId)
  references SEC_ROLE (roleId);
 
  alter table tickets
  add constraint TICKET_FK1 foreign key (userName)
  references SEC_USER (userName);
  


