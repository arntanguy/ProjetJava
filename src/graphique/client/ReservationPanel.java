package graphique.client;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import logiqueMetier.Admin;
import logiqueMetier.Serveur;


public class ReservationPanel extends JPanel {
	private Serveur serveur;
	private TrajetPanel trajetP;
	private ClientPanel clientP;
	private ResultatsPanel resultatsP;
	
	public ReservationPanel(Serveur s){
		super();
		serveur = s;
		build(); 
	}
	private void build(){
		setBorder(BorderFactory.createTitledBorder("Réservation d'un trajet"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		trajetP = new TrajetPanel(serveur);
		clientP = new ClientPanel(serveur);
		resultatsP = new ResultatsPanel(serveur);
		add(trajetP);
		add(clientP);
		add(new JButton(new ValidateAction("Rechercher")));
		add(resultatsP);			
	}

	
	private class ValidateAction extends AbstractAction {
		public ValidateAction(String texte){
			super(texte);
			System.out.println(trajetP.getDateDepart());
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé !");
		}
	}
}