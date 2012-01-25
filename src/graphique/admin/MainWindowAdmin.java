package graphique.admin;

import javax.swing.SwingUtilities;

public class MainWindowAdmin {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				FenetreClientAdmin fenetre = new FenetreClientAdmin();
				fenetre.setVisible(true);
			}
		});
	}
}
