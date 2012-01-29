package graphique.client;

import graphique.client.FenetreClient;

import javax.swing.SwingUtilities;

/**
 * Classe principale créeant la fenêtre du client graphique.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class MainWindowClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FenetreClient fenetre = new FenetreClient();
                fenetre.setVisible(true);
            }
        });
    }
}
