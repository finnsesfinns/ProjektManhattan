package mypackage;

public class Game {

    public Player[] players;
    private int wuerfelZahl;
    private int currentPlayer = 0;  //Spieler, der gerade dran ist; Spieler 0 am Anfang
    //Anzahl bereits gemachter Wuerfe; beim Rauskommen
    private int ausstehendeWuerfelVersuche = -1;
    Positions positions;

    /**
     * true, wenn der Spieler noch einen Wuerfel Versuch hat
     */
    private boolean istErneuterVersuch = false;
    private int gewinner = -1; //Sieger (0-3); -1, wenn noch niemand gewonnen hat
    /**
     * true, wenn der currentPlayer mindestens einmal schon gewuerfelt hat
     */
    private boolean hatBereitsGewuerfelt;

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getAusstehendeWuerfelVersuche() {
        return ausstehendeWuerfelVersuche;
    }

    public boolean getIstErneuterVersuch() {
        return istErneuterVersuch;
    }

    public int getWuerfelZahl() {
        return wuerfelZahl;
    }

    public int getGewinner() {
        return gewinner;
    }
    
    public boolean getBereitsGewuerfelt(){
        return hatBereitsGewuerfelt;
    }

    /**
     * Erstellt neues Spiel; Figuren befinden sich auf den Startpsoitionen
     *
     * @param numPlayers Anzahl der Spieler (inklusive Computer gesteuerter
     * Spieler)
     */
    public Game(int numPlayers) {
        //create players array
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i);
        }

        positions = new Positions(players);
        
        ausstehendeWuerfelVersuche = 3;
    }

    /**
     * wuerfelt und bestimmt die erlaubten Spielzuege
     *
     * @param playersNumber Nummer des Spielers, der gewuerfelt hat
     * @return Actions Object mit zulaessigen Spielzuegen
     */
    public Actions wuerfeln() {
        //zufaellige zahl generieren (1-6)
        wuerfelZahl = (int) Math.round(Math.random() * 6 + 0.5);
        ausstehendeWuerfelVersuche--;   //Ein Versuch weniger verbleibend
        hatBereitsGewuerfelt = true;
        int[] figurZiehen = new int[4];
        for (int figur = 0; figur < 4; figur++) {
            //fuer jede eigene Spielfigur
            figurZiehen[figur] = wuerfelZahl;
            // check if erlaubt
            // check eigene Figuren
            for (int eigeneFigur = 0; eigeneFigur < 4; eigeneFigur++) {
                if (players[currentPlayer].positions[eigeneFigur] == players[currentPlayer].positions[figur] + wuerfelZahl) {
                    //eigene Figur steht bereits auf diesem Feld
                    figurZiehen[figur] = 0;
                    break;
                }
            }

            //check Spielfeld Ende
            if (players[currentPlayer].positions[figur] + wuerfelZahl > 44) {
                figurZiehen[figur] = 0;
            }

            // check noch nicht draussen
            if (players[currentPlayer].positions[figur] == 0) {
                if (wuerfelZahl == 6) {
                    figurZiehen[figur] = 1;
                    //check if eine eigene Figur bereits auf dem Feld 1 steht
                    for (int eigeneFigur = 0; eigeneFigur < 4; eigeneFigur++) {
                        if (players[currentPlayer].positions[eigeneFigur] == 1) {
                            //eigene Figur steht bereits auf diesem Feld
                            figurZiehen[figur] = 0;
                            break;
                        }
                    }
                } else {
                    figurZiehen[figur] = 0;
                }
            }
        }

        Actions actions = new Actions(figurZiehen, wuerfelZahl, currentPlayer);

        //ausstehendeWuerfelVersuche berechnen
        if (actions.keinZugMoeglich) {
            if(ausstehendeWuerfelVersuche <= 0){
                //naechsterSpieler();
                istErneuterVersuch = false;
            }else{
                istErneuterVersuch = true;
            }
        } else {
            //Zug moeglich -> ausstehendeWuerfelVersuche zuruecksetzen
            //ausstehendeWuerfelVersuche = -1;
            istErneuterVersuch = false;
        }
        return actions;
    }

    /**
     * bewegt eine Spielfigur und reft naechsterSpieler() auf. unerlaubte Zuege
     * werden auch durchgefuert!
     *
     * @param figurNumber Nummer der Spielfigur, die gezogen werden soll (0 bis
     * 3), ansonsten ist der naechste Spieler dran
     * @return Positions object, mit den neuen Positionen aller Figuren
     */
    public Positions moveFigur(int figurNumber) {
        if(figurNumber < 0 || figurNumber > 3){
            if(ausstehendeWuerfelVersuche <= 0){
                naechsterSpieler();
                istErneuterVersuch = false;
            }else{
                istErneuterVersuch = true;
            }
            return new Positions(players);
        }
        if (players[currentPlayer].positions[figurNumber] == 0) {
            //stelle Figur raus
            players[currentPlayer].positions[figurNumber] = 1;
        } else {
            //stelle Figur (wuerfelZahl) Felder weiter
            players[currentPlayer].positions[figurNumber] += wuerfelZahl;
        }

        int newPosition = players[currentPlayer].generalPosition(figurNumber);

        // check if gegnerische Figuren geschlagen
        for (int gegner = 0; gegner < 4; gegner++) {
            if (gegner != currentPlayer) {
                for (int figur = 0; figur < 4; figur++) {
                    if (players[gegner].generalPosition(figur) == newPosition && newPosition <= 40) {
                        // gegnerische Figur geschlagen
                        players[gegner].positions[figur] = 0;
                        break;
                    }
                }
            }
        }

        //check if currentPlayer schon gewonnen hat
        boolean hatGewonnen = true;
        for (int figur = 0; figur < 4; figur++) {
            if (players[currentPlayer].positions[figur] < 41) {
                hatGewonnen = false;
                break;
            }
        }
        if (hatGewonnen) {
            gewinner = currentPlayer;
        }

        naechsterSpieler();
        //update positions object
        positions = new Positions(players);
        // return new positions
        return new Positions(players);
    }

    /**
     * aendert variable, die den Spieler identifiziert, welcher dran ist
     */
    private void naechsterSpieler() {
        if (currentPlayer >= 3) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
        hatBereitsGewuerfelt = false;
        //Anzahl wuerfelVersuche fuer den naechsten Spieler bestimmen
        if (players[currentPlayer].keineFigurDraussen()) {
            ausstehendeWuerfelVersuche = 3;
        }else{
            ausstehendeWuerfelVersuche = 1;
        }
    }

}
