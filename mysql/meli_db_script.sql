mysql_home/bin/mysql –u root –p

CREATE DATABASE melidb;

CREATE USER 'meli_user'@'localhost' IDENTIFIED  BY 'meli_pass';

GRANT ALL PRIVILEGES ON melidb TO 'meli_user'@'localhost';

mysql_home/bin/mysql -u meli_user -p

USE melidb;

CREATE TABLE banned_ip 
(
ip varchar(40) not null,
dml_tmst timestamp not null, 
constraint ban_ip_pk primary key (ip) 
);

CREATE TABLE country_detail 
(
country_code varchar(3),
country_name varchar(50) not null,
country_currency varchar(20) not null,
dml_tmst timestamp not null, 
constraint country_detail_pk primary key (country_code) 
);

CREATE TABLE country_currency
(
country_code varchar(3),
currency_code varchar(3),
currency_name varchar(30) not null,
currency_symbol varchar(2) not null,
dml_tmst timestamp not null, 
constraint country_currency_pk primary key (country_code, currency_code),
constraint country_currency_fk foreign key (country_code) references country_detail(country_code)
);

CREATE TABLE currency_exchange
(
country_code varchar(3),
currency_code varchar(3),
value FLOAT(10,5) not null,
dml_tmst timestamp not null, 
constraint country_exchange_pk primary key (country_code, currency_code) ,
constraint country_exchange_fk foreign key (country_code) references country_detail(country_code)
);