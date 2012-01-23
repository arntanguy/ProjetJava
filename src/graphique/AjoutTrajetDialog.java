package graphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AjoutTrajetDialog extends JDialog {
	private JTextField villeDepartText;
	private JTextField villeArriveeText;
	private JTextField dateDepartText;
	private JTextField dateArriveeText;
	
	public AjoutTrajetDialog(){
		super();
		build();//On initialise notre fenêtre
	}
	private void build(){
		setTitle("Ajout d'un trajet"); 
		setSize(400,200); 
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setContentPane(buildContentPane()); // construit le ContentPane
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

		panel.add(new JLabel("Date de départ "));
		dateDepartText = new JTextField();
		panel.add(dateDepartText);
		panel.add(new JLabel("Date d'arrivée "));
		dateArriveeText = new JTextField();
		panel.add(dateArriveeText);

		JButton bouton = new JButton(new ValidateAction("Valider"));
		panel.add(bouton);
		JButton bouton2 = new JButton(new CancelAction("Annuler"));
		panel.add(bouton2);


		return panel;
	}

	public class ValidateAction extends AbstractAction {
		public ValidateAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Validé !"+villeDepartText.getText());
		}
	}
	public class CancelAction extends AbstractAction {
		public CancelAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Annulé !");
		}
	}
}
