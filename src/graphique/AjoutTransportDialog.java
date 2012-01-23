package graphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;


public class AjoutTransportDialog extends JDialog {
	private JTextField nomText;
	private JSpinner capaciteSpinner;
	
	public AjoutTransportDialog(){
		super();
		build();//On initialise notre fenêtre
	}
	private void build(){
		setTitle("Ajout d'un trajet"); 
		setSize(400,150); 
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setContentPane(buildContentPane()); // construit le ContentPane
	}

	private JPanel buildContentPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));

		nomText = new JTextField();
		panel.add(new JLabel("Nom du transport "));
		panel.add(nomText);
		
		panel.add(new JLabel("Capacité d'acceuil"));
		capaciteSpinner = new JSpinner();
		capaciteSpinner.setValue(50);
		panel.add(capaciteSpinner);

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
			System.out.println("Validé !");
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
