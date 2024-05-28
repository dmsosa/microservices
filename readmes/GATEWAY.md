### Hindernisse fur das Entwlickung das Gateway:



Der Authentikation unseres WebClient, durch ein ExchangeFilterFunction gelost war.

Da das ThymeleafTemplateEngine nur ein ReactiveDataDrivenContextVariable laden kann, wir versuchte die folgende 'Workarounds':

  

- Ein "AccountContextVariable" klass zu erstellen, um alle die Datei zu erhalten (dieses schauen sie in commit 370a8824572c0d4832e1c21726307de5d3174131 mit Titel 'AccountContextVariable'), wennschon dieses Losung gelauft hat, ich nutzen es nicht am liebsten, denn man einfach vorstellen wenn wir Datei von 10 APIs nutzen brauchen, es wurde ein grosse Kopfschmerzen werden.

- "portlets" nutzen.

- Ein View fur jedes API Datei erstellen.