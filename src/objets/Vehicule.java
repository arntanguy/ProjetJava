package objets;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun.
 * 
 * Cette classe permet de stocker un véhicule. Un véhicule est constitué d'un
 * String indiquant son nom, de sa capacité et de son identifiant.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class Vehicule implements Serializable {
    protected String vehicule;
    protected TypeVehicule type;
    protected int capacite;
    protected int identifiant;
    protected int prix;
    protected List<Repas> repas;

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
    public Vehicule(int prix, String vehicule, TypeVehicule type, int capacite,
            int identifiant) {
        this.vehicule = vehicule;
        this.type = type;
        this.capacite = capacite;
        this.identifiant = identifiant;
        repas = new ArrayList<Repas>();
        this.prix = prix;
    }

    public Vehicule(int identifiant) {
        this(0, "", TypeVehicule.INCONNU, 0, identifiant);
    }

    public Vehicule() {
        this(0, "", TypeVehicule.INCONNU, 0, -1);
    }

    /**
     * @return nom du véhicule
     */
    public String getVehicule() {
        return vehicule;
    }

    public int getPrix() {
        return prix;
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

    public TypeVehicule getType() {
        return type;
    }

    /**
     * @param identifiant
     *            identifiant du véhicule
     */
    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public boolean avecCouchette() {
        return false;
    }

    public List<Repas> getRepas() {
        return repas;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append(vehicule)
                .append(" (id=")
                .append(identifiant)
                .append(") de type " + type.getNom()
                        + " et qui a une capacité de ").append(capacite)
                .toString();
    }

    /**
     * retourner une ligne de texte contenant les informations utiles pour
     * reconstituer un véhicule, séparé par des '#'
     * 
     * @return la ligne de texte terminée par un '\n'
     */
    public String print() {
        return new StringBuffer().append(vehicule).append("#").append(type)
                .append("#").append(capacite).append("#").append(identifiant)
                .append("#").append(prix).append("#").append("\n").toString();
    }

    public void setType(TypeVehicule typeV) {
        type = typeV;
    }
}
