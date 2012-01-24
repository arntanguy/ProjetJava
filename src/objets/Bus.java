package objets;
/**
*
*@author 
*@version 1.0
*/
public class Bus extends Vehicule{
	
    /**
     *Contructeur par défaut. Permet de construire un Vehicule avec un id.
     *@param
     *@return
     */
    public Bus(){
    	super(50);
    }
    public Bus(int nbPlaces){
    	super(nbPlaces);
    }
    /**Méthode qui permet d'afficher un Vehicule
     *@param
     *@return String : toString
     */
    public String toString() {
	return "Vehicule n°" + getIdVehicule() + "\n" + "Capacité : " + getCap();
    }
}