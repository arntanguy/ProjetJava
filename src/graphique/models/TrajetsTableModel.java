package graphique.models;

import java.util.ArrayList;
import java.util.Date;

import logiqueMetier.Serveur;

import objets.Trajet;
import objets.Vehicule;
import objets.Ville;
import tools.DateTools;

/**
 * Fournit un modèle gérant les trajets
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class TrajetsTableModel<T> extends AbstractTableModel<T> {
    private static final long serialVersionUID = 1L;

    public TrajetsTableModel(ArrayList<T> liste) {
        super(liste);
    }

    public TrajetsTableModel() {
        super();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Trajet v = null;
        try {
            v = (Trajet) liste.get(rowIndex);
        } catch (Exception e) {
            System.out.println("TransportsTable : Impossible de lire la ligne "
                    + rowIndex);
        }
        if (v == null)
            return null;
        switch (columnIndex) {
        case 0:
            return v.getDepart();
        case 1:
            return v.getArrivee();
        case 2:
            return Serveur.calendarToDate(v.getDateDepart());
        case 3:
            return Serveur.calendarToDate(v.getDateArrivee());
        case 4:
            return (v.getVehicule() != null) ? v.getVehicule().getType()
                    .getNom()
                    + " (" + v.getVehicule().getVehicule() + ")" : "Aucun";
        }
        return null;
    }

    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        Trajet t = (Trajet) liste.get(rowIndex);
        switch (columnIndex) {
        case 0:
            Ville v = (Ville) o;
            if (v != null) {
                t.setDepart(v);
            } else {
                t.setDepart(new Ville());
            }
            break;
        case 1:
            Ville v1 = (Ville) o;
            if (v1 != null) {
                t.setArrivee(v1);
            } else {
                t.setArrivee(new Ville());
            }
            break;
        case 2:
            t.setDateDepart(DateTools.dateToCalendar((Date) o));
            break;
        case 3:
            t.setDateArrivee(DateTools.dateToCalendar((Date) o));
            break;
        case 4:
            t.setVehicule((Vehicule) o);
        }
        fireTableDataChanged();
    }
}
