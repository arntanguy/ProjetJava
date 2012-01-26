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
        classes.add(new ClassesRepas("Premiere_classe", 10));
        classes.add(new ClassesRepas("Seconde_classe", 5));
        repas.add(new ClassesRepas("petit_dejeuner", 5));
        repas.add(new ClassesRepas("dejeuner", 10));
        repas.add(new ClassesRepas("diner", 10));
        repas.add(new ClassesRepas("vegetarien", 5));
        repas.add(new ClassesRepas("viande", 5));
        repas.add(new ClassesRepas("poisson", 5));
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
