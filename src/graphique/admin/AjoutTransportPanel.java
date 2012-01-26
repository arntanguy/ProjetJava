package graphique.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import objets.TypeVehicule;

import logiqueMetier.Serveur;


public class AjoutTransportPanel extends JPanel {
	private JTextField nomText;
	private JSpinner capaciteSpinner;
	private JComboBox typeTransportsCombo;
	
	private Serveur serveur;
	private GestionTransportsPanel parent;
	
	public AjoutTransportPanel(Serveur s, GestionTransportsPanel p) {
		super();
		serveur = s;
		parent = p;
		build();
	}
	private void build(){
		setBorder(BorderFactory.createTitledBorder("Ajouter un transport"));
		setLayout(new GridLayout(0,2));

		nomText = new JTextField();
		add(new JLabel("Nom du transport "));
		add(nomText);
		
		add(new JLabel("Type de transport"));
		typeTransportsCombo = new JComboBox();
		for(TypeVehicule v : TypeVehicule.values()) {
			typeTransportsCombo.addItem(v);
		}
		add(typeTransportsCombo);
		
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
			try {
				serveur.creerVehicule(nomText.getText(), (TypeVehicule)typeTransportsCombo.getSelectedItem(), 
						(Integer)capaciteSpinner.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
