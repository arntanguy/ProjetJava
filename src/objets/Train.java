package objets;

/**
 * 
 * @author
 * @version 1.0
 */
public class Train extends Vehicule {
    /**
     * Contructeur par défaut. Permet de construire un Vehicule avec un id.
     * 
     * @param
     * @return
     */
    public Train(String vehicule, typeVehicule type, int capacite,
            int identifiant) {
        super(vehicule, typeVehicule.train, capacite, identifiant);
    }
    public Train(String vehicule, typeVehicule type,
            int identifiant) {
        super(vehicule, typeVehicule.train, 500, identifiant);
    }
}
