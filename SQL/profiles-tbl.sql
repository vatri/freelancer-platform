create table profile(
  id int not null PRIMARY KEY AUTO_INCREMENT,
  user_id int not null,
  biography text ,
  linkedin VARCHAR (255),
  location VARCHAR (255)
);

alter table profile add FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE users DROP COLUMN location;