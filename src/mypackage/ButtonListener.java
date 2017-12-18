package mypackage;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class ButtonListener implements ActionListener {
	Startbildschirm startbildschirm;
	public ButtonListener(Startbildschirm startbildschirm) {
		this.startbildschirm = startbildschirm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int ThemeAuswahl;
		if (e.getActionCommand() == "Start") {
			startbildschirm.setVisible(false);
	    	Oberflaeche hauptfenster = new Oberflaeche(2);     	//Hauptfenster generieren
		}
		if (e.getActionCommand() == "Einstellungen") {
			ThemeAuswahl = JOptionPane.showOptionDialog(null, "Wählen Sie eines der Sonderthemes! Falls gewünscht, können Sie das Programm auch über die Schaltfläche beenden. ", "Auswahl der Themes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] {"Weihnachten", "Technologie", "Beenden"}, null);
			startbildschirm.setVisible(false);
			if (ThemeAuswahl==2) {
				System.exit(0);
			}
	    	Oberflaeche hauptfenster = new Oberflaeche(ThemeAuswahl);     	//Hauptfenster generieren
		}
		if (e.getActionCommand() == "Hilfe") {
			JDialog hilfeFenster = new JDialog();
			hilfeFenster.setVisible(true);
			hilfeFenster.setBounds(125,170,400,100);
			hilfeFenster.setTitle("Hilfe");
			JLabel hilfe = new JLabel("     Bei Fragen wenden Sie sich bitte an den Systemadministrator! ");
			hilfeFenster.add(hilfe);
		}
	}
	
}
