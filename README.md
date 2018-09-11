# spring-hibernate-crm-web
Tiny Customer Relationship Manager (CRM) with a Customer entity CRUD

## Configuration

- Spring Framework 5.8.0
- Spring Web MVC 5.8.0
- Spring Security 5.7.0
- Servlet API 3.1.0
- Servlet JSP API 2.3.1
- Servlet JSTL 1.2
- MySQL Java Connector 8.0.12
- C3P0 0.9.5.2

## Use case

Tiny Customer Relationship Manager (CRM) with a Customer entity CRUD.

Support for:

- Login users
- Listing customers
  - Plain list with no options for ROLE_EMPLOYEE
  - List with Update Customer option for ROLE_MANAGER
  - List with Update and Delete Customer options for ROLE_ADMIN
- Form for customer creation/update
- Access Denied / Security support
- Coherent navigation among pages
- Logout

This project covers the most important areas when talking about a web page made with Java.

The folloging features are covered:

- Spring Java Configuration. No XML configuration is used in this project at all. But, just to clarify: I'm not against; it's just the chosen strategy.
  - Two DataSource's created (Customer and Spring Security DB's)
  - SessionFactory Bean support
  - HibernateTransactionManager support
- Spring Security:
  - Enhanced Login form using Bootstrap
  - CSRF support
  - Role Based Access and output.
- Spring MVC
  - Controller for the main Customer operations
  - Independent Service (transactional) and DAO layers
- Hibernate
  - Customer Entity
  - Main database operations

## Getting started

To get this Maven project working:

- Database
  - Install latest MySQL version
  - Execute two scripts:
    - `01-setup-spring-security-bcrypt-demo-database.sql`
    - `02-customer-tracker.sql`
  - Two databases are created:
    - [Spring Security Database](https://github.com/pgbonino/spring-hibernate-crm-web/blob/master/sql-scripts/spring-security-demo-database.png)
    - [Customer Database](https://github.com/pgbonino/spring-hibernate-crm-web/blob/master/sql-scripts/customer-database.png)
  - Note that the first script creates three users with the following access details:
     - john/fun123/[ROLE_EMPLOYEE]
     - mary/fun123/[ROLE_EMPLOYEE, ROLE_MANAGER]
     - susan/fun123/[ROLE_EMPLOYEE, ROLE_ADMIN]
  - Note that passwords are stored using bscrypt algorithm.  


- Java
  - Clone this repo
  - Build using Maven
  - In your IDE, run the application on a Server (I use Tomcat 9.0)
  - Play around
