package graphique.admin;

import graphique.widgets.TableSpinnerEditor;

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
import javax.swing.SpinnerDateModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import logiqueMetier.Serveur;
import objets.Trajet;
import objets.TypeVehicule;
import objets.Vehicule;
import objets.Ville;

public class TableTrajetsPanel extends JPanel {
	private TrajetsTableModel trajetsModel;
	private JTable trajetsTable;
	private JScrollPane scrollPane;
	
	private TableSpinnerEditor dateDepartSpinner;
	private TableSpinnerEditor dateArriveeSpinner;
	
	private ArrayList<Trajet> trajets;
	private ArrayList<Ville> villes;

	private Serveur serveur;
	
	public TableTrajetsPanel(Serveur s) {
		super();
		serveur = s;
		trajets = serveur.getTrajets();
		villes = serveur.getVilles();
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Gestion des trajets"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		buildReservationsTable();
		buildButtons();
	}

	private void buildReservationsTable() {
		String[] columnNames = { "Départ", "Arrivée", "Date départ",
		"Date arrivée" };
		
		// Create a SpinnerDateModel with current date as the initial value.
		SpinnerDateModel model = new SpinnerDateModel();
		SpinnerDateModel model1 = new SpinnerDateModel();
		dateDepartSpinner = new TableSpinnerEditor(model);
		dateArriveeSpinner = new TableSpinnerEditor(model1);
		
		trajetsModel = new TrajetsTableModel(trajets);
		trajetsModel.setColumnNames(columnNames);
		trajetsTable = new JTable();
		trajetsTable.setModel(trajetsModel);
		trajetsTable.setFillsViewportHeight(true); // Fill all the
		// container
		trajetsTable.getModel().addTableModelListener(new CellListener()); 

		JComboBox combo = buildDepartCombo();
		addComboToTable(combo, 0);
		addComboToTable(combo, 1);
		addSpinnerToTable(dateDepartSpinner, 2);
		addSpinnerToTable(dateArriveeSpinner, 3);
		

		scrollPane = new JScrollPane(trajetsTable);
		add(scrollPane);
	}

	private JComboBox buildDepartCombo() {
		JComboBox combo = new JComboBox();
		for(Ville v : villes) {
			combo.addItem(v);
		}
		return combo;
	}
	
	private void buildButtons() {

		JPanel panel = new JPanel();
		panel.setLayout( new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(new JButton(new AddAction("Ajouter")), BorderLayout.CENTER);
		panel.add(new JButton(new DeleteAction("Supprimer")), BorderLayout.CENTER);
		add(panel);
	}

	public class AddAction extends AbstractAction {
		public AddAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Ajout !");
			trajetsModel.addRow(new Trajet(serveur.getTrajetNewIdentifiant()));
		}
	}
	
	public class DeleteAction extends AbstractAction {
		public DeleteAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Supprimé !");
			int[] selectedIndexes = trajetsTable.getSelectedRows();
			for (int i=selectedIndexes.length-1;i>=0;i--) {
				int row = selectedIndexes[i];
				System.out.println(trajetsModel.getValueAt(row, 0));
				trajetsModel.removeRow(row);
				// XXX: Call the delete method
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
				//trajetsModel.setValueAt(serveur.getVehiculeNewIdentifiant(), row, 0);
				break;
			case TableModelEvent.UPDATE:
			/*	System.out.println("Updated");
				for(Trajet v:trajets) {
					Trajet tv = trajetsModel.getVehicule(row);
					if(v.getIdentifiant() == tv.getIdentifiant()) {
						v.setVehicule((String) trajetsModel.getValueAt(row, 0));
						v.setType((TypeVehicule)trajetsModel.getValueAt(row, 1));
						v.setCapacite((Integer) trajetsModel.getValueAt(row, 2));
						try {
							serveur.modifierVehicule(tv, v);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}*/
				break;
			}	
		}
	}

	private void addSpinnerToTable(TableSpinnerEditor spinner, int column) {
		TableColumn gradeColumn = trajetsTable.getColumnModel().getColumn(column);
		gradeColumn.setCellEditor(spinner);
	}
	private void addComboToTable(JComboBox combo, int column) {
		TableColumn gradeColumn = trajetsTable.getColumnModel().getColumn(column);
		gradeColumn.setCellEditor(new DefaultCellEditor(combo));
	}
}
