# Microdienste Projekt

  

[![English Readme](https://img.shields.io/badge/lang-en-green)](https://github.com/dmsosa/microservices/blob/main/README.md) [![Frazosisch Readme](https://img.shields.io/badge/lang-de-blue)](https://github.com/dmsosa/microservices/blob/main/readmes/README.fr.md)

  

Microdienste Projekt ist nach [piggymetrics](https://github.com/sqshq/piggymetrics/tree/master) von @sqshq "Alexander Lukyanchikov" inspiriert, aber dieses Implementation PostgreSQL und ein einfacher Businesslogik benutzt, der Hauptsziele dieses Projekt ist ein Beispiel von Microdienste zu zeigen.

TechStack: PostgreSQL, Spring, Docker

Die Hindernisse, die ich wahrend der Entwlickung der Projekt gefunden hatte finden Sie in weiterer "etwas . md" Dateien, denn ich mochte nicht dieses Text entweder sehr groß noch gemischt von Themen machen.

### Dokumentation:

Die Dokumentation unsere APIs mit Swagger entwickelt war, um seinen Funktionen zu erklaren.

### DIENSTE:
##### GATEWAY DIENST:

Durch das Gateway es ist moglich, mit jeder Dienst zu kommunizieren.

Es auch um Templates mit Thymeleaf fur ein einfach UI zu zeigen benutzt war.

#### AUTH DIENST:

Sicherheitserver, womit das Kunden in unseres App login and registrieren kann. OAuth2 sicherheit auch fur Dienst zu Dienst kommunikation enrichten.

Grant Types:

1. Client Credentials (bei jeder Dienst ausser der Gateway verwendet):

Es ist benutz, so ein Dienst anfragen zu ein anderes authorisiert werden konnen, ohne neuen Beglaubigungsschreiben zu geben

2. Authorization Code:

Unseres Gateway eine Art Kunde sein, um die anfrage zu jedes Dienst zu senden, aber es muss autorisiert bei die AuthService sein.

#### ACCOUNT DIENST:

Beispiel von ein Restful API, blocking, synchronous, es Kundenkonten und den Aktuellenitems jeder Konto speichern, erhalten und geben werde.

**Context Path:** /api/stats/

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

#### STAT DIENST:

Beispiel von ein Reaktiv API, nonblocking, asynchronous, es Kontenstatistiken und alle der Item jeder Konto speichern, erhalten und geben werde. Ich gilt es, dieses Dienst vollreaktiv zu machen, aber es nutzen RDBMS mit JPA, vielleicht spater ein MongoDB implementiert werden konnen

    Offnete Endepunkte:
| Method | Endepunkte |Request Body | Beschreibung |
|--|--|--|--| 
| GET | /demo | | demo Stats erhalten |


    Gesicherte Endepunkte
| Method | Endepunkte |Request Body|  Beschreibung |
|--|--|--|--|
| GET| /{accountName}| | Stats fur Konto zu erhalten|
| POST | /save| AccountDTO| Stats fur Konto zu speichern  |