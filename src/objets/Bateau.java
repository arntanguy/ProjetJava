package objets;
/**
*
*@author 
*@version 1.0
*/
public class Bateau extends Vehicule{
	
    /**
     *Contructeur par défaut. Permet de construire un Vehicule avec un id.
     *@param
     *@return
     */
    public Bateau(){
	super(1000);
    }
    /**Méthode qui permet d'afficher un Vehicule
     *@param
     *@return String : toString
     */
    public String toString() {
	return "Vehicule n°" + getIdVehicule() + "\n" + "Capacité : " + getCap();
    }
}