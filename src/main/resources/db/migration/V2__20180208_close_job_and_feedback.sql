ALTER TABLE `bid` ADD `closed` TINYINT(1) default 0 AFTER `accepted`;

create table feedback(
	id int primary key auto_increment,
	bid_id int not null,
	client_rate tinyint(1) COMMENT 'Rate for client posted by contractor',
	client_feedback text COMMENT 'Feedback for client posted by contractor',
	contractor_rate tinyint(1) COMMENT 'Rate for contractor posted by client',
	contractor_feedback text COMMENT 'Feedback for contractor posted by client'
);

alter table feedback add foreign key(bid_id) references bid(id);