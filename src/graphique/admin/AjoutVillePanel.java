package graphique.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class AjoutVillePanel extends JPanel {
	private JTextField nomText;
	
	public AjoutVillePanel(){
		super();
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
		}
	}
}
