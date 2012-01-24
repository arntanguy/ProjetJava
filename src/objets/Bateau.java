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
    public Bateau(String vehicule, TypeVehicule type, int capacite,
            int identifiant) {
        super(vehicule, TypeVehicule.bateau, capacite, identifiant);
        classes.add("Première classe");
        classes.add("Standard");
        repas.add("petit déjeuner");
        repas.add("déjeuner");
        repas.add("dîner");
        repas.add("végétarien");
        repas.add("viande");
        repas.add("poisson");
    }
    public Bateau(String vehicule, TypeVehicule type,
            int identifiant) {
        this(vehicule, TypeVehicule.bateau, 1000, identifiant);
    }
    
    public boolean avecCouchette()
    {
        return true;
    }
    
    public boolean avecRepas()
    {
        return true;
    }
    
    public boolean aDifferentesClasses()
    {
        return true;
    }
}
