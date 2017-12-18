package mypackage;


public class Positions{
    
    /**
     * 2d Array mit positionen der Spielfiguren [Nummer des Spielers][Nummer der Spielfigur]
     * 0 entspricht Startposition
     * 1-39: Figur auf dem Spielfeld (relativ zum ersten Feld von Spieler 0)
     * 40-43: Figur in der Endzone
     */
    int[][] positions;
    
    public Positions(Player[] players){
        this.positions = new int[players.length][4];
        for(int player = 0; player < players.length; player++){
            for(int figur = 0; figur < 4; figur++){
                this.positions[player][figur] = players[player].generalPosition(figur);
            }
        }
    }
}
