create table creator (id bigserial not null, user_id bigint unique, primary key (id));
create table friends (id bigserial not null, request_date_time timestamp(6), user_id_receiver bigint, user_id_sender bigint, status varchar(255) check (status in ('PENDING','ACCEPTED','DECLINED')), primary key (id));
create table multimedia (id bigserial not null, sender_id bigint, content_type varchar(255), name varchar(255), data oid, primary key (id));
create table user_friends (friend_id bigint not null, user_id bigint not null);
create table users (id bigserial not null, email varchar(255) not null, password varchar(255) not null, refresh_token varchar(255), username varchar(255) not null unique, primary key (id));
alter table if exists creator add constraint FKkcaddymucihg6240ur9hdppro foreign key (user_id) references users;
alter table if exists friends add constraint FK8x5uu08x701cu9rqggna0f46f foreign key (user_id_receiver) references users;
alter table if exists friends add constraint FKbyp7oq5n0eqjhnqkyorca5iwf foreign key (user_id_sender) references users;
alter table if exists multimedia add constraint FK64my33msu1i913hebs7r2ngyj foreign key (sender_id) references users;
alter table if exists user_friends add constraint FK11y5boh1e7gh60rdqixyetv3x foreign key (friend_id) references users;
alter table if exists user_friends add constraint FKk08ugelrh9cea1oew3hgxryw2 foreign key (user_id) references users;