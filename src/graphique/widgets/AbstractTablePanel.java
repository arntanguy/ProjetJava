package graphique.widgets;

import graphique.models.AbstractTableModel;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import logiqueMetier.Serveur;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public abstract class AbstractTablePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    protected Serveur serveur;
    protected JPanel buttonsPanel;
    protected boolean buttonsVisible = true;

    protected JTable table;
    protected AbstractTableModel model;
    protected JScrollPane scrollPane;

    public AbstractTablePanel(Serveur s) {
        super();
        serveur = s;
        table = new JTable();
        model = new AbstractTableModel();
        buttonsPanel = new JPanel();
        buttonsPanel
                .setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
    }

    public void setButtonsVisible(boolean isVisible) {
        buttonsPanel.setVisible(isVisible);
    }

    public void addSpinnerToTable(TableSpinnerEditor spinner, int column) {
        TableColumn gradeColumn = table.getColumnModel().getColumn(column);
        gradeColumn.setCellEditor(spinner);
    }

    public void addComboToTable(JComboBox combo, int column) {
        TableColumn gradeColumn = table.getColumnModel().getColumn(column);
        gradeColumn.setCellEditor(new DefaultCellEditor(combo));
    }

    public void addCellEditorToTable(TableCellEditor e, int column) {
        TableColumn gradeColumn = table.getColumnModel().getColumn(column);
        gradeColumn.setCellEditor(e);
    }

    public void setEditable(boolean b) {
        model.setEditable(b);
    }
}
