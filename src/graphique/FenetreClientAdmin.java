package graphique;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FenetreClientAdmin extends JFrame {
	public FenetreClientAdmin() {
		super();
		build();
	}

	private void build() {
		setTitle("Administration"); 
		setSize(800,600); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		AjoutTransportPanel ajoutTransportPanel = new AjoutTransportPanel();
		panel.add(ajoutTransportPanel);
		
		AjoutTrajetPanel ajoutTrajetPanel = new AjoutTrajetPanel();
		panel.add(ajoutTrajetPanel);
		
		AjoutVillePanel ajoutVillePanel = new AjoutVillePanel();
		panel.add(ajoutVillePanel);
		
		GestionReservationsPanel reservationsPanel = new GestionReservationsPanel();
		
		panel.add(reservationsPanel, BorderLayout.CENTER);
		JButton quit = new JButton(new QuitAction("Quitter"));
		panel.add(quit);
		
		return panel;
	}

	public class AjoutClientAction extends AbstractAction {
		public AjoutClientAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AjoutTrajetPanel fenetre = new AjoutTrajetPanel();
					fenetre.setVisible(true);
				}
			});
		}
	}

	public class AjoutTransportAction extends AbstractAction {
		public AjoutTransportAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AjoutTransportPanel fenetre = new AjoutTransportPanel();
					fenetre.setVisible(true);
				}
			});
		}
	}
	public class AjoutVilleAction extends AbstractAction {
		public AjoutVilleAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AjoutVillePanel fenetre = new AjoutVillePanel();
					fenetre.setVisible(true);
				}
			});
		}
	}
	public class QuitAction extends AbstractAction {
		public QuitAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
}
