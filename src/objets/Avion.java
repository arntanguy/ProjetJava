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
    public Avion(String vehicule, int capacite, int identifiant) {
        super(vehicule, TypeVehicule.avion, capacite, identifiant);
    }
    public Avion(String vehicule, int identifiant) {
        super(vehicule, TypeVehicule.avion, 300, identifiant);
    }
}
