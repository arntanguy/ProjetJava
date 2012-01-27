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
    public Bateau(int prix, String vehicule, int capacite, int identifiant) {
        super(prix, vehicule, TypeVehicule.BATEAU, capacite, identifiant);
        repas.add(new Repas("petit_dejeuner", 5));
        repas.add(new Repas("dejeuner", 10));
        repas.add(new Repas("dener", 10));
        repas.add(new Repas("vegetarien", 5));
        repas.add(new Repas("viande", 5));
        repas.add(new Repas("poisson", 5));
    }

    public Bateau(int prix, String vehicule, int identifiant) {
        this(prix, vehicule, 1000, identifiant);
    }
    
    public Bateau(String vehicule, int identifiant) {
        this(10, vehicule, 1000, identifiant);
    }

    public Bateau(String vehicule, int capacite, int identifiant) {
        this(10, vehicule, capacite, identifiant);
    }
    
    public boolean avecCouchette() {
        return true;
    }
}
