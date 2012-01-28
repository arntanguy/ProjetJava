package graphique.client;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import objets.Profil;
import tools.DateTools;

import logiqueMetier.Admin;
import logiqueMetier.Serveur;



public class ClientPanel extends JPanel {
	private JTextField nomText;
	private JTextField prenomText;
	private JSpinner naissanceSpinner;
	private JSpinner nbPassagersSpinner;
	private JComboBox categoriePassager;
	private JCheckBox carteAbonnement;
		
	public ClientPanel(){
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
		
		add(new JLabel("Date de naissance"));
		naissanceSpinner = new JSpinner(new SpinnerDateModel());
		add(naissanceSpinner);
		
		add(new JLabel("Nombre de passagers"));
		nbPassagersSpinner = new JSpinner();
		nbPassagersSpinner.setValue(1);
		add(nbPassagersSpinner);
		
		add(new JLabel("Passager"));
		categoriePassager = new JComboBox();
		for(Profil p : Profil.values()) {
			categoriePassager.addItem(p);
		}
		add(categoriePassager);
		
		add(new JLabel("Carte de fidélité"));
		carteAbonnement = new JCheckBox();
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
	public Calendar getDateNaissance() {
		return DateTools.dateToCalendar((Date)naissanceSpinner.getValue());
	}
	public Profil getProfil() {
		return (Profil) categoriePassager.getSelectedItem();
	}
	public boolean hasFidelite() {
		return carteAbonnement.isSelected();
	}
}
