package mypackage;

/**
 *
 * @author alexa
 */
public class KI {
    
    /**
     * 
     * @param actions erlaubte Aktionen fuer die KI
     * @param positions Positionen aller Figuren
     * @return Figur die bewegt werden soll 
     */
    public static int decideAction(Actions actions, Positions positions){
        for (int i = 0; i < 4; i++){
            if(actions.figurZiehen[i] > 0){
                return i;
            }
        }
        return -1;    
    }
}
