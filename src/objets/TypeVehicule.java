package objets;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public enum TypeVehicule {
    AVION("avion"), BUS("bus"), BATEAU("bateau"), TRAIN("train"), INCONNU(
            "inconnu");

    private String nom;

    TypeVehicule(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
