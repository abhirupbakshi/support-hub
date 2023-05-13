# Support Hub

A simple software system for creating, maintaining and viewing complaints / issues faced during worktime in an organization by employees. 

In this hypothetical organization, there are three types of employees:
* **Engineer**: Solves the complaint
* **User**: An employee who creates the complains
* **HOD**: Head of Department, manages the complaints and engineers

A typical workflow looks like this:
* User registers themselves in the system by creating an account.
* User creates a complaint.
* HOD creates a engineer account.
* HOD assigns a engineer to a complaint.
* Engineer changes the status of the problem (e.g. Resolved or In Progress etc.).
* Engineer adds an update (a comment) to the problem.
* User change the "status" of their complains to "Resolved" (or what ever name it is that indecates it has been resolved) if it gets resolvd without the help of an engineer.

### Notes:
An HOD account has to be created by the database administrator and an Engineer account can only be created by a HOD, unless permission is granted to them. Although the UI does not have any options for this at this moment.

# Architecture and Tech Stack of the software

## Database:
This app has been created by using JPA and not any specific ORM framework. Hence only an relational database can be used to store the data.
<br>
ER Diagram:
<img src="https://i.imgur.com/l18YUhm.png">
The diagram has been generated with [drawio](https://www.drawio.com/) and the generated XML can be accessed from [here](https://github.com/abhirupbakshi/support-hub/blob/main/er_diagram.xml).

## Permissions:
In the database, permissions can be granted or revoked to the employees (all types) with the help of a table called Authorizations. The table consists of following attributes:
* **Employee Type**: The type of the employee who is doing the operation. Member of the compound primary key (other one is Permission Name).
* **Permission Name**: The permission name for the employee. Member of the compound primary key (other one is Employee Type). All charecters are lowercase. The name follows this scheme:
    * \<on requested class name\>\_\<CRUD operation name\>_\<name of the operation\>.
    * Example: If HOD is granted permission to create an Engineer account then the permission name would be: engineer_create_account.
* **Value**: 1 or 0, indicating whether the permission is granted or revoked.

## Authentication:
Every service that this app provides requires password for authentication.

## Abstruction Layers:
There are three abstruction layers:
* **Persistence Layer**: This layer provides access to the database through various interfaces.
* **Service Layer**: This layer provides the core functionality for the app, especially the authentication and authorization.
* **UI Layer**: This layer provides the console based UI.

## Tech Stack:
* Java Standard Edition, version 17
* [Maven](https://maven.apache.org/) as the build tool
* [Jakarta Persistence APIs](https://jakarta.ee/specifications/persistence/3.0/)
* [Lombok](https://projectlombok.org/)
* [Hibernate](https://hibernate.org/) as the ORM framework (but can be changed to any other ORM framework, complient with JPA)

# Authors:
* [Abhirup Bakshi](https://github.com/abhirupbakshi)

# Docs:
* [README.md](https://github.com/abhirupbakshi/Support-Hub/blob/main/README.md)
* [JavaDocs](https://abhirupbakshi.github.io/support-hub/)

