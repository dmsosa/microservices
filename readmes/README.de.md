# Microdienste Projekt

  

[![English Readme](https://img.shields.io/badge/lang-en-green)](https://github.com/dmsosa/microservices/blob/main/README.md) [![Frazosisch Readme](https://img.shields.io/badge/lang-de-blue)](https://github.com/dmsosa/microservices/blob/main/readmes/README.fr.md)

  

Microdienste Projekt ist nach [piggymetrics](https://github.com/sqshq/piggymetrics/tree/master) von @sqshq "Alexander Lukyanchikov" inspiriert, aber dieses Implementation PostgreSQL und ein einfacher Businesslogik benutzt, der Hauptsziele dieses Projekt ist ein Beispiel von Microdienste zu zeigen.

TechStack: PostgreSQL, Spring, Docker

Die Hindernisse, die ich wahrend der Entwlickung der Projekt gefunden hatte finden Sie in weiterer "etwas . md" Dateien, denn ich mochte nicht dieses Text entweder sehr groß noch gemischt von Themen machen.

### Dokumentation:

Die Dokumentation unsere APIs mit Swagger entwickelt war, um seinen Funktionen zu erklaren.

### Uber das Projekt:

# Das Dateibank, und was kommst am Nächsten

Jedesmal du ein neues Item in deinem Konto erstellst, neue “Stats” sind also gerstellten.

Gerade in diesem Fall ich habe ein implementation mit PostgreSQL in GitHub noch nicht gesehen, und es ist verständlich, denn est ist schwierig mit RDBMS Dateien durch Beziehungen zu verbinden. 

Ich versuchte es, Datapoints  zu erstellen, aber es ging nicht, Datapoints sind die Stats eines Konto am ein Zeitpunkt. Nichts mehr als “Mein Ersparnisse für Mai 15, 2024” ich nenne Datapoints als “Dp” von nun an.

z.B, Stats sind grundsätzlich einfach, es hatte nur drei Felder: totalIncomes, totalExpenses, totalSavings. Es gibt ein “Stats” für jedes Currency (USD, EUR, GBP) also drei Stats für jedes “Dp”. Aber wie viel Euro ein Dolar kosten änderst sich mit der Zeit.

Also es ist bei ein BaseRatio gerechnet, der Base ist das Dolar und der Ratio wie viel Dolar ein andere Münze kostet (ein außer API gibst uns der aktuell BaseRatio) zum Beispiel von USD zu GBP es konnte 0.71 sein.

Also vorstellt man, jedes POST Anfrage ein neues “Dp” für LocalDateTime.now() aufbauen --Also 1 Tabelle— wer drei (3) Stats hat —2 Tabelle—, jeder mit ein BaseRatio verbunden —3 Tabelle-. Es ist möglich, aber unnötig schwierig und der Absicht des Projekt nicht ein Komplexe Business Logic zu erstellen, sondern der Struktur den Microdienste zu erklären ist. Für ein potenter Beispiel für Business Logic mit RDBMS kannst du mein dmblog anzuschauen. Für dieses Grund habe ich ein Änderung zu ein MongoDB geplant. 

# Das Gateway: Spring mit Thymeleaf

Ich möchte ein tolles Interface erstellen sowie piggymetrics getan hat, aber ich habe Thymeleaf benutzt um es aufzubauen.

GET request: Get das Konto von AccountService und zeigen es auf in index.html

( Wenn das Konto noch nicht existiert, das Gateway erstellt es für dich mit Standardwerten )

PUT: Formular mit versteckten Inputs, es gibt zwei Formulare, ein um das Kontodetails zu ändern, anderes für den Items zu arbeiten.

DELETE: Ein knopf um das Konto zu entfernen

Notiz: Selbst wenn du nicht ein Konto erstellen möchtest, kannst einfach als ‘demo’ fortfahren, es werde nur offenen endpoint anfragen.

# Dev: H2 Dateibank, Prod: PostgreSQL Dateibank

H2 Dateibank für jeder Dienst im Dev Profile, ein schnelle Notiz: wenn man das H2 mit ein vorgegebenes Nutzer für AuthServer nutzen, es ist nicht möglich das Login zu machen, weil das AuthServer sein eigenes BCryptEncoder nutzt um der Nutzer zu identifizieren, und es wird in keinem Fall mit der vorgegebene Passwort passen. 

Dafür ich hatte ein Bean benutzt, um ein POST zu der /register Endpoint zu machen als bald als das App gestartet ist, sodass H2 hat ein demo Benutzer, womit man Login machen kann

# Hindernisse:

Eine meine schwieriger Hindernisse was der Dienste durch Eureka in Docker zu kommunizieren.

Hindernis:

Erst wenn man nur “http://localhost:9000/api/uaa” als Issuer jedes ResourceServer konfiguriert, der Docker Container kann nicht der AuthServer herausfinden.

Lösung: Das Issuer IP Addresse durch DiscoveryClient finden, und als Bean in unsere Sicherheitseinstellungen anwen

[Die Dockerfile](https://www.notion.so/Die-Dockerfile-1e47b6e81f244145b64b3458365b08ba?pvs=21)

Select * From notifications u where u.active = true AND u.lastNotified <

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