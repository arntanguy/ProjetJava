package graphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;


public class AjoutTrajetDialog extends JDialog {
	private JTextField villeDepartText;
	private JTextField villeArriveeText;
	private JSpinner dateDepartSpinner;
	private JSpinner dateArriveeSpinner;

	public AjoutTrajetDialog(){
		super();
		build();//On initialise notre fenêtre
	}
	private void build(){
		setTitle("Ajout d'un trajet"); 
		setSize(400,200); 
		setLocationRelativeTo(null); // Center the window
		setResizable(false); 
		setContentPane(buildContentPane()); 
	}

	private JPanel buildContentPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));

		villeDepartText = new JTextField();
		panel.add(new JLabel("Ville de départ "));
		panel.add(villeDepartText);

		panel.add(new JLabel("Ville d'arrivée"));
		villeArriveeText = new JTextField();
		panel.add(villeArriveeText);

		

	
		// Create a SpinnerDateModel with current date as the initial value.
		SpinnerDateModel model = new SpinnerDateModel();
		SpinnerDateModel model1 = new SpinnerDateModel();


		panel.add(new JLabel("Date de départ "));
		dateDepartSpinner = new JSpinner(model);
		panel.add(dateDepartSpinner);
		panel.add(new JLabel("Date d'arrivée "));
		dateArriveeSpinner = new JSpinner(model1);
		panel.add(dateArriveeSpinner);


		JButton validateButton = new JButton(new ValidateAction("Valider"));
		panel.add(validateButton);
		JButton cancelButton = new JButton(new CancelAction("Annuler"));
		panel.add(cancelButton);

		return panel;
	}

	public class ValidateAction extends AbstractAction {
		public ValidateAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé !"+villeDepartText.getText());
			GregorianCalendar dateDepart = (GregorianCalendar) dateDepartSpinner.getModel().getValue();
			System.out.println(dateDepart);
		}
	}
	public class CancelAction extends AbstractAction {
		public CancelAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose(); // Close the dialog
		}
	}
}
