### Hindernisse fur das Entwlickung das Gateway:

### Warum wir ein Custom ServerAuthenticationFailureHandler haben

Wenn man das Code Grant Type in der OAuth2Flow nutzt, wie Sie wissen, der Kunde zu der 'redirect-uri' redirigiert werden, zu dieses Einzigkeit ich ein wenig Anderung gemacht habe, dann da man localhost um das App zu laufen, und 127.0.0.1 als das Host der redirect-uri nutzt, der Browser des Kundes zwei WebSessions gerstellt werde (1 fur localhost, 1 fur 127.0.0.1). Dann das Folgende passiert:

<img width="553" alt="Screenshot 2024-06-28 154524" src="https://github.com/dmsosa/microservices/assets/112881114/539e0988-6b00-4ed1-8565-8662117a73bc">
<br/>
<br/>
<img width="473" alt="Screenshot 2024-06-28 154525" src="https://github.com/dmsosa/microservices/assets/112881114/bb2b603c-2b94-4d23-9681-76772e9bd340">

Am beginnen, alles wirkt und mann kann das App nutzen ohne kein Problem, aber nehmen wir an, dass man aus ingerdeinem Grund wieder zu 'localhost' gehen, z.B weil man es in Google geschreibt hast oder wahrend des Development des Apps ein Anderung machst, das App restarten werden und zu localhost noch einmal redirigiert wirst, dann:

**Wir sind in in dieses Punkt: Localhost (unauthorized), 127.0.0.1 (noch authorized), AuthServer (authorized)**

1. localhost uns wieder zu AuthServer redirigieren werden.
2. AuthServer noch ein auth-code zu 127.0.0.1 senden (/login/oauth2/code/{registrationId}) Endepunkt.
3. Das 127.0.0.1 ein Exception auswirft, (Authorization Request Not Found), dann es bat keine auth-code.

Aus dieses Grund, man ein custom ServerAuthenticationFailureHandler findest, womit wenn ein "authorization-request-not-found" fehler erhalten werde, dann loschen wir das SecurityContext, um das 127.0.0.1 fur ein neues Code bitten zu machen.


### Nachdem Authorisation: 
Sobald wir authorisiert wird, unseres WebClient durch ein ExchangeFilterFunction der AccessToken zu das Anfrage hinzufugen werden, um mit der Dienste kommunizieren zu konnen.

Fur die geoffneten Endepunkte unseres API (mit Demokonto) kein Authentication notig ist.

# Terms und Conditions hinzufugen
