package graphique.admin;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import logiqueMetier.Serveur;

/**
 * Cette classe fournit un composant permettant de gérér les trajets
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class GestionTrajetsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private TableTrajetsPanel tableTrajetsPanel;

    private Serveur serveur;

    public GestionTrajetsPanel(Serveur s) {
        super();
        serveur = s;
        build();
    }

    private void build() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        tableTrajetsPanel = new TableTrajetsPanel(serveur);
        add(tableTrajetsPanel);
    }
    
    public void updateVilles() {
        tableTrajetsPanel.updateVilles();
    }

}
