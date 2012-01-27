package graphique.admin;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logiqueMetier.Serveur;

public class TransportSelectorDialog extends JDialog {
	private TableTransportsPanel selectorPanel;
	private TableTrajetsPanel parent;
	private int parentSelectedRow;
	
	private Serveur serveur;
	
	public TransportSelectorDialog(Serveur s, TableTrajetsPanel parent, int row) {
		serveur = s;
		this.parent = parent;
		parentSelectedRow = row;
		setTitle("Choix du transport à lier.");
		setSize(800,400); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setContentPane(buildContentPane());
	}
	
	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(buildTransportSelector());
		
		buildButtons(panel);
		
		return panel;
	}
	private JPanel buildTransportSelector() {
		//setBorder(BorderFactory.createTitledBorder("Gestion des réservations"));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		selectorPanel = new TableTransportsPanel(serveur);
		selectorPanel.setEditable(false);
		selectorPanel.setButtonsVisible(false);
		panel.add(selectorPanel);
		
		return panel;
	}
	
	private void buildButtons(JPanel panel) {
		panel.add(new JButton(new LinkAction("Lier")));
		panel.add(new JButton(new QuitAction("Quitter")));
	}
	
	private class QuitAction extends AbstractAction {
		public QuitAction(String s) {
			super(s);
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Quit");
			dispose();
		}
	}
	private class LinkAction extends AbstractAction {
		public LinkAction(String s) {
			super(s);
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Link");			
			parent.linkTransport(parentSelectedRow, selectorPanel.getSelectedTransport());
			dispose();
		}
	}
}
