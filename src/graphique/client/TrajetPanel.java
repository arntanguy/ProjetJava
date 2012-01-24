package graphique.client;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import logiqueMetier.Admin;


public class TrajetPanel extends JPanel {
	private JTextField villeDepartText;
	private JTextField villeArriveeText;
	private JSpinner dateDepartSpinner;
	private JSpinner dateArriveeSpinner;

	private Admin admin;
	
	public TrajetPanel(Admin a){
		super();
		admin = a;
		build(); 
	}
	private void build(){
		setBorder(BorderFactory.createTitledBorder("Où et quand souhaitez-vous partir ?"));
		setLayout(new GridLayout(0,2));

		villeDepartText = new JTextField();
		add(new JLabel("Ville de départ "));
		add(villeDepartText);

		add(new JLabel("Ville d'arrivée"));
		villeArriveeText = new JTextField();
		add(villeArriveeText);

	
		// Create a SpinnerDateModel with current date as the initial value.
		SpinnerDateModel model = new SpinnerDateModel();
		SpinnerDateModel model1 = new SpinnerDateModel();


		add(new JLabel("Date de départ "));
		dateDepartSpinner = new JSpinner(model);
		add(dateDepartSpinner);
		add(new JLabel("Date d'arrivée "));
		dateArriveeSpinner = new JSpinner(model1);
		add(dateArriveeSpinner);
	}
}
