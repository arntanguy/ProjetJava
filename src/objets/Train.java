package objets;
/**
*
*@author 
*@version 1.0
*/
	public class Train extends Vehicule{
		
	    /**
	     *Contructeur par défaut. Permet de construire un Vehicule avec un id.
	     *@param
	     *@return
	     */
	    public Train(){
		super(500);
	    }
	    /**Méthode qui permet d'afficher un Vehicule
	     *@param
	     *@return String : toString
	     */
	    public String toString() {
		return "Vehicule n°" + getIdVehicule() + "\n" + "Capacité : " + getCap();
	    }
	}

}
