package graphique.admin;

import javax.swing.SwingUtilities;

/**
 * Il s'agit de la classe principale de l'interface d'administration. Elle se charge
 * de créer la fenêtre d'administration.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class MainWindowAdmin {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FenetreClientAdmin fenetre = new FenetreClientAdmin();
                fenetre.setVisible(true);
            }
        });
    }
}
