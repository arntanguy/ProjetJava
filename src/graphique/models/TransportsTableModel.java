package graphique.models;

import java.util.ArrayList;

import objets.TypeVehicule;
import objets.Vehicule;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class TransportsTableModel<T> extends AbstractTableModel<T> {
    private static final long serialVersionUID = 1L;

    public TransportsTableModel(ArrayList<T> vehicules) {
        super(vehicules);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vehicule v = null;
        try {
            v = (Vehicule) liste.get(rowIndex);
        } catch (Exception e) {
            System.out.println("TransportsTable : Impossible de lire la ligne "
                    + rowIndex);
        }
        if (v == null)
            return null;
        switch (columnIndex) {
        case 0:
            return v.getVehicule();
        case 1:
            return v.getType();
        case 2:
            return v.getCapacite();
        default:
            return null;
        }
    }

    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        Vehicule v = (Vehicule) liste.get(rowIndex);
        switch (columnIndex) {
        case 0:
            v.setVehicule((String) o);
            break;
        case 1:
            v.setType((TypeVehicule) o);
            break;
        case 2:
            v.setCapacite(Integer.parseInt((String) o));
            break;
        }
        fireTableDataChanged();
    }
}
