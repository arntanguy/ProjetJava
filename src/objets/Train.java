package objets;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class Train extends Vehicule {
    /**
     * Contructeur par défaut. Permet de construire un Vehicule avec un id.
     * 
     * @param
     * @return
     */
    public Train(int prix, String vehicule, int capacite, int identifiant) {
        super(prix, vehicule, TypeVehicule.TRAIN, capacite, identifiant);
        repas.add(new Repas("petit_dejeuner", 5));
        repas.add(new Repas("dejeuner", 10));
        repas.add(new Repas("diner", 10));
        repas.add(new Repas("vegetarien", 5));
        repas.add(new Repas("viande", 5));
        repas.add(new Repas("poisson", 5));
    }

    public Train(int prix, String vehicule, int identifiant) {
        this(prix, vehicule, 500, identifiant);
    }

    public Train(String vehicule, int identifiant) {
        this(5, vehicule, 500, identifiant);
    }

    public Train(String vehicule, int capacite, int identifiant) {
        this(5, vehicule, capacite, identifiant);
    }

    public boolean avecCouchette() {
        return true;
    }
}
