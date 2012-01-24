package objets;

/**
 * 
 * @author
 * @version 1.0
 */
public class Bateau extends Vehicule {
    /**
     * Contructeur par défaut. Permet de construire un Vehicule avec un id.
     * 
     * @param
     * @return
     */
    public Bateau(String vehicule, int capacite,
            int identifiant) {
        super(vehicule, TypeVehicule.bateau, capacite, identifiant);
    }
    public Bateau(String vehicule, 
            int identifiant) {
        super(vehicule, TypeVehicule.bateau, 1000, identifiant);
    }
}
