package graphique.admin;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

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
		setSize(800,600); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setContentPane(buildTabbedPane());
	}

	private JTabbedPane buildTabbedPane() {
		tabbedPane = new JTabbedPane();
		
		tabbedPane.add("Test", buildContentPane());
		
		return tabbedPane;
	}
	
	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(new AjoutTransportPanel(serveur));		
		panel.add(new AjoutTrajetPanel(serveur));
		panel.add(new AjoutVillePanel(serveur));
		panel.add(new GestionReservationsPanel(serveur), BorderLayout.CENTER);

		JButton quit = new JButton(new QuitAction("Quitter"));
		panel.add(quit);
		
		return panel;
	}

	private class AjoutClientAction extends AbstractAction {
		public AjoutClientAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AjoutTrajetPanel fenetre = new AjoutTrajetPanel(serveur);
					fenetre.setVisible(true);
				}
			});
		}
	}

	private class AjoutTransportAction extends AbstractAction {
		public AjoutTransportAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AjoutTransportPanel fenetre = new AjoutTransportPanel(serveur);
					fenetre.setVisible(true);
				}
			});
		}
	}
	private class AjoutVilleAction extends AbstractAction {
		public AjoutVilleAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AjoutVillePanel fenetre = new AjoutVillePanel(serveur);
					fenetre.setVisible(true);
				}
			});
		}
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
