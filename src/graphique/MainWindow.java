package graphique;

import graphique.admin.FenetreClientAdmin;
import graphique.client.FenetreClient;

import javax.swing.SwingUtilities;

public class MainWindow {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//On crée une nouvelle instance de notre JDialog
				//FenetreClientAdmin fenetre = new FenetreClientAdmin();
				//fenetre.setVisible(true);//On la rend visible
				
				FenetreClient fenetre = new FenetreClient();
				fenetre.setVisible(true);//On la rend visible
			}
		});
	}
}
