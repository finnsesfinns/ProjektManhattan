package mypackage;


public class Player {
	
    /**
    * Position aus eigener Sicht 
     */
    int[] positions = new int[4];
    
    /**
     * Nummer des Spielers (0-3)
     */
    private int num;
	
    /**
     * erstellt einen neuen Spieler. Alle Figurebn befinden sich auf den Startpositionen
     * @param num Nummer des Spielers (0-3)
     */
    public Player(int num){
        this.num = num;
        for (int i = 0; i < 4; i++) {
            positions[i] = 0;	//Startposition 
        }
    }
        
    // gibt position der Figur zurueck aus der Sicht von Spieler 0
    public int generalPosition(int figur) {
        int generalPositions = positions[figur];
        if (positions[figur] >= 1 && positions[figur] <= 40) {
            generalPositions = positions[figur] + num * 10;
            //sicherstellen, dass generalPosition kleiner als 41 ist
            if (generalPositions > 40) {
                generalPositions -= 40;
            }
        }

        return generalPositions;
    }
    
    public boolean keineFigurDraussen(){
        if(positions[0] == 0 && positions[1] == 0 && positions[2] == 0 && positions[3] == 0){
            return true;
        }else{
            return false;
        }
    }
}
