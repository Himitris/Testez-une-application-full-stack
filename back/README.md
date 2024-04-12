# Yoga app

Yoga is a booking platform for Yoga sessions.

This is the Yoga app back-end.


## Installation

First install [Java](https://www.oracle.com/fr/java/technologies/downloads/#java21) according your operating system.

Then install [Maven](https://maven.apache.org/install.html).

And finally install [MySQL](https://dev.mysql.com/doc/mysql-installation-excerpt/8.0/en/).

Go to resources/sql in the root directory and :
* open a terminal,
* connect to mysql with your credentials,
* create a database 'CREATE DATABASE yoga',
* import the SQL script 'SOURCE script.sql'.

Open 'src/main/resources/application.properties' and update spring.datasource.username and spring.datasource.password with your MySQL credentials.

## Tests

In the back-end root directory, run mvn clean test to run the tests.

Tests report with Jacoco code coverage is available at 'target/site/jacoco/index.html'.

## Run

By default the admin account is:
- login: yoga@studio.com
- password: test1234

You can test the back-end with a tool like Postman. A collection is available at 'resources/postman'.

## Author

Antoine Gautier