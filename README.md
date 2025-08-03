# Spring Core Task 1

This is a **learning project** developed as part of a Spring Core assignment.  
The goal of the project was to build a modular **Gym CRM system** using **Spring Framework** (Core, no Spring Boot), applying best practices of Java-based configuration, dependency injection, and test-driven development.

## Task Description

The assignment required implementing a system that manages:
- **Trainees**
- **Trainers**
- **Trainings**

Each of these has its own service and DAO layer, with data stored in in-memory `Map`s. The system supports operations like **create, update, delete, and select**.

Additional requirements included:
- Using **Spring annotations** for configuration (`@Service`, `@Repository`, etc.)
- **BeanPostProcessor** to load initial data from external files
- Property-based configuration with `application.properties`
- Generation of **usernames** (`first.last[.n]`) and **random 10-character passwords**
- Proper **logging** of service activity
- Writing full **unit tests** using JUnit and Mockito

The project serves as practice for clean architecture, testable code, and Spring Core fundamentals.

## Technologies Used
- Java 21
- Spring Framework (Core)
- JUnit 5
- Mockito
