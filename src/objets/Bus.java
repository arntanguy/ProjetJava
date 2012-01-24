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
    public Bus(String vehicule, TypeVehicule type, int capacite, int identifiant) {
        super(vehicule, TypeVehicule.bus, capacite, identifiant);
    }
    public Bus(String vehicule, TypeVehicule type, int identifiant) {
        this(vehicule, TypeVehicule.bus, 50, identifiant);
    }
}
