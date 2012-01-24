package graphique.client;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logiqueMetier.Admin;
import logiqueMetier.ServeurV2;

public class FenetreClient extends JFrame {
	private Admin admin;
	
	public FenetreClient() {
		super();
		admin = new Admin(new ServeurV2());
		build();
	}

	private void build() {
		setTitle("Client de réservation"); 
		setSize(800,600); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setContentPane(buildContentPane());
	}
	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		panel.add(new ReservationPanel(admin));
		
		JButton quit = new JButton(new QuitAction("Quitter"));
		panel.add(quit);
		
		return panel;
	}
	private class QuitAction extends AbstractAction {
		public QuitAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
}
