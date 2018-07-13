# Java Spring Freelancer Platform (Alpha)

Open Source platform for freelance portals based on Java Spring technology.

## Technology

- Java
- Spring Boot
- MySQL
- Hibernate ORM
- Maven
- Thymeleaf 
- Bootstrap framework


## Features

- Internationalization (i18n: Serbian and English by default)
- User module (register, login, forgot password)
- Post a job
- Place a bid
- View bids
- Hire freelancer
- Message rooms (jobs and one-to-one)
- Feedback / rating system

## Requirements 

1. Java 8
2. Maven
3. MySQL server

## How to install and run 

### 1. Clone GIT repo

1. open CMD or Linux terminal and CD to (eclipse) projects root folder
2. git clone [THIS REPO]

### 2. Prepare MySQL

1. Create MySQL database 
2. Import /freelance.sql

### 3. Set your config

1. Copy /src/main/resources/application.properties-sample to /src/main/resources/application.properties
2. Edit spring.datasource.* values to fit your DB server
3. Edit freelancer.locale.default = rs_SR and change to "en_US" if you want English by default
4. Edit freelancer.job.page_size = 5 to change how many jobs are displayed per page.

### 4. Run Java app

1. CD to the cloned folder
2. mvn clean install
3. mvn compile
4. mvn spring-boot:run

Note: you can also run project from Eclipse:

open project > right click on FreelancePlatformApplication.java > Run As > Java Application

### 5. Test

1. Open URL in your browser http://localhost:8000
2. (optional) Login with triva89@yahoo.com / 123456 (client) and aabb@mail.com/123456 (freelancer)

## How to translate to your language

1. Create properties file with name "messages_[locale].properties", for example messages_ru_RU.properties in folder /src/main/resources/
2. Add appropriate link to top menu (?locale=ru_RU)