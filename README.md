# WebCrawlerImmobileApp

## Crawler

Es wird eine URL erstellt, nach jedem erstellten filter von dem Kund:in.
Crawler verwendet den WebDriverManager von FireFox, um den Link zu öffnen.
Danach werden die RAW-HTML Daten bearbeitet und ein Objekt erstellt und dann alle Objekten von dem jeweiligen Filter in einer Liste gespeichert.
Diese Liste wird dann in unserer 'Email-Template' eingefügt.
Nachdem dieses Template bereit ist, senden wir es durch 'crawlerimmobilien@gmail.com' zu dem jeweiligen Nutzer.


## Web-Application
Die Kundin kann ein Konto erstellen, wo man seine Email/Benutzername und Password zum einloggen verwenden kann.
Nachdem die Kundin sich eingemeldet hat, kann man beliebige Filter erstellen.

### Suchkriterien
Die Suchkriterien sind wie folgt:

* Preis (von|bis)
* Preis pro m^2 (von|bis)
* Fläche (von|bis)

* Kategorien:
  - Haus kaufen
  - Haus mieten
  - Wohnung kaufen
  - Wohnung mieten
  - Grundstücke

* Bundesland (alle 9 Bundesländer von Österreich)

* Je nach wahl von dem gewählten Bundesland bekommt die Kundin automatisch gelistet, die Orte/Bezirke(im Fall von Wien).


### Allgemein
Die Kundin bekommt dann eine E-Mail von unserer Crawler durch 'crawlerimmobilien@gmail.com'

Die Crawler, wir jede fünf Minuten aktiviert werden und durch die alle Filter gehen.

In unserer App wird auch gecheckt, ob die Anzeigen, die der Crawler bekommt bereits, der Kund:In gesendet worden sind.

In diesem Fall wird keine E-Mail gesendet.

Im Profil kann der Kund:in bereit erstellte Filters sehen und nach wunsch löschen.

Zusätzlich wird die Kund:in seine/ihre Zugangsdaten sehen.


### Webseite Architektur

Unsere Webseite ist von 5 Seiten erstellt

- Startseite
- Über uns
- Login/Signup (wenn man nicht bereits eingeloggt ist)
- Profil (wenn man bereits eingeloggt ist)
- Filter erstellen (wenn man bereits eingeloggt ist)

### Sicherheit

- Die Passwörter werden in Hash umgewandelt
- Die ganze Web-App verwendet ORM, die gegen SQL-INJECTION schützt
- Jede Seite ist so konfiguriert, dass wenn man keine Rechte hat, wird er/sie auf die Anmelde-Seite umgeleitet



