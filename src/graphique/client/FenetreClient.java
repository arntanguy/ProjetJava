package graphique.client;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logiqueMetier.Serveur;
import logiqueMetier.ServeurV2;

/**
 * Fenêtre principale du client.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class FenetreClient extends JFrame {
    private static final long serialVersionUID = 1L;
  
    private Serveur serveur;

    public FenetreClient() {
        super();
        serveur = new ServeurV2();
        try {
            serveur.charger();
        } catch (Exception e) {
            e.printStackTrace();
        }
        build();
    }

    private void build() {
        setTitle("Client de réservation");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(buildContentPane());
    }

    private JPanel buildContentPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new ReservationPanel(serveur));

        JButton quit = new JButton(new QuitAction("Quitter"));
        panel.add(quit);

        return panel;
    }

    private class QuitAction extends AbstractAction {
        public QuitAction(String texte) {
            super(texte);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                serveur.sauvegarder();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dispose();
        }
    }
}
