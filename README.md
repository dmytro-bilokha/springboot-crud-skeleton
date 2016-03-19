# springboot-crud-skeleton

The repository contains very simple application based on Spring Boot.
The application was developed just to try some interesting technologies
and concepts.

Currently the application is able only to register new user and to
login user already registered.

## Requirements

 * JDK 1.8.

 * MySQL 5.6 or later. Using other SQL database is possible, but may require changes
in configuration.

 * Maven.

## Brief of Technologies Involved

* [Spring Boot](http://projects.spring.io/spring-boot/) for creating Spring-based application.

* [Spring Security](http://projects.spring.io/spring-security/) for users authorizing.

* [Thymeleaf template engine](http://www.thymeleaf.org/) for front-end HTML generating.

* [Hibernate Validator](http://hibernate.org/validator/) for validating data of the web form.

* [EclipseLink](http://eclipse.org/eclipselink/) as JPA provider.

* [MySQL](https://www.mysql.com/) for storing data.

* [JUnit](http://junit.org/) for unit tests.

## List of Concepts and Approaches

* Reading configuration file location from Java command line property (java -Dconfigfile=/path ...).

* Switching data storage(MySQL or XML file) depending from configuration file property.

* Server side data validation.

* Using Spring Security for users authorization. Users data stored in MySQL or XML file.

* Testing application with JUnit.

## Environment Configuration

Create the database and user with rights on the database.

## Application Configuration

The configuration file with application default options is `/src/main/resources/defaultconfig.properties`.
You may edit it or use external configuration file with the same syntax.
One should set JDBC URL, username, password for MySQL storage and XML file name for XML storage
(see details in the `defaultconfig.properties` file).

## Building and Running

To build and run application use `mvn spring-boot:run`.
Use `mvn spring-boot:run -Dconfigfile=/path/to/configfile` to run application with external configuration
file. 

## TODO

* Add users ability to store some data (phonebook for example).

* Add functionality for viewing and filtering phonebook entries.

* Develop CRUD functionality.

* Add much more unit tests.

* Develop JavaScript input data validation.

