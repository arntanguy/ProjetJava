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
    public Bus(int prix, String vehicule, int capacite, int identifiant) {
        super(prix, vehicule, TypeVehicule.BUS, capacite, identifiant);
    }

    public Bus(int prix, String vehicule, int identifiant) {
        this(prix, vehicule, 50, identifiant);
    }
    public Bus(String vehicule, int identifiant) {
        this(1, vehicule, 50, identifiant);
    }
    public Bus(String vehicule, int capacite, int identifiant) {
        this(1, vehicule, capacite, identifiant);
    }
}
