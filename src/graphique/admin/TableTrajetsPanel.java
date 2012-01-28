package graphique.admin;

import graphique.models.TrajetsTableModel;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import logiqueMetier.Serveur;
import objets.Trajet;
import objets.Vehicule;
import objets.Ville;

public class TableTrajetsPanel extends AbstractTablePanel {
	private TrajetsTableModel trajetsModel;
	private JTable trajetsTable;
	private JScrollPane scrollPane;

	private TableSpinnerEditor dateDepartSpinner;
	private TableSpinnerEditor dateArriveeSpinner;

	private ArrayList<Trajet> trajets;
	private ArrayList<Ville> villes;
	
	public TableTrajetsPanel(Serveur s) {
		super(s);
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
				"Date arrivée", "Transport" };

		// Create a SpinnerDateModel with current date as the initial value.
		SpinnerDateModel model = new SpinnerDateModel();
		SpinnerDateModel model1 = new SpinnerDateModel();
		dateDepartSpinner = new TableSpinnerEditor(model);
		dateArriveeSpinner = new TableSpinnerEditor(model1);

		trajetsModel = new TrajetsTableModel<Trajet>(trajets);
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
		buttonsPanel.setLayout( new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.add(new JButton(new AddAction("Ajouter")), BorderLayout.CENTER);
		buttonsPanel.add(new JButton(new DeleteAction("Supprimer")), BorderLayout.CENTER);
		buttonsPanel.add(new JButton(new LinkAction("Lier à un tranport", this)));
		add(buttonsPanel);
	}

	public class LinkAction extends AbstractAction {
		private TableTrajetsPanel parent;

		public LinkAction(String texte, TableTrajetsPanel parent) {
			super(texte);
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Link !");
			int [] selected = trajetsTable.getSelectedRows();
			for(final int i : selected) {
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						TransportSelectorDialog d = new TransportSelectorDialog(serveur, parent, i);
						d.setVisible(true);
					}
				});

			}
		}
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
				serveur.removeTrajet((Trajet) trajetsModel.get(row));
				trajetsModel.removeRow(row);
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
			case TableModelEvent.UPDATE:
				System.out.println("Updated");
				for(Trajet t:trajets) {
					Trajet tt = (Trajet) trajetsModel.get(row);
					if(t.getIdentifiant() == tt.getIdentifiant()) {
						try {
							serveur.modifierTrajet(t, tt);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
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

	private void addCellEditorToTable(TableCellEditor e, int column) {
		TableColumn gradeColumn = trajetsTable.getColumnModel().getColumn(column);
		gradeColumn.setCellEditor(e);
	}


	public void linkTransport(int parentSelectedRow, Vehicule selectedTransport) {
		trajetsModel.setValueAt(selectedTransport, parentSelectedRow, 4);
		System.out.println("Lié à "+selectedTransport.toString());
	}

	public void setEditable(boolean b) {
		trajetsModel.setEditable(b);
	}
}
