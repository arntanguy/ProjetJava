package graphique.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import logiqueMetier.Serveur;


public class AjoutTrajetPanel extends JPanel {
	private JTextField villeDepartText;
	private JTextField villeArriveeText;
	private JSpinner dateDepartSpinner;
	private JSpinner dateArriveeSpinner;
	private TableTrajetsPanel gestionTrajetsPanel;
	
	private Serveur serveur;

	public AjoutTrajetPanel(Serveur s){
		super();
		serveur = s;
		build(); 
	}
	private void build(){
		setBorder(BorderFactory.createTitledBorder("Ajouter un trajet"));
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

		add(new JLabel());
		JButton validateButton = new JButton(new ValidateAction("Valider"));
		add(validateButton);
	}

	
	private class ValidateAction extends AbstractAction {
		public ValidateAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé !"+villeDepartText.getText());
			
		/*	Trajet t = new Trajet(DateTools.dateToCalendar((Date)dateDepartSpinner.getModel().getValue())
					, DateTools.dateToCalendar((Date)dateArriveeSpinner.getModel().getValue()),
					villeDepartText.getText(), villeArriveeText.getText(), vehicule , identifiant) */
			//serveur.addTrajet(t);
			Date dateDepart = (Date) dateDepartSpinner.getModel().getValue();
			System.out.println(dateDepart);
		}
	}
}
