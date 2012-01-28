package graphique.admin;

import graphique.models.TransportsTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import logiqueMetier.Serveur;
import objets.TypeVehicule;
import objets.Vehicule;

public class TableTransportsPanel extends AbstractTablePanel {
	private TransportsTableModel<Vehicule> transportModel;
	private JTable transportTable;
	private JScrollPane scrollPane;

	ArrayList<Vehicule> vehicules;

	public TableTransportsPanel(Serveur s) {
		super(s);
		vehicules = serveur.getVehicules();
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Gestion des transports"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		buildTrajetsTable();
		buildButtons();
	}

	private void buildTrajetsTable() {
		String[] columnNames = { "Nom du véhicule", "Type de véhicule", "Capacité d'accueil"};

		transportModel = new TransportsTableModel<Vehicule>(vehicules);
		transportModel.setColumnNames(columnNames);
		transportTable = new JTable();
		transportTable.setModel(transportModel);
		transportTable.setFillsViewportHeight(true); // Fill all the container
		
		transportTable.getModel().addTableModelListener(new CellListener()); 

		JComboBox combo = buildTypeCombo();
		addComboToTable(combo, 1);
		scrollPane = new JScrollPane(transportTable);
		add(scrollPane);
	}

	private void buildButtons() {
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout( new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.add(new JButton(new AddAction("Ajouter")), BorderLayout.CENTER);
		buttonsPanel.add(new JButton(new DeleteAction("Supprimer")), BorderLayout.CENTER);
		add(buttonsPanel);
	}

	public void setEditable(boolean isEditable) {
			transportModel.setEditable(isEditable);
	}
	public void setButtonsVisible(boolean visible) {
			buttonsPanel.setVisible(visible);
	}

	public class AddAction extends AbstractAction {
		public AddAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Ajout !");
			transportModel.addRow(new Vehicule(serveur.getVehiculeNewIdentifiant()));	
		}
	}
	
	public class DeleteAction extends AbstractAction {
		public DeleteAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Supprimé !");
			int[] selectedIndexes = transportTable.getSelectedRows();
			for (int i=selectedIndexes.length-1;i>=0;i--) {
				try {
					int row = selectedIndexes[i];
					serveur.removeVehicule((Vehicule) transportModel.get(row));
					transportModel.removeRow(row);
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
			int row    = e.getFirstRow();
			int column = e.getColumn();
			System.out.println("Row " + row);
			System.out.println("Column " + column);
			
			if(e.getType() == TableModelEvent.UPDATE) {
				System.out.println("Updated");
				for(Vehicule v:vehicules) {
					Vehicule tv = (Vehicule) transportModel.get(row);
					if(tv.getIdentifiant() == v.getIdentifiant()) {
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
		for(TypeVehicule v : TypeVehicule.values()) {
			combo.addItem(v);
		}
		return combo;
	}
	private void addComboToTable(JComboBox combo, int column) {
		TableColumn gradeColumn = transportTable.getColumnModel().getColumn(column);
		gradeColumn.setCellEditor(new DefaultCellEditor(combo));
	}

	public Vehicule getSelectedTransport() {
		return (Vehicule) transportModel.get(transportTable.getSelectedRow());
	}

}
