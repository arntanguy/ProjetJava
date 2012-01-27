package graphique.admin;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import logiqueMetier.Serveur;

public abstract class AbstractTablePanel extends JPanel {
	protected Serveur serveur;
	protected JPanel buttonsPanel;
	protected boolean buttonsVisible = true;
	
	public AbstractTablePanel(Serveur s) {
		super();
		serveur = s;
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout( new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
	}
	
}
