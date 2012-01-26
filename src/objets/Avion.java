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
        classes.add(new ClassesRepas("Classe affaire", 20));
        classes.add(new ClassesRepas("Classe première", 10));
        classes.add(new ClassesRepas("Classe standard", 5));
        repas.add(new ClassesRepas("petit déjeuner", 5));
        repas.add(new ClassesRepas("déjeuner", 10));
        repas.add(new ClassesRepas("dîner", 10));
        repas.add(new ClassesRepas("végétarien", 5));
        repas.add(new ClassesRepas("viande", 5));
        repas.add(new ClassesRepas("poisson", 5));
    }

    public Avion(int prix, String vehicule, int identifiant) {
        this(prix, vehicule, 300, identifiant);
    }
    
    public Avion(String vehicule, int identifiant) {
        this(500, vehicule, 300, identifiant);
    }
    
    public Avion(String vehicule, int capacite, int identifiant) {
        this(500, vehicule, capacite, identifiant);
    }

    public boolean avecCouchette() {
        return true;
    }
}
