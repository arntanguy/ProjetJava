package graphique.client;

import graphique.models.TrajetsTableModel;
import graphique.widgets.AbstractTablePanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import logiqueMetier.Serveur;
import objets.Trajet;

public class ResultatsPanel extends AbstractTablePanel {

	public ResultatsPanel(Serveur s) {
		super(s);
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Gestion des trajets"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		buildReservationsTable();
	}

	private void buildReservationsTable() {
		String[] columnNames = { "Départ", "Arrivée", "Date départ",
				"Date arrivée", "Transport", "Etat" };

		model = new TrajetsTableModel();
		model.setColumnNames(columnNames);
		table = new JTable();
		table.setModel(model);
		table.setFillsViewportHeight(true); // Fill all the

		scrollPane = new JScrollPane(table);		
		add(scrollPane);
		
		buildButtons();
	}

	
	private void buildButtons() {
		add(new JButton(new ReserverAction("Réserver")), BorderLayout.CENTER);
	}

	public class ReserverAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ReserverAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Réserver... !");
			int[] selectedIndexes = table.getSelectedRows();
			for (int i=selectedIndexes.length-1;i>=0;i--) {
				int row = selectedIndexes[i];
				System.out.println(table.getValueAt(row, 0));
			}	
		}
	}

	public void removeAllRows() {
		model.removeAllRows();
	} 
	
	public void addTrajet(Trajet t) {
		model.addRow(t);
	}

}
