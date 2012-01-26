package logiqueMetier;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import objets.Vehicule;

public class TransportsTableModel extends DefaultTableModel {

	ArrayList<Vehicule> vehicules;
	String[] columnNames;
	
	public TransportsTableModel(ArrayList<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}
	
	@Override
	public int getColumnCount() {
		// Returns the number of columns in the model : nom, capacite, type transport
		return 3;
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
		Vehicule v = vehicules.get(rowIndex);
		switch(columnIndex) {
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
	
	public Vehicule getVehicule(int rowIndex) {
		return vehicules.get(rowIndex);
	}

	public void addRow(Vehicule v) {
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
