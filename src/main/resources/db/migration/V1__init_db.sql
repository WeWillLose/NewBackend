create table report (id bigint not null auto_increment, created_by varchar(255), created_date datetime(6), last_modified_by varchar(255), last_modified_date datetime(6), data json, report_name varchar(255), status varchar(255), user_id bigint, primary key (id)) engine=InnoDB;
create table role (name varchar(20) not null, primary key (name)) engine=InnoDB;
create table todo (id bigint not null auto_increment, created_by varchar(255), created_date datetime(6), last_modified_by varchar(255), last_modified_date datetime(6), description varchar(255), text varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB;
create table user (id bigint not null, created_by varchar(255), created_date datetime(6), last_modified_by varchar(255), last_modified_date datetime(6), first_name varchar(255), is_active bit, last_name varchar(255), middle_name varchar(255), password varchar(255), username varchar(255), chairman_id bigint, primary key (id)) engine=InnoDB;
create table user_id_sequence (next_val bigint) engine=InnoDB;
insert into user_id_sequence values ( 2 );
create table user_role (user_id bigint not null, role_name varchar(20) not null, primary key (user_id, role_name)) engine=InnoDB;
alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
alter table report add constraint FKj62onw73yx1qnmd57tcaa9q3a foreign key (user_id) references user (id);
alter table todo add constraint FK2ft3dfk1d3uw77pas3xqwymm7 foreign key (user_id) references user (id);
alter table user add constraint FKktw7d77ohdkibpxo2jlndg59f foreign key (chairman_id) references user (id);
alter table user_role add constraint FKn6r4465stkbdy93a9p8cw7u24 foreign key (role_name) references role (name);
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id);