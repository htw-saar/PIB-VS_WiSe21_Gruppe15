# PIB-VS_WiSe21_Gruppe15
* Alexander/Simon
* Mario/Max
* Ahmad/Oliver

# Tic-Tac-Toe Server

Dies ist ein Gruppenprojekt im fünften Semester der htwsaar im Fach Verteilte Systeme. Ziel ist es ein Tic-Tac-Toe Spiel so zu implementieren, dass es über einen Server läuft und beliebig viele Spiele online parallel erstellt werden können. Aufgabe ist es die Technologien aus der Vorlesung wie Java RMI, MQTT usw. zu verwenden um das Projekt zu realisieren. Die Spieler spielen klassisch 1v1.

## Architektur

Unser Tic-Tac-Toe Spiel läuft über das Terminal. Um dies zu realisieren, haben wir uns ein Gameboard angelegt, welches X bzw. O Einträge der jeweiligen Spieler entgegennimmt. Es hat einen 1 gegen 1 Modus, in dem man gegen einen anderen Spieler online spielen kann.Gewinnt ein Spieler, erscheint die Siegesausgabe für den jeweiligen Spieler. Bei einem Unentschieden oder einem vollen Board erscheint eine entsprechende Ausgabe im Terminal. Bei falschen Eingaben werden entsprechende Meldungen im Terminal ausgegeben. Beim Start des Spiels kann man einen Namen eingeben und dieser wird mit einer ID vom Server verknüpft. Es wird ein Raum auf dem Server erstellt, in dem beide Spieler gespeichert sind. Die Spieler senden dann nur ihre Interaktion an den Server, welcher dann das Ergebnis an die Teilnehmer sendet.

Man kann auswählen, ob man ein Spiel hosten möchte oder nur einem Spiel beitreten möchte. Wenn man ein Spiel hostet, wird vom Server eine eindeutige ID vergeben, über die andere Spieler beitreten können. Spieler 2 kann einem offenen Spiel über die vom Spieler 1 erstellte Game ID beitreten.

Der Server benutzt Java RMI für die Kommunikation zwischen Client und Server. Der Client bindet auf den Sever und erbt demenstprechend Methoden von diesem. Für die Spielhistorie wird eine MYSQL-Datenbank benutzt, wo diese dann schlussendlich gespeichert wird.

#### Use Cases

Die jeweiligen Clients sollen sich mit dem Server verbinden, sodass dementsprechend die jeweiligen Spieler online gegeneinander spielen können. Orientiert am klassischen Tic-Tac-Toe benutzt Spieler 1 ein X bzw. Spieler 2 ein O um seine Felder zu setzen.

#### Anforderungen
Abgeleitet aus den Use Cases und gemeinsamen Teamsitzungen ergeben sich folgende Anforderungen für das Projekt:

###### Funktionale Anforderungen
* Einmaliger Benutzername/ ID
* Lobbyhosting
* Tracken von Spielerhistorien und Spielen
* Erstellen von User-Daten um Historie aller vergangenen Spiele zu erstellen

###### Nichtfunktionale Anforderungen
* Einheitliche Ausgaben für beide Spieler
* Fairness/gleiche Spielbedingungen (z. B. selbe Spielzugdauer)
* System darf nicht länger als eine Sekunde benötigen für die In-Game Kommunikation

#### Lösungsstrategie
Geben Sie eine kompakte Beschreibung der Kernidee Ihres Lösungsansatzes. Stellen Sie dar, welche Technologien Sie einsetzen. Begründen Sie wichtige Designentscheidungen. Z.B. die Wahl der Middleware, der Programmiersprache, des Architekturansatzes etc.

Erläutern Sie, welche aus der Vorlesung bekannten Architekturprinzipien Sie umgesetzt haben und begründen Sie warum. Stellen Sie dar, in welcher Form diese Aspekte in ihre Lösung eingeflossen sind. Gehen Sie insb. auf folgende Aspekte ein:
- Transparancy: Welche Aspekte der Transparancy sind umgesetzt? Wie? Warum?
- Softwarearchitektur
- Systemarchitektur
- Request-Reply Pattern (Synchron oder Asynchron)
- Thin-Client vs. Fat-Client
- Stateless vs. stateful Server-Desgin
- Fehlersemantik
- Idempotenz
- Skalierbarkeit


#### Statisches Modell

###### Komponentendiagramm
![alt text](https://i.imgur.com/UGgXo3g.png)

###### Verteilungsdiagramm
![alt text](https://i.imgur.com/QhfyZ5h.png)



Die Kommunikation zwischen Client und Server wird über Java RMI implementiert. Java RMI erlaubt den Aufruf von Methoden von Objekten, die sich auf einem anderen bzw. einem entfernten  Rechnersystem befinden. Der Server steht in unmittelbarer Verbindung mit einer MYSQL Datenbank. Über SQL-Befehle können z. B. Spielhistorie bearbeitet und manuell User angelegt werden.

###### Optional: Paketdiagramm

###### Klassendiagramme

###### API
Dokumentieren Sie die API Ihrer Software in geeigneter Art und Weise. Im Fall einer REST API z.B. so: https://gist.github.com/iros/3426278 oder mithilfe einer OpenAPI-Spezifikation.

Zur Dokumentation einer SOAP-Schnittstelle gibt es zahlreiche Tools, um die Dokumentation direkt aus dem WSDL-Dokument zu erzeugen. Z.B.: https://github.com/chenjianjx/wsdl2html

Im Falle einer RMI-Schnittstelle nutzen Sie Javadoc.

Falls Sie MQTT nutzen, dokumentieren Sie ihre MQTT-Topics.

#### Dynamisches Modell
Beschreiben Sie den Ablauf Ihres Programms in Form von:

###### Aktivitätsdiagramm

###### Sequenzdiagramm
Darstellung des Nachrichtenaustausches an Prozessgrenzen

## Getting Started
Dokumentieren Sie, wie man ihr Projekt bauen, installieren und starten kann.

#### Vorraussetzungen
Welche Voraussetzungen werden benötigt, um ihr Projekt zu starten: Frameworks, Software, Libraries, ggf. Hardware.

#### Installation und Deployment
Beschreiben Sie die Installation und das Starten ihrer Software Schritt für Schritt.

## Built With
* Git (GitHub) zur Versionskontrolle
* GitHub ReadMe zeigt abgenommene Zusammenfassung der Konzepte und kleine Einführung des Spiels
* GitHub Wiki
* GitHub Projects - zur Aufgabenplanung/Verteilung
* Java
* JavaRMI
* MySQL



## License

This project is licensed under ...

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
