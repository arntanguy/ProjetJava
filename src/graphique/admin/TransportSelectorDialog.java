package graphique.admin;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logiqueMetier.Serveur;

public class TransportSelectorDialog extends JDialog {
	private TableTransportsPanel selectorPanel;
	
	private Serveur serveur;
	
	public TransportSelectorDialog(Serveur s) {
		serveur = s;
		setTitle("Choix du transport à lier.");
		setSize(800,400); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setContentPane(buildContentPane());
	}
	
	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(buildTransportSelector());
		
		JButton quit = new JButton("Quitter");
		panel.add(quit);
		
		return panel;
	}
	private JPanel buildTransportSelector() {
		//setBorder(BorderFactory.createTitledBorder("Gestion des réservations"));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		selectorPanel = new TableTransportsPanel(serveur);
		selectorPanel.setEditable(false);
		panel.add(selectorPanel);
		
		return panel;
	}
	
	private void buildButtons() {
		//BoxLayout l = new BoxLayout(this, BoxLayout.LINE_AXIS);
		//l.addLayoutComponent(new JButton(new ValidateAction("Valider")), null);
	}
	
	private class ValidateAction extends AbstractAction {
		public ValidateAction(String s) {
			super(s);
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé");
		}
	}
}
