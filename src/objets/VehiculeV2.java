package objets;
package reservationPackage;
/**
*
*@author 
*@version 1.0
*/
public class VehiculeV2{
	
    private static String idVehicule=""+0;
    private int cap;
    /**
     *Constructeur générique. Permet de construire un Vehicule avec un id précis et la capacité indiqué.
     *@param int : capacité
     @return
    */
    public Vehicule(int cap){
	this.setIdVehicule();
	this.cap = cap;
    }
    /**
     *Contructeur par défaut. Permet de construire un Vehicule avec un id.
     *@param
     *@return
     */
    public Vehicule(){
	this.setIdVehicule();
	this.cap = 0;
    }
    /**
     *Méthode qui renvoie la capacité d'un Vehicule
     *@param
     *@return int : capacité.
     */
    public int getCap(){
	return this.cap;
    }
    /**
     *Méthode qui permet de modifier l'id d'un Vehicule
     *@param String : id de l'Vehicule
     *@return
     */
    public void setIdVehicule() {
	int id = Serv.getIdVehicule();
	id ++;
	this.idVehicule = ""+id;
    }
    /**
     *Méthode qui renvoie l'id d'un Vehicule
     *@param
     *@return String : id de l'Vehicule
     */
    public String getIdVehicule() {
	return idVehicule;
    }
    /**Méthode qui permet d'afficher un Vehicule
     *@param
     *@return String : toString
     */
    public String toString() {
	return "Vehicule n°" + this.idVehicule + "\n" + "Capacité : " + this.cap;
    }
}