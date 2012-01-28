package logiqueMetier;

import java.io.File;
import java.io.IOException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objets.*;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun. Cette classe est le client d'administration
 * qui permet de gérer en ligne de commande les véhicules et les trajets. Il
 * contient toutes les fonctions pour faire le lien entre les instructions
 * données via la ligne de commande et les fonctions de l'admin.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class ClientAdmin {
    private Parser parser;
    private Serveur s;
    private Admin a;

    /**
     * créer une instance du client d'administration
     * 
     * @param version
     *            la version du serveur choisi
     */
    public ClientAdmin(int version) {
        parser = new Parser(); // créer un parser

        if (version == 1) {
            s = new ServeurV1();
        } else if (version == 2) {
            s = new ServeurV2();
        } else {
            s = new ServeurV3();
        }
        a = new Admin(s); // créer une instance d'admin
    }

    /**
     * Lance le client d'administration
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    protected void launch() throws IOException, ClassNotFoundException,
            Exception {
        boolean finished = false;
        this.printHelp();
        // A chaque tour de boucle, on charge les données du serveur avant tout
        // Quand on a fini de travailler, on sauvegarde les éventuelles
        // modifications
        if (!a.lancerChargement())
            a.lancerSauvegarde();
        while (!finished) {
            Command command = parser.getCommand();
            try {
                finished = processCommand(command);
            } catch (Exception e) {
                System.err.println(e);
            }
            a.lancerSauvegarde();
        }
    }

    /**
     * Exécute une commande donnée
     * 
     * @param command
     *            la commande à exécuter
     * @return true si on veut quitter le client, false sinon
     * @throws Exception
     */
    private boolean processCommand(Command command) throws Exception {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.INCONNU) {
            System.out.println("Mot inconnu...");
            return false;
        }

        // On gère les différentes méthodes possibles
        if (commandWord == CommandWord.AIDE) {
            printHelp();
        } else if (commandWord == CommandWord.CONSULTER) {
            consulter(command);
        } else if (commandWord == CommandWord.AJOUTER) {
            ajouter(command);
        } else if (commandWord == CommandWord.MODIFIER) {
            modifier(command);
        } else if (commandWord == CommandWord.SUPPRIMER) {
            supprimer(command);
        } else if (commandWord == CommandWord.RECHERCHER) {
            rechercher(command);
        } else if (commandWord == CommandWord.RESERVER) {
            reserver(command);
        } else if (commandWord == CommandWord.QUITTER) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    /**
     * "quitter" a été entré. Quitter le client d'admin.
     * 
     * @param command
     *            la commande à exécuter
     * @return true si on a quitter le client, false sinon
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quitter quoi ?");
            return false;
        } else {
            return true; // signal pour dire qu'on veut quitter
        }
    }

    /**
     * "aide" a été entré. Affiche l'aide.
     */
    private void printHelp() {
        System.out.println("Les commandes possibles sont :");
        System.out.print(parser.getCommands());
    }

    /**
     * Consulter les trajets ou les véhicules
     * 
     * @param command
     *            la commande à exécuter
     */
    private void consulter(Command command) {
        if (!command.hasSecondWord()) {
            System.out
                    .println("Consulter quoi ? trajets ou vehicules ou ville ?");
            return;
        }

        String quoi = command.getSecondWord();

        if (quoi.equals("trajets")) {
            a.consulterTrajet();
        } else if (quoi.equals("vehicules")) {
            a.consulterVehicules();
        } else if (quoi.equals("villes")) {
            a.consulterVille();
        } else if (quoi.equals("reservations")) {
            a.consulterReservation();
        } else {
            System.out
                    .println("Vous ne pouvez consulter que des trajets ou des vehicules ou des villes");
        }
    }

    /**
     * Rechercher des trajets
     * 
     * @param command
     *            la commande à exécuter
     * @throws Exception
     */
    private void rechercher(Command command) throws Exception {
        if (!command.hasSecondWord()) {
            System.out.println("Rechercher quoi ? trajets ?");
            return;
        }

        String quoi = command.getSecondWord();

        if (quoi.equals("trajets")) {
            // on demande les infos sur les trajets à rechercher
            int idDepart = 0;
            int idArrivee = 0;
            int idVehicule = 0;
            Vehicule vehicule = null;
            int placesVoulues = 1;
            String dateDepart = "";
            String horaireDepart = "";
            int intervalleVoulue = 12;
            boolean avecCouchette = false;
            boolean premiereClasse = false;
            boolean trierParPrix = false;
            boolean trajetDirect = false;

            // On demande à l'utilisateur toutes les infos qu'on veut
            // puis on vérifie si ce qui a été entré est correct

            System.out.print("Date de départ (jj/mm/aaaa) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                dateDepart = tokenizer.next(); // récupère le premier mot
            }
            if (!Pattern.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}", dateDepart))
                throw new Exception("Date de départ mal écrite");

            System.out.print("Horaire de départ (hh:mm) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                horaireDepart = tokenizer.next(); // récupère le premier mot
            }
            if (!Pattern.matches("[0-9]{1,2}:[0-9]{1,2}", horaireDepart))
                throw new Exception("Heure de départ mal écrite");

            a.consulterVille();
            System.out.print("Lieu de départ (id) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                idDepart = Integer.valueOf(tokenizer.next()); // récupère le
                                                              // premier mot
            }

            Ville depart = a.getVille(idDepart);
            if (depart == null)
                throw new Exception("ville non reconnu");

            a.consulterVille();
            System.out.print("Lieu d'arrivée : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                idArrivee = Integer.valueOf(tokenizer.next()); // récupère le
                                                               // premier mot
            }
            Ville arrivee = a.getVille(idArrivee);
            if (arrivee == null)
                throw new Exception("ville non reconnu");

            a.consulterVehicules();
            System.out.print("Véhicule (id) (défaut=n'importe lequel) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                String textTokenizer = tokenizer.next();

                if (textTokenizer != "") {
                    idVehicule = Integer.valueOf(textTokenizer); // récupère le
                                                                 // premier mot
                    // on récupère le véhicule choisi
                    vehicule = a.getVehicule(idVehicule);
                }
            }

            System.out.print("Places voulues (défaut=1) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                placesVoulues = Integer.valueOf(tokenizer.next()); // récupère
                                                                   // le premier
                                                                   // mot
            }
            if (placesVoulues == 0)
                placesVoulues = 1;

            System.out.print("Qu'avec couchette ? (1=oui,0=non) : ");
            int couchetteString = 0;
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                couchetteString = Integer.valueOf(tokenizer.next()); // récupère
                                                                     // le
                                                                     // premier
                                                                     // mot
            }
            if (couchetteString != 0) {
                avecCouchette = true;
            }

            System.out.print("Que les trajets directs ? (1=oui,0=non) : ");
            int trajetsDirectsString = 0;
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                trajetsDirectsString = Integer.valueOf(tokenizer.next()); // récupère
                // le premier
                // mot
            }
            if (trajetsDirectsString != 0) {
                trajetDirect = true;
            }

            System.out.print("Première classe ? (1=oui,0=non) : ");
            int classeString = 0;
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                classeString = Integer.valueOf(tokenizer.next()); // récupère
                                                                  // le premier
                                                                  // mot
            }
            if (classeString != 0) {
                premiereClasse = true;
            }

            System.out.print("Trier par prix (0=non,1=oui) : ");
            int trierPrix = 0;
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                trierPrix = Integer.valueOf(tokenizer.next()); // récupère
                                                               // le premier
                                                               // mot
            }
            if (trierPrix != 0) {
                trierParPrix = true;
            }
            Calendar dateCompleteDepart = Serveur.textToCalendar(dateDepart,
                    horaireDepart);
            if (dateCompleteDepart == null)
                throw new Exception("date de départ mal écrite");

            List<Trajet> trajetRecherche = null;
            if (!trierParPrix) {
                // on recherche les trajets correspondant aux paramètres rentrés
                trajetRecherche = s.rechercherTrajet(depart, arrivee, vehicule,
                        placesVoulues, dateCompleteDepart, intervalleVoulue,
                        avecCouchette, premiereClasse, trajetDirect);
            } else {
                trajetRecherche = s.rechercherTrajetParPrix(depart, arrivee,
                        vehicule, placesVoulues, dateCompleteDepart,
                        intervalleVoulue, avecCouchette, premiereClasse,
                        trajetDirect);
            }

            // on affiche les trajets correspondant aux paramètres rentrés, s'il
            // y en a
            if (trajetRecherche != null && trajetRecherche.size() != 0) {
                for (Trajet t : trajetRecherche) {
                    System.out.println(t);
                }
            } else
                System.out.println("Aucun trajet ne correspond.");
        } else {
            System.out.println("Vous ne pouvez rechercher que des trajets");
        }
    }

    /**
     * Réserver un trajet
     * 
     * @param command
     *            la commande à exécuter
     * @throws Exception
     */
    private void reserver(Command command) throws Exception {
        if (!command.hasSecondWord()) {
            System.out.println("Réserver quoi ? trajets ?");
            return;
        }

        String quoi = command.getSecondWord();

        if (quoi.equals("trajets")) {
            // on demande les infos sur le trajet à réserver
            Reservation reservation = null;
            Trajet trajetAReserver = null;
            int placesVoulues = 1;
            Passager passager = null;
            String nom = "";
            String prenom = "";
            String dateNaissance = "";
            int profilId = 0;
            Profil profil = null;

            boolean fidelite = false;
            boolean modifiable = false;
            boolean prendCouchette = false;
            Map<String, Boolean> prendRepas = new HashMap<String, Boolean>();

            a.consulterTrajet();

            // On demande à l'utilisateur toutes les infos qu'on veut
            // puis on vérifie si ce qui a été entré est correct
            System.out.print("Trajet à réserver (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                trajetAReserver = a
                        .getTrajet(Integer.valueOf(tokenizer.next())); // récupère
                                                                       // le
                                                                       // premier
                                                                       // mot
            }

            System.out.print("Places voulues (défaut=1) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                placesVoulues = Integer.valueOf(tokenizer.next()); // récupère
                                                                   // le premier
                                                                   // mot
            }
            if (placesVoulues == 0)
                placesVoulues = 1;

            // On regarde si le trajet existe
            if (trajetAReserver != null) {

                // On réserve le trajet que s'il reste des places
                if (trajetAReserver.restePlaces(placesVoulues)) {
                    // choix du passager
                    System.out.print("Nom du passager : ");
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    if (tokenizer.hasNext()) {
                        nom = tokenizer.next(); // récupère le premier mot
                    }
                    if (nom.trim() == "")
                        throw new Exception("Nom du passager vide");

                    System.out.print("Prénom du passager : ");
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    if (tokenizer.hasNext()) {
                        prenom = tokenizer.next(); // récupère le premier mot
                    }
                    if (prenom.trim() == "")
                        throw new Exception("Prénom du passager vide");

                    System.out.print("Date de naissance (jj/mm/aaaa) : ");
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    if (tokenizer.hasNext()) {
                        dateNaissance = tokenizer.next(); // récupère le premier
                                                          // mot
                    }
                    if (!Pattern.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}",
                            dateNaissance))
                        throw new Exception("Date de naissance mal écrite");

                    for (int i = 0; i < Profil.values().length - 1; i++) {
                        System.out.print(Profil.values()[i].getProfil() + " ("
                                + i + "), ");
                    }
                    int last = Profil.values().length - 1;
                    System.out.println(Profil.values()[last].getProfil() + " ("
                            + last + ")");
                    System.out.print("Profil choisi (id) : ");
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    if (tokenizer.hasNext()) {
                        profilId = Integer.valueOf(tokenizer.next()); // récupère
                                                                      // le
                                                                      // premier
                                                                      // mot
                    }

                    // on récupère le profil choisi
                    profil = Profil.values()[profilId];
                    if (profil == null)
                        throw new Exception("profil non reconnu");

                    System.out.print("Fidélité ? (0=non, 1=oui)");
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    int res = 0;
                    if (tokenizer.hasNext()) {
                        res = Integer.valueOf(tokenizer.next()); // récupère le
                                                                 // premier mot
                    }
                    if (res == 0)
                        fidelite = false;
                    else
                        fidelite = true;

                    System.out.print("Ticket Modifiable ? (0=non, 1=oui)");
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    res = 0;
                    if (tokenizer.hasNext()) {
                        res = Integer.valueOf(tokenizer.next()); // récupère le
                                                                 // premier mot
                    }
                    if (res == 0)
                        modifiable = false;
                    else
                        modifiable = true;

                    if (trajetAReserver.getVehicule().avecCouchette()) {
                        System.out.print("Prend Couchette ? (0=non, 1=oui)");
                        tokenizer = new Scanner(
                                (new Scanner(System.in)).nextLine());
                        res = 0;
                        if (tokenizer.hasNext()) {
                            res = Integer.valueOf(tokenizer.next()); // récupère
                                                                     // le
                                                                     // premier
                                                                     // mot
                        }
                        if (res == 0)
                            prendCouchette = false;
                        else
                            prendCouchette = true;
                    }

                    for (Repas repas : trajetAReserver.getVehicule().getRepas()) {
                        System.out.print(repas.getNom() + " ? (0=non, 1=oui)");
                        tokenizer = new Scanner(
                                (new Scanner(System.in)).nextLine());
                        res = 0;
                        if (tokenizer.hasNext()) {
                            res = Integer.valueOf(tokenizer.next()); // récupère
                                                                     // le
                                                                     // premier
                                                                     // mot
                        }
                        if (res == 0) {
                            prendRepas.put(repas.getNom(), false);
                        } else {
                            prendRepas.put(repas.getNom(), true);
                        }
                    }

                    passager = new Passager(nom, prenom, s.textToCalendar(
                            dateNaissance, "00:00"), profil, fidelite);

                    reservation = new Reservation(passager, trajetAReserver,
                            modifiable, prendCouchette, prendRepas,
                            s.getReservationNewIdentifiant(), placesVoulues);

                    a.reserver(trajetAReserver, placesVoulues);
                    reservation.genereTicket();

                    a.addReservation(reservation);
                    a.consulterReservation();

                    System.out
                            .println("Merci d'avoir réserver ce trajet ! Bon voyage.");
                } else
                    System.out
                            .println("Il ne reste plus de place à bord de ce véhicule !");
            } else {
                System.out.println("Mauvais identifiant.");
            }
        } else {
            System.out.println("Vous ne pouvez réserver que des trajets");
        }
    }

    /**
     * Ajouter un trajet ou un véhicule
     * 
     * @param command
     *            la commande à exécuter
     * @throws Exception
     */
    private void ajouter(Command command) throws Exception {
        if (!command.hasSecondWord()) {
            System.out.println("Ajouter quoi ? trajet ou vehicule ?");
            return;
        }

        String quoi = command.getSecondWord();
        if (quoi.equals("trajet")) {
            // on demande les infos sur le trajet à ajouter
            String dateDepart = "";
            String dateArrivee = "";
            String horaireDepart = "";
            String horaireArrivee = "";
            int idDepart = 0;
            int idArrivee = 0;
            int idVehicule = 0;
            int distance = 0;
            boolean premiereClasse = false;

            // On demande à l'utilisateur toutes les infos qu'on veut
            // puis on vérifie si ce qui a été entré est correct
            System.out.print("Date de départ (jj/mm/aaaa) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                dateDepart = tokenizer.next(); // récupère le premier mot
            }
            if (!Pattern.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}", dateDepart))
                throw new Exception("Date de départ mal écrite");

            System.out.print("Date d'arrivée (jj/mm/aaaa) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                dateArrivee = tokenizer.next(); // récupère le premier mot
            }
            if (!Pattern.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}", dateArrivee))
                throw new Exception("Date d'arrivée mal écrite");

            System.out.print("Horaire de départ (hh:mm) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                horaireDepart = tokenizer.next(); // récupère le premier mot
            }
            if (!Pattern.matches("[0-9]{1,2}:[0-9]{1,2}", horaireDepart))
                throw new Exception("Heure de départ mal écrite");

            System.out.print("Horaire d'arrivée (hh:mm) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                horaireArrivee = tokenizer.next(); // récupère le premier mot
            }
            if (!Pattern.matches("[0-9]{1,2}:[0-9]{1,2}", horaireArrivee))
                throw new Exception("Heure d'arrivée mal écrite");

            a.consulterVille();
            System.out.print("Lieu de départ (id) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                idDepart = Integer.valueOf(tokenizer.next()); // récupère le
                                                              // premier mot
            }

            Ville depart = a.getVille(idDepart);
            if (depart == null)
                throw new Exception("ville non reconnu");

            a.consulterVille();
            System.out.print("Lieu d'arrivée : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                idArrivee = Integer.valueOf(tokenizer.next()); // récupère le
                                                               // premier mot
            }
            Ville arrivee = a.getVille(idArrivee);
            if (arrivee == null)
                throw new Exception("ville non reconnu");

            a.consulterVehicules();
            System.out.print("Véhicule (id) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                idVehicule = Integer.valueOf(tokenizer.next()); // récupère le
                                                                // premier mot
            }

            // on récupère le véhicule choisi
            Vehicule v = a.getVehicule(idVehicule);
            if (v == null)
                throw new Exception("véhicule non reconnu");

            System.out.print("Distance (km) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                distance = Integer.valueOf(tokenizer.next()); // récupère le
                                                              // premier mot
            }

            System.out.print("Première classe (0=non,1=oui) : ");
            int classe = 0;
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                classe = Integer.valueOf(tokenizer.next()); // récupère le
                                                            // premier mot
            }
            if (classe != 0)
                premiereClasse = true;

            // on convertit les dates entrées en Calendar
            Calendar dateCompleteDepart = Serveur.textToCalendar(dateDepart,
                    horaireDepart);
            if (dateCompleteDepart == null)
                throw new Exception("date de départ mal écrite");

            Calendar dateCompleteArrivee = Serveur.textToCalendar(dateArrivee,
                    horaireArrivee);
            if (dateCompleteArrivee == null)
                throw new Exception("date d'arrivée mal écrite");

            // on créé le trajet voulu puis on l'ajoute
            Trajet t = new Trajet(dateCompleteDepart, dateCompleteArrivee,
                    depart, arrivee, distance, v, a.getTrajetNewIdentifiant(),
                    premiereClasse);

            a.addTrajet(t);
            a.consulterTrajet();

        } else if (quoi.equals("vehicule")) {
            // on demande les infos sur le véhicule à ajouter
            String vehicule = "";
            int typeId = 0;
            int capacite = 0;

            // On demande à l'utilisateur toutes les infos qu'on veut
            // puis on vérifie si ce qui a été entré est correct
            System.out.print("Nom du véhicule : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                vehicule = tokenizer.next(); // récupère le premier mot
            }
            if (vehicule.trim() == "")
                throw new Exception("Nom du véhicule vide");

            for (int i = 0; i < TypeVehicule.values().length - 1; i++) {
                System.out.print(TypeVehicule.values()[i].getNom() + " (" + i
                        + "), ");
            }
            int last = TypeVehicule.values().length - 1;
            System.out.println(TypeVehicule.values()[last].getNom() + " ("
                    + last + ")");
            System.out.print("Type choisi (id) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                typeId = Integer.valueOf(tokenizer.next()); // récupère le
                                                            // premier mot
            }

            // on récupère le véhicule choisi

            TypeVehicule type = TypeVehicule.values()[typeId];
            if (type == null)
                throw new Exception("type de véhicule non reconnu");

            System.out
                    .print("Capacité du véhicule [laisser blanc pour mettre à défaut]: ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                capacite = Integer.valueOf(tokenizer.next()); // récupère le
                                                              // premier mot
            }

            // on créé le véhicule voulu puis on l'ajoute
            Vehicule v = null;
            if (type == TypeVehicule.AVION) {
                if (capacite != 0)
                    v = new Avion(vehicule, capacite,
                            a.getVehiculeNewIdentifiant());
                else
                    v = new Avion(vehicule, a.getVehiculeNewIdentifiant());
            } else if (type == TypeVehicule.BATEAU) {
                if (capacite != 0)
                    v = new Bateau(vehicule, capacite,
                            a.getVehiculeNewIdentifiant());
                else
                    v = new Bateau(vehicule, a.getVehiculeNewIdentifiant());
            } else if (type == TypeVehicule.BUS) {
                if (capacite != 0)
                    v = new Bus(vehicule, capacite,
                            a.getVehiculeNewIdentifiant());
                else
                    v = new Bus(vehicule, a.getVehiculeNewIdentifiant());
            } else if (type == TypeVehicule.TRAIN) {
                if (capacite != 0)
                    v = new Train(vehicule, capacite,
                            a.getVehiculeNewIdentifiant());
                else
                    v = new Train(vehicule, a.getVehiculeNewIdentifiant());
            }

            if (v != null)
                a.addVehicule(v);
            a.consulterVehicules();
        } else if (quoi.equals("ville")) {
            // on demande les infos sur le véhicule à ajouter
            String ville = "";

            // On demande à l'utilisateur toutes les infos qu'on veut
            // puis on vérifie si ce qui a été entré est correct
            System.out.print("Nom de la ville : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                ville = tokenizer.next(); // récupère le premier mot
            }
            if (ville.trim() == "")
                throw new Exception("Nom de la ville vide");

            // on créé le véhicule voulu puis on l'ajoute
            Ville v = new Ville(ville, a.getVilleNewIdentifiant());

            if (v != null)
                a.addVille(v);
            a.consulterVille();
        }

        else {
            System.out
                    .println("Vous ne pouvez ajouter qu'un trajet ou un vehicule");
        }
    }

    /**
     * Modifier un trajet ou un véhicule
     * 
     * @param command
     *            la commande à exécuter
     * @throws Exception
     */
    private void modifier(Command command) throws Exception {
        if (!command.hasSecondWord()) {
            System.out.println("Modifier quoi ? trajet ou vehicule ?");
            return;
        }

        String quoi = command.getSecondWord();
        if (quoi.equals("trajet")) {
            // on demande les infos sur le trajet à ajouter
            Trajet trajetAModifier = null;
            String dateDepart = "";
            String dateArrivee = "";
            String horaireDepart = "";
            String horaireArrivee = "";
            int idDepart = 0;
            int idArrivee = 0;
            int idVehicule = 0;
            int distance = 0;
            boolean premiereClasse = false;

            a.consulterTrajet();
            // On demande à l'utilisateur l'identifiant du trajet à modifier
            System.out.print("Trajet à modifier (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                trajetAModifier = a
                        .getTrajet(Integer.valueOf(tokenizer.next())); // récupère
                                                                       // le
                                                                       // premier
                                                                       // mot
            }
            if (trajetAModifier != null) {
                System.out
                        .println("N'entrez rien si vous voulez la même valeur que précédemment.");

                // On demande à l'utilisateur toutes les infos qu'on veut
                // puis on vérifie si ce qui a été entré est correct
                // Si rien n'est entré, on ne change pas les valeurs
                System.out.print(new StringBuffer()
                        .append("Date de départ (jj/mm/aaaa) [")
                        .append(Serveur.calendarToDate(trajetAModifier
                                .getDateDepart())).append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    dateDepart = tokenizer.next(); // récupère le premier mot
                } else {
                    dateDepart = Serveur.calendarToDate(trajetAModifier
                            .getDateDepart());
                }
                if (!Pattern.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}",
                        dateDepart))
                    throw new Exception("Date de départ mal écrite");

                System.out.print(new StringBuffer()
                        .append("Date d'arrivée (jj/mm/aaaa) [")
                        .append(Serveur.calendarToDate(trajetAModifier
                                .getDateArrivee())).append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    dateArrivee = tokenizer.next(); // récupère le premier mot
                } else {
                    dateArrivee = Serveur.calendarToDate(trajetAModifier
                            .getDateArrivee());
                }
                if (!Pattern.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}",
                        dateArrivee))
                    throw new Exception("Date d'arrivée mal écrite");

                System.out.print(new StringBuffer()
                        .append("Horaire de départ (hh:mm) [")
                        .append(Serveur.calendarToTime(trajetAModifier
                                .getDateDepart())).append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    horaireDepart = tokenizer.next(); // récupère le premier mot
                } else {
                    horaireDepart = Serveur.calendarToTime(trajetAModifier
                            .getDateDepart());
                }
                if (!Pattern.matches("[0-9]{1,2}:[0-9]{1,2}", horaireDepart))
                    throw new Exception("Heure de départ mal écrite");

                System.out.print(new StringBuffer()
                        .append("Horaire d'arrivée (hh:mm) [")
                        .append(Serveur.calendarToTime(trajetAModifier
                                .getDateArrivee())).append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    horaireArrivee = tokenizer.next(); // récupère le premier
                                                       // mot
                } else {
                    horaireArrivee = Serveur.calendarToTime(trajetAModifier
                            .getDateArrivee());
                }
                if (!Pattern.matches("[0-9]{1,2}:[0-9]{1,2}", horaireArrivee))
                    throw new Exception("Heure d'arrivée mal écrite");

                a.consulterVille();
                System.out.print(new StringBuffer()
                        .append("Lieu de départ (id) [")
                        .append(trajetAModifier.getDepart().getIdentifiant())
                        .append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    idDepart = Integer.valueOf(tokenizer.next()); // récupère le
                                                                  // premier mot
                } else {
                    idDepart = trajetAModifier.getDepart().getIdentifiant();
                }

                // on créé le véhicule voulu
                Ville depart = a.getVille(idDepart);
                if (depart == null)
                    throw new Exception("ville non reconnue");

                a.consulterVille();
                System.out.print(new StringBuffer()
                        .append("Lieu d'arrivée (id) [")
                        .append(trajetAModifier.getArrivee().getIdentifiant())
                        .append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    idArrivee = Integer.valueOf(tokenizer.next()); // récupère
                                                                   // le premier
                                                                   // mot
                } else {
                    idArrivee = trajetAModifier.getArrivee().getIdentifiant();
                }

                // on créé le véhicule voulu
                Ville arrivee = a.getVille(idArrivee);
                if (arrivee == null)
                    throw new Exception("ville non reconnue");

                a.consulterVehicules();
                System.out.print(new StringBuffer().append("Véhicule (id) [")
                        .append(trajetAModifier.getVehicule().getIdentifiant())
                        .append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    idVehicule = Integer.valueOf(tokenizer.next()); // récupère
                                                                    // le
                                                                    // premier
                                                                    // mot
                } else {
                    idVehicule = trajetAModifier.getVehicule().getIdentifiant();
                }

                // on créé le véhicule voulu
                Vehicule v = a.getVehicule(idVehicule);
                if (v == null)
                    throw new Exception("véhicule non reconnu");

                System.out.print(new StringBuffer().append("Distance (km) [")
                        .append(trajetAModifier.getDistance()).append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    distance = Integer.valueOf(tokenizer.next()); // récupère le
                                                                  // premier mot
                } else
                    distance = trajetAModifier.getDistance();

                int texteClasse = (trajetAModifier.isPremiereClasse()) ? 1 : 0;
                System.out.print(new StringBuffer()
                        .append("Première classe (0=non,1=oui) [")
                        .append(texteClasse).append("] : "));
                int classe = 0;
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    classe = Integer.valueOf(tokenizer.next()); // récupère le
                                                                // premier mot
                } else {
                    classe = texteClasse;
                }
                if (classe != 0)
                    premiereClasse = true;

                // on convertit les dates entrées en Calendar
                Calendar dateCompleteDepart = Serveur.textToCalendar(
                        dateDepart, horaireDepart);
                if (dateCompleteDepart == null)
                    throw new Exception("date de départ mal écrite");
                Calendar dateCompleteArrivee = Serveur.textToCalendar(
                        dateArrivee, horaireArrivee);
                if (dateCompleteArrivee == null)
                    throw new Exception("date d'arrivée mal écrite");

                // On créé le nouveau trajet puis on le met à la place de
                // l'ancien
                Trajet t = new Trajet(dateCompleteDepart, dateCompleteArrivee,
                        depart, arrivee, distance, v,
                        trajetAModifier.getIdentifiant(), premiereClasse);
                a.modifierTrajet(trajetAModifier, t);
                a.consulterTrajet();
            } else {
                throw new Exception(
                        "Aucun trajet ne correspond à cet identifiant");
            }
        } else if (quoi.equals("vehicule")) {
            // on demande les infos sur le véhicule à ajouter
            Vehicule vehiculeAModifier = null;
            String vehicule = "";
            int typeId = 0;
            int capacite = 0;
            TypeVehicule type = null;

            a.consulterVehicules();
            // On demande à l'utilisateur l'identifiant du véhicule à modifier
            System.out.print("Véhicule à modifier (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                vehiculeAModifier = a.getVehicule(Integer.valueOf(tokenizer
                        .next())); // get first word
            }
            if (vehiculeAModifier != null) {
                System.out
                        .println("N'entrez rien si vous voulez la même valeur que précédemment.");

                // On demande à l'utilisateur toutes les infos qu'on veut
                // puis on vérifie si ce qui a été entré est correct
                // Si rien n'est entré, on ne change pas les valeurs
                System.out
                        .print(new StringBuffer().append("Nom du véhicule [")
                                .append(vehiculeAModifier.getVehicule())
                                .append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    vehicule = tokenizer.next(); // récupère le premier mot
                } else {
                    vehicule = vehiculeAModifier.getVehicule();
                }
                if (vehicule.trim() == "")
                    throw new Exception("Nom du véhicule vide");

                for (int i = 0; i < TypeVehicule.values().length - 1; i++) {
                    System.out.print(TypeVehicule.values()[i] + " (" + i
                            + "), ");
                }
                int last = TypeVehicule.values().length - 1;
                System.out.println(TypeVehicule.values()[last] + " (" + last
                        + ")");

                System.out.print(new StringBuffer()
                        .append("Type choisi (id) [")
                        .append(vehiculeAModifier.getType()).append("] : "));

                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    typeId = Integer.valueOf(tokenizer.next()); // récupère le
                                                                // premier mot
                    type = TypeVehicule.values()[typeId];
                } else {
                    type = vehiculeAModifier.getType();
                }

                if (type == null)
                    throw new Exception("type de véhicule non reconnu");

                System.out
                        .print(new StringBuffer()
                                .append("Capacité du véhicule [")
                                .append(vehiculeAModifier.getCapacite())
                                .append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    capacite = Integer.valueOf(tokenizer.next()); // récupère le
                                                                  // premier mot
                } else {
                    capacite = vehiculeAModifier.getCapacite();
                }

                // on créé le nouveau véhicule, puis on le met à la place de
                // l'ancien

                Vehicule v = null;
                if (type == TypeVehicule.AVION) {
                    if (capacite != 0)
                        v = new Avion(vehicule, capacite,
                                vehiculeAModifier.getIdentifiant());
                    else
                        v = new Avion(vehicule,
                                vehiculeAModifier.getIdentifiant());
                } else if (type == TypeVehicule.BATEAU) {
                    if (capacite != 0)
                        v = new Bateau(vehicule, capacite,
                                vehiculeAModifier.getIdentifiant());
                    else
                        v = new Bateau(vehicule,
                                vehiculeAModifier.getIdentifiant());
                } else if (type == TypeVehicule.BUS) {
                    if (capacite != 0)
                        v = new Bus(vehicule, capacite,
                                vehiculeAModifier.getIdentifiant());
                    else
                        v = new Bus(vehicule,
                                vehiculeAModifier.getIdentifiant());
                } else if (type == TypeVehicule.TRAIN) {
                    if (capacite != 0)
                        v = new Train(vehicule, capacite,
                                vehiculeAModifier.getIdentifiant());
                    else
                        v = new Train(vehicule,
                                vehiculeAModifier.getIdentifiant());
                }

                if (v != null)
                    a.modifierVehicule(vehiculeAModifier, v);
                a.consulterVehicules();
            } else {
                throw new Exception(
                        "Aucun véhicule ne correspond à cet identifiant");
            }
        } else if (quoi.equals("ville")) {
            // on demande les infos sur le véhicule à ajouter
            Ville villeAModifier = null;
            String ville = "";

            a.consulterVille();
            // On demande à l'utilisateur l'identifiant du véhicule à modifier
            System.out.print("Ville à modifier (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                villeAModifier = a.getVille(Integer.valueOf(tokenizer.next())); // get
                                                                                // first
                                                                                // word
            }
            if (villeAModifier != null) {
                System.out
                        .println("N'entrez rien si vous voulez la même valeur que précédemment.");

                // On demande à l'utilisateur toutes les infos qu'on veut
                // puis on vérifie si ce qui a été entré est correct
                // Si rien n'est entré, on ne change pas les valeurs
                System.out.print(new StringBuffer().append("Nom de la ville [")
                        .append(villeAModifier.getVille()).append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    ville = tokenizer.next(); // récupère le premier mot
                } else {
                    ville = villeAModifier.getVille();
                }
                if (ville.trim() == "")
                    throw new Exception("Nom de la ville vide");

                // on créé le nouveau véhicule, puis on le met à la place de
                // l'ancien
                Ville v = new Ville(ville, villeAModifier.getIdentifiant());
                a.modifierVille(villeAModifier, v);
                a.consulterVille();
            } else {
                throw new Exception(
                        "Aucune ville ne correspond à cet identifiant");
            }

        } else if (quoi.equals("reservation")) {
            Reservation reservationAModifier = null;
            Trajet trajetAReserver = null;
            int placesVoulues = 1;
            Passager passager = null;
            String nom = "";
            String prenom = "";
            String dateNaissance = "";
            int profilId = 0;
            Profil profil = null;

            boolean fidelite = false;
            boolean modifiable = false;
            boolean prendCouchette = false;
            Map<String, Boolean> prendRepas = new HashMap<String, Boolean>();

            a.consulterReservation();
            System.out.print("Réservation à modifier (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                reservationAModifier = a.getReservation(Integer
                        .valueOf(tokenizer.next())); // récupère
                // le
                // premier
                // mot
            }
            if (reservationAModifier == null)
                throw new Exception(
                        "Aucune réservation ne correspond à cet identifiant");
            System.out
                    .println("N'entrez rien si vous voulez la même valeur que précédemment.");

            trajetAReserver = reservationAModifier.getTrajet();

            placesVoulues = reservationAModifier.getPlacesVoulues();

            // On regarde si le trajet existe
            if (trajetAReserver != null) {

                // choix du passager
                System.out.print(new StringBuffer().append("Nom du passager [")
                        .append(reservationAModifier.getPassager().getNom())
                        .append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    nom = tokenizer.next(); // récupère le premier mot
                } else {
                    nom = reservationAModifier.getPassager().getNom();
                }
                if (nom.trim() == "")
                    throw new Exception("Nom du passager vide");

                System.out.print(new StringBuffer()
                        .append("Prénom du passager [")
                        .append(reservationAModifier.getPassager().getPrenom())
                        .append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    prenom = tokenizer.next(); // récupère le premier mot
                } else {
                    prenom = reservationAModifier.getPassager().getPrenom();
                }
                if (prenom.trim() == "")
                    throw new Exception("Prénom du passager vide");

                System.out.print(new StringBuffer()
                        .append("Date de naissance (jj/mm/aaaa) [")
                        .append(Serveur.calendarToDate(reservationAModifier
                                .getPassager().getDateNaissance()))
                        .append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    dateNaissance = tokenizer.next(); // récupère le premier mot
                } else {
                    dateNaissance = Serveur.calendarToDate(reservationAModifier
                            .getPassager().getDateNaissance());
                }
                if (!Pattern.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}",
                        dateNaissance))
                    throw new Exception("Date de naissance mal écrite");

                for (int i = 0; i < Profil.values().length - 1; i++) {
                    System.out.print(Profil.values()[i].getProfil() + " (" + i
                            + "), ");
                }
                int last = Profil.values().length - 1;
                System.out.println(Profil.values()[last].getProfil() + " ("
                        + last + ")");
                System.out.print(new StringBuffer()
                        .append("Profil choisi (id) [")
                        .append(reservationAModifier.getPassager().getProfil()
                                .getProfil()).append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                if (tokenizer.hasNext()) {
                    profilId = Integer.valueOf(tokenizer.next()); // récupère le
                                                                  // premier mot
                    profil = Profil.values()[profilId];
                } else {
                    profil = reservationAModifier.getPassager().getProfil();
                }

                if (profil == null)
                    throw new Exception("profil non reconnu");

                int texteFidelite = 0;
                if (reservationAModifier.getPassager().getFidelite())
                    texteFidelite = 1;
                System.out.print(new StringBuffer()
                        .append("Fidélité ? (0=non, 1=oui) [")
                        .append(texteFidelite).append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                int res = 0;
                if (tokenizer.hasNext()) {
                    res = Integer.valueOf(tokenizer.next()); // récupère le
                                                             // premier mot
                } else
                    res = texteFidelite;
                if (res == 0)
                    fidelite = false;
                else
                    fidelite = true;

                int texteModifiable = 0;
                if (reservationAModifier.isModifiable())
                    texteModifiable = 1;
                System.out.print(new StringBuffer()
                        .append("Ticket Modifiable ? (0=non, 1=oui) [")
                        .append(texteModifiable).append("] : "));
                tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                res = 0;
                if (tokenizer.hasNext()) {
                    res = Integer.valueOf(tokenizer.next()); // récupère le
                                                             // premier mot
                } else
                    res = texteModifiable;
                if (res == 0)
                    modifiable = false;
                else
                    modifiable = true;

                if (trajetAReserver.getVehicule().avecCouchette()) {
                    int texteCouchette = 0;
                    if (reservationAModifier.isPrendCouchette())
                        texteCouchette = 1;
                    System.out.print(new StringBuffer()
                            .append("Prend Couchette ? (0=non, 1=oui) [")
                            .append(texteCouchette).append("] : "));
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    res = 0;
                    if (tokenizer.hasNext()) {
                        res = Integer.valueOf(tokenizer.next()); // récupère le
                                                                 // premier mot
                    } else
                        res = texteCouchette;
                    if (res == 0)
                        prendCouchette = false;
                    else
                        prendCouchette = true;
                }

                for (Repas repas : trajetAReserver.getVehicule().getRepas()) {
                    int texteRepas = 0;
                    if (reservationAModifier.getRepas(repas.getNom()))
                        texteRepas = 1;
                    System.out.print(new StringBuffer()
                            .append(repas.getNom() + " ? (0=non, 1=oui) [")
                            .append(texteRepas).append("] : "));
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    res = 0;
                    if (tokenizer.hasNext()) {
                        res = Integer.valueOf(tokenizer.next()); // récupère le
                                                                 // premier mot
                    } else
                        res = texteRepas;
                    if (res == 0) {
                        prendRepas.put(repas.getNom(), false);
                    } else {
                        prendRepas.put(repas.getNom(), true);
                    }
                }

                passager = new Passager(nom, prenom, s.textToCalendar(
                        dateNaissance, "00:00"), profil, fidelite);

                Reservation reservation = new Reservation(passager,
                        trajetAReserver, modifiable, prendCouchette,
                        prendRepas, reservationAModifier.getIdentifiant(),
                        placesVoulues);

                a.modifierReservation(reservationAModifier, reservation);

                File file = new File("ticketReservation"
                        + reservationAModifier.getPassager().getNom() + "$"
                        + reservationAModifier.getPassager().getPrenom()
                        + ".html");
                file.delete();

                reservation.genereTicket();

                a.consulterReservation();

                System.out
                        .println("Merci d'avoir réserver ce trajet ! Bon voyage.");
            } else {
                System.out.println("Mauvais identifiant.");
            }

        } else {
            System.out
                    .println("Vous ne pouvez modifier qu'un trajet ou un vehicule");
        }
    }

    /**
     * Supprimer un véhicule ou un trajet
     * 
     * @param command
     *            la commande à exécuter
     */
    private void supprimer(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Supprimer quoi ? trajet ou vehicule ?");
            return;
        }

        String quoi = command.getSecondWord();
        if (quoi.equals("trajet")) {
            Trajet trajetASupprimer = null;

            a.consulterTrajet();
            // On demande à l'utilisateur l'identifiant du trajet à supprimer
            System.out.print("Trajet à supprimer (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                trajetASupprimer = a
                        .getTrajet(Integer.valueOf(tokenizer.next())); // récupère
                                                                       // le
                                                                       // premier
                                                                       // mot
            }
            // on le supprime s'il existe
            if (trajetASupprimer != null) {
                a.removeTrajet(trajetASupprimer);
            }
        } else if (quoi.equals("vehicule")) {
            Vehicule vehiculeASupprimer = null;

            a.consulterVehicules();
            // On demande à l'utilisateur l'identifiant du véhicule à supprimer
            System.out.print("Vehicule à supprimer (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                vehiculeASupprimer = a.getVehicule(Integer.valueOf(tokenizer
                        .next())); // récupère le premier mot
            }
            // on le supprime s'il existe
            if (vehiculeASupprimer != null) {
                a.removeVehicule(vehiculeASupprimer);
            }
        } else if (quoi.equals("ville")) {
            Ville villeASupprimer = null;

            a.consulterVille();
            // On demande à l'utilisateur l'identifiant du véhicule à supprimer
            System.out.print("Ville à supprimer (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                villeASupprimer = a.getVille(Integer.valueOf(tokenizer.next())); // récupère
                                                                                 // le
                                                                                 // premier
                                                                                 // mot
            }
            // on le supprime s'il existe
            if (villeASupprimer != null) {
                a.removeVille(villeASupprimer);
            }
        } else if (quoi.equals("reservation")) {
            Reservation reservationASupprimer = null;

            a.consulterReservation();
            // On demande à l'utilisateur l'identifiant du véhicule à supprimer
            System.out.print("Réservation à supprimer (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                reservationASupprimer = a.getReservation(Integer
                        .valueOf(tokenizer.next())); // récupère
                // le
                // premier
                // mot
            }
            // on le supprime s'il existe
            if (reservationASupprimer != null) {
                a.removeReservation(reservationASupprimer);
            }
        }

        else {
            System.out
                    .println("Vous ne pouvez supprimer qu'un trajet ou un vehicule");
        }
    }

}
