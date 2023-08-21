# Film API with Spring and PostgreSQL

This project is a part of the assignment for creating a Web API and database using Spring, Hibernate, and PostgreSQL. The application focuses on managing movie characters, movies, and franchises. The provided guidelines outline the minimum requirements for the project.

## Group Members
- Linnéa Khan (GitHub: @ChatGPT3000)
- Victor Lundqvist (GitHub: @victorlun)

## Development Environment Setup

Before starting, ensure you have the following tools installed:

- IntelliJ IDEA with Java 17
- Spring Web, Spring Data JPA, PostgreSQL, Lombok plugins
- PostgreSQL with PgAdmin
- Docker for environment replication

## Hibernate Database Setup

### Models, Repositories, and Services
The application uses Hibernate to create a PostgreSQL database with the following entities:

1. Character
    - Autoincremented Id
    - Full name
    - Alias (if applicable)
    - Gender
    - Picture (URL to photo)

2. Movie
    - Autoincremented Id
    - Movie title
    - Genre (comma-separated string)
    - Release year
    - Director (string name)
    - Picture (URL to movie poster)
    - Trailer (YouTube link)

3. Franchise
    - Autoincremented Id
    - Name
    - Description

The relationships between these entities are defined according to the business rules.

### Data Seeding
Seeding is implemented using Spring's `data.sql` file. 9 movies, along with characters and franchises, are added as dummy data.

## Spring Web API Setup

### Controllers
The Web API is built using Spring Web and consists of controllers that provide endpoints to manipulate movie characters, movies, and franchises.

### Swagger/Open API Documentation
API documentation is generated using Swagger/Open API to provide clear and comprehensive information about the available endpoints, request and response structures, and their usage.

### Data Transfer Objects (DTOs)
DTOs are used to transfer data between the client and the API. Mapstruct is employed to simplify the conversion between DTOs and entities.


### ________________________
By Linnéa & Victor - Experis Academy 
