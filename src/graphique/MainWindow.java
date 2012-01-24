package graphique;

import javax.swing.SwingUtilities;

public class MainWindow {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//On crée une nouvelle instance de notre JDialog
				FenetreClient fenetre = new FenetreClient();
				fenetre.setVisible(true);//On la rend visible
			}
		});
	}
}
