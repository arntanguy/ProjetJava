package graphique.admin;


import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import logiqueMetier.Serveur;
import logiqueMetier.ServeurV2;

public class FenetreClientAdmin extends JFrame {
	private Serveur serveur;
	JTabbedPane tabbedPane;
	
	public FenetreClientAdmin() {
		super();
		serveur = new ServeurV2();
		try {
			serveur.charger();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		build();
	}

	private void build() {
		setTitle("Administration"); 
		setSize(800,400); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setContentPane(buildContentPane());
	}

	private JTabbedPane buildTabbedPane() {
		tabbedPane = new JTabbedPane();
		
		tabbedPane.add("Ville", buildVillePanel());
		tabbedPane.add("Transports", buildTransportsPanel());
		tabbedPane.add("Trajets", buildTrajetsPanel());
		tabbedPane.add("Reservations", buildReservationsPanel());
		
		return tabbedPane;
	}
	
	private JPanel buildVillePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(new AjoutVillePanel(serveur));
		return panel;
	}
	
	private JPanel buildTransportsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(new AjoutTransportPanel(serveur));		
		return panel;
	}
	
	private JPanel buildTrajetsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(new GestionTrajetsPanel(serveur));
		return panel;
	}
	
	private JPanel buildReservationsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(new GestionReservationsPanel(serveur));
		return panel;
	}
	
	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(buildTabbedPane());
		
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
			try {
				serveur.sauvegarder();
			} catch (IOException e) {
				e.printStackTrace();
			}
			dispose();
		}
	}
}
