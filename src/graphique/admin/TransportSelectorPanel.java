package graphique.admin;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import logiqueMetier.Serveur;

public class TransportSelectorPanel extends JPanel {
	private TableTransportsPanel tableTransportsPanel;

	private Serveur serveur;
	
	public TransportSelectorPanel(Serveur s) {
		super();
		serveur = s;
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Choix du transport"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		tableTransportsPanel = new TableTransportsPanel(serveur);
		add(tableTransportsPanel);
	}
}
