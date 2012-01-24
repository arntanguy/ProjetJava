package graphique;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FenetreClientAdmin extends JFrame {
	public FenetreClientAdmin() {
		super();
		build();
	}

	private void build() {
		setTitle("Réservations"); 
		setSize(400, 200); 
		setLocationRelativeTo(null); 
		setResizable(false); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel label = new JLabel("Client d'administration");
		panel.add(label);

		JButton ajoutTrajetButton = new JButton(new AjoutClientAction(
		"Ajouter un trajet"));
		panel.add(ajoutTrajetButton);

		JButton ajoutTransportButton = new JButton(new AjoutTransportAction(
		"Ajouter un transport"));
		panel.add(ajoutTransportButton);
		
		JButton ajoutVilleButton = new JButton(new AjoutVilleAction(
		"Ajouter une ville"));
		panel.add(ajoutVilleButton);

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
					AjoutTrajetDialog fenetre = new AjoutTrajetDialog();
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
					AjoutTransportDialog fenetre = new AjoutTransportDialog();
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
					AjoutVilleDialog fenetre = new AjoutVilleDialog();
					fenetre.setVisible(true);
				}
			});
		}
	}
}
