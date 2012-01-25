package logiqueMetier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import objets.*;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun. Cette classe est celle qui permet
 * d'administrer les véhicules et les trajets en étroite relation avec le
 * serveur.
 * 
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public class Admin {
    Serveur serveur;

    /**
     * Créer une instance d'administration
     * 
     * @param serveur
     *            le serveur qui contient les données à administrer
     */
    public Admin(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Récupère un nouvel identifiant pour un véhicule qui va être créé
     * 
     * @return le nouvel identifiant
     */
    public int getVehiculeNewIdentifiant() {
        return serveur.getVehiculeNewIdentifiant();
    }

    /**
     * Récupère un nouvel identifiant pour un trajet qui va être créé
     * 
     * @return le nouvel identifiant
     */
    public int getTrajetNewIdentifiant() {
        return serveur.getTrajetNewIdentifiant();
    }
    
    public int getVilleNewIdentifiant(){
        return serveur.getVilleNewIdentifiant();
    }

    /**
     * Récupère un véhicule grâce à son identifiant
     * 
     * @param id
     *            l'identifiant du véhicule à récupérer
     * @return le véhicule trouvé
     */
    public Vehicule getVehicule(int id) {
        return serveur.getVehicule(id);
    }

    /**
     * Récupère un trajet grâce à son identifiant
     * 
     * @param id
     *            l'identifiant du trajet à récupérer
     * @return le trajet trouvé
     */
    public Trajet getTrajet(int id) {
        return serveur.getTrajet(id);
    }

    public Ville getVille(int id) {
        return serveur.getVille(id);
    }
  
    public ArrayList<Ville> getVilles() {
		return serveur.getVilles();
	}
    
    /**
     * Ajouter un véhicule à la liste des véhicules
     * 
     * @param v
     *            le véhicule à ajouter
     * @throws Exception
     */
    protected void addVehicule(Vehicule v) throws Exception {
        serveur.addVehicule(v);
    }
    
    public void addVille(Ville v) throws Exception {
        serveur.addVille(v);
    }

    public Ville createVille(String ville) throws Exception {
    	Ville v = new Ville(ville, getVilleNewIdentifiant());
    	serveur.addVille(v);
		return v;
    }
    
    
    /**
     * Ajouter un trajet à la liste des trajets
     * 
     * @param v
     *            le trajet à ajouter
     * @throws Exception
     */
    protected void addTrajet(Trajet v) throws Exception {
        serveur.addTrajet(v);
    }

    /**
     * Supprimer un véhicule de la liste des véhicules
     * 
     * @param v
     *            le véhicule à supprimer
     */
    protected void removeVehicule(Vehicule v) {
        serveur.removeVehicule(v);
    }

    /**
     * Supprimer un trajet de la liste des trajets
     * 
     * @param v
     *            le trajet à supprimer
     */
    protected void removeTrajet(Trajet v) {
        serveur.removeTrajet(v);
    }

    protected void removeVille(Ville v) {
        serveur.removeVille(v);
    }
    
    /**
     * Consulter la liste des véhicules
     */
    protected void consulterVehicules() {
        serveur.consulterVehicule();
    }

    /**
     * Consulter la liste des trajets
     */
    protected void consulterTrajet() {
        serveur.consulterTrajet();
    }
    
    protected void consulterVille() {
        serveur.consulterVille();
    }

    /**
     * Modifier un véhicule se trouvant dans la liste des véhicules
     * 
     * @param vehicule
     *            le véhicule à modifier
     * @param vehicule2
     *            le véhicule remplaçant celui qui est à modifier
     * @throws Exception
     */
    protected void modifierVehicule(Vehicule vehicule, Vehicule vehicule2)
            throws Exception {
        serveur.modifierVehicule(vehicule, vehicule2);
    }

    /**
     * Modifier un trajet se trouvant dans la liste des trajets
     * 
     * @param trajet
     *            le trajet à modifier
     * @param trajet2
     *            le trajet remplaçant celui qui est à modifier
     * @throws Exception
     */
    protected void modifierTrajet(Trajet trajet, Trajet trajet2)
            throws Exception {
        serveur.modifierTrajet(trajet, trajet2);
    }
    
    protected void modifierVille(Ville ville, Ville ville2)
            throws Exception {
        serveur.modifierVille(ville, ville2);
    }

    /**
     * Lance la sauvegarde des listes de trajet et de véhicule sur le serveur
     * 
     * @return true si la sauvegarde a réussi, false sinon
     * @throws IOException
     */
    protected boolean lancerSauvegarde() throws IOException {
        return serveur.sauvegarder();
    }

    /**
     * Lance le chargement des listes de trajet et de véhicule du serveur
     * 
     * @return true si le chargement a réussi, false sinon
     * @throws Exception
     */
    protected boolean lancerChargement() throws Exception {
        return serveur.charger();
    }

    /**
     * Réserver un trajet existant dans la liste des trajets
     * 
     * @param t
     *            le trajet à réserver
     * @param placesDemande
     *            le nombre de places à réserver
     * @return true si la réservation a bien eu lieu, false sinon
     */
    protected boolean reserver(Trajet t, int placesDemande) {
        return serveur.reserver(t, placesDemande);
    }

    /**
     * Rechercher des trajets suivant leur lieu de départ, d'arrivée, le moyen
     * de transport (facultatif), le nombre de places voulues, la date de
     * départ, l'intervalle d'acceptation (en heures)
     * 
     * @param depart
     *            le lieu de départ
     * @param arrivee
     *            le lieu d'arrivée
     * @param vehicule
     *            le véhicule choisi
     * @param placesVoulues
     *            le nombre de places voulues
     * @param dateDepart
     *            la date de départ
     * @param intervalleVoulue
     *            l'intervalle d'acceptation voulue (en heures)
     * @return la liste de trajets correspondant aux critères
     */
    protected List<Trajet> rechercherTrajet(Ville depart, Ville arrivee,
            Vehicule vehicule, int placesVoulues, Calendar dateDepart,
            int intervalleVoulue) {
        return serveur.rechercherTrajet(depart, arrivee, vehicule,
                placesVoulues, dateDepart, intervalleVoulue);
    }
}
