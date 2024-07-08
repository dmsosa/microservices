# Microservices project ⚙️🐷

[![English Readme](https://img.shields.io/badge/lang-en-green)](https://github.com/dmsosa/microservices/blob/main/README.md) [![French Readme](https://img.shields.io/badge/lang-de-blue)](https://github.com/dmsosa/microservices/blob/main/readmes/README.fr.md) [![German Readme](https://img.shields.io/badge/lang-de-red)](https://github.com/dmsosa/microservices/blob/main/readmes/README.de.md)

⚙️Microservices project is inspired by [piggymetrics](https://github.com/sqshq/piggymetrics/tree/master) by @sqshq "Alexander Lukyanchikov", but this implementation uses PostgreSQL and a simple business logic, the main goal of this project is to show an example of microservices architecture.

TechStack: PostgreSQL, Spring, Docker

![something](https://github.com/dmsosa/microservices/assets/112881114/6950e84b-5f82-46e1-ae11-681106470a0b)

The obstacles I encountered during the development of the project can be found in additional "somewhat . md" files, because I didn't want to make this text either very large nor mixed of topics.

### Documentation: 
![swagger](https://github.com/dmsosa/microservices/assets/112881114/16444857-9111-4e6f-bc5a-8b6bdd2a42fa) The documentation of our APIs was developed with Swagger to explain its functions.

### SERVICES:

##### GATEWAY SERVICE: 🏬

Entrypoint of the app, allows users to send requests to each of the four (4) services of the application. Provides an UI with Thymeleaf as well.

#### AUTHORIZATION SERVICE:🔒

The purpose of this security server is to allow users to log in and register for all the services in one place.

GRANT TYPES:
Client Credentials:
 Account Service
 Stats Service
 Noti Service

Authorization Code:
 Gateway

#### ACCOUNT SERVICE:🪪

Example of a Restful API, blocking, synchronous, it will store, receive and give customer accounts and the current items of each account.

**Context Path:** /api/account/

Open Endpoints:
| Method | Endpoints| Request Body | Description |
|--|--|--|--|
| GET | /demo | | demo get account|
| POST | /save/demo | | Save stats for demo account |

Secured endpoints
| Method | Endpoints | Description |
|--|--|--|
| GET | /{accountName} | Get account by name|
| POST | /create/{accountName} | Create account |
| POST | /save/{accountName} | Save stats of account |
| POST | /edit/{accountName} | Edit account|
| POST | /items/{accountName} | Edit items of account|
| DELETE| /{accountName} | Remove account by name|

#### STAT SERVICE: 🔢

Example of a reactive API, nonblocking, asynchronous, it will save, get and return account statistics and all the items of each account.


Open endpoints:
| Method | Endpoints |Request Body | Description |
|--|--|--|--|
| GET | /demo | | demo Get stats |

Secured endpoints
| Method | Endpoints |Request Body| Description |
|--|--|--|--|
| GET| /{accountName}| | Get stats for account|
| POST | /save| AccountDTO| Save stats for account |

The gateway will request the stats of our account
Give stats a flow
If there are no stats, we return an empty card
Saving statistics was done directly with the service
Reload the page


### NOTIFICATION SERVICE 🔔

Open endpoints:

Secured endpoints:
| Method | Endpoints |Request Body | Parameters | Description |
|--|--|--|--| --|
| GET | /{accountName} | | | to receive notification of the account |
| POST | /create | NotificationEntity | | to create notification |
| PUT | /edit | NotificationEntity | | to edit notification |
| DELETE | /{accountName} | | Notification Type (REMIND or BACKUP)| to remove notification |

---

You can run the project in your local machine with an in-memory H2 database or PostgreSQL,

By default,  an in-memory H2 database is used for development profile, and  PostgreSQL is used for production

## Run the project on your PC

1. Clone the repository with the following command:

`git clone https://github.com/dmsosa/microservices.git`

2. Go to the repo

`cd microservices`

3. Make sure your [Docker daemon](https://docs.docker.com/config/daemon/start/) is running, then run:

`docker compose up`

4. The app should be reachable at http://localhost:8061/index

 <img width="384" alt="Microservices" src="https://github.com/dmsosa/microservices/assets/112881114/a2263ba8-0ba0-4aa0-ab02-5e246eb9002e">

