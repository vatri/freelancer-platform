create table bid (
    id int PRIMARY KEY AUTO_INCREMENT,
    job_id int not null,
    user_id int not null,
    price double(10,2),
    deadline varchar(255),
    proposal text not null,
    created timestamp default CURRENT_TIMESTAMP
);

alter TABLE bid add FOREIGN KEY (job_id ) REFERENCES job (id);
alter TABLE bid add FOREIGN KEY (user_id ) REFERENCES users (id);