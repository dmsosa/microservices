# Notidienst
Es gibt zwei (2) Arten von Notificationen: REMIND und BACKUP, die letzten ein Attachment als "backup.json" gennant brauchen.

Wenn Das Accountdienst nicht verufgbar ist, wir nutz der Resilience4J Library um ein gutes Fallback Antwort zum Kunden zu geben. 

Methode: getAccount(String accountName)
Wir haben ein Fallback mit OpenFeing hinzugefugt, aber das Ziele ist Resilience4J zu nutzen, und mit Gateway auch

* Resilience Retry nutzen
* Resilience Circuit Breaker nutzen
* Nchricht mit {1} zu reparieren    
* Backup.json zu verbessern