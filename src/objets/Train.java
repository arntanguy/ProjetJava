package objets;

/**
*
*@author 
*@version 1.0
*/
public class Train extends Vehicule {
    /**
     * Contructeur par défaut. Permet de construire un Vehicule avec un id.
     * 
     * @param
     * @return
     */
    public Train(String vehicule, int capacite,
            int identifiant) {
        super(vehicule, TypeVehicule.train, capacite, identifiant);
    }
    public Train(String vehicule,
            int identifiant) {
        super(vehicule, TypeVehicule.train, 500, identifiant);
    }
}
