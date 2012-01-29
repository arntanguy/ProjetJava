package graphique.models;

import java.util.ArrayList;

import logiqueMetier.Serveur;

import objets.Passager;
import objets.Reservation;
import objets.Trajet;

/**
 * Fournit un modèle gérant les réservations
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class ReservationsTableModel<T> extends AbstractTableModel<T> {
    private static final long serialVersionUID = 1L;

    public ReservationsTableModel(ArrayList<T> liste) {
        super(liste);
    }

    public ReservationsTableModel() {
        super();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reservation r = null;
        try {
            r = (Reservation) liste.get(rowIndex);
        } catch (Exception e) {
            System.out.println("Reservations : Impossible de lire la ligne "
                    + rowIndex);
        }
        if (r == null)
            return null;
        Trajet t = r.getTrajet();
        switch (columnIndex) {
        case 0:
            return t.getDepart();
        case 1:
            return t.getArrivee();
        case 2:
            return Serveur.calendarToDate(t.getDateDepart());
        case 3:
            return Serveur.calendarToDate(t.getDateArrivee());
        case 4:
            return (t.getVehicule() != null) ? t.getVehicule().getType()
                    .getNom()
                    + " (" + t.getVehicule().getVehicule() + ")" : "Aucun";
        case 5:
            if (!r.isActive())
                return "";
            else {
                Passager p = r.getPassager();
                System.out.println(p);
                return (p != null) ? p.getNom() + " " + p.getPrenom() : "null";
            }
        case 6:
        	return r.getPrixValue();
        case 7:
            return (r.isActive()) ? "Réservé" : "Non réservé";
        }
        return null;
    }

    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        Reservation r = (Reservation) liste.get(rowIndex);
        switch (columnIndex) {
        case 0:
            r.setPassager((Passager) o);
            break;
        /*
         * case 1: Ville v1 = (Ville) o; if (v1 != null) { r.setArrivee(v1); }
         * else { r.setArrivee(new Ville()); } break; case 2:
         * r.setDateDepart(DateTools.dateToCalendar((Date)o)); break; case 3:
         * r.setDateArrivee(DateTools.dateToCalendar((Date)o)); break; case 4:
         * r.setVehicule((Vehicule)o);
         */
        }
        fireTableDataChanged();
    }
}
