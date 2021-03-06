package graphique.admin;

import graphique.models.TransportsTableModel;
import graphique.widgets.AbstractTablePanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import logiqueMetier.Serveur;
import objets.TypeVehicule;
import objets.Vehicule;

/**
 * Cette classe fournit un tableau et des boutons associés permettant de modifier
 * créer et supprimer des transports.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class TableTransportsPanel extends AbstractTablePanel {
    private static final long serialVersionUID = 1L;

    private TransportsTableModel<Vehicule> model;

    ArrayList<Vehicule> vehicules;

    public TableTransportsPanel(Serveur s) {
        super(s);
        vehicules = serveur.getVehicules();
        build();
    }

    private void build() {
        setBorder(BorderFactory.createTitledBorder("Gestion des transports"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        buildTable();
        buildButtons();
    }

    private void buildTable() {
        String[] columnNames = { "Nom du véhicule", "Type de véhicule",
                "Capacité d'accueil" };

        model = new TransportsTableModel<Vehicule>(vehicules);
        model.setColumnNames(columnNames);
        table = new JTable();
        table.setModel(model);
        table.setFillsViewportHeight(true); // Fill all the container

        table.getModel().addTableModelListener(new CellListener());

        JComboBox combo = buildTypeCombo();
        addComboToTable(combo, 1);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    private void buildButtons() {
        buttonsPanel = new JPanel();
        buttonsPanel
                .setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(new JButton(new AddAction("Ajouter")),
                BorderLayout.CENTER);
        buttonsPanel.add(new JButton(new DeleteAction("Supprimer")),
                BorderLayout.CENTER);
        add(buttonsPanel);
    }

    public void setEditable(boolean isEditable) {
        model.setEditable(isEditable);
    }

    public void setButtonsVisible(boolean visible) {
        buttonsPanel.setVisible(visible);
    }

    public class AddAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public AddAction(String texte) {
            super(texte);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            System.out.println("Ajout !");
            model.addRow(new Vehicule(serveur.getVehiculeNewIdentifiant()));
        }
    }
    /**
     * Supprime un transport à la fois dans le tableau, et sur le serveur.
     */
    public class DeleteAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public DeleteAction(String texte) {
            super(texte);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            System.out.println("Supprimé !");
            int[] selectedIndexes = table.getSelectedRows();
            for (int i = selectedIndexes.length - 1; i >= 0; i--) {
                try {
                    int row = selectedIndexes[i];
                    serveur.removeVehicule((Vehicule) model.get(row));
                    model.removeRow(row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class CellListener implements TableModelListener {
        public CellListener() {
        }

        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            System.out.println("Row " + row);
            System.out.println("Column " + column);

            if (e.getType() == TableModelEvent.UPDATE) {
                System.out.println("Updated");
                for (Vehicule v : vehicules) {
                    Vehicule tv = (Vehicule) model.get(row);
                    if (tv.getIdentifiant() == v.getIdentifiant()) {
                        try {
                            serveur.modifierVehicule(tv, v);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private JComboBox buildTypeCombo() {
        JComboBox combo = new JComboBox();
        for (TypeVehicule v : TypeVehicule.values()) {
            combo.addItem(v);
        }
        return combo;
    }

    public Vehicule getSelectedTransport() {
        return (Vehicule) model.get(table.getSelectedRow());
    }

}
