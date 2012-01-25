package graphique.client;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import logiqueMetier.Admin;
import logiqueMetier.Serveur;



public class ClientPanel extends JPanel {
	private JTextField nomText;
	private JTextField prenomText;
	private JSpinner nbPassagersSpinner;
	private JComboBox categoriePassager;
	private JComboBox carteAbonnement;
	
	private Serveur serveur;
	
	public ClientPanel(Serveur s){
		super();
		build();
	}
	private void build(){
		setBorder(BorderFactory.createTitledBorder("Qui participe à ce voyage ?"));
		setLayout(new GridLayout(0,2));

		add(new JLabel("Nom"));
		nomText = new JTextField();
		add(nomText);
		add(new JLabel("Prenom"));
		prenomText = new JTextField();
		add(prenomText);
		
		add(new JLabel("Nombre de passagers"));
		nbPassagersSpinner = new JSpinner();
		nbPassagersSpinner.setValue(1);
		add(nbPassagersSpinner);
		
		add(new JLabel("Passager"));
		String[] test = { "", "12-25" };
		categoriePassager = new JComboBox(test);
		add(categoriePassager);
		
		add(new JLabel(""));
		carteAbonnement = new JComboBox(test);
		add(carteAbonnement);
	}
	
	public String getNom() {
		return nomText.getText();
	}
	public String getPrenom() {
		return prenomText.getText();
	}
	public int getNbPassagers() {
		return (Integer)nbPassagersSpinner.getValue();
	}
}
