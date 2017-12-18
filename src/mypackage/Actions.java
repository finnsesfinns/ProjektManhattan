package mypackage;


public class Actions {
    /**
     * Anzahl Felder, die eine Figur gezogen werden darf; 0 bedeuted unmoeglich
    */
    public int[] figurZiehen = new int[4];	
    public int wuerfelZahl;
    public int currentPlayer;
    public boolean keinZugMoeglich;
	
    public Actions(int[] figurZiehen, int wuerfelZahl, int currentPlayer) {
        this.figurZiehen = figurZiehen;
        this.wuerfelZahl = wuerfelZahl;
        this.currentPlayer = currentPlayer;

        if ((figurZiehen[0] == 0) && (figurZiehen[1] == 0) && (figurZiehen[2] == 0) && (figurZiehen[3] == 0)) {
            keinZugMoeglich = true;
        } else keinZugMoeglich = false;
    }
}
