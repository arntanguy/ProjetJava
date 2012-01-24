package graphique.client;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import logiqueMetier.Admin;


public class ReservationPanel extends JPanel {
	private Admin admin;
	
	public ReservationPanel(Admin a){
		super();
		admin = a;
		build(); 
	}
	private void build(){
		setBorder(BorderFactory.createTitledBorder("Réservation d'un trajet"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(new TrajetPanel(admin));
		add(new ClientPanel(admin));
		add(new JButton(new ValidateAction("Rechercher")));
		add(new ResultatsPanel(admin));			
	}

	
	private class ValidateAction extends AbstractAction {
		public ValidateAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé !");
		}
	}
}
