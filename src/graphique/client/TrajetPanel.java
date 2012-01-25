package graphique.client;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Date;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import objets.Ville;
import tools.DateTools;

import logiqueMetier.Admin;


public class TrajetPanel extends JPanel {
	private JComboBox villeDepartCombo;
	private JComboBox villeArriveeCombo;
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
/** To remove later **/
		try {
			admin.createVille("Paris");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/** end remove **/
		villeDepartCombo = new JComboBox();
		for(Ville v:admin.getVilles()) {
			villeDepartCombo.addItem(v);
		}
		add(new JLabel("Ville de départ "));
		add(villeDepartCombo);

		add(new JLabel("Ville d'arrivée"));
		villeArriveeCombo = new JComboBox();
		add(villeArriveeCombo);

	
		// Create a SpinnerDateModel with current date as the initial value.
		SpinnerDateModel model = new SpinnerDateModel();

		add(new JLabel("Date de départ "));
		dateDepartSpinner = new JSpinner(model);
		add(dateDepartSpinner);
	}

	public Ville getVilleDepart() {
		return (Ville)villeDepartCombo.getSelectedItem();
	}
	public Ville getVilleArrivee() {
		return (Ville)villeArriveeCombo.getSelectedItem();
	}
	public Calendar getDateDepart() {
		return DateTools.dateToCalendar((Date)dateDepartSpinner.getModel().getValue());
	}
}
