package graphique.admin;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import logiqueMetier.Serveur;

import objets.Trajet;
import objets.Ville;
import tools.DateTools;

public class TrajetsTableModel extends DefaultTableModel {

	ArrayList<Trajet> trajets;
	String[] columnNames;

	public TrajetsTableModel(ArrayList<Trajet> trajets) {
		this.trajets = trajets;
	}

	@Override
	public int getColumnCount() {
		// Returns the number of columns in the model : départ, arrivée, 
		// date départ, date arrivée, transport, transport
		return 5;
	}

	@Override
	public int getRowCount() {
		return (trajets != null) ? trajets.size() : 0;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames; 
	} 
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Trajet v = null;
		try {
			v = trajets.get(rowIndex);
		} catch(Exception e) {
			System.out.println("TransportsTable : Impossible de lire la ligne "+rowIndex);
		}
		if (v==null) return null;
		switch(columnIndex) {
		case 0:
			return v.getDepart();
		case 1:
			return v.getArrivee();
		case 2: 
			return Serveur.calendarToDate(v.getDateDepart());
		case 3: 
			return Serveur.calendarToDate(v.getDateArrivee());
		}
		return null;
	}

	public void setValueAt(Object o, int rowIndex, int columnIndex) {
		Trajet t = trajets.get(rowIndex);
		switch(columnIndex) {
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
			t.setDateDepart(DateTools.dateToCalendar((Date)o));
			break;
		case 3: 
			t.setDateArrivee(DateTools.dateToCalendar((Date)o));
			break;
		}
		fireTableDataChanged();
	}

	public Trajet getTrajet(int rowIndex) {
		return trajets.get(rowIndex);
	}

	public void addRow(Trajet v) {
		trajets.add(v);
		fireTableDataChanged();
	} 

	public void removeRow(int row) {
		trajets.remove(row);
		fireTableDataChanged();
	}

	public boolean isCellEditable(int row, int column)
	{
		return true;
	}
}
