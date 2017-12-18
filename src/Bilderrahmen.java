import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bilderrahmen extends JFrame {
	Container c;

	public Bilderrahmen() {
		c = getContentPane();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Bilder");
		
		JMenuItem menuItem = new JMenuItem("Hund");
		menu.add(menuItem);
		
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		c.setBackground(Color.red);
	}

	public static void main(String[] args) {
		Bilderrahmen fenster = new Bilderrahmen();
		fenster.setTitle("Bilderrahmen");
		fenster.setLocation(200,200);
		fenster.setSize(180,280);
		fenster.setVisible(true);
	}
}