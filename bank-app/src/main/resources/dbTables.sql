show search_path;

drop table bank.transactions ;
drop table bank.account_requests ;
drop table bank.accounts ;
drop table bank.employee ;
drop table bank.customer ;

create table customer (
	id serial not null unique,
	first_name varchar(40) not NULL,
	last_name varchar(40) not NULL,
	email varchar(50) not null,
	"password" varchar(20) not null,
	phone varchar(10),
	join_date date null,
	primary key(id)
);


create table bank.employee (
	id serial not null unique,
	first_name varchar(40) not NULL,
	last_name varchar(40) not NULL,
	email varchar(50) not null,
	"password" varchar(20) not null,
	primary key(id)
);

create table bank.account_requests (
	id serial not null unique,
	customer_id int not null,
	"name" varchar(30) not null,
	balance decimal(9,2),
	status varchar(10) Default('pending'),
	time_requested timestamptz,
	time_updated timestamptz,
	employee_id int,
	constraint fk_customer_id foreign key (customer_id) references bank.customer(id),
	constraint fk_employee_id foreign key (employee_id) references bank.employee(id)
);

create table bank.accounts (
	id serial not null unique,
	customer_id int not null,
	"name" varchar(30) not null,
	balance decimal(9,2),
	date_created date,
	constraint fk_customer_id foreign key (customer_id) references bank.customer(id)
);

create table bank.transactions (
	id serial not null unique,
	account_id int not null,
	customer_id int not null,
	"type" char(1) not null,
	amount decimal(9,2),
	"time" timestamptz,
	constraint fk_account_id foreign key (account_id) references bank.accounts(id),
	constraint fk_customer_id foreign key (customer_id) references bank.customer(id)
);

select * from bank.account_requests;