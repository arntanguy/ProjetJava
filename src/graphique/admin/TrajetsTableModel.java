package graphique.admin;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import objets.Trajet;

public class TrajetsTableModel extends DefaultTableModel {

	ArrayList<Trajet> vehicules;
	String[] columnNames;

	public TrajetsTableModel(ArrayList<Trajet> vehicules) {
		this.vehicules = vehicules;
	}

	@Override
	public int getColumnCount() {
		// Returns the number of columns in the model : départ, arrivée, 
		// date départ, date arrivée, transport, places restantes
		return 5;
	}

	@Override
	public int getRowCount() {
		return (vehicules != null) ? vehicules.size() : 0;
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
	/*	Trajet v = null;
		try {
			v = vehicules.get(rowIndex);
		} catch(Exception e) {
			System.out.println("TransportsTable : Impossible de lire la ligne "+rowIndex);
		}
		if (v==null) return null;
		switch(columnIndex) {
		case 0:
			return v.getTrajet();
		case 1:
			return v.getType();
		case 2: 
			return v.getCapacite();
		default:
			return null;
		}*/
		return null;
	}

	public void setValueAt(Object o, int rowIndex, int columnIndex) {
/*		Trajet v = vehicules.get(rowIndex);
		switch(columnIndex) {
		case 0:
			v.setTrajet((String)o);
			break;
		case 1:
			v.setType((TypeTrajet)o);
			break;
		case 2: 
			v.setCapacite(Integer.parseInt( (String)o ));
			break;
		}
		fireTableDataChanged();*/
	}

	public Trajet getTrajet(int rowIndex) {
		return vehicules.get(rowIndex);
	}

	public void addRow(Trajet v) {
		vehicules.add(v);
		fireTableDataChanged();
	}

	public void removeRow(int row) {
		vehicules.remove(row);
		fireTableDataChanged();
	}

	public boolean isCellEditable(int row, int column)
	{
		return true;
	}

}
