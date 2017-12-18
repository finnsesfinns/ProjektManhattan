package mypackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class MenuListener implements ActionListener {

    public MenuListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Wuerfeln":
                Actions actions = Oberflaeche.game.wuerfeln();

                if (Oberflaeche.game.getCurrentPlayer() == 0) {
                    //Spieler ist dran
                    Oberflaeche.hinweisHinzufuegen("Du hast eine " + actions.wuerfelZahl + " gewuerfelt");
                    if (actions.keinZugMoeglich) {
                        if (Oberflaeche.game.getAusstehendeWuerfelVersuche() > 0) {
                            Oberflaeche.hinweisHinzufuegen("Du hast eine " + actions.wuerfelZahl
                                    + " gewuerfelt. Leider kannst du keine Figur bewegen. Du darfst noch "
                                    + Oberflaeche.game.getAusstehendeWuerfelVersuche() + " einmal wuerfeln");
                            Oberflaeche.updateButtonStates(null);
                        } else {
                            //Alle Versuche gescheitert -> naechster spieler
                            Oberflaeche.game.moveFigur(-1);
                            Oberflaeche.hinweisHinzufuegen("Spieler "
                                    + Oberflaeche.game.getCurrentPlayer() + " ist jetzt dran");

                            Oberflaeche.updateButtonStates(null);
                        }
                    } else {

                        Oberflaeche.hinweisHinzufuegen("Waehle eine Figur aus, die du bewegen moechtest");
                        Oberflaeche.updateButtonStates(actions);
                    }

                } else {
                    //KI ist dran
                    int ausgewaehlteFigur = KI.decideAction(actions, null);
                    Positions newPositions = Oberflaeche.game.moveFigur(ausgewaehlteFigur);
                    /*if (ausgewaehlteFigur >= 0 && ausgewaehlteFigur < 4) {
                        Positions newPositions = Oberflaeche.game.moveFigur(ausgewaehlteFigur);
                        //Oberflaeche.updatePositions();
                        Oberflaeche.hinweisHinzufuegen("Spieler " + (Oberflaeche.game.getCurrentPlayer() - 1)
                                + " hast seine " + ausgewaehlteFigur + ". Figur um "
                                + actions.wuerfelZahl + " Felder nach vorne bewegt");
                    }*/
                    Oberflaeche.updateButtonStates(null);
                }
                Oberflaeche.updateSpielbrett();
                if(Oberflaeche.game.getCurrentPlayer() == 0 && (actions.keinZugMoeglich || !Oberflaeche.game.getBereitsGewuerfelt())){
                    Oberflaeche.hinweisHinzufuegen("Bitte Wuerfeln");
                }
                break;

            case "End":
                System.exit(0);
                break;

            case "jbutton_figur1":
                Oberflaeche.game.moveFigur(0);
                Oberflaeche.updateSpielbrett();
                //wuerfelAutomatisch(1000);
                break;

            case "jbutton_figur2":
                Oberflaeche.game.moveFigur(1);
                Oberflaeche.updateSpielbrett();
                //wuerfelAutomatisch(1000);
                break;

            case "jbutton_figur3":
                Oberflaeche.game.moveFigur(2);
                Oberflaeche.updateSpielbrett();
                //wuerfelAutomatisch(1000);
                break;

            case "jbutton_figur4":
                Oberflaeche.game.moveFigur(3);
                Oberflaeche.updateSpielbrett();
                //wuerfelAutomatisch(1000);
                break;
        }
        if (Oberflaeche.game.getGewinner() >= 0) {
            //Spiel zuende
            if (Oberflaeche.game.getGewinner() == 0) {
                JOptionPane.showMessageDialog(null, "Glueckwunsch! Du hast gewonnen");
            } else {
                JOptionPane.showMessageDialog(null, "Du hast leider verloren");
            }
            System.exit(0);
        }

        //automatisch wuerfeln, wenn nicht der Spieler dran ist
        if (Oberflaeche.game.getCurrentPlayer() != 0) {
            if (Oberflaeche.game.getIstErneuterVersuch()) {
                //nicht verzoegern
                wuerfelAutomatisch(0);
            } else {
                wuerfelAutomatisch(1000);
            }
        }
    }

    /**
     * ruft actionPerformed mit "Wuerfel" command nach delay auf
     *
     * @param delay laenge der verzoegerung
     */
    private void wuerfelAutomatisch(int delay) {
        Oberflaeche.updateButtonStates(null);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                actionPerformed(new ActionEvent(this, 0, "Wuerfeln"));
            }
        }, delay);
    }
}
