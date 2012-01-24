package logiqueMetier;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
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
        while (!finished) {
            if (!a.lancerChargement())
                a.lancerSauvegarde();
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
            String depart = "";
            String arrivee = "";
            String vehicule = "";
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

            System.out.print("Lieu de départ : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                depart = tokenizer.next(); // récupère le premier mot
            }
            if (depart.trim() == "")
                throw new Exception("Lieu de départ vide");

            System.out.print("Lieu d'arrivée : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                arrivee = tokenizer.next(); // récupère le premier mot
            }
            if (arrivee.trim() == "")
                throw new Exception("Lieu d'arrivée vide");

            System.out.print("Véhicule (défaut=n'importe lequel) : ");
            tokenizer = new Scanner((new Scanner(System.in)).nextLine());
            if (tokenizer.hasNext()) {
                vehicule = tokenizer.next(); // récupère le premier mot
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
            Trajet trajetAReserver = null;
            int placesVoulues = 1;

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
                if (a.reserver(trajetAReserver, placesVoulues))
                    System.out
                            .println("Merci d'avoir réserver ce trajet ! Bon voyage.");
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
                    depart, arrivee, v, a.getTrajetNewIdentifiant());

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
                System.out.print(TypeVehicule.values()[i] + " (" + i + "), ");
            }
            int last = TypeVehicule.values().length - 1;
            System.out.println(TypeVehicule.values()[last] + " (" + last + ")");
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
            if (type == TypeVehicule.avion) {
                if (capacite != 0)
                    v = new Avion(vehicule, type, capacite,
                            a.getVehiculeNewIdentifiant());
                else
                    v = new Avion(vehicule, type, a.getVehiculeNewIdentifiant());
            } else if (type == TypeVehicule.bateau) {
                if (capacite != 0)
                    v = new Bateau(vehicule, type, capacite,
                            a.getVehiculeNewIdentifiant());
                else
                    v = new Bateau(vehicule, type,
                            a.getVehiculeNewIdentifiant());
            } else if (type == TypeVehicule.bus) {
                if (capacite != 0)
                    v = new Bus(vehicule, type, capacite,
                            a.getVehiculeNewIdentifiant());
                else
                    v = new Bus(vehicule, type, a.getVehiculeNewIdentifiant());
            } else if (type == TypeVehicule.train) {
                if (capacite != 0)
                    v = new Train(vehicule, type, capacite,
                            a.getVehiculeNewIdentifiant());
                else
                    v = new Train(vehicule, type, a.getVehiculeNewIdentifiant());
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
                        depart, arrivee, v, trajetAModifier.getIdentifiant());
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
                if (type == TypeVehicule.avion) {
                    if (capacite != 0)
                        v = new Avion(vehicule, type, capacite,
                            vehiculeAModifier.getIdentifiant());
                    else
                        v = new Avion(vehicule, type,
                                vehiculeAModifier.getIdentifiant());
                } else if (type == TypeVehicule.bateau) {
                    if (capacite != 0)
                        v = new Bateau(vehicule, type, capacite,
                            vehiculeAModifier.getIdentifiant());
                    else
                        v = new Bateau(vehicule, type,
                                vehiculeAModifier.getIdentifiant());
                } else if (type == TypeVehicule.bus) {
                    if (capacite != 0)
                        v = new Bus(vehicule, type, capacite,
                            vehiculeAModifier.getIdentifiant());
                    else
                        v = new Bus(vehicule, type,
                                vehiculeAModifier.getIdentifiant());
                } else if (type == TypeVehicule.train) {
                    if (capacite != 0)
                        v = new Train(vehicule, type, capacite,
                            vehiculeAModifier.getIdentifiant());
                    else
                        v = new Train(vehicule, type,
                                vehiculeAModifier.getIdentifiant());
                }

                // A CORRIGER
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
        }

        else {
            System.out
                    .println("Vous ne pouvez supprimer qu'un trajet ou un vehicule");
        }
    }

}
