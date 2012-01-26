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
    public Train(String vehicule, int capacite, int identifiant) {
        super(vehicule, TypeVehicule.TRAIN, capacite, identifiant);
        classes.add(new ClassesRepas("Première classe", 10));
        classes.add(new ClassesRepas("Seconde classe", 5));
        repas.add(new ClassesRepas("petit déjeuner", 5));
        repas.add(new ClassesRepas("déjeuner", 10));
        repas.add(new ClassesRepas("dîner", 10));
        repas.add(new ClassesRepas("végétarien", 5));
        repas.add(new ClassesRepas("viande", 5));
        repas.add(new ClassesRepas("poisson", 5));
    }

    public Train(String vehicule, int identifiant) {
        this(vehicule, 500, identifiant);
    }

    public boolean avecCouchette() {
        return true;
    }
}
