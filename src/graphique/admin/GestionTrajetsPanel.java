package graphique.admin;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import logiqueMetier.Serveur;

public class GestionTrajetsPanel extends JPanel {
	private TableTrajetsPanel tableTrajetsPanel;
	private AjoutTrajetPanel ajoutTrajetsPanel;

	private Serveur serveur;
	
	public GestionTrajetsPanel(Serveur s) {
		super();
		serveur = s;
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Gestion des réservations"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		ajoutTrajetsPanel = new AjoutTrajetPanel(serveur);
		add(ajoutTrajetsPanel);
		
		tableTrajetsPanel = new TableTrajetsPanel(serveur);
		add(tableTrajetsPanel);
		
		buildButtons();
	}
	private void buildButtons() {
		add(new JButton(new ValidateAction("Valider")));
	}
	
	private class ValidateAction extends AbstractAction {
		public ValidateAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé");
		}
	}

}
