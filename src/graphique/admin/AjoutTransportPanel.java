package graphique.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import logiqueMetier.Admin;


public class AjoutTransportPanel extends JPanel {
	private JTextField nomText;
	private JSpinner capaciteSpinner;
	
	private Admin admin;
	
	public AjoutTransportPanel(Admin a){
		super();
		admin = a;
		build();
	}
	private void build(){
		setBorder(BorderFactory.createTitledBorder("Ajouter un transport"));
		setLayout(new GridLayout(0,2));

		nomText = new JTextField();
		add(new JLabel("Nom du transport "));
		add(nomText);
		
		add(new JLabel("Capacité d'acceuil"));
		capaciteSpinner = new JSpinner();
		capaciteSpinner.setValue(50);
		add(capaciteSpinner);

		add(new JLabel());
		
		JButton bouton = new JButton(new ValidateAction("Valider"));
		add(bouton);
		
	}

	public class ValidateAction extends AbstractAction {
		public ValidateAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé !");
		}
	}
}
