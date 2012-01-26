package objets;

/**
 * 
 * @author
 * @version 1.0
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
        classes.add(new ClassesRepas("Première classe", 10));
        classes.add(new ClassesRepas("Seconde classe", 5));
        repas.add(new ClassesRepas("petit déjeuner", 5));
        repas.add(new ClassesRepas("déjeuner", 10));
        repas.add(new ClassesRepas("dîner", 10));
        repas.add(new ClassesRepas("végétarien", 5));
        repas.add(new ClassesRepas("viande", 5));
        repas.add(new ClassesRepas("poisson", 5));
    }

    public Train(int prix, String vehicule, int identifiant) {
        this(prix, vehicule, 500, identifiant);
    }
    
    public Train(String vehicule, int identifiant) {
        this(10, vehicule, 500, identifiant);
    }
    
    public Train(String vehicule, int capacite, int identifiant) {
        this(10, vehicule, capacite, identifiant);
    }

    public boolean avecCouchette() {
        return true;
    }
}
