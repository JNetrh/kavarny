/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Kuba
 * Created: 16.5.2017
 */

CREATE TABLE `cafes` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(255) CHARACTER SET utf8 NULL DEFAULT '' ,
`place`  varchar(255) CHARACTER SET utf8 NULL DEFAULT '' ,
`city`  varchar(255) CHARACTER SET utf8 NULL DEFAULT '' ,
`adress`  varchar(255) CHARACTER SET utf8 NULL DEFAULT '' ,
`gpsX`  varchar(255) CHARACTER SET utf8 NULL DEFAULT '' ,
`gpsY`  varchar(255) CHARACTER SET utf8 NULL DEFAULT '' ,
`dobaOd`  varchar(255) CHARACTER SET utf8 NULL DEFAULT '' ,
`dobaDo`  varchar(255) CHARACTER SET utf8 NULL DEFAULT '' ,
`logo`  mediumblob NULL ,
`listek`  mediumblob NULL ,
`wifi`  binary(255) NULL ,
`access`  varchar(255) CHARACTER SET utf8 NULL DEFAULT '' ,
`description`  text CHARACTER SET utf8 NULL ,
PRIMARY KEY (`id`)
)
;

CREATE TABLE `cafeRating` (
`id`  int NOT NULL AUTO_INCREMENT ,
`cafeId`  int NULL DEFAULT NULL ,
`ratingText`  text CHARACTER SET utf8 NULL ,
`ratingInt`  tinyint NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
;


CREATE TABLE `users` (
`id`  int NOT NULL AUTO_INCREMENT ,
`email`  VARCHAR(255) CHARACTER SET utf8 NULL DEFAULT NULL ,
`password`  VARCHAR(255) CHARACTER SET utf8 NULL DEFAULT NULL ,
`admin`  int NULL DEFAULT NULL ,
`processed`  int NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
;

insert into users (email, password, admin, processed) values ("netj", "1234", 0, 0);

insert into caferating (cafeId, ratingText, ratingInt) values (2, "první 1 hodnocení", 2);
insert into caferating (cafeId, ratingText, ratingInt) values (2, "druhé 1 hodnocení", 3);
insert into caferating (cafeId, ratingText, ratingInt) values (2, "třetí 1 hodnocení", 1);
insert into caferating (cafeId, ratingText, ratingInt) values (12, "první 2 hodnocení", 5);
insert into caferating (cafeId, ratingText, ratingInt) values (12, "druhé 2 hodnocení", 5);
insert into caferating (cafeId, ratingText, ratingInt) values (12, "třetí 2 hodnocení", 4);
insert into caferating (cafeId, ratingText, ratingInt) values (22, "první 3 hodnocení", 2);
insert into caferating (cafeId, ratingText, ratingInt) values (22, "druhé 3 hodnocení", 3);
insert into caferating (cafeId, ratingText, ratingInt) values (22, "třetí 3 hodnocení", 2);
select * from caferating;