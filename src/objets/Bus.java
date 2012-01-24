package objets;

/**
 * 
 * @author
 * @version 1.0
 */
public class Bus extends Vehicule {
    /**
     * Contructeur par défaut. Permet de construire un Vehicule avec un id.
     * 
     * @param
     * @return
     */
    public Bus(String vehicule, typeVehicule type, int capacite, int identifiant) {
        super(vehicule, typeVehicule.bus, capacite, identifiant);
    }
    public Bus(String vehicule, typeVehicule type, int identifiant) {
        super(vehicule, typeVehicule.bus, 50, identifiant);
    }
}