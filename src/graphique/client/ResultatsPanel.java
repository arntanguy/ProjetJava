package graphique.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import logiqueMetier.Serveur;

public class ResultatsPanel extends JPanel {
	private DefaultTableModel trajetsModel;
	private JTable trajetsTable;
	private JScrollPane scrollPane;

	private Serveur serveur;
	
	public ResultatsPanel(Serveur s) {
		super();
		serveur = s;
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Résultats de votre recherche"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		buildtrajetsTable();
		buildButtons();
	}

	private void buildtrajetsTable() {
		String[] columnNames = { "Id", "Départ", "Arrivée", "Ville départ",
		"Ville arrivée" };
		trajetsModel = new DefaultTableModel(null, columnNames);
		trajetsTable = new JTable();
		trajetsTable.setModel(trajetsModel);
		trajetsTable.setFillsViewportHeight(true); // Fill all the
		// container
		trajetsTable.getSelectionModel().addListSelectionListener(
				new ReservationListener(trajetsTable));
		/*trajetsTable.getColumnModel().getSelectionModel()
				.addListSelectionListener(
						new ReservationListener(trajetsTable));*/

		scrollPane = new JScrollPane(trajetsTable);
		add(scrollPane);
	}

	private void buildButtons() {
		add(new JButton(new ReserverAction("Réserver")), BorderLayout.CENTER);
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
	public class ReserverAction extends AbstractAction {
		public ReserverAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Réseerver... !");
			int[] selectedIndexes = trajetsTable.getSelectedRows();
			for (int i=selectedIndexes.length-1;i>=0;i--) {
				int row = selectedIndexes[i];
				System.out.println(trajetsModel.getValueAt(row, 0));
				//trajetsModel.removeRow(row);
				// XXX: Call the delete method
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
			} 
		}
	}

}
