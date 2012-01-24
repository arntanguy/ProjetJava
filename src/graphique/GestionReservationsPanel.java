package graphique;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GestionReservationsPanel extends JPanel {
	private JTable reservationsTable;
	private JScrollPane scrollPane;

	public GestionReservationsPanel() {
		super();
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Gestion des réservations"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		buildReservationsTable();
	}

	private void buildReservationsTable() {
		String[] columnNames = { "Id", "Last Name", "Sport", "# of Years",
				"Vegetarian" };
		Object[][] data = {
				{ "Kathy", "Smith", "Snowboarding", new Integer(5),
						new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
				{ "Sue", "Black", "Knitting", new Integer(2),
						new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20),
						new Boolean(true) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };
		reservationsTable = new JTable(data, columnNames);
		reservationsTable.setFillsViewportHeight(true); // Fill all the
														// container
		reservationsTable.getSelectionModel().addListSelectionListener(
				new ReservationListener(reservationsTable));
		/*reservationsTable.getColumnModel().getSelectionModel()
				.addListSelectionListener(
						new ReservationListener(reservationsTable));*/

		scrollPane = new JScrollPane(reservationsTable);
		add(scrollPane);
	}

	public class ValidateAction extends AbstractAction {
		public ValidateAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Validé !");
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
