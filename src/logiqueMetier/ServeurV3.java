package logiqueMetier;

/**
 *Classe qui permet de créer, modifier et lire les fichiers xml
 *Elle créer aussi les pages au format HTML
 *@author 
 *@version 1.0
 */
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.io.FileOutputStream;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.filter.*;
import org.jdom.Attribute;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import objets.*;

/**
 * Cette classe permet de sauvegarder toute les données que se soit pour les
 * villes, les vehicules, les trajets ou les reservations dans un fichier de
 * type xml. Elle permet de aussi de restituer toutes ces données dans les des
 * listes ce qui permet de modifier les données puis de les re-écrire dans le
 * fichiers xml correspondant.
 * 
 * @author Tanguy Arnaud / Ceschel Marvin / Kruck Nathan / Fauvel-Jaeger Olivier
 * @Version 3
 */
public class ServeurV3 extends Serveur {
    public ServeurV3() {
        super();
    }
    /**
     * On charge les fichier xml dans la mémoire afin de pouvoir y accéder
     * lorsque l'on veut sauvegarder une donnée.
     */
    org.jdom.Document documentVilles = getDoc("MesVilles.xml");
    Element racineVilles = getElem(documentVilles);
    org.jdom.Document documentVehicules = getDoc("MesVehicules.xml");
    Element racineVehicules = getElem(documentVehicules);
    org.jdom.Document documentTrajets = getDoc("MesTrajets.xml");
    Element racineTrajets = getElem(documentTrajets);
    org.jdom.Document documentReservations = getDoc("MesReservations.xml");
    Element racineReservations = getElem(documentReservations);

    /**
     * Renvoie un Element construit a partir du fichier a l'adresse donné.
     * 
     * @param fichier
     *            Adresse du fichier xml a lire.
     * @return L'element contenu dans le fichier xml.
     * @throws JDOMException
     *             Si le fichier ne contient pas d'inforamtions xml.
     * @throws IOException
     *             Si le fichier est inaccessible.
     */
    public Element getElem(org.jdom.Document document) {
        try {
            Element racine = document.getRootElement();
            return racine;
        } catch (Exception e) {
            System.out.println("erreur" + e.getMessage());
            return null;
        }
    }

    public org.jdom.Document getDoc(String fichier) {
        try {
            SAXBuilder sxb = new SAXBuilder();
            org.jdom.Document document = sxb.build(new File(fichier));
            return document;
        } catch (Exception e) {
            System.out.println("erreur" + e.getMessage());
            return null;
        }
    }
    /**
     * Cette méthode permet d'enregistrer les villes dans le fichier xml
     * "MesVilles" La méthode parcourt la liste mesVilles créee dans la classe
     * serveur cette liste contient directement les données rentrer par
     * l'utilisateur.
     */
    public void enregistreVilles() {
        Ville ville;
        racineVilles.removeContent();
        for (int i = 0; i < super.mesVilles.size(); i++) {
            ville = super.getVille(i);
            Element bal = new Element("ville");

            racineVilles.addContent(bal);

            Attribute classeville = new Attribute("id", String.valueOf(ville
                    .getIdentifiant()));
            bal.setAttribute(classeville);

            Attribute classeville2 = new Attribute("Nom", ville.getVille());
            bal.setAttribute(classeville2);
        }
        enregistreXml("MesVilles.xml", documentVilles);
    }

    /**
     * Cette méthode permet d'enregistrer les vehicules dans le fichier xml
     * "MesVéhicules" La méthode parcourt la liste mesVilles créee dans la
     * classe serveur cette liste contient directement les données rentrer par
     * l'utilisateur.
     */
    public void enregistreVehicules() {
        Vehicule vehicule;
        racineVehicules.removeContent();
        for (int i = 0; i < super.mesVehicules.size(); i++) {
            vehicule = super.getVehicule(i);
            Element bal = new Element("vehicule");
            racineVehicules.addContent(bal);

            Attribute classevehicule = new Attribute("id",
                    String.valueOf(vehicule.getIdentifiant()));
            bal.setAttribute(classevehicule);

            Attribute classevehicule2 = new Attribute("Nom",
                    vehicule.getVehicule());
            bal.setAttribute(classevehicule2);

            Element typeVehicule = new Element("typeDuVehicule");
            String typeDuVehicule = "" + vehicule.getType();
            typeVehicule.setText(typeDuVehicule);
            bal.addContent(typeVehicule);

            Element nombresPlaces = new Element("nombreDePlaces");
            nombresPlaces.setText(String.valueOf(vehicule.getCapacite()));
            bal.addContent(nombresPlaces);
        }
        enregistreXml("MesVehicules.xml", documentVehicules);
    }

    /**
     * Cette méthode permet d'enregistrer les trajets dans le fichier xml
     * "MesTrajets" La méthode parcourt la liste mesVilles créee dans la classe
     * serveur cette liste contient directement les données rentrer par
     * l'utilisateur.
     */
    public void enregistreTrajets() {
        Trajet trajet;
        racineTrajets.removeContent();
        for (int i = 0; i < super.mesTrajets.size(); i++) {
            trajet = super.getTrajet(i);
            Element bal = new Element("trajet");
            racineTrajets.addContent(bal);

            Attribute classetrajet = new Attribute("id", String.valueOf(trajet
                    .getIdentifiant()));
            bal.setAttribute(classetrajet);

            Element dateDepart = new Element("dateDepart");
            Calendar dateDeDepart = Calendar.getInstance();
            dateDeDepart = trajet.getDateDepart();
            dateDepart.setText(super.calendarToDate(dateDeDepart));
            bal.addContent(dateDepart);

            Element horaireDepart = new Element("horaireDepart");
            horaireDepart.setText(super.calendarToTime(dateDeDepart));
            bal.addContent(horaireDepart);

            Element dateArrivee = new Element("dateArrivee");
            Calendar dateDarrivee = Calendar.getInstance();
            dateDarrivee = trajet.getDateArrivee();
            dateArrivee.setText(super.calendarToDate(dateDarrivee));
            bal.addContent(dateArrivee);

            Element horaireArrivee = new Element("horaireArrivee");
            horaireArrivee.setText(super.calendarToTime(dateDarrivee));
            bal.addContent(horaireArrivee);

            Ville ville = trajet.getDepart();
            Element idDepart = new Element("idVilleDepart");
            idDepart.setText(String.valueOf(ville.getIdentifiant()));
            bal.addContent(idDepart);

            Ville villeAr = trajet.getArrivee();
            Element idArrivee = new Element("idVilleArrivee");
            idArrivee.setText(String.valueOf(villeAr.getIdentifiant()));
            bal.addContent(idArrivee);

            Element distance = new Element("distance");
            distance.setText(String.valueOf(trajet.getDistance()));
            bal.addContent(distance);

            Vehicule vehiculeDeTransport = trajet.getVehicule();
            Element idVehicule = new Element("idVehicule");
            idVehicule.setText(String.valueOf(vehiculeDeTransport
                    .getIdentifiant()));
            bal.addContent(idVehicule);

            Element placesRestantes = new Element("placesRestantes");
            placesRestantes
                    .setText(String.valueOf(trajet.getPlacesRestantes()));
            bal.addContent(placesRestantes);

            Element premiereClasse = new Element("premiereClasse");
            premiereClasse.setText(String.valueOf(trajet.isPremiereClasse()));
            bal.addContent(premiereClasse);
        }
        enregistreXml("MesTrajets.xml", documentTrajets);
    }

    /**
     * Cette méthode permet d'enregistrer les reservations dans le fichier xml
     * "MesReservations" La méthode parcourt la liste mesVilles créee dans la
     * classe serveur cette liste contient directement les données rentrer par
     * l'utilisateur.
     */
    public void enregistreReservations() {
        Reservation reservation;
        racineReservations.removeContent();
        for (int i = 0; i < super.mesReservations.size(); i++) {
            reservation = super.getReservation(i);
            Element bal = new Element("reservation");
            racineReservations.addContent(bal);

            Attribute classetrajet = new Attribute("id",
                    String.valueOf(reservation.getIdentifiant()));
            bal.setAttribute(classetrajet);

            Passager passager = reservation.getPassager();
            Element nomPassager = new Element("nomPassager");
            nomPassager.setText(passager.getNom());
            bal.addContent(nomPassager);

            Element prenomPassager = new Element("prenomPassager");
            prenomPassager.setText(passager.getPrenom());
            bal.addContent(prenomPassager);

            Calendar dateDeNaissance = Calendar.getInstance();
            dateDeNaissance = passager.getDateNaissance();
            Element dateNaissance = new Element("dateNaissance");
            dateNaissance.setText(calendarToDate(dateDeNaissance));
            bal.addContent(dateNaissance);

            Profil profil = passager.getProfil();
            Element profilPassager = new Element("profil");
            profilPassager.setText(profil.getProfil());
            bal.addContent(profilPassager);

            Element fidelite = new Element("fidelite");
            fidelite.setText(String.valueOf(passager.getFidelite()));
            bal.addContent(fidelite);

            Trajet trajet = reservation.getTrajet();
            Element idTrajet = new Element("idTrajet");
            idTrajet.setText(String.valueOf(trajet.getIdentifiant()));
            bal.addContent(idTrajet);

            Element modifiable = new Element("modifiable");
            modifiable.setText(String.valueOf(reservation.isModifiable()));
            bal.addContent(modifiable);

            Element placesVoulues = new Element("placesVoulues");
            placesVoulues
                    .setText(String.valueOf(reservation.getPlacesVoulues()));
            bal.addContent(placesVoulues);

            Element prendCouchette = new Element("prendCouchette");
            prendCouchette.setText(String.valueOf(reservation
                    .isPrendCouchette()));
            bal.addContent(prendCouchette);

            for (Repas repas : reservation.getTrajet().getVehicule().getRepas()) {
                Element prendRepas = new Element(repas.getNom());
                prendRepas.setText(String.valueOf(reservation.getRepas(repas
                        .getNom())));
                bal.addContent(prendRepas);
            }
        }
        enregistreXml("MesReservations.xml", documentReservations);
    }

    /**
     * Cette méthode parcourt le fichier xml element après élément afin de
     * récuperer toutes les informations nécessaires pour re-créer une liste
     * contenant toutes les villes enregistrée par l'utilisateur.
     * 
     * @throws Exception
     */
    public void chargerVilles() throws Exception {
        List liste = racineVilles.getChildren("ville");
        // On crée un Iterator sur notre liste
        Iterator i = liste.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            String id = courant.getAttributeValue("id");
            String nom = courant.getAttributeValue("Nom");
            addVille(new Ville(nom, Integer.valueOf(id)));
        }
    }

    /**
     * Cette méthode parcourt le fichier xml element après élément afin de
     * récuperer toutes les informations nécessaires pour re-créer une liste
     * contenant tous les vehicules enregistrée par l'utilisateur.
     * 
     * @throws Exception
     */
    public void chargerVehicules() throws Exception {
        List liste = racineVehicules.getChildren("vehicule");
        // On crée un Iterator sur notre liste
        Iterator i = liste.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            String id = courant.getAttributeValue("id");
            String nom = courant.getAttributeValue("Nom");
            String type = courant.getChild("typeDuVehicule").getText();
            String nbPlaces = courant.getChild("nombreDePlaces").getText();
            if (TypeVehicule.valueOf(type) == TypeVehicule.AVION) {
                addVehicule(new Avion(nom, Integer.valueOf(nbPlaces),
                        Integer.valueOf(id)));
            } else if (TypeVehicule.valueOf(type) == TypeVehicule.BATEAU) {
                addVehicule(new Bateau(nom, Integer.valueOf(nbPlaces),
                        Integer.valueOf(id)));
            } else if (TypeVehicule.valueOf(type) == TypeVehicule.BUS) {
                addVehicule(new Bus(nom, Integer.valueOf(nbPlaces),
                        Integer.valueOf(id)));
            } else if (TypeVehicule.valueOf(type) == TypeVehicule.TRAIN) {
                addVehicule(new Train(nom, Integer.valueOf(nbPlaces),
                        Integer.valueOf(id)));
            }
        }
    }

    /**
     * Cette méthode parcourt le fichier xml element après élément afin de
     * récuperer toutes les informations nécessaires pour re-créer une liste
     * contenant tous les trajets enregistrée par l'utilisateur.
     * 
     * @throws Exception
     */
    public void chargerTrajets() throws Exception {
        List liste = racineTrajets.getChildren("trajet");
        // On crée un Iterator sur notre liste
        Iterator i = liste.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            String id = courant.getAttributeValue("id");
            String dateDeDepart = courant.getChild("dateDepart").getText();
            String horaireDeDepart = courant.getChildText("horaireDepart");
            String dateDArrivee = courant.getChild("dateArrivee").getText();
            String horaireDArrivee = courant.getChild("horaireArrivee")
                    .getText();
            String idVilleDeDepart = courant.getChild("idVilleDepart")
                    .getText();
            String idVilleDArrivee = courant.getChild("idVilleArrivee")
                    .getText();
            String distance = courant.getChild("distance").getText();
            String idDuVehicule = courant.getChild("idVehicule").getText();
            String nbPlacesRestantes = courant.getChild("placesRestantes")
                    .getText();
            String premiereClasse = courant.getChild("premiereClasse")
                    .getText();

            Calendar dateDepart = Calendar.getInstance();
            dateDepart = textToCalendar(dateDeDepart, horaireDeDepart);
            Calendar dateArrivee = Calendar.getInstance();
            dateArrivee = textToCalendar(dateDArrivee, horaireDArrivee);
            Ville villeDepart = getVille(Integer.valueOf(idVilleDeDepart));
            Ville villeDArrivee = getVille(Integer.valueOf(idVilleDArrivee));
            Vehicule vehicule = getVehicule(Integer.valueOf(idDuVehicule));
            addTrajet(new Trajet(dateDepart, dateArrivee, villeDepart,
                    villeDArrivee, Integer.valueOf(distance), vehicule,
                    Integer.valueOf(id), Integer.valueOf(nbPlacesRestantes),
                    Boolean.valueOf(premiereClasse)));

        }
    }

    /**
     * Cette méthode parcourt le fichier xml element après élément afin de
     * récuperer toutes les informations nécessaires pour re-créer une liste
     * contenant toutes les reservations effectuée par l'utilisateur.
     * 
     * @throws Exception
     */
    public void chargerReservations() throws Exception {
        List liste = racineReservations.getChildren("reservation");
        // On crée un Iterator sur notre liste
        Iterator i = liste.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            String id = courant.getAttributeValue("id");
            String nomPassager = courant.getChild("nomPassager").getText();
            String prenomPassager = courant.getChild("prenomPassager").getText();
            String dateNaissance = courant.getChild("dateNaissance").getText();
            String profilPassager = courant.getChild("profil").getText();
            String fidelite = courant.getChild("fidelite").getText();
            String placesVoulues = courant.getChild("placesVoulues").getText();
            String idTrajet = courant.getChild("idTrajet").getText();
            String modifiable = courant.getChild("modifiable").getText();
            String prendCouchette = courant.getChild("prendCouchette").getText();
            Profil profil = null;
            for (Profil value : Profil.values()) {
                if (profilPassager.equals(value.getProfil())) {
                    profil = value;
                }
            }
            Calendar dateNaissancePassager = Calendar.getInstance();
            dateNaissancePassager = textToCalendar(dateNaissance, "00:00");
            Passager passager = new Passager(nomPassager, prenomPassager,
                    dateNaissancePassager, profil, Boolean.valueOf(fidelite));
            Trajet trajet = getTrajet(Integer.valueOf(idTrajet));

            Map<String, Boolean> prendRepas = new HashMap<String, Boolean>();
            for (Repas repas : trajet.getVehicule().getRepas()) {
                prendRepas.put(repas.getNom(), Boolean.valueOf(courant.getChild(repas.getNom()).getText()));
            }

             super.addReservation(new Reservation(passager, trajet,
            Boolean.valueOf(modifiable), Boolean.valueOf(prendCouchette),
             prendRepas, Integer.valueOf(id),
             Integer.valueOf(placesVoulues)));
        }
    }
    /**
     * Méthode général qui permet d'enregistrer les fichiers xml dans le bon
     * ficher correspondant
     * 
     * @param fichier
     * @param doc
     */
    void enregistreXml(String fichier, org.jdom.Document doc) {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(doc, new FileOutputStream(fichier));
        } catch (java.io.IOException e) {
        }
    }

    /**
     * Lance la sauvegarde des listes de trajet et de véhicule sur le serveur
     * Méthode abstraite implémentée par ServeurV1 ou ServeurV2
     * 
     * @return true si la sauvegarde a réussi, false sinon
     * @throws IOException
     */
    public boolean sauvegarder() throws IOException {
        enregistreVehicules();
        enregistreVilles();
        enregistreTrajets();
        enregistreReservations();
        return true;
    }

    /**
     * Lance le chargement des listes de trajet et de véhicule du serveur
     * Méthode abstraite implémentée par ServeurV1 ou ServeurV2
     * 
     * @return true si le chargement a réussi, false sinon
     * @throws Exception
     */
    public boolean charger() throws Exception {
        chargerVilles();
        chargerVehicules();
        chargerTrajets();
        chargerReservations();

        return true;
    }
}