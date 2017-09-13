CREATE TABLE `Privilege` (`id` bigint(20) NOT NULL, `name` varchar(255) DEFAULT NULL, PRIMARY KEY (`id`) );

CREATE TABLE `Role` (`id` bigint(20) NOT NULL, `name` varchar(255) DEFAULT NULL, PRIMARY KEY (`id`) );

CREATE TABLE `user` (`id` bigint(20) NOT NULL AUTO_INCREMENT, `email` varchar(255) DEFAULT NULL, `enabled` bit(1) NOT NULL, `firstName` varchar(255) DEFAULT NULL, `isUsing2FA` bit(1) NOT NULL, `lastName` varchar(255) DEFAULT NULL, `password` varchar(60) DEFAULT NULL, `secret` varchar(255) DEFAULT NULL, PRIMARY KEY (`id`) );
create table role_privilege_mapping (id bigint(20) AUTO_INCREMENT, role_id bigint(20), privilege_id bigint(20), primary key(id));

create table user_role_mapping (id bigint(20) AUTO_INCREMENT, role_id bigint(20), user_id bigint(20), primary key(id));

create table location (id bigint(20) AUTO_INCREMENT, user_id bigint(20), latitude varchar(255), longitude varchar(255), locationTime varchar(255)   DEFAULT NULL , primary key(id));