# Microservices project



 



[![English Readme](https://img.shields.io/badge/lang-en-green)](https://github.com/dmsosa/microservices/blob/main/README.md) [![German Readme](https://img.shields.io/badge/lang-de-blue)](https://github.com/dmsosa/microservices/blob/main/readmes/README.de.md) [![French Readme](https://img.shields.io/badge/lang-fr-red)](https://github.com/dmsosa/microservices/blob/main/readmes/README.fr.md)



 



Microservices project is inspired by [ piggymetrics ](https://github.com/sqshq/piggymetrics/tree/master) by @sqshq "Alexander Lukyanchikov", but this implementation uses PostgreSQL and a simpler business logic, the main goal of this project is to provide an example of microservices structure.



TechStack: PostgreSQL, Spring, Docker



The obstacles that I found during the development of the project can be found in other "readme . md" files, because I did not want to make this README either very large or mixed in topics.

### The project:

<img src="https://github.com/dmsosa/microservices/assets/112881114/19d3821a-a846-4d13-8be6-e32e9061f6ad" style="width:700px; height: 450px"/>


![Animation](https://github.com/dmsosa/microservices/assets/112881114/19d3821a-a846-4d13-8be6-e32e9061f6ad)



### Documentation:


The documentation of our APIs developed with Swagger was to explain its features.



### SERVICES:

##### GATEWAY SERVICE:

In this project, we have four (4) services fronted by a Gateway using Spring Cloud Gateway, and the UI was made with the help of Thymeleaf. 

#### AUTH SERVICE:

OAuth Flow is used to secure our microservices with a single Authorization Server, it allows customers to login in and register. OAuth also secures service-to-service communication.





Grant Types:



1. Client Credentials (used for every service except the gateway):



It is used so that one service request to another can be authorized without giving a new credential



2. Authorization Code:



Our gateway must be a type of customer to send the request to any service, but it must be authorized by the AuthService.



#### ACCOUNT SERVICE:



Example of a Restful API, blocking, synchronous, it will save, receive and give customer accounts and the current items of each account.


**Context Path:** /api/account/


    Open Endpoint:
| Method | Endpoints| Request Body | Description |
|--|--|--|--|
| GET | /demo | | get demo account|
| POST | /save/demo | | Save stats for demo account |

    Secured endpoints
| Method | Endpoints | Description |
|--|--|--|
| GET | /{accountName} | Get account by name|
| POST | /create/{accountName} | Create Account |
| POST | /save/{accountName} | Save account stats |
| POST | /edit/{accountName} | Edit Account|
| POST | /items/{accountName} | Edit Account Items|
| DELETE| /{accountName} | Remove account by name|



#### STAT SERVICE:

**Context Path:** /api/stats/

Example of a Reactive API, nonblocking, asynchronous, it will save, receive and give account statistics and all the items of each account. I aim to make this service fully reactive, but it will use RDBMS with JPA, perhaps a MongoDB can be implemented later


    Open Endpoints:
| Method | Endpoints |Request Body | Description |
|--|--|--|--|
| GET | /demo | | get demo stats |


    Secured endpoints
| Method | Endpoints |Request Body| Description |
|--|--|--|--|
| GET| /{accountName}| | Get stats for account |
| POST | /save| AccountDTO| Save stats for account |
