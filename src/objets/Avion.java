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
    public Avion(int prix, String vehicule, int capacite, int identifiant) {
        super(prix, vehicule, TypeVehicule.AVION, capacite, identifiant);
        repas.add(new Repas("petit_dejeuner", 5));
        repas.add(new Repas("dejeuner", 10));
        repas.add(new Repas("diner", 10));
        repas.add(new Repas("vegetarien", 5));
        repas.add(new Repas("viande", 5));
        repas.add(new Repas("poisson", 5));
    }

    public Avion(int prix, String vehicule, int identifiant) {
        this(prix, vehicule, 300, identifiant);
    }
    
    public Avion(String vehicule, int identifiant) {
        this(20, vehicule, 300, identifiant);
    }
    
    public Avion(String vehicule, int capacite, int identifiant) {
        this(20, vehicule, capacite, identifiant);
    }

    public boolean avecCouchette() {
        return true;
    }
}
