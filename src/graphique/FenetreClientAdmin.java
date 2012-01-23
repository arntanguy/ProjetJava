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
		build();// On initialise notre fenêtre
	}

	private void build() {
		setTitle("Réservations"); // On donne un titre à l'application
		setSize(400, 200); // On donne une taille à notre fenêtre
		setLocationRelativeTo(null); // On centre la fenêtre sur l'écran
		setResizable(false); // On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit à
		// l'application de se
		// fermer lors du clic
		// sur la croix
		setContentPane(buildContentPane()); // construit le ContentPane
	}

	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel label = new JLabel("Client de réservation");
		panel.add(label);

		JButton ajoutTrajetButton = new JButton(new AjoutClientAction(
		"Ajouter un trajet"));
		panel.add(ajoutTrajetButton);

		JButton ajoutTransportButton = new JButton(new AjoutTransportAction(
		"Ajouter un transport"));
		panel.add(ajoutTransportButton);

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
}
