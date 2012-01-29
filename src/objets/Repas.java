package objets;

import java.io.Serializable;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class Repas implements Serializable {
    String nom;
    int prix;

    /**
     * @param nom
     * @param prix
     */
    public Repas(String nom, int prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public int getPrix() {
        return prix;
    }
}
