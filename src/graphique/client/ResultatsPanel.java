package graphique.client;

import graphique.admin.ReservationsTableModel;
import graphique.admin.TableTrajetsPanel;
import graphique.models.TrajetsTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import logiqueMetier.Serveur;
import objets.Reservation;
import objets.Trajet;

public class ResultatsPanel extends JPanel {
	private TrajetsTableModel trajetsModel;
	private JTable trajetsTable;
	private JScrollPane scrollPane;
	
	private Serveur serveur;
	
	public ResultatsPanel(Serveur s) {
		super();
		serveur = s;
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

		trajetsModel = new TrajetsTableModel();
		trajetsModel.setColumnNames(columnNames);
		trajetsTable = new JTable();
		trajetsTable.setModel(trajetsModel);
		trajetsTable.setFillsViewportHeight(true); // Fill all the

		scrollPane = new JScrollPane(trajetsTable);		
		add(scrollPane);
		
		buildButtons();
	}

	
	private void buildButtons() {
		add(new JButton(new ReserverAction("Réserver")), BorderLayout.CENTER);
	}

	public class ReserverAction extends AbstractAction {
		public ReserverAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Réserver... !");
			int[] selectedIndexes = trajetsTable.getSelectedRows();
			for (int i=selectedIndexes.length-1;i>=0;i--) {
				int row = selectedIndexes[i];
				System.out.println(trajetsTable.getValueAt(row, 0));
			}	
		}
	}

	public void removeAllRows() {
		trajetsModel.removeAllRows();
	} 
	
	public void addTrajet(Trajet t) {
		trajetsModel.addRow(t);
	}

}
