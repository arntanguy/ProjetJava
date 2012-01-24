package graphique;

import graphique.AjoutTransportDialog.CancelAction;
import graphique.AjoutTransportDialog.ValidateAction;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class AjoutVilleDialog extends JDialog {
	private JTextField nomText;
	
	public AjoutVilleDialog(){
		super();
		build();
	}
	private void build(){
		setTitle("Ajout d'un trajet"); 
		setSize(400,150);
		setLocationRelativeTo(null); 
		setResizable(false);
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));

		nomText = new JTextField();
		panel.add(new JLabel("Nom de la ville "));
		panel.add(nomText);

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
			System.out.println("Validé !");
		}
	}
	public class CancelAction extends AbstractAction {
		public CancelAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {	
			dispose();
		}
	}
}
