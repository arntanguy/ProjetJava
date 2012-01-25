package graphique.client;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import objets.Ville;

import logiqueMetier.Admin;


public class TrajetPanel extends JPanel {
	private JComboBox villeDepartText;
	private JComboBox villeArriveeText;
	private JSpinner dateDepartSpinner;

	private Admin admin;
	
	public TrajetPanel(Admin a){
		super();
		admin = a;
		build(); 
	}
	private void build(){
		setBorder(BorderFactory.createTitledBorder("Où et quand souhaitez-vous partir ?"));
		setLayout(new GridLayout(0,2));

		villeDepartText = new JComboBox();
		for(Ville v:admin.getVilles()) {
			villeDepartText.add(new JLabel(v.toString()));
		}
		add(new JLabel("Ville de départ "));
		add(villeDepartText);

		add(new JLabel("Ville d'arrivée"));
		villeArriveeText = new JComboBox();
		add(villeArriveeText);

	
		// Create a SpinnerDateModel with current date as the initial value.
		SpinnerDateModel model = new SpinnerDateModel();

		add(new JLabel("Date de départ "));
		dateDepartSpinner = new JSpinner(model);
		add(dateDepartSpinner);
	}
}
