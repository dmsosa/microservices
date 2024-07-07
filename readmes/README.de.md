# Microdienste Projekt ⚙️🐷

  

[![English Readme](https://img.shields.io/badge/lang-en-green)](https://github.com/dmsosa/microservices/blob/main/README.md) [![Frazosisch Readme](https://img.shields.io/badge/lang-de-blue)](https://github.com/dmsosa/microservices/blob/main/readmes/README.fr.md)

  

⚙️Microdienste Projekt ist nach [piggymetrics](https://github.com/sqshq/piggymetrics/tree/master) von @sqshq "Alexander Lukyanchikov" inspiriert, aber dieses Implementation PostgreSQL und ein einfacher Businesslogik benutzt, der Hauptsziele dieses Projekt ist ein Beispiel von Microdienste zu zeigen.

TechStack: PostgreSQL, Spring, Docker

![etwa](https://github.com/dmsosa/microservices/assets/112881114/6950e84b-5f82-46e1-ae11-681106470a0b)


Die Hindernisse, die ich wahrend der Entwlickung der Projekt gefunden hatte finden Sie in weiterer "etwas . md" Dateien, denn ich mochte nicht dieses Text entweder sehr groß noch gemischt von Themen machen.




### Dokumentation:

Die Dokumentation unsere APIs mit Swagger entwickelt war, um seinen Funktionen zu erklaren.

### DIENSTE:

##### GATEWAY DIENST: 🏬

Das Gateway Thymeleaf nutzen, um der UI zu erstellen

#### AUTH DIENST:🔒 

Sicherheitserver, womit das Kunden in unseres App login and registrieren kann. OAuth2 sicherheit auch fur Dienst zu Dienst kommunikation enrichten.

Grant Types:

1. Client Credentials (bei jeder Dienst ausser der Gateway verwendet):

Es ist benutz, so ein Dienst anfragen zu ein anderes authorisiert werden konnen, ohne neuen Beglaubigungsschreiben zu geben

2. Authorization Code:

Unseres Gateway eine Art Kunde sein, um die anfrage zu jedes Dienst zu senden, aber es muss autorisiert bei die AuthService sein.

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



