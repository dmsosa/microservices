# Microservices project



 



[![English Readme](https://img.shields.io/badge/lang-en-green)](https://github.com/dmsosa/microservices/blob/main/README.md) [![German Readme](https://img.shields.io/badge/lang-de-blue)](https://github.com/dmsosa/microservices/blob/main/readmes/README.de.md) [![French Readme](https://img.shields.io/badge/lang-fr-red)](https://github.com/dmsosa/microservices/blob/main/readmes/README.fr.md)



 



Microservices project is inspired by [ piggymetrics ](https://github.com/sqshq/piggymetrics/tree/master) by @sqshq "Alexander Lukyanchikov", but this implementation uses PostgreSQL and a simple business logic, the main goals of this project is an example of microservices.



TechStack: PostgreSQL, Spring, Docker



The obstacles that I found during the development of the project can be found in other "somewhat . md" files, because I did not want to make this text either very large or mixed in topics.

### The project:

<blockquote class="imgur-embed-pub" lang="en" data-id="a/sCSWpRG"  ><a href="//imgur.com/a/sCSWpRG">Microdienste Projekt</a></blockquote><script async src="//s.imgur.com/min/embed.js" charset="utf-8"></script>

### Documentation:



The documentation of our APIs developed with Swagger was to explain its features.



### SERVICES:

##### GATEWAY SERVICE:



Through the gateway it is possible to communicate with any service.



It was also used to display templates with Thymeleaf for a simple UI.



#### AUTH SERVICE:



Security server that allows customers to login and register in our app. OAuth2 security can also be set up for service-to-service communication.



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
