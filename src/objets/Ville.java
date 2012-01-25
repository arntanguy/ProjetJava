package objets;

import java.io.*;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun.
 * 
 * Cette classe permet de stocker un véhicule. Un véhicule est constitué d'un
 * String indiquant son nom, de sa capacité et de son identifiant.
 * 
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public class Ville implements Serializable {
    String ville;
    int identifiant;

    /**
     * Créé une instance de véhicule
     * 
     * @param vehicule
     *            nom du véhicule
     * @param capacite
     *            capacité du véhicule
     * @param identifiant
     *            identifiant du véhicule
     */
    public Ville(String ville, int identifiant) {
        this.ville = ville;
        this.identifiant = identifiant;
    }

    /**
     * @return nom du véhicule
     */
    public String getVille() {
        return ville;
    }

    /**
     * @return identifiant du véhicule
     */
    public int getIdentifiant() {
        return identifiant;
    }

    /**
     * @param vehicule
     *            nom du véhicule
     */
    public void setVille(String ville) {
        this.ville = ville;
    }
    
    /**
     * @param identifiant
     *            identifiant du véhicule
     */
    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    @Override
    public String toString() {
        return new StringBuffer().append(ville).toString();
    }
    
    /** 
     * Affichage pour le client console
     * @return chaine descriptive de l'objet Ville
     */
    public String toStringC() {
        return new StringBuffer().append(ville).append(" (id=")
                .append(identifiant).append(")").toString();
    }

    /**
     * retourner une ligne de texte contenant les informations utiles pour
     * reconstituer un véhicule, séparé par des '#'
     * 
     * @return la ligne de texte terminée par un '\n'
     */
    public String print() {
        return new StringBuffer().append(ville).append("#").append(identifiant).append("#").append("\n")
                .toString();
    }
}
