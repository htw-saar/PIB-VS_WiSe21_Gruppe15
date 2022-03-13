# PIB-VS_WiSe21_Gruppe15

# "!!!!" Markierungen als Gruppe durchgehen und schreibfehler/grammer überprüfe <br>-> nur ein grundkonzept

# Tic-Tac-Toe Server

Dies ist ein Gruppenprojekt im fünften Semester der htwsaar im Fach Verteilte Systeme. Ziel ist es ein Tic-Tac-Toe Spiel
so zu implementieren, dass es über einen Server läuft und beliebig viele Spiele online parallel erstellt werden können.
Aufgabe ist es die Technologien aus der Vorlesung wie Java RMI, MQTT usw. zu verwenden, um das Projekt zu realisieren.
Die Spieler spielen klassisch 1v1.

## Architektur

Unser Tic-Tac-Toe Spiel läuft über das Terminal. Um dies zu realisieren, haben wir uns ein Gameboard angelegt, welches X
bzw. O Einträge der jeweiligen Spieler entgegennimmt. Es hat einen 1 gegen 1 Modus, in dem man gegen einen anderen
Spieler online spielen kann. Gewinnt ein Spieler, erscheint die Siegesausgabe für den jeweiligen Spieler. Bei einem
Unentschieden oder einem vollen Board erscheint eine entsprechende Ausgabe im Terminal. Bei falschen Eingaben werden
entsprechende Meldungen im Terminal ausgegeben. Beim Start des Spiels kann man einen Namen eingeben und dieser wird mit
einer ID vom Server verknüpft. Es wird ein Raum auf dem Server erstellt, in dem beide Spieler gespeichert sind. Die
Spieler senden dann nur ihre Interaktion an den Server, welcher dann das Ergebnis an die Teilnehmer sendet.

Man kann auswählen, ob man ein Spiel hosten möchte oder nur einem Spiel beitreten möchte. Wenn man ein Spiel hostet,
wird vom Server eine eindeutige ID vergeben, über die andere Spieler beitreten können. Spieler 2 kann einem offenen
Spiel über die vom Spieler 1 erstellte Game ID beitreten.

Der Server benutzt Java RMI für die Kommunikation zwischen Client und Server. Der Client bindet auf den Sever und erbt
Methoden von diesem. Für die Spielhistorie wird eine MYSQL-Datenbank benutzt, wo diese dann
schlussendlich gespeichert wird.

#### Use Cases

Die jeweiligen Clients sollen sich mit dem Server verbinden, sodass dementsprechend die jeweiligen Spieler online
gegeneinander spielen können. Orientiert am klassischen Tic-Tac-Toe benutzt Spieler 1 ein X bzw. Spieler 2 ein O um
seine Felder zu setzen.

#### Anforderungen

Abgeleitet aus den Use Cases und gemeinsamen Teamsitzungen ergeben sich folgende Anforderungen für das Projekt:

###### Funktionale Anforderungen

* Einmaliger Benutzername/ ID
* Lobby hosten
* Lobby über ID beitreten
* Tracken von Spielhistorien und Spielen
* Persistente Serververbindung
* Erstellen von User-Daten, um Historie aller vergangenen Spiele zu erstellen

###### Nichtfunktionale Anforderungen

* Einheitliche Ausgaben für beide Spieler
* Fairness/gleiche Spielbedingungen (z. B. selbe Spielzugdauer)
* System darf nicht länger als eine Sekunde benötigen für die In-Game-Kommunikation

#### Lösungsstrategie

# !!! weitere Infos hier
Es handelt sich bei unserer Implementierung um einen Thin Client, weshalb man offline nicht spielen kann. Wir haben uns für dieses Design entschieden, da es uns die Implementierung des Codes einfacher macht, indem wir als Übergabeparameter Client RMI Objekte übergeben. Der Code wird dadurch kompakter und ordentlicher. So werden auch lokal weniger Resourcen, Speicher und Rechnerleistung verbraucht. Dementsprechend speichern wir unsere Daten auf dem Server.

_Interface:_ <br>
Wir haben uns für ein Command Line Interface entschieden, da dieses schnell und einfach gebaut werden kann. Eine solche
Terminal-Lösung ist auch deutlich stabiler und produziert weniger Fehler.

_Middleware:_ <br>
Als Middleware benutzen wir Java-RMI....

# !!!!!! Hier Middleware Infos rein

_Datenbank:_ <br>
Als Datenbank haben wir uns MySql ausgesucht. Durch das Hibernate Framework konnten wir eine solche Datenbank schnell
und einfach erstellen. Tabellen werden automatisch generiert und Einträge lassen sich schnell und einfach
einfügen/löschen.

#### Statisches Modell

###### Komponentendiagramm

![alt text](https://i.imgur.com/UGgXo3g.png)

Es gibt Server und Client. Der User gibt Daten wie z. B. den Spielernamen an den Client weiter. Der Client steht über eine Java RMI Schnittstelle mit dem Server in Verbindung. Über Java RMI erhält der Client Daten über die Spielhistorie durch die Datenbank. Des Weiteren stellt Java RMI die Schnittstelle über den Austausch von Spieldaten da. Es werden neue Daten vom Server an den jeweils anderen Client geschickt und analog Eingaben der jeweilgen Clients an den Server übermittelt. 

###### Verteilungsdiagramm

![alt text](https://i.imgur.com/QhfyZ5h.png)

Die Kommunikation zwischen Client und Server wird über Java RMI implementiert. Java RMI erlaubt den Aufruf von Methoden
von Objekten, die sich auf einem anderen bzw. einem entfernten Rechnersystem befinden. Der Server steht in unmittelbarer
Verbindung mit einer MYSQL Datenbank. Über SQL-Befehle, die durch Hibernate automatisch generiert werden, können z. B.
Spielhistorie bearbeitet und manuell User angelegt werden.

###### Paketdiagramm

# !!!!! Kleine übersicht bauen

###### Klassendiagramme
#!!! Muss geändert werden

![alt text](https://i.imgur.com/1SWntWC.png)

###### API

[Wiki Link zur API-Dokumentation](https://github.com/htw-saar/PIB-VS_WiSe21_Gruppe15/wiki/API-Dokumentation#api-dokumentation)

#### Dynamisches Modell

###### Aktivitätsdiagramm

![alt text](https://i.imgur.com/GZvQMQs.png)

###### Sequenzdiagramm

![alt text](https://i.imgur.com/dm6JNR5.png)

## Getting Started

Um unser Projekt zu bauen wird Maven benötigt und mindestens Java 15. Um den Client zu starten brauchen beide Spieler
eine identische Client-Konfiguration und die gleiche Server-IP. Ein Client wird über **
src/main/java/com/htwsaar/client/Client.java** gestartet. Alternativ kann man die jeweiligen Clients über die
bereitgestellten Jar Dateien starten.

Der Server wird einmalig über **src/main/java/com/htwsaar/server/Server.java** gestartet. Die IP des Servers muss über
die jeweiligen Clients eingetragen werden.

#### Vorraussetzungen

_Client_:

* Java installiert
* Maven installiert
* Grundkenntnisse eines Terminals/IDE
* Vorhandene Internetverbindung

_Server_:

* Java installiert
* MYSQL installiert
* Vorhandene Internetverbindung

#### Installation und Deployment

# !!!! jar dateien erzeugen und erklären wie man sie im Terminal startet oder direkt in der IDE

## Built With

* Git (GitHub) zur Versionskontrolle
* GitHub ReadMe zeigt abgenommene Zusammenfassung der Konzepte und kleine Einführung des Spiels
* GitHub Wiki
* GitHub Projects - zur Aufgabenplanung/Verteilung
* Java 16
* JavaRMI
* MySQL mit Hibernate
* Maven

## License

HTW Gruppe 15

#### Gruppen

* Alexander/Simon
* Mario/Max
* Ahmad/Oliver

[Liquiditätsplan](https://github.com/htw-saar/PIB-VS_WiSe21_Gruppe15/wiki/Liquidit%C3%A4tsplan)

## Acknowledgments

Danke an Prof. Esch für eine Einweisung in die verteilte Programmierung, dadurch haben wir Java-RMI als Middleware
entdeckt.
