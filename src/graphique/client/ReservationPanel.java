package graphique.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import logiqueMetier.Serveur;
import objets.Passager;
import objets.Reservation;
import objets.Trajet;


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
		clientP = new ClientPanel();
		resultatsP = new ResultatsPanel(serveur);
		add(trajetP);
		add(clientP);
		add(new JButton(new ValidateAction("Rechercher")));
		add(resultatsP);			
	}

	
	private class ValidateAction extends AbstractAction {
		public ValidateAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé !");
			ArrayList<Trajet> trajets = (ArrayList<Trajet>) serveur.rechercherTrajet(trajetP
					.getVilleDepart(), trajetP.getVilleArrivee(), null, clientP
					.getNbPassagers(), trajetP.getDateDepart(), 12, true,
					false, true);

			resultatsP.removeAllRows();
			/* Reservation(Passager passager, Trajet trajet, boolean modifiable,
            boolean prendCouchette, Map<String, Boolean> prendRepas, int identifiant,int placesVoulues */
			Passager p = new Passager(clientP.getNom(), clientP.getPrenom(),
					clientP.getDateNaissance(), clientP.getProfil(), clientP
							.hasFidelite());
			System.out.println(p);

			for(Trajet t:trajets) {
				Reservation r = new Reservation(p, t, trajetP.getModifiable(), trajetP.getCouchette(),
						trajetP.getRepas(), serveur.getReservationNewIdentifiant(), clientP.getNbPassagers());
				r.setActive(false);
				System.out.println(r);
				resultatsP.addReservation(r);
			}
		}
	}
}
