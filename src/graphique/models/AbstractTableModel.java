package graphique.models;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import objets.Trajet;

public class AbstractTableModel<T> extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private String[] columnNames;
	private boolean isEditable = true;
	protected ArrayList<T> liste;
	
	public AbstractTableModel() {
		super();
		liste = new ArrayList<T>();
	}
	public AbstractTableModel(ArrayList<T> liste) {
		this.liste = liste;
	}

	@Override
	public int getRowCount() {
		return (liste != null) ? liste.size() : 0;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
	
	public T get(int rowIndex) {
		return (T)liste.get(rowIndex);
	}
	
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames; 
	} 
	
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public boolean isCellEditable(int row, int column)
	{
		return isEditable;
	}
	
	public void addRow(T t) {
		liste.add(t);
		fireTableDataChanged();
	} 

	public void removeAllRows() {
		liste.clear();
		fireTableDataChanged();
	}
	public void removeRow(int row) {
		liste.remove(row);
		fireTableDataChanged();
	}
}
