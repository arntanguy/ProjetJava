package graphique.admin;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import objets.Trajet;

import logiqueMetier.Serveur;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class GestionTrajetsPanel extends JPanel {
    private TableTrajetsPanel tableTrajetsPanel;

    private Serveur serveur;

    public GestionTrajetsPanel(Serveur s) {
        super();
        serveur = s;
        build();
    }

    private void build() {
        // setBorder(BorderFactory.createTitledBorder("Gestion des trajets"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        tableTrajetsPanel = new TableTrajetsPanel(serveur);
        add(tableTrajetsPanel);

    }

}
