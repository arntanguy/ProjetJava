package objets;

/**
 * 
 * @author
 * @version 1.0
 */
public class Avion extends Vehicule {
    /**
     * Contructeur par défaut. Permet de construire un Vehicule avec un id.
     * 
     * @param
     * @return
     */
    public Avion(String vehicule, typeVehicule type, int capacite,
            int identifiant) {
        super(vehicule, typeVehicule.avion, capacite, identifiant);
    }
    public Avion(String vehicule, typeVehicule type,
            int identifiant) {
        super(vehicule, typeVehicule.avion, 300, identifiant);
    }
}