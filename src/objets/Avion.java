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
    public Avion(String vehicule, TypeVehicule type, int capacite,
            int identifiant) {
        super(vehicule, TypeVehicule.avion, capacite, identifiant);
        classes.add("Classe affaire");
        classes.add("Classe première");
        classes.add("Classe standard");
        repas.add("petit déjeuner");
        repas.add("déjeuner");
        repas.add("dîner");
        repas.add("végétarien");
        repas.add("viande");
        repas.add("poisson");
    }
    public Avion(String vehicule, TypeVehicule type,
            int identifiant) {
        this(vehicule, TypeVehicule.avion, 300, identifiant);
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
