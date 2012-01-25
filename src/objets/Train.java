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
		super(vehicule, TypeVehicule.train, capacite, identifiant);
		classes.add("Première classe");
		classes.add("Seconde classe");
		repas.add("petit déjeuner");
		repas.add("déjeuner");
		repas.add("dîner");
		repas.add("végétarien");
		repas.add("viande");
		repas.add("poisson");
	}

	public Train(String vehicule, int identifiant) {
		this(vehicule, 500, identifiant);
	}

	public boolean avecCouchette() {
		return true;
	}

	public boolean avecRepas() {
		return true;
	}

	public boolean aDifferentesClasses() {
		return true;
	}

}
