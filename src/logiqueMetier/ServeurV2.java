package logiqueMetier;

import java.io.*;
import java.util.*;

import objets.*;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun. Cette classe implémente la classe abstraite
 * Serveur. Elle implémente les méthodes sauvegarder et charger. Pour lire et
 * écrire les données, deux fichiers textes sont utilisés : l'un pour la liste
 * des véhicules, l'autre pour la liste des trajets.
 * 
 * Sur chaque ligne des fichiers, on écrit uniquement les informations
 * permettant de reconstituer l'objet. Chaque information est séparée par le
 * séparateur '#'. Pour reconstituer les listes de données, il suffit de lire
 * chaque ligne des fichiers, puis de récupérer chaque information en splitant
 * la ligne. On peut alors reconstituer l'objet.
 * 
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public class ServeurV2 extends Serveur implements Serializable {
    // on hérite de toutes les méthodes et données de Serveur
    public ServeurV2() {
        super();
    }

    /**
     * Lance la sauvegarde des listes de trajet et de véhicule sur le serveur On
     * écrit les données des listes dans deux fichiers textes. Sur chaque ligne
     * des fichiers, on écrit uniquement les informations permettant de
     * reconstituer l'objet. Chaque information est séparée par le séparateur
     * '#'.
     * 
     * @return true si la sauvegarde a réussi, false sinon
     * @throws IOException
     */
    public boolean sauvegarder() throws IOException {
        PrintWriter writeVehicule = new PrintWriter(new FileWriter(
                "dataV2vehicule"));
        PrintWriter writeVille = new PrintWriter(new FileWriter("dataV2ville"));
        PrintWriter writeTrajet = new PrintWriter(
                new FileWriter("dataV2trajet"));
        PrintWriter writeReservation = new PrintWriter(new FileWriter(
                "dataV2reservation"));

        if (writeVehicule.checkError() || writeVille.checkError()
                || writeTrajet.checkError() || writeReservation.checkError())
            return false;

        // On créé des String où à chaque ligne est écrit uniquement les
        // informations permettant de reconstituer l'objet.
        // Chaque information est séparée par le séparateur '#'.
        StringBuffer sVehicule = new StringBuffer("");
        StringBuffer sVille = new StringBuffer("");
        StringBuffer sTrajet = new StringBuffer("");
        StringBuffer sReservation = new StringBuffer("");

        for (int j = 0; j < mesVilles.size(); j++) {
            sVille.append(mesVilles.get(j).print());
        }
        for (int j = 0; j < mesVehicules.size(); j++) {
            sVehicule.append(mesVehicules.get(j).print());
        }
        for (int i = 0; i < mesTrajets.size(); i++) {
            sTrajet.append(mesTrajets.get(i).print());
        }

        // System.out.println(mesReservations.size());
        for (int k = 0; k < mesReservations.size(); k++) {
            sReservation.append(mesReservations.get(k).print());
        }

        // on met ces String dans les fichiers
        writeVehicule.print(sVehicule.toString());
        writeVille.print(sVille.toString());
        writeTrajet.print(sTrajet.toString());
        writeReservation.print(sReservation.toString());
        writeVehicule.close();
        writeVille.close();
        writeTrajet.close();
        writeReservation.close();
        return true;
    }

    /**
     * Lance le chargement des listes de trajet et de véhicule du serveur
     * 
     * Pour reconstituer les listes de données, il suffit de lire chaque ligne
     * des fichiers, puis de récupérer chaque information en splitant la ligne.
     * On peut alors reconstituer l'objet.
     * 
     * @return true si le chargement a réussi, false sinon
     * @throws Exception
     */
    public boolean charger() throws Exception {
        BufferedReader bufferVehicule = null;
        BufferedReader bufferTrajet = null;
        BufferedReader bufferVille = null;
        BufferedReader bufferReservation = null;

        try {
            bufferVehicule = new BufferedReader(
                    new FileReader("dataV2vehicule"));
            bufferTrajet = new BufferedReader(new FileReader("dataV2trajet"));
            bufferVille = new BufferedReader(new FileReader("dataV2ville"));
            bufferReservation = new BufferedReader(new FileReader(
                    "dataV2reservation"));
        } catch (Exception e) {
            return false;
        }
        StringBuffer accumulateur = new StringBuffer("");

        mesVehicules = new ArrayList<Vehicule>();

        // On lit chaque ligne du fichier des véhicules
        while (bufferVehicule.ready())
            accumulateur.append(bufferVehicule.readLine()).append("\n");
        String[] tab = accumulateur.toString().split("\n");

        // Pour chaque ligne, on splite les données séparés par '#'
        // On peut alors reconstituer le véhicule, et l'ajouter à la liste des
        // véhicules
        for (int i = 0; i < tab.length; i++) {
            String[] tab2 = tab[i].split("#");
            if (tab2.length == 5) {
                Vehicule v = null;
                if (TypeVehicule.valueOf(tab2[1]) == TypeVehicule.AVION) {
                    v = new Avion(Integer.valueOf(tab2[4]), tab2[0],
                            Integer.valueOf(tab2[2]), Integer.valueOf(tab2[3]));
                } else if (TypeVehicule.valueOf(tab2[1]) == TypeVehicule.BATEAU) {
                    v = new Bateau(Integer.valueOf(tab2[4]), tab2[0],
                            Integer.valueOf(tab2[2]), Integer.valueOf(tab2[3]));
                } else if (TypeVehicule.valueOf(tab2[1]) == TypeVehicule.BUS) {
                    v = new Bus(Integer.valueOf(tab2[4]), tab2[0],
                            Integer.valueOf(tab2[2]), Integer.valueOf(tab2[3]));
                } else if (TypeVehicule.valueOf(tab2[1]) == TypeVehicule.TRAIN) {
                    v = new Train(Integer.valueOf(tab2[4]), tab2[0],
                            Integer.valueOf(tab2[2]), Integer.valueOf(tab2[3]));
                }

                if (v != null)
                    this.addVehicule(v);
            }
        }

        accumulateur.setLength(0);
        // On lit chaque ligne du fichier des trajets
        mesVilles = new ArrayList<Ville>();
        while (bufferVille.ready())
            accumulateur.append(bufferVille.readLine()).append("\n");
        String tab5[] = accumulateur.toString().split("\n");

        // Pour chaque ligne, on splite les données séparés par '#'
        // On peut alors reconstituer le trajet, et l'ajouter à la liste des
        // trajets
        for (int i = 0; i < tab5.length; i++) {
            String[] tab6 = tab5[i].split("#");
            if (tab6.length == 2) {
                this.addVille(new Ville(tab6[0], Integer.valueOf(tab6[1])));
            }
        }

        accumulateur.setLength(0);
        // On lit chaque ligne du fichier des trajets
        mesTrajets = new ArrayList<Trajet>();
        while (bufferTrajet.ready())
            accumulateur.append(bufferTrajet.readLine()).append("\n");
        String tab3[] = accumulateur.toString().split("\n");

        // Pour chaque ligne, on splite les données séparés par '#'
        // On peut alors reconstituer le trajet, et l'ajouter à la liste des
        // trajets
        for (int j = 0; j < tab3.length; j++) {
            String[] tab4 = tab3[j].split("#");
            if (tab4.length == 11) {
                Vehicule v = this.getVehicule(Integer.valueOf(tab4[6]));
                try {
                this.addTrajet(new Trajet(textToCalendar(tab4[0], tab4[1]),
                        textToCalendar(tab4[2], tab4[3]), this.getVille(Integer
                                .valueOf(tab4[4])), this.getVille(Integer
                                .valueOf(tab4[5])), Integer.valueOf(tab4[9]),
                        v, Integer.valueOf(tab4[8]), Integer.valueOf(tab4[7]),Boolean.valueOf(tab4[10])));
                } catch(Exception e) {
                	System.out.println("Impossible de charger le trajet !");
                }
            }
        }

        accumulateur.setLength(0);
        // On lit chaque ligne du fichier des trajets
        mesReservations = new ArrayList<Reservation>();
        while (bufferReservation.ready())
            accumulateur.append(bufferReservation.readLine()).append("\n");
        String tab7[] = accumulateur.toString().split("\n");

        // Pour chaque ligne, on splite les données séparés par '#'
        // On peut alors reconstituer la réservation, et l'ajouter à la liste
        // des
        // réservations
        for (int i = 0; i < tab7.length; i++) {
            String[] tab8 = tab7[i].split("#");
            if (tab8.length >= 10) {
                Profil profil = null;
                for (Profil value : Profil.values()) {
                    if (tab8[3].equals(String.valueOf(value))) {
                        profil = value;
                    }
                }

                Passager passager = new Passager(tab8[0], tab8[1],
                        Serveur.textToCalendar(tab8[2], "00:00"), profil,
                        Boolean.valueOf(tab8[4]));

                Trajet trajet = getTrajet(Integer.valueOf(tab8[5]));

                Map<String, Boolean> prendRepas = new HashMap<String, Boolean>();

                for (int k = 10; k < 10 + trajet.getVehicule().getRepas().size(); k++) {
                    String[] tab10 = tab8[k].split("=");
                    prendRepas.put(tab10[0], Boolean.valueOf(tab10[1]));
                }

                Reservation reservation = new Reservation(passager, trajet,
                        Boolean.valueOf(tab8[6]), Boolean.valueOf(tab8[7]),
                        prendRepas, Integer.valueOf(tab8[8]),
                        Integer.valueOf(tab8[9]));

                this.addReservation(reservation);
            }
        }

        return true;
    }

}
