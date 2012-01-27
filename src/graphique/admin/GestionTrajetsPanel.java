package graphique.admin;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import logiqueMetier.Serveur;

public class GestionTrajetsPanel extends JPanel {
	private TableTrajetsPanel tableTrajetsPanel;

	private Serveur serveur;
	
	public GestionTrajetsPanel(Serveur s) {
		super();
		serveur = s;
		build();
	}

	private void build() {
		//setBorder(BorderFactory.createTitledBorder("Gestion des trajets"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		tableTrajetsPanel = new TableTrajetsPanel(serveur);
		add(tableTrajetsPanel);
	}
}
