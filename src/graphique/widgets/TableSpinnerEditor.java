package graphique.widgets;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

/**
 * Cette classe fournit un Spinner comme éditeur de tableau, permettant ainsi de faciliter les 
 * saisies, notemment de dates.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class TableSpinnerEditor extends AbstractCellEditor implements
        TableCellEditor {

    private static final long serialVersionUID = 1L;
    final JSpinner spinner;
    // Initializes the spinner.
    public TableSpinnerEditor() {
        this(new SpinnerNumberModel());
    }

    public TableSpinnerEditor(SpinnerModel model) {
        spinner = new JSpinner();
        spinner.setModel(model);
        spinner.setFocusable(true);

        // List all of the components and make them focusable
        // then add an empty focuslistener to each
        for (Component tmpComponent : spinner.getComponents()) {
            tmpComponent.setFocusable(true);
            tmpComponent.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent fe) {
                }
            });
        }

    }

    public void setModel(SpinnerDateModel model) {
        spinner.setModel(model);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        spinner.setValue(new Date());
        return spinner;
    }

    public Object getCellEditorValue() {
        return spinner.getValue();
    }
}
