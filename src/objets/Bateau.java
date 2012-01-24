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
    public Bateau(String vehicule, typeVehicule type, int capacite,
            int identifiant) {
        super(vehicule, typeVehicule.bateau, capacite, identifiant);
    }
    public Bateau(String vehicule, typeVehicule type,
            int identifiant) {
        super(vehicule, typeVehicule.bateau, 1000, identifiant);
    }
}