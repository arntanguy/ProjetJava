package graphique.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logiqueMetier.Serveur;

public class AjoutVillePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField nomText;
	
	private Serveur serveur;
	
	public AjoutVillePanel(Serveur s) {
		super();
		serveur = s;
		build();
	}
	private void build(){
		setBorder(BorderFactory.createTitledBorder("Ajouter une ville"));
		setLayout(new GridLayout(0,2));

		nomText = new JTextField();
		add(new JLabel("Nom de la ville "));
		add(nomText);

		add(new JLabel());
		JButton bouton = new JButton(new ValidateAction("Valider"));
		add(bouton);
	}


	private class ValidateAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ValidateAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé !");
			try {
				serveur.createVille(nomText.getText());
				serveur.sauvegarder();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
