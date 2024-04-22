## Microdienste Projekt


Microdienste Projekt ist nach "Piggymetrics" von  @sqshq "Alexander Lukyanchikov" inspiriert.

### Status: Development

ZU MACHEN:
* Unit test fur jeder Dienst
* CSRF Token zu hinzufugen
* Auth mit OAuth
  * Password Grant (JWT Token) fur Kunden
  * Client Grant fur Inneresdienstekommunikation
  

### Dokumentation:

Die Dokumentation unsere APIs mit Swagger entwickelt war, um en ein einfaches, verstandliche Weise die seinen Funktionen zu erklaren.

### DIENST / SERVICES:

### GATEWAY:

Alle die dienste in ein einzige Ort, es erfull die folgenden funktionen:

1. Es verwendet ein WebRequest, um anfrage zu sich selbst zu senden
2. Die Templates des Frontends zu geben 

#### AUTH SERVICE:

Es ist ein Sicherheitserver, womit benutzere das login und register machen kann, wir nutzen OAuth2 sowohl fur ein Login mit anderen Nutzerkonto von verschiedene Dienste zu erlauben, als auch Erlaubnis von unsere Dienste notig zu machen.

Grant Types:

1. Client Credentials:
Es ist benutz, so ein Dienst anfragen zu ein anderes authorisiert werden konnen, ohne neuen Beglaubigungsschreiben zu geben

2. Authorization Code:
Unseres Gateway eine Art Kunde sein, um die anfrage zu jedes Dienst zu senden, aber es muss autorisiert bei die AuthService sein.


