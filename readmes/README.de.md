# Microdienste Projekt ⚙️🐷

  

[![English Readme](https://img.shields.io/badge/lang-en-green)](https://github.com/dmsosa/microservices/blob/main/README.md) [![Frazosisch Readme](https://img.shields.io/badge/lang-de-blue)](https://github.com/dmsosa/microservices/blob/main/readmes/README.fr.md)

  

⚙️Microdienste Projekt ist nach [piggymetrics](https://github.com/sqshq/piggymetrics/tree/master) von @sqshq "Alexander Lukyanchikov" inspiriert, aber dieses Implementation PostgreSQL und ein einfacher Businesslogik benutzt, der Hauptsziele dieses Projekt ist ein Beispiel von Microdienste zu zeigen.

TechStack: Spring, PostgreSQL

Merkmale:
-Service Registry with Eureka Netflix
-Spring Cloud Config Server
-Spring Cloud Gateway
-OAuth2 Authorization Server
-Resilience4J
-Thymeleaf

![etwa](https://github.com/dmsosa/microservices/assets/112881114/6950e84b-5f82-46e1-ae11-681106470a0b)


Die Hindernisse, die ich wahrend der Entwlickung der Projekt gefunden hatte finden Sie in weiterer "etwas . md" Dateien, denn ich mochte nicht dieses Text entweder sehr groß noch gemischt von Themen machen.




### Dokumentation:

![swagger](https://github.com/dmsosa/microservices/assets/112881114/16444857-9111-4e6f-bc5a-8b6bdd2a42fa) Die Dokumentation unsere APIs mit Swagger entwickelt war, um seinen Funktionen zu erklaren.

### DIENSTE:

##### GATEWAY DIENST: 🏬

Das Gateway Thymeleaf nutzen, um der UI zu erstellen

#### AUTHORIZATION DIENST:🔒 

Der Zweck dieses Sicherheitsservers besteht darin, Benutzern die Anmeldung und Registrierung für alle die Dienste in ein jeinziges Ort zu ermöglichen.

GRANT TYPES:
Client Credentials:
  Account Dienst
  Stats Dienst
  Noti Dienst

Authorization Code:
  Gateway

#### ACCOUNT DIENST:🪪

Beispiel von ein Restful API, blocking, synchronous, es Kundenkonten und den Aktuellenitems jeder Konto speichern, erhalten und geben werde.

**Context Path:** /api/account/

    Offnete Endepunkte:
| Method | Endepunkte| Request Body | Beschreibung |
|--|--|--|--| 
| GET | /demo | | demo Konto erhalten|
| POST | /save/demo |  | Stats fur demo Konto zu speichern  |


    Gesicherte Endepunkte
| Method | Endepunkte | Beschreibung |
|--|--|--| 
| GET | /{accountName}  | Konto bei Name erhalten|
| POST | /create/{accountName} | Konto erstellen |
| POST | /save/{accountName} | Stats von Konto speichern |
| POST | /edit/{accountName} | Konto bearbeiten|
| POST | /items/{accountName} | Items der Konto bearbeiten|
| DELETE| /{accountName} | Konto bei Name entfernen|

#### STAT DIENST: 🔢

Beispiel von ein Reaktiv API, nonblocking, asynchronous, es Kontenstatistiken und alle der Item jeder Konto speichern, erhalten und geben werde. Ich gilt es, dieses Dienst vollreaktiv zu machen, aber es nutzt RDBMS mit JPA, vielleicht spater ein MongoDB implementiert werden konnen

    Offnete Endepunkte:
| Method | Endepunkte |Request Body | Beschreibung |
|--|--|--|--| 
| GET | /demo | | demo Stats erhalten |


    Gesicherte Endepunkte
| Method | Endepunkte |Request Body|  Beschreibung |
|--|--|--|--|
| GET| /{accountName}| | Stats fur Konto zu erhalten|
| POST | /save| AccountDTO| Stats fur Konto zu speichern  |

Das Gateway fur der Stats unseres Konto anfragen werden
Stats ein Flux geben
Wenn kein Stats gibt, geben wir eine Leeres Karte zuruck
Das Speichern von Statistiken wurde direkt mit den Dienst durchgefuhrt
Das Seite neu laden

### NOTIFICATION DIENST 🔔

Offnete Endepunkte:


Gesicherte Endepunkte:
| Method | Endepunkte |Request Body | Parameter | Beschreibung |
|--|--|--|--| --|
| GET | /{accountName} |  | | notification des Konto zu erhalten |
| POST | /create | NotificationEntity | | notification zu erstellen |
| PUT | /edit | NotificationEntity | | notification zu bearbeiten |
| DELETE | /{accountName} |  | Notification Type (REMIND or BACKUP)| notification zu entfernen |

---

Für die Entwicklung wird eine In-Memory-H2-Datenbank verwendet, für die Produktion wird PostgreSQL verwendet

## Laufen das Projekt in Ihres PC

1. Klonen Sie das Repository mit dem folgenden Kommand:

`git clone https://github.com/dmsosa/microservices.git`

2. Gehen zu das Repo ein

`cd microservices`

3. Stellen Sie sicher, dass Ihr [Docker-Daemon](https://docs.docker.com/config/daemon/start/) laufen ausgeführt wird, und führen Sie dann Folgendes aus:

`docker compose up`

4. Das App sollen zu http://localhost:8061/index erreichbar sein
<img width="384" alt="Microservices" src="https://github.com/dmsosa/microservices/assets/112881114/a2263ba8-0ba0-4aa0-ab02-5e246eb9002e">


