drop database quiz;

create database quiz;


use quiz;

create table users(
	Id serial primary key,
    username varchar(50) unique,
    password_hash varchar(200),
    is_admin boolean
);

create table quizes(
	Id serial primary key,
    Title varchar(200),
    description varchar(1500),
    creator_id bigint unsigned,
	foreign key (creator_id) references users(id),
    quizTime bigint
);

create table questions(
	Id serial primary key,
    quiz_id bigint unsigned,
    Question_type varchar(30),
    Question_Description varchar(2000),
    foreign key (quiz_id) references QUIZES(id)
);

create table answers(
	Id serial primary key,
    Question_id bigint unsigned,
    answer_Description varchar(1000),
    is_correct boolean,
    foreign key (Question_id) references Questions(id)
);

create table history(
	Id serial primary key,
	user_Id bigint unsigned,
    quiz_id bigint unsigned,
    score integer,
    start_time date,
    end_time date,
    foreign key (user_id) references users(id),
	foreign key (quiz_id) references quizes(id)
);

create table friends(
	Id serial primary key,
	user_Id bigint unsigned,
	friend_Id bigint unsigned,
    accepted boolean,
    foreign key (user_id) references users(id),
	foreign key (friend_Id) references users(id)
);

create table mails(
	Id serial primary key,
	from_Id bigint unsigned,
	to_Id bigint unsigned,
    message_type varchar(5),
    message_title varchar(200),
    message_content varchar(1000),
    send_date date,
    foreign key (from_Id) references users(id),
	foreign key (to_Id) references users(id)
);

create table achievments(
		Id serial primary key,
        name varchar(100),
        description varchar(500)
);


create table achievment_history(
	Id serial primary key,
	user_Id bigint unsigned,
	achievment_Id bigint unsigned,
    acquired_date date,
    foreign key (user_Id) references users(id),
	foreign key (achievment_Id) references achievments(id)
);



