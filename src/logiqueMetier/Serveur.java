package logiqueMetier;

import java.io.*;
import java.util.*;

import objets.Trajet;

import objets.*;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun. Cette classe contient la liste des véhicules
 * et des trajets. Elle permet de gérer ces listes, de charger les données à
 * partir de fichier externe de données, de sauvegarder les données sur ces
 * fichiers.
 * 
 * Cette classe est abstraite, car deux classes héritent d'elle pour implémenter
 * les méthodes sauvegarder et charger.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public abstract class Serveur implements Serializable {
    private static final long serialVersionUID = 1L;
   
    // liste des trajets et des véhicules
    protected ArrayList<Trajet> mesTrajets;
    protected ArrayList<Vehicule> mesVehicules;
    protected ArrayList<Ville> mesVilles;
    protected ArrayList<Reservation> mesReservations;

    /**
     * Créer une instance de serveur
     */
    public Serveur() {
        mesTrajets = new ArrayList<Trajet>();
        mesVehicules = new ArrayList<Vehicule>();
        mesVilles = new ArrayList<Ville>();
        mesReservations = new ArrayList<Reservation>();
    }

    /**
     * Transforme un String date(jj/mm/aaaa) et un String horaire(hh:mm) en
     * Calendar
     * 
     * @param date
     *            la date sous la forme jj/mm/aaaa
     * @param horaire
     *            l'horaire sous la forme hh:mm
     * @return la date comme un objet de type Calendar
     */
    static public Calendar textToCalendar(String date, String horaire) {
        String[] dateSplitted = date.split("/");
        String[] horaireSplitted = horaire.split(":");

        if (dateSplitted.length == 3 && horaireSplitted.length == 2) {
            Calendar dateComplete = Calendar.getInstance();
            dateComplete
                    .set(Calendar.DATE, Integer.valueOf(date.split("/")[0]));
            dateComplete.set(Calendar.MONTH,
                    Integer.valueOf(date.split("/")[1]) - 1);
            dateComplete
                    .set(Calendar.YEAR, Integer.valueOf(date.split("/")[2]));
            dateComplete.set(Calendar.HOUR_OF_DAY,
                    Integer.valueOf(horaire.split(":")[0]));
            dateComplete.set(Calendar.MINUTE,
                    Integer.valueOf(horaire.split(":")[1]));
            return dateComplete;
        } else
            return null;
    }

    /**
     * Retourne la date (jj/mm/aaaa) à partir d'un Calendar
     * 
     * @param c
     *            la calendar à transformer
     * @return la date sous la forme jj/mm/aaaa
     */
    static public String calendarToDate(Calendar c) {
        return new StringBuffer().append(String.valueOf(c.get(Calendar.DATE)))
                .append("/").append(String.valueOf(c.get(Calendar.MONTH) + 1))
                .append("/").append(String.valueOf(c.get(Calendar.YEAR)))
                .toString();
    }

    /**
     * Retourne l'heure (hh:mm) à partir d'un Calendar
     * 
     * @param c
     *            la calendar à transformer
     * @return l'heure sous la forme hh:mm
     */
    static public String calendarToTime(Calendar c) {
        return new StringBuffer()
                .append(String.valueOf(c.get(Calendar.HOUR_OF_DAY)))
                .append(":").append(String.valueOf(c.get(Calendar.MINUTE)))
                .toString();
    }

    /**
     * Récupère un nouvel identifiant pour un véhicule qui va être créé
     * 
     * @return le nouvel identifiant
     */
    public int getVehiculeNewIdentifiant() {
        int i = 0;

        if (mesVehicules.size() != 0) {
            for (Vehicule v : mesVehicules) {
                if (v.getIdentifiant() > i)
                    i = v.getIdentifiant();
            }
            i++;
        }

        return i;
    }

    public int getVilleNewIdentifiant() {
        int i = 0;

        if (mesVilles.size() != 0) {
            for (Ville v : mesVilles) {
                if (v.getIdentifiant() > i)
                    i = v.getIdentifiant();
            }
            i++;
        }

        return i;
    }

    /**
     * Récupère un nouvel identifiant pour un trajet qui va être créé
     * 
     * @return le nouvel identifiant
     */
    public int getTrajetNewIdentifiant() {
        int i = 0;
        if (mesTrajets.size() != 0) {
            for (Trajet t : mesTrajets) {
                if (t.getIdentifiant() > i)
                    i = t.getIdentifiant();
            }
            i++;
        }
        return i;
    }

    public int getReservationNewIdentifiant() {
        int i = 0;
        if (mesReservations.size() != 0) {
            for (Reservation r : mesReservations) {
                if (r.getIdentifiant() > i)
                    i = r.getIdentifiant();
            }
            i++;
        }
        return i;
    }

    /**
     * Récupère un véhicule grâce à son identifiant
     * 
     * @param id
     *            l'identifiant du véhicule à récupérer
     * @return le véhicule trouvé
     */
    public Vehicule getVehicule(int id) {
        for (Vehicule v : mesVehicules) {
            if (v.getIdentifiant() == id)
                return v;
        }
        return null;
    }

    public Ville getVille(int id) {
        for (Ville v : mesVilles) {
            if (v.getIdentifiant() == id)
                return v;
        }
        return null;
    }

    public Reservation getReservation(int id) {
        for (Reservation r : mesReservations) {
            if (r.getIdentifiant() == id)
                return r;
        }
        return null;
    }

    /**
     * Récupère un trajet grâce à son identifiant
     * 
     * @param id
     *            l'identifiant du trajet à récupérer
     * @return le trajet trouvé
     */
    public Trajet getTrajet(int id) {
        for (Trajet t : mesTrajets) {
            if (t.getIdentifiant() == id)
                return t;
        }
        return null;
    }

    /**
     * Consulter la liste des véhicules
     */
    public void consulterVehicule() {
        System.out.println("Liste des Vehicules : ");
        for (int i = 0; i < this.mesVehicules.size(); i++) {
            System.out.println(mesVehicules.get(i).toString());
        }
    }

    public void consulterVille() {
        System.out.println("Liste des Villes : ");
        for (int i = 0; i < this.mesVilles.size(); i++) {
            System.out.println(mesVilles.get(i).toStringC());
        }
    }

    public void consulterReservation() {
        System.out.println("Liste des Réservations : ");
        for (int i = 0; i < this.mesReservations.size(); i++) {
            System.out.println(mesReservations.get(i).toString());
        }
    }

    /**
     * Consulter la liste des trajets
     */
    public void consulterTrajet() {
        System.out.println("Liste des Trajets : ");
        for (int i = 0; i < this.mesTrajets.size(); i++) {
            System.out.println(mesTrajets.get(i).toString());
        }
    }

    /**
     * Vérifie s'il y a un conflit au niveau des horaires entre le trajet en
     * paramètre et les autres trajets de la liste (si le trajet prend le même
     * véhicule qu'un autre trajet)
     * 
     * @param trajet
     *            trajet à vérifier
     * @return true si il y a un conflit, false sinon
     */
    public boolean checkConflict(Trajet trajet) {
        boolean check = false;
        for (Trajet t : mesTrajets) {
            if (t.getVehicule().getIdentifiant() == trajet.getVehicule()
                    .getIdentifiant()
                    && t.getIdentifiant() != trajet.getIdentifiant()) {
                if ((trajet.getDateDepart().after(t.getDateDepart()) && trajet
                        .getDateDepart().before(t.getDateArrivee()))
                        || (trajet.getDateArrivee().after(t.getDateDepart()) && trajet
                                .getDateArrivee().before(t.getDateArrivee()))
                        || t.getDateDepart().equals(trajet.getDateDepart())
                        || t.getDateDepart().equals(trajet.getDateArrivee())
                        || t.getDateArrivee().equals(trajet.getDateDepart())
                        || t.getDateArrivee().equals(trajet.getDateArrivee())) {
                    check = true;
                }
            }
        }
        return check;
    }

    /**
     * Vérifie que pour le trajet en paramètre, la date d'arrivée est après la
     * date de départ
     * 
     * @param trajet
     *            le trajet qu'on veut vérifier
     * @return true si la date d'arrivée est après la date de départ, false
     *         sinon
     */
    public boolean checkOrdre(Trajet trajet) {
        return trajet.getDateArrivee().after(trajet.getDateDepart())
                && !trajet.getDateArrivee().equals(trajet.getDateDepart());
    }

    /**
     * Ajouter un véhicule à la liste des véhicules
     * 
     * @param v
     *            le véhicule à ajouter
     * @throws Exception
     */
    public void addVehicule(Vehicule v) throws Exception {
        if (!mesVehicules.contains(v)) {
            mesVehicules.add(v);
        } else
            throw new Exception(
                    "Ce vehicule appartient deja à la liste des véhicules.");
    }

    /**
     * Créé un véhicule. Attention, ne l'ajoute pas aux listes de véhicules,
     * appeler addVehicule pour ça.
     * 
     * @param nom
     * @param type
     * @param capacite
     * @return
     * @throws Exception
     */
    public Vehicule creerVehicule(String nom, TypeVehicule type, int capacite)
            throws Exception {
        Vehicule v = null;
        switch (type) {
        case AVION:
            v = new Avion(nom, capacite, getVehiculeNewIdentifiant());
            break;
        case BATEAU:
            v = new Bateau(nom, capacite, getVehiculeNewIdentifiant());
            break;
        case BUS:
            v = new Bus(nom, capacite, getVehiculeNewIdentifiant());
            break;
        case TRAIN:
            v = new Train(nom, capacite, getVehiculeNewIdentifiant());
            break;
        }
        return v;
    }

    public void addVille(Ville v) throws Exception {
        if (!mesVilles.contains(v)) {
            mesVilles.add(v);
        } else
            throw new Exception(
                    "Cette ville appartient deja à la liste des villes.");
    }

    public void addReservation(Reservation r) throws Exception {
        if (!mesReservations.contains(r)) {
            mesReservations.add(r);
        } else
            throw new Exception(
                    "Cette réservation appartient deja à la liste des réservations.");
    }

    public Ville createVille(String ville) throws Exception {
        Ville v = new Ville(ville, getVilleNewIdentifiant());
        try {
            addVille(v);
        } catch (Exception e) {
            return null;
        }
        return v;
    }

    /**
     * Renvoit la liste des villes disponibles
     * 
     * @return Liste de villes
     */
    public ArrayList<Ville> getVilles() {
        return mesVilles;
    }

    /**
     * Renvoit la liste des villes accessibles depuis une ville de départ donnée
     * 
     * @param depart
     *            Ville de départ.
     * @return arrivee Liste des villes accessibles depuis la ville de départ
     *         donnée
     */
    public ArrayList<Ville> getVillesArrivee(Ville depart) {
        ArrayList<Ville> arrivee = new ArrayList<Ville>();
        for (Trajet t : mesTrajets) {
            if (t.getDepart().equals(depart)) {
                arrivee.add(t.getArrivee());
            }
        }
        return arrivee;
    }

    /**
     * Ajouter un trajet à la liste des trajets
     * 
     * @param t
     *            le trajet à ajouter
     * @throws Exception
     */
    public void addTrajet(Trajet t) throws Exception {
        if (!mesTrajets.contains(t) && !checkConflict(t) && checkOrdre(t)) {
            mesTrajets.add(t);
        } else if (!checkOrdre(t)) {
            throw new Exception(
                    "Le date d'arrivée doit être strictement supérieure à la date de départ !");
        } else if (checkConflict(t)) {
            throw new Exception(
                    "Ce véhicule ne peut pas faire deux trajets dans le même intervalle de temps !");
        } else
            throw new Exception(
                    "Ce trajet fait deja partie de la liste des trajets.");
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
    public void modifierVehicule(Vehicule vehicule, Vehicule vehicule2)
            throws Exception {
        if (mesVehicules.contains(vehicule)) {
            mesVehicules.set(mesVehicules.indexOf(vehicule), vehicule2);
        } else
            throw new Exception(
                    "Ce véhicule ne fait pas partie de la base de données.");
    }

    public void modifierVille(Ville ville, Ville ville2) throws Exception {
        if (mesVilles.contains(ville)) {
            mesVilles.set(mesVilles.indexOf(ville), ville2);
        } else
            throw new Exception(
                    "Cette ville ne fait pas partie de la base de données.");
    }

    public void modifierReservation(Reservation reservation,
            Reservation reservation2) throws Exception {
        if (mesReservations.contains(reservation)) {
            mesReservations.set(mesReservations.indexOf(reservation),
                    reservation2);
        } else
            throw new Exception(
                    "Cette réservation ne fait pas partie de la base de données.");
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
    public void modifierTrajet(Trajet trajet, Trajet trajet2) throws Exception {
        if (mesTrajets.contains(trajet) && !checkConflict(trajet2)
                && checkOrdre(trajet2)) {
            mesTrajets.set(mesTrajets.indexOf(trajet), trajet2);
        } else if (!checkOrdre(trajet2)) {
            throw new Exception(
                    "Le date d'arrivée doit être strictement supérieure à la date de départ !");
        } else if (checkConflict(trajet2)) {
            throw new Exception(
                    "Ce véhicule ne peut pas faire deux trajets dans le même intervalle de temps !");
        } else {
            throw new Exception(
                    "Ce trajet ne fait pas partie de la base de données");
        }
    }

    /**
     * Supprimer un véhicule de la liste des véhicules
     * 
     * @param v
     *            le véhicule à supprimer
     */
    public void removeVehicule(Vehicule v) {
        if (mesVehicules.contains(v)) {
            for (int i = 0; i < mesTrajets.size(); i++) {
                if (mesTrajets.get(i).getVehicule() == v) {
                    mesTrajets.remove(i);
                    i--;
                }
            }
            mesVehicules.remove(v);
        }
    }

    /**
     * Supprimer un véhicule de la liste des véhicules
     * 
     * @param id
     *            Identifiant du véhicule à supprimer
     */
    public void removeVehicule(int id) {
        for(int i=0; i<mesVehicules.size(); i++) {
            Vehicule v = mesVehicules.get(i);
            if (v.getIdentifiant() == id) {
                for (int j = 0; j < mesTrajets.size(); j++) {
                    if (mesTrajets.get(j).getVehicule() == v) {
                        mesTrajets.remove(j);
                        j--;
                    }
                }
                mesVehicules.remove(v);
            }
        }
    }

    /**
     * Supprimer un trajet de la liste des trajets
     * 
     * @param t
     *            le trajet à supprimer
     */
    public void removeTrajet(Trajet t) {
        if (mesTrajets.contains(t))
            mesTrajets.remove(t);
    }

    public void removeVille(Ville v) {
        if (mesVilles.contains(v))
            mesVilles.remove(v);
    }

    public void removeReservation(Reservation r) {
        if (mesReservations.contains(r))
            mesReservations.remove(r);
    }

    /**
     * Lance la sauvegarde des listes de trajet et de véhicule sur le serveur
     * Méthode abstraite implémentée par ServeurV1 ou ServeurV2
     * 
     * @return true si la sauvegarde a réussi, false sinon
     * @throws IOException
     */
    public abstract boolean sauvegarder() throws IOException;

    /**
     * Lance le chargement des listes de trajet et de véhicule du serveur
     * Méthode abstraite implémentée par ServeurV1 ou ServeurV2
     * 
     * @return true si le chargement a réussi, false sinon
     * @throws Exception
     */
    public abstract boolean charger() throws Exception;

    /**
     * Réserver un trajet existant dans la liste des trajets
     * 
     * @param t
     *            le trajet à réserver
     * @param placesDemande
     *            le nombre de places à réserver
     * @return true si la réservation a bien eu lieu, false sinon
     */
    public boolean reserver(Trajet t, int placesDemande) {
        if (t.restePlaces(placesDemande)) {
            t.decPlacesRestantes(placesDemande);
            return true;
        } else
            return false;
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
    public List<Trajet> rechercherTrajet(Ville depart, Ville arrivee,
            TypeVehicule vehicule, int placesVoulues, Calendar dateDepart,
            int intervalleVoulue, boolean avecCouchette,
            boolean premiereClasse, boolean direct) {
        List<Trajet> trajetsConvenables = new ArrayList<Trajet>();

        if (direct) {
System.out.println("direct");
        	for (int i = 0; i < mesTrajets.size(); i++) {
                Calendar departRetard = (Calendar) mesTrajets.get(i)
                        .getDateDepart().clone();
                departRetard.add(Calendar.HOUR, intervalleVoulue);
                Calendar departAvance = (Calendar) mesTrajets.get(i)
                        .getDateDepart().clone();
                departAvance.add(Calendar.HOUR, -intervalleVoulue);

                if (mesTrajets.get(i).getDepart().equals(depart)
                        && mesTrajets.get(i).getArrivee().equals(arrivee)
                        && (vehicule == null || mesTrajets.get(i).getVehicule().getType() == vehicule)
                        && mesTrajets.get(i).restePlaces(placesVoulues)
                        && (!avecCouchette || mesTrajets.get(i).getVehicule()
                                .avecCouchette() == avecCouchette)
                        && mesTrajets.get(i).isPremiereClasse() == premiereClasse
                        && dateDepart.before(departRetard)
                        && dateDepart.after(departAvance)) {
                    trajetsConvenables.add(mesTrajets.get(i));
                }
            }
        } else {
            List<Trajet> listeTrajetsFiltree = new ArrayList<Trajet>();
            for (Trajet trajet : mesTrajets) {
                if ((vehicule == null || trajet.getVehicule().equals(vehicule))
                         && trajet.restePlaces(placesVoulues)
                        && (!avecCouchette || trajet.getVehicule()
                                .avecCouchette() == avecCouchette)
                        && trajet.isPremiereClasse() == premiereClasse) {

                    if (trajet.getDepart().getIdentifiant() != depart
                            .getIdentifiant())
                        listeTrajetsFiltree.add(trajet);
                    else {
                        Calendar departRetard = (Calendar) trajet.getDateDepart()
                                .clone();
                        departRetard.add(Calendar.HOUR, intervalleVoulue);
                        Calendar departAvance = (Calendar) trajet.getDateDepart()
                                .clone();
                        departAvance.add(Calendar.HOUR, -intervalleVoulue);
                        
                        if (dateDepart.before(departRetard)
                                && dateDepart.after(departAvance))
                        {
                            listeTrajetsFiltree.add(trajet);
                        }
                        else
                        {
                            listeTrajetsFiltree.add(null);
                        }
                    }
                }
            }

            Distance d = new Distance(listeTrajetsFiltree, getTrajetNewIdentifiant(),
                    getVilleNewIdentifiant());

            List<Trajet> listeTrajetsChemin = d.cout(depart.getIdentifiant(),
                    arrivee.getIdentifiant());

            if (listeTrajetsChemin != null) {
                for (Trajet trajet : listeTrajetsChemin) {
                    trajetsConvenables.add(trajet);
                }
            }

        }

        // On trie la liste des trajets convenables pour que les trajets
        // apparaissent sous forme chronologique
        Collections.sort(trajetsConvenables);
        return trajetsConvenables;
    }

    public List<Trajet> rechercherTrajetParPrix(Ville depart, Ville arrivee,
            TypeVehicule vehicule, int placesVoulues, Calendar dateDepart,
            int intervalleVoulue, boolean avecCouchette,
            boolean premiereClasse, boolean direct) {
        List<Trajet> list = rechercherTrajet(depart, arrivee, vehicule,
                placesVoulues, dateDepart, intervalleVoulue, avecCouchette,
                premiereClasse, direct);

        Collections.sort(list, new CompareInteger());

        return list;
    }

    /**
     * Section des m�thodes de recherche de trajet
     * 
     */
    /**
     * Recherche trajet en fonction de la ville
     * 
     * @param ville
     * @return
     */
    public List<Trajet> rechercheTrajet(String ville) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getDepart().equals(ville)
                    || mesTrajets.get(i).getArrivee().equals(ville)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville de d�part
     * 
     * @param villeD
     * @return
     */
    public List<Trajet> rechercheTrajetDepart(String villeD) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getDepart().equals(villeD)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville d'arriv�e
     * 
     * @param villeA
     * @return
     */
    public List<Trajet> rechercheTrajetArrivee(String villeA) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getArrivee().equals(villeA)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la date
     * 
     * @param date
     * @return
     */
    public List<Trajet> rechercheTrajetDate(String date) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (calendarToDate(mesTrajets.get(i).getDateDepart()).equals(date)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction du moyen de transport
     * 
     * @param mdt
     * @return
     */
    public List<Trajet> rechercheTrajetTransport(String mdt) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getVehicule().getType().equals(mdt)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville et de la date
     * 
     * @param ville
     * @param date
     * @return
     */
    public List<Trajet> rechercheTrajet(String ville, String date) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if ((mesTrajets.get(i).getArrivee().equals(ville) || mesTrajets
                    .get(i).getDepart().equals(ville))
                    && calendarToDate(mesTrajets.get(i).getDateDepart())
                            .equals(date)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville et du moyen de transport
     * 
     * @param ville
     * @param mdt
     * @return
     */
    public List<Trajet> rechercheTrajetVM(String ville, String mdt) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if ((mesTrajets.get(i).getArrivee().equals(ville) || mesTrajets
                    .get(i).getDepart().equals(ville))
                    && mesTrajets.get(i).getVehicule().getType().equals(mdt)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville de d�part et de la date
     * 
     * @param villeD
     * @param date
     * @return
     */
    public List<Trajet> rechercheTrajetD(String villeD, String date) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getDepart().equals(villeD)
                    && calendarToDate(mesTrajets.get(i).getDateDepart())
                            .equals(date)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville de d�part et du moyen de
     * transport
     * 
     * @param villeD
     * @param mdt
     * @return
     */
    public List<Trajet> rechercheTrajetDepart(String villeD, String mdt) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getDepart().equals(villeD)
                    && mesTrajets.get(i).getVehicule().getType().equals(mdt)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville d'arriv�e et de la date
     * 
     * @param villeA
     * @param date
     * @return
     */
    public List<Trajet> rechercheTrajetA(String villeA, String date) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getArrivee().equals(villeA)
                    && calendarToDate(mesTrajets.get(i).getDateDepart())
                            .equals(date)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville d'arriv�e et du moyen de
     * transport
     * 
     * @param villeA
     * @return
     */
    public List<Trajet> rechercheTrajetArrivee(String villeA, String mdt) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getArrivee().equals(villeA)
                    && mesTrajets.get(i).getVehicule().getType().equals(mdt)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la date et du moyen de transport
     * 
     * @param date
     * @param mdt
     * @return
     */
    public List<Trajet> rechercheTrajetDM(String date, String mdt) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (calendarToDate(mesTrajets.get(i).getDateDepart()).equals(date)
                    && mesTrajets.get(i).getVehicule().getType().equals(mdt)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville, de la date et du moyen de
     * transport
     * 
     * @param ville
     * @param date
     * @param mdt
     * @return
     */
    public List<Trajet> rechercheTrajet(String ville, Date date, String mdt) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if ((mesTrajets.get(i).getArrivee().equals(ville) || mesTrajets
                    .get(i).getDepart().equals(ville))
                    && calendarToDate(mesTrajets.get(i).getDateDepart())
                            .equals(date)
                    && mesTrajets.get(i).getVehicule().getType().equals(mdt)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville de d�part de la date et du moyen
     * de transport
     * 
     * @param villeD
     * @param date
     * @param mdt
     * @return
     */
    public List<Trajet> rechercheTrajetD(String villeD, String date, String mdt) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getDepart().equals(villeD)
                    && calendarToDate(mesTrajets.get(i).getDateDepart())
                            .equals(date)
                    && mesTrajets.get(i).getVehicule().getType().equals(mdt)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville d'arriv�e, de la date et du
     * moyen de transport
     * 
     * @param villeA
     * @param date
     * @param mdt
     * @return
     */
    public List<Trajet> rechercheTrajetA(String villeA, String date, String mdt) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getArrivee().equals(villeA)
                    && calendarToDate(mesTrajets.get(i).getDateDepart())
                            .equals(date)
                    && mesTrajets.get(i).getVehicule().getType().equals(mdt)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville de d�part, d'arriv�e et de la
     * date
     * 
     * @param villeD
     * @param villeA
     * @param date
     * @return
     */
    public List<Trajet> rechercheTrajet(String villeD, String villeA,
            String date) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getDepart().equals(villeD)
                    && mesTrajets.get(i).getArrivee().equals(villeA)
                    && calendarToDate(mesTrajets.get(i).getDateDepart())
                            .equals(date)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    /**
     * Recherche trajet en fonction de la ville de d�part, d'arriv�e, de la date
     * et du moyen de transport
     * 
     * @param villeD
     * @param villeA
     * @param date
     * @param mdt
     * @return
     */
    public List<Trajet> rechercheTrajet(String villeD, String villeA,
            String date, String mdt) {
        List<Trajet> trajets = new ArrayList<Trajet>();
        for (int i = 0; i < mesTrajets.size(); i++) {
            if (mesTrajets.get(i).getDepart().equals(villeD)
                    && mesTrajets.get(i).getArrivee().equals(villeA)
                    && calendarToDate(mesTrajets.get(i).getDateDepart())
                            .equals(date)
                    && mesTrajets.get(i).getVehicule().getType().equals(mdt)) {
                trajets.add(mesTrajets.get(i));
            }
        }
        return trajets;
    }

    public ArrayList<Trajet> getTrajets() {
        return mesTrajets;
    }

    public ArrayList<Vehicule> getVehicules() {
        return mesVehicules;
    }

    public ArrayList<Reservation> getReservations() {
        return mesReservations;
    }
}
