package graphique.client;

import graphique.models.ReservationsTableModel;
import graphique.models.TrajetsTableModel;
import graphique.widgets.AbstractTablePanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import logiqueMetier.Serveur;
import objets.Reservation;
import objets.Trajet;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class ResultatsPanel extends AbstractTablePanel {
    private static final long serialVersionUID = 1L;

    public ResultatsPanel(Serveur s) {
        super(s);
        build();
    }

    private void build() {
        setBorder(BorderFactory.createTitledBorder("Gestion des trajets"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        buildReservationsTable();
    }

    private void buildReservationsTable() {
        String[] columnNames = { "Départ", "Arrivée", "Date départ",
                "Date arrivée", "Transport", "Passager", "Réservé" };

        model = new ReservationsTableModel<Reservation>();
        model.setColumnNames(columnNames);
        table = new JTable();
        table.setModel(model);
        table.setFillsViewportHeight(true); // Fill all the

        scrollPane = new JScrollPane(table);
        add(scrollPane);

        buildButtons();
    }

    private void buildButtons() {
        add(new JButton(new ReserverAction("Réserver")), BorderLayout.CENTER);
    }

    public class ReserverAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public ReserverAction(String texte) {
            super(texte);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            System.out.println("Réserver... !");
            int[] selectedIndexes = table.getSelectedRows();
            for (int i = selectedIndexes.length - 1; i >= 0; i--) {
                int row = selectedIndexes[i];
                Reservation r = (Reservation) model.get(row);
                r.setActive(true);
                model.fireTableDataChanged();
                try {
                    serveur.addReservation(r);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeAllRows() {
        model.removeAllRows();
    }

    public void addReservation(Reservation r) {
        model.addRow(r);
    }

}
