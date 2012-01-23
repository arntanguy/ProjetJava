package moyenDeTransport;

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

public class Vehicule implements Serializable {
    String vehicule;
    int capacite;
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
    public Vehicule(String vehicule, int capacite, int identifiant) {
        this.vehicule = vehicule;
        this.capacite = capacite;
        this.identifiant = identifiant;
    }

    /**
     * @return nom du véhicule
     */
    public String getVehicule() {
        return vehicule;
    }

    /**
     * @return capacité du véhicule
     */
    public int getCapacite() {
        return capacite;
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
    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    /**
     * @param capacite
     *            capacité du véhicule
     */
    public void setCapacite(int capacite) {
        this.capacite = capacite;
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
        return new StringBuffer().append(vehicule).append(" (id=")
                .append(identifiant).append(") qui a une capacité de ")
                .append(capacite).toString();
    }

    /**
     * retourner une ligne de texte contenant les informations utiles pour
     * reconstituer un véhicule, séparé par des '#'
     * 
     * @return la ligne de texte terminée par un '\n'
     */
    public String print() {
        return new StringBuffer().append(vehicule).append("#").append(capacite)
                .append("#").append(identifiant).append("#").append("\n")
                .toString();
    }
}
