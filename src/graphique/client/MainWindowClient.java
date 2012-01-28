package graphique.client;

import graphique.client.FenetreClient;

import javax.swing.SwingUtilities;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class MainWindowClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // On crée une nouvelle instance de notre JDialog
                // FenetreClientAdmin fenetre = new FenetreClientAdmin();
                // fenetre.setVisible(true);//On la rend visible

                FenetreClient fenetre = new FenetreClient();
                fenetre.setVisible(true);// On la rend visible
            }
        });
    }
}
