package graphique.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objets.Ville;

import logiqueMetier.Admin;



public class AjoutVillePanel extends JPanel {
	private JTextField nomText;
	
	private Admin admin;
	
	public AjoutVillePanel(Admin a) {
		super();
		admin = a;
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
		public ValidateAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé !");
			Ville v = new Ville(nomText.getText(), admin.getVilleNewIdentifiant());
			try {
				admin.addVille(v);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
