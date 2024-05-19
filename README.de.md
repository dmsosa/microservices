## Microdienste Projekt

[![English Readme](https://img.shields.io/badge/lang-en-green)](https://github.com/dmsosa/microservices/blob/main/README.md)
Microdienste Projekt ist nach "Piggymetrics" von  @sqshq "Alexander Lukyanchikov" inspiriert. Aber mit dieses Implementation benutzer wir PostgreSQL und ein einfacher Statsdienst, der Wirkung das Programm ist nicht die Gleiche, aber der Hauptsziele ist, ein Mikrodienstearchitektur zu zeigen.

Jedes Dienst verfügt über eine eigene Dokumentation, dessen mit S[README.md](README.md)wagger erstellt wurde, und auch jedes seine eigenen Tests.

### Status: Development

ZU MACHEN:
* Unit test fur jeder Dienst
* CSRF Token zu hinzufugen
* Customize Swagger Docs page
* Encrypt client secrets
* Auth mit OAuth
  * Password Grant (JWT Token) fur Kunden
  * Client Grant fur Inneresdienstekommunikation
  

### Dokumentation:

Die Dokumentation unsere APIs mit Swagger entwickelt war, um en ein einfaches, verstandliche Weise die seinen Funktionen zu erklaren.

### DIENST / SERVICES:

##### GATEWAY SERVICE /  GATEWAY DIENST:

Alle die dienste in ein einzige Ort, es erfull die folgenden funktionen:

1. Es verwendet ein WebRequest, um anfrage zu sich selbst zu senden
2. Die Templates des Frontends zu geben 

##### Die Hindernisse, mit denen wir konfrontiert waren

Der Authentikation unseres WebClient, durch ein ExchangeFilterFunction gelost war.
Da das ThymeleafTemplateEngine nur ein ReactiveDataDrivenContextVariable laden kann, wir versuchte die folgende 'Workarounds':

- Ein "AccountContextVariable" klass zu erstellen, um alle die Datei zu erhalten (dieses schauen sie in commit 370a8824572c0d4832e1c21726307de5d3174131 mit Titel 'AccountContextVariable'), wennschon dieses Losung gelauft hat, ich nutzen es nicht am liebsten, denn man einfach vorstellen wenn wir Datei von 10 APIs nutzen brauchen, es wurde ein grosse Kopfschmerzen werden.
- "portlets" nutzen.
- Ein View fur jedes API Datei erstellen.

#### AUTH SERVICE / AUTH DIENST:

Es ist ein Sicherheitserver, womit benutzere das login und register machen kann, wir nutzen OAuth2 sowohl fur ein Login mit anderen Nutzerkonto von verschiedene Dienste zu erlauben, als auch Erlaubnis von unsere Dienste notig zu machen.

Grant Types:

1. Client Credentials:
Es ist benutz, so ein Dienst anfragen zu ein anderes authorisiert werden konnen, ohne neuen Beglaubigungsschreiben zu geben

2. Authorization Code:
Unseres Gateway eine Art Kunde sein, um die anfrage zu jedes Dienst zu senden, aber es muss autorisiert bei die AuthService sein.

#### ACCOUNT SERVICE / ACCOUNT DIENST:

* Example of MVC Rest API, blocking, synchronous

#### STATS SERVICE / STAT DIENST:

* Ich gilt es, dieses Dienst vollreaktiv zu machen, aber es nutzen RDBMS mit JPA, vielleicht spater ein MongoDB implementiert werden konnen

#### DOCKER CONTAINERS ZU LAUFEN

* Das Eureka Server wirkts und die Dienste kann miteinander kommunizieren, aber Eureka gibst die IP Addresse den Dienste, z.B '127.23.0.5', also unsere Docker Host (Locale Machine) kann der Dienste nicht erreichen. Denn wenn du ein Anfrag zu 'http://localhost:8888/' sendest, es ging, aber nicht mit 'http://127.23.0.4:8888/', dass machst der Dienste nicht erreichbar von unseres Machine . Aus dieses grund, der "hostname" jeder Dienst ist als "localhost" eingestellt.