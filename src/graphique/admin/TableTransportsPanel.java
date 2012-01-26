package graphique.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import logiqueMetier.Serveur;
import objets.TypeVehicule;
import objets.Vehicule;

public class TableTransportsPanel extends JPanel {
	private TransportsTableModel transportModel;
	private JTable transportTable;
	private JScrollPane scrollPane;

	ArrayList<Vehicule> vehicules;

	private Serveur serveur;

	public TableTransportsPanel(Serveur s) {
		super();
		serveur = s;
		vehicules = serveur.getVehicules();
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Gestion des trajets"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		buildTrajetsTable();
		buildButtons();
	}

	private void buildTrajetsTable() {
		/* protected String vehicule;
		    protected TypeVehicule type;
		    protected int capacite;
		    protected int identifiant;
		    protected List<ClassesRepas> classes;
		    protected List<ClassesRepas> repas; */
		String[] columnNames = { "Nom du véhicule", "Type de véhicule", "Capacité d'accueil"};

		transportModel = new TransportsTableModel(vehicules);
		transportModel.setColumnNames(columnNames);
		transportTable = new JTable(null, columnNames);
		transportTable.setModel(transportModel);
		transportTable.setFillsViewportHeight(true); // Fill all the container
		transportTable.getSelectionModel().addListSelectionListener(
				new ReservationListener(transportTable)); 

		transportTable.getModel().addTableModelListener(new CellListener()); 

		Vector<Object> l = null;
		JComboBox combo = buildTypeCombo();
		addComboToTable(combo, 1);
		scrollPane = new JScrollPane(transportTable);
		add(scrollPane);
	}

	private void buildButtons() {
		JPanel panel = new JPanel();
		panel.setLayout( new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(new JButton(new AddAction("Ajouter")), BorderLayout.CENTER);
		panel.add(new JButton(new DeleteAction("Supprimer")), BorderLayout.CENTER);
		panel.add(new JButton(new SaveAction("Enregistrer")), BorderLayout.CENTER);
		add(panel);
	}


	public class AddAction extends AbstractAction {
		public AddAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Ajout !");
			TransportsTableModel model = (TransportsTableModel) transportTable.getModel();
			model.addRow(new Vehicule(serveur.getVehiculeNewIdentifiant()));	
		}
	}
	
	public class SaveAction extends AbstractAction {
		public SaveAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Enregistré !");
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
					serveur.removeVehicule(transportModel.getVehicule(row));
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
			
			switch(e.getType()) {
			case TableModelEvent.INSERT:
				System.out.println("Insertion");
				transportModel.setValueAt(serveur.getVehiculeNewIdentifiant(), row, 0);
				break;
			case TableModelEvent.UPDATE:
				System.out.println("Updated");
				for(Vehicule v:vehicules) {
					Vehicule tv = transportModel.getVehicule(row);
					if(v.getIdentifiant() == tv.getIdentifiant()) {
						v.setVehicule((String) transportModel.getValueAt(row, 0));
						v.setType((TypeVehicule)transportModel.getValueAt(row, 1));
						v.setCapacite((Integer) transportModel.getValueAt(row, 2));
						try {
							serveur.modifierVehicule(tv, v);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				break;
			}	
		}
	}


	private class ReservationListener implements ListSelectionListener {
		JTable table;

		// It is necessary to keep the table since it is not possible
		// to determine the table from the event's source
		ReservationListener(JTable table) {
			this.table = table;
		}

		public void valueChanged(ListSelectionEvent e) {
			// If cell selection is enabled, both row and column change events are fired
			if (e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()) {
				// Column selection changed
				int first = e.getFirstIndex();
				int last = e.getLastIndex();
				System.out.println("Selection changed");
				System.out.println(table.getModel().getValueAt(table.getSelectedRow(), 2));
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

}
