CREATE TABLE melidb.banned_ip 
(
ip varchar(40) not null,
dml_tmst timestamp not null, 
constraint ban_ip_pk primary key (ip) 
);

CREATE TABLE melidb.country_detail 
(
country_code varchar(3),
country_name varchar(50) not null,
dml_tmst timestamp not null, 
constraint country_detail_pk primary key (country_code) 
);

CREATE TABLE melidb.country_currency
(
country_currency_id int AUTO_INCREMENT,
country_code varchar(3),
currency_code varchar(3),
currency_name varchar(30) not null,
currency_symbol varchar(2) not null,
dml_tmst timestamp not null, 
constraint country_currency_pk primary key (country_currency_id),
constraint country_currency_uk unique (country_code, currency_code),
constraint country_currency_fk foreign key (country_code) references country_detail(country_code)
);

CREATE TABLE melidb.currency_exchange
(
currency_exchange_id int AUTO_INCREMENT,
country_code varchar(3),
currency_code varchar(3),
value double not null,
dml_tmst timestamp not null, 
constraint country_exchange_pk primary key (currency_exchange_id),
constraint country_exchange_uk unique (country_code, currency_code),
constraint country_exchange_fk foreign key (country_code) references country_detail(country_code)
);

