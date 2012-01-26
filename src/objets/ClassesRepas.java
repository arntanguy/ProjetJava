package objets;

import java.io.Serializable;

public class ClassesRepas implements Serializable {
    String nom;
    int prix;
    /**
     * @param nom
     * @param prix
     */
    public ClassesRepas(String nom, int prix) {
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
