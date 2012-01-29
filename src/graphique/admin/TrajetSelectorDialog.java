package graphique.admin;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logiqueMetier.Serveur;

/**
 * Cette classe fournit une boite de dialogue permettant de sélectionner un trajet.
 * Elle est notemment utilisée pour lier un trajet à une réservation.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class TrajetSelectorDialog extends JDialog {
    private static final long serialVersionUID = 1L;
  
    private TableTrajetsPanel selectorPanel;
    private Serveur serveur;

    public TrajetSelectorDialog(Serveur s, TableTrajetsPanel parent, int row) {
        serveur = s;
        setTitle("Choix du transport à lier.");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(buildContentPane());
    }

    private JPanel buildContentPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(buildTrajetSelector());

        buildButtons(panel);

        return panel;
    }

    private JPanel buildTrajetSelector() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        selectorPanel = new TableTrajetsPanel(serveur);
        selectorPanel.setEditable(false);
        selectorPanel.setButtonsVisible(false);
        panel.add(selectorPanel);

        return panel;
    }

    private void buildButtons(JPanel panel) {
        panel.add(new JButton(new LinkAction("Lier")));
        panel.add(new JButton(new QuitAction("Quitter")));
    }

    /**
     * Quitte sans rien faire
     */
    private class QuitAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public QuitAction(String s) {
            super(s);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            System.out.println("Quit");
            dispose();
        }
    }

    private class LinkAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public LinkAction(String s) {
            super(s);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            System.out.println("Link");
            // TODO
            // parent.linkTrajet(parentSelectedRow,
            // selectorPanel.getSelectedTrajet());
            dispose();
        }
    }
}
