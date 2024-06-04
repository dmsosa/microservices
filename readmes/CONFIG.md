# Config Dienst 

Eine der storesten Dingen ist es, dass ein Restart fur jedes einzenes Anderung der Dateien unseres Configdienst notig ist, am Beginnen, ich habe der Configdateien mit Native Profile gerstellt.

Aber ich mochte nicht ein Restart machen jedermals ein Anderung getan ist, man verliert zu viel Zeit so. Sodass ich habe der Actuator Abhangigkeit hinzugefugt und der Configdateien in ein GithubRepo zentralisiert.

Ziele:

* Dynamisch Refresh von Configdienst
* Sicherheit mit JWT implementieren