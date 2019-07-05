
////////////////////////////////////////////// READ ME \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


INFO:						Risiko
				- Das bekannte Brettspiel Risiko, als 
				Software programmiert. Gespielt werden kann
				mit verschiedenen Spielern durch die
				Server-Client Struktur.
				 
				Projekt f�r das Modul Progammieren 2 
				-------- Hochschule Bremen --------
Abgabedatum:				      5.7.2019

Benutzte Programmiersprache: 	Java JDK 8

Anweisung:	-MiniRisServer.java   in - Risikou-Server/src/ris/server/net/MiniRisServer.java 
		einmalig starten um Server zum Laufen zu bringen
		
		-RisikoClientGUI.java in - Risikou-Client/src/ris/client/gui/RisikoClientGUI.java
		pro Spieler starten

Anmerkungen: 	-Die Verbindung von Server und Client ist durch Sockets realisiert.
		-Jeder Spieler geht eine eigene Socket-Verbindung mit dem Server ein
		-Anfragen an den Server passieren �ber Risiko-Fassade/Client Request Processor, Updates �ber
		Server-Fassade/Server Request Processor. Beide nutzen daf�r den gleichen ObjectOutput-Stream.

bekannte Fehler:-Es kann nur ein Spiel gleichzeitig laufen
		-Laden funktioniert leider noch nicht vollst�ndig
		-Es kann �ftermal zu Fehlern in der Verbindung kommen :(

Wir w�nschen viel Spa� beim Spielen :)

Autors: Annie Berend -5033782
	Tobi Schmitt -5052509
	(Hannes Lesemann)

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		