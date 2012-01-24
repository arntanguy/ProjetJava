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
        PrintWriter writeTrajet = new PrintWriter(
                new FileWriter("dataV2trajet"));
        PrintWriter writeVille = new PrintWriter(
                new FileWriter("dataV2ville"));
        
        if (writeVehicule.checkError() || writeTrajet.checkError() || writeVille.checkError())
            return false;

        // On créé des String où à chaque ligne est écrit uniquement les
        // informations permettant de reconstituer l'objet.
        // Chaque information est séparée par le séparateur '#'.
        StringBuffer sVehicule = new StringBuffer("");
        StringBuffer sTrajet = new StringBuffer("");
        StringBuffer sVille = new StringBuffer("");
        
        for (int i = 0; i < mesTrajets.size(); i++) {
            sTrajet.append(mesTrajets.get(i).print());
        }
        for (int j = 0; j < mesVehicules.size(); j++) {
            sVehicule.append(mesVehicules.get(j).print());
        }
        for (int j = 0; j < mesVilles.size(); j++) {
            sVille.append(mesVilles.get(j).print());
        }
        
        // on met ces String dans les fichiers
        writeVehicule.print(sVehicule.toString());
        writeTrajet.print(sTrajet.toString());
        writeVille.print(sVille.toString());
        writeVehicule.close();
        writeTrajet.close();
        writeVille.close();
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
        try {
            bufferVehicule = new BufferedReader(
                    new FileReader("dataV2vehicule"));
            bufferTrajet = new BufferedReader(new FileReader("dataV2trajet"));
            bufferVille= new BufferedReader(new FileReader("dataV2ville"));
        } catch (Exception e) {
            return false;
        }
        StringBuffer accumulateur = new StringBuffer("");
        mesTrajets = new ArrayList<Trajet>();
        mesVehicules = new ArrayList<Vehicule>();
        mesVilles = new ArrayList<Ville>();
        
        // On lit chaque ligne du fichier des véhicules
        while (bufferVehicule.ready())
            accumulateur.append(bufferVehicule.readLine()).append("\n");
        String[] tab = accumulateur.toString().split("\n");

        // Pour chaque ligne, on splite les données séparés par '#'
        // On peut alors reconstituer le véhicule, et l'ajouter à la liste des
        // véhicules
        for (int i = 0; i < tab.length; i++) {
            String[] tab2 = tab[i].split("#");
            if (tab2.length == 4) {
                Vehicule v = null;
                if(typeVehicule.valueOf(tab2[1])==typeVehicule.avion)
                {
                    v = new Avion(tab2[0],
                            typeVehicule.valueOf(tab2[1]),Integer.valueOf(tab2[2]), Integer.valueOf(tab2[3]));
                }
                else if(typeVehicule.valueOf(tab2[1])==typeVehicule.bateau)
                {
                    v = new Bateau(tab2[0],
                            typeVehicule.valueOf(tab2[1]),Integer.valueOf(tab2[2]), Integer.valueOf(tab2[3]));
                }
                else if(typeVehicule.valueOf(tab2[1])==typeVehicule.bus)
                {
                    v = new Bus(tab2[0],
                            typeVehicule.valueOf(tab2[1]),Integer.valueOf(tab2[2]), Integer.valueOf(tab2[3]));
                }
                else if(typeVehicule.valueOf(tab2[1])==typeVehicule.train)
                {
                    v = new Train(tab2[0],
                            typeVehicule.valueOf(tab2[1]),Integer.valueOf(tab2[2]), Integer.valueOf(tab2[3]));
                }
                
                if(v!=null)
                    this.addVehicule(v);
            }
        }

        accumulateur.setLength(0);
        // On lit chaque ligne du fichier des trajets
        while (bufferTrajet.ready())
            accumulateur.append(bufferTrajet.readLine()).append("\n");
        String tab3[] = accumulateur.toString().split("\n");

        // Pour chaque ligne, on splite les données séparés par '#'
        // On peut alors reconstituer le trajet, et l'ajouter à la liste des
        // trajets
        for (int j = 0; j < tab3.length; j++) {
            String[] tab4 = tab3[j].split("#");
            if (tab4.length == 9) {
                Vehicule v = this.getVehicule(Integer.valueOf(tab4[6]));
                this.addTrajet(new Trajet(textToCalendar(tab4[0], tab4[1]),
                        textToCalendar(tab4[2], tab4[3]), this.getVille(Integer.valueOf(tab4[4])), this.getVille(Integer.valueOf(tab4[5])), v,
                        Integer.valueOf(tab4[8]), Integer.valueOf(tab4[7])));
            }
        }
        
        accumulateur.setLength(0);
        // On lit chaque ligne du fichier des trajets
        while (bufferVille.ready())
            accumulateur.append(bufferVille.readLine()).append("\n");
        String tab5[] = accumulateur.toString().split("\n");

        // Pour chaque ligne, on splite les données séparés par '#'
        // On peut alors reconstituer le trajet, et l'ajouter à la liste des
        // trajets
        for (int i = 0; i < tab5.length; i++) {
            String[] tab6 = tab5[i].split("#");
            if (tab6.length == 4) {
                this.addVille(new Ville(tab6[0], Integer.valueOf(tab6[1])));
            }
        }

        return true;
    }

}
