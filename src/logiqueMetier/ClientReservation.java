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
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public class ClientReservation {
    private Parser parser;
    private Serveur s;
    private Admin a;

    /**
     * créer une instance du client d'administration
     * 
     * @param version
     *            la version du serveur choisi
     */
    public ClientReservation(int version) {
        parser = new Parser(); // créer un parser

        s = (version == 1) ? new ServeurV1() : new ServeurV2();
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
                    .println("Consulter quoi ? trajets ou vehicules ou villes ou reservations ?");
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
            Vehicule vehicule=null;
            int placesVoulues = 1;
            String dateDepart = "";
            String horaireDepart = "";
            int intervalleVoulue = 12;

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
                
                if(textTokenizer!="")
                {
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

            Calendar dateCompleteDepart = Serveur.textToCalendar(dateDepart,
                    horaireDepart);
            if (dateCompleteDepart == null)
                throw new Exception("date de départ mal écrite");

            // on recherche les trajets correspondant aux paramètres rentrés
            List<Trajet> trajetRecherche = a.rechercherTrajet(depart, arrivee,
                    vehicule, placesVoulues, dateCompleteDepart,
                    intervalleVoulue);

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
            Reservation reservation=null;
            Trajet trajetAReserver = null;
            int placesVoulues = 1;
            Passager passager=null;
            String nom="";
            String prenom="";
            String dateNaissance="";
            int profilId=0;
            Profil profil=null;
            
            boolean fidelite=false;
            boolean modifiable=false;
            boolean prendCouchette=false;
            Map<String,Boolean> prendRepas=new HashMap<String,Boolean>();
            Map<String,Boolean> prendClasses=new HashMap<String,Boolean>();
            
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
                if (trajetAReserver.restePlaces(placesVoulues))
                {
                    //choix du passager
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
                        dateNaissance = tokenizer.next(); // récupère le premier mot
                    }
                    if (!Pattern.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}", dateNaissance))
                        throw new Exception("Date de naissance mal écrite");
                    
                    
                    
                    for (int i = 0; i < Profil.values().length - 1; i++) {
                        System.out.print(Profil.values()[i].getProfil() + " (" + i + "), ");
                    }
                    int last = Profil.values().length - 1;
                    System.out.println(Profil.values()[last].getProfil() + " (" + last + ")");
                    System.out.print("Profil choisi (id) : ");
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    if (tokenizer.hasNext()) {
                        profilId = Integer.valueOf(tokenizer.next()); // récupère le
                                                                    // premier mot
                    }

                    // on récupère le profil choisi
                    profil = Profil.values()[profilId];
                    if (profil == null)
                        throw new Exception("profil non reconnu");
                    
                    
                    System.out.print("Fidélité ? (0=non, 1=oui)");
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    int res=0;
                    if (tokenizer.hasNext()) {
                        res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                    }
                    if(res==0)
                        fidelite=false;
                    else
                        fidelite=true;
                    
                    System.out.print("Ticket Modifiable ? (0=non, 1=oui)");
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    res=0;
                    if (tokenizer.hasNext()) {
                        res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                    }
                    if(res==0)
                        modifiable=false;
                    else
                        modifiable=true;
                    
                    if(trajetAReserver.getVehicule().avecCouchette())
                    {
                        System.out.print("Prend Couchette ? (0=non, 1=oui)");
                        tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                        res=0;
                        if (tokenizer.hasNext()) {
                            res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                        }
                        if(res==0)
                            prendCouchette=false;
                        else
                            prendCouchette=true;
                    }
                    
                    
                    for(ClassesRepas classe : trajetAReserver.getVehicule().getClasses())
                    {
                        System.out.print(classe.getNom()+" ? (0=non, 1=oui)");
                        tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                        res=0;
                        if (tokenizer.hasNext()) {
                            res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                            
                        }
                        if(res==0)
                        {
                            prendClasses.put(classe.getNom(), false);
                        }
                        else
                        {
                            prendClasses.put(classe.getNom(), true);
                        }
                    }
                    
                    for(ClassesRepas repas : trajetAReserver.getVehicule().getRepas())
                    {
                        System.out.print(repas.getNom()+" ? (0=non, 1=oui)");
                        tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                        res=0;
                        if (tokenizer.hasNext()) {
                            res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                        }
                        if(res==0)
                        {
                            prendRepas.put(repas.getNom(), false);
                        }
                        else
                        {
                            prendRepas.put(repas.getNom(), true);
                        }
                    }
                    
                    
                    passager=new Passager(nom,prenom,s.textToCalendar(dateNaissance, "00:00"),profil,fidelite);
                    
                    
                    reservation=new Reservation(passager,trajetAReserver,modifiable,prendCouchette,prendRepas,prendClasses, s.getReservationNewIdentifiant(),placesVoulues);
                    
                    
                    a.reserver(trajetAReserver, placesVoulues);
                    reservation.genereTicket();
                    
                    a.addReservation(reservation);
                    a.consulterReservation();
                    
                    System.out
                            .println("Merci d'avoir réserver ce trajet ! Bon voyage.");
                }
                else
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
     * Modifier un trajet ou un véhicule
     * 
     * @param command
     *            la commande à exécuter
     * @throws Exception
     */
    private void modifier(Command command) throws Exception {
        if (!command.hasSecondWord()) {
            System.out.println("Modifier quoi ? reservation ?");
            return;
        }

        String quoi = command.getSecondWord();
            if (quoi.equals("reservation")) {
            Reservation reservationAModifier=null;
            Trajet trajetAReserver = null;
            int placesVoulues = 1;
            Passager passager=null;
            String nom="";
            String prenom="";
            String dateNaissance="";
            int profilId=0;
            Profil profil=null;
            
            boolean fidelite=false;
            boolean modifiable=false;
            boolean prendCouchette=false;
            Map<String,Boolean> prendRepas=new HashMap<String,Boolean>();
            Map<String,Boolean> prendClasses=new HashMap<String,Boolean>();
            
            a.consulterReservation();
            System.out.print("Réservation à modifier (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                reservationAModifier = a
                        .getReservation(Integer.valueOf(tokenizer.next())); // récupère
                                                                       // le
                                                                       // premier
                                                                       // mot
            }
            if(reservationAModifier==null)
                throw new Exception(
                        "Aucune réservation ne correspond à cet identifiant\n");
            if(!reservationAModifier.isModifiable())
                throw new Exception(
                        "Vous ne pouvez plus modifier cette réservation !\n");
            
            System.out
            .println("N'entrez rien si vous voulez la même valeur que précédemment.");
            
            trajetAReserver=reservationAModifier.getTrajet();
            
            placesVoulues = reservationAModifier.getPlacesVoulues();
            
            // On regarde si le trajet existe
            if (trajetAReserver != null) {
                
                    //choix du passager
                    System.out.print(new StringBuffer().append("Nom du passager [").append(reservationAModifier.getPassager().getNom())
                            .append("] : "));
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    if (tokenizer.hasNext()) {
                        nom = tokenizer.next(); // récupère le premier mot
                    }
                    else
                    {
                        nom = reservationAModifier.getPassager().getNom();
                    }
                    if (nom.trim() == "")
                        throw new Exception("Nom du passager vide");
                    
                    
                    System.out.print(new StringBuffer().append("Prénom du passager [").append(reservationAModifier.getPassager().getPrenom())
                            .append("] : "));
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    if (tokenizer.hasNext()) {
                        prenom = tokenizer.next(); // récupère le premier mot
                    }
                    else
                    {
                        prenom = reservationAModifier.getPassager().getPrenom();
                    }
                    if (prenom.trim() == "")
                        throw new Exception("Prénom du passager vide");
                    
                    System.out.print(new StringBuffer().append("Date de naissance (jj/mm/aaaa) [").append(Serveur.calendarToDate(reservationAModifier.getPassager().getDateNaissance()))
                            .append("] : "));
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    if (tokenizer.hasNext()) {
                        dateNaissance = tokenizer.next(); // récupère le premier mot
                    }
                    else
                    {
                        dateNaissance = Serveur.calendarToDate(reservationAModifier.getPassager().getDateNaissance());
                    }
                    if (!Pattern.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}", dateNaissance))
                        throw new Exception("Date de naissance mal écrite");
                    
                    for (int i = 0; i < Profil.values().length - 1; i++) {
                        System.out.print(Profil.values()[i].getProfil() + " (" + i + "), ");
                    }
                    int last = Profil.values().length - 1;
                    System.out.println(Profil.values()[last].getProfil() + " (" + last + ")");
                    System.out.print(new StringBuffer().append("Profil choisi (id) : ").append(reservationAModifier.getPassager().getProfil())
                            .append("] : "));
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    if (tokenizer.hasNext()) {
                        profilId = Integer.valueOf(tokenizer.next()); // récupère le
                                                                    // premier mot
                        profil = Profil.values()[profilId];
                    }
                    else
                    {
                        profil = reservationAModifier.getPassager().getProfil();
                    }

                    if (profil == null)
                        throw new Exception("profil non reconnu");
                    
                    int texteFidelite=0;
                    if(reservationAModifier.getPassager().getFidelite())
                        texteFidelite=1;
                    System.out.print(new StringBuffer().append("Fidélité ? (0=non, 1=oui) [").append(texteFidelite)
                            .append("] : "));
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    int res=0;
                    if (tokenizer.hasNext()) {
                        res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                    }
                    else
                        res=texteFidelite;
                    if(res==0)
                        fidelite=false;
                    else
                        fidelite=true;
                    
                    
                    int texteModifiable=0;
                    if(reservationAModifier.isModifiable())
                        texteModifiable=1;
                    System.out.print(new StringBuffer().append("Ticket Modifiable ? (0=non, 1=oui) [").append(texteModifiable)
                            .append("] : "));
                    tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                    res=0;
                    if (tokenizer.hasNext()) {
                        res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                    }
                    else
                        res=texteModifiable;
                    if(res==0)
                        modifiable=false;
                    else
                        modifiable=true;
                    
                    if(trajetAReserver.getVehicule().avecCouchette())
                    {
                        int texteCouchette=0;
                        if(reservationAModifier.isPrendCouchette())
                            texteCouchette=1;
                        System.out.print(new StringBuffer().append("Prend Couchette ? (0=non, 1=oui) [").append(texteCouchette)
                                .append("] : "));
                        tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                        res=0;
                        if (tokenizer.hasNext()) {
                            res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                        }
                        else
                            res=texteCouchette;
                        if(res==0)
                            prendCouchette=false;
                        else
                            prendCouchette=true;
                    }
                    
                    
                    for(ClassesRepas classe : trajetAReserver.getVehicule().getClasses())
                    {
                        int texteClasse=0;
                        if(reservationAModifier.getClasse(classe.getNom()))
                            texteClasse=1;
                        System.out.print(new StringBuffer().append(classe.getNom()+" ? (0=non, 1=oui) [").append(texteClasse)
                                .append("] : "));
                        tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                        res=0;
                        if (tokenizer.hasNext()) {
                            res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                            
                        }
                        else
                            res=texteClasse;
                        if(res==0)
                        {
                            prendClasses.put(classe.getNom(), false);
                        }
                        else
                        {
                            prendClasses.put(classe.getNom(), true);
                        }
                    }
                    
                    for(ClassesRepas repas : trajetAReserver.getVehicule().getRepas())
                    {
                        int texteRepas=0;
                        if(reservationAModifier.getRepas(repas.getNom()))
                            texteRepas=1;
                        System.out.print(new StringBuffer().append(repas.getNom()+" ? (0=non, 1=oui) [").append(texteRepas)
                                .append("] : "));
                        tokenizer = new Scanner((new Scanner(System.in)).nextLine());
                        res=0;
                        if (tokenizer.hasNext()) {
                            res=Integer.valueOf(tokenizer.next()); // récupère le premier mot
                        }
                        else
                            res=texteRepas;
                        if(res==0)
                        {
                            prendRepas.put(repas.getNom(), false);
                        }
                        else
                        {
                            prendRepas.put(repas.getNom(), true);
                        }
                    }
                    
                    
                    passager=new Passager(nom,prenom,s.textToCalendar(dateNaissance, "00:00"),profil,fidelite);
                    
                    
                    Reservation reservation=new Reservation(passager,trajetAReserver,modifiable,prendCouchette,prendRepas,prendClasses, reservationAModifier.getIdentifiant(),placesVoulues);
                    
                    a.modifierReservation(reservationAModifier, reservation);
                    
                    File file=new File("ticketReservation" + reservationAModifier.getPassager().getNom()
                            + "$" + reservationAModifier.getPassager().getPrenom() + ".html");
                    file.delete();
                    
                    reservation.genereTicket();
                    
                    a.consulterReservation();
                    
                    System.out
                            .println("Merci d'avoir réserver ce trajet ! Bon voyage.");
            } else {
                System.out.println("Mauvais identifiant.");
            }
            
        }
        else {
            System.out
                    .println("Vous ne pouvez modifier qu'une réservation");
        }
    }

    /**
     * Supprimer un véhicule ou un trajet
     * 
     * @param command
     *            la commande à exécuter
     */
    private void supprimer(Command command) throws Exception {
        if (!command.hasSecondWord()) {
            System.out.println("Supprimer quoi ? reservation ?");
            return;
        }

        String quoi = command.getSecondWord();
        if (quoi.equals("reservation")) {
            Reservation reservationASupprimer = null;

            a.consulterReservation();
            // On demande à l'utilisateur l'identifiant de la réservation à supprimer
            System.out.print("Réservation à supprimer (id) : ");
            Scanner tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                reservationASupprimer = a.getReservation(Integer.valueOf(tokenizer.next())); // récupère
                                                                                 // le
                                                                                 // premier
                                                                                 // mot
            }
            if(!reservationASupprimer.isModifiable())
                throw new Exception(
                        "Vous ne pouvez plus supprimer cette réservation !\n");
            
            // on le supprime s'il existe
            if (reservationASupprimer != null) {
                a.removeReservation(reservationASupprimer);
            }
        }
        else {
            System.out
                    .println("Vous ne pouvez supprimer qu'une réservation");
        }
    }

}
