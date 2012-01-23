package logiqueMetier;

import java.io.*;
import java.util.*;

import objets.Trajet;
import objets.Vehicule;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun. Cette classe implémente la classe abstraite
 * Serveur. Elle implémente les méthodes sauvegarder et charger. Pour lire et
 * écrire les données, un fichier de donnée est utilisé. Pour écrire les
 * données, on stocke directement les listes dans le fichier. Pour lire les
 * données, on récupère directement les listes à partir du fichier.
 * 
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public class ServeurV1 extends Serveur implements Serializable {
    // on hérite de toutes les méthodes et données de Serveur
    public ServeurV1() {
        super();
    }

    /**
     * Lance la sauvegarde des listes de trajet et de véhicule sur le serveur
     * Pour écrire les données, on stocke directement les listes dans le
     * fichier.
     * 
     * @return true si la sauvegarde a réussi, false sinon
     * @throws IOException
     */
    public boolean sauvegarder() throws IOException {
        FileOutputStream test = new FileOutputStream("dataV1");
        ObjectOutputStream oos4 = new ObjectOutputStream(test);

        try {
            oos4.writeObject(mesTrajets);
            oos4.writeObject(mesVehicules);
            oos4.flush();
            oos4.close();
            return true;
        } catch (IOException e) {
            System.out.println("Problème d'écriture.");
            oos4.close();
            return false;
        }
    }

    /**
     * Lance le chargement des listes de trajet et de véhicule du serveur Pour
     * lire les données, on récupère directement les listes à partir du fichier.
     * 
     * @return true si le chargement a réussi, false sinon
     * @throws Exception
     */
    public boolean charger() throws IOException, ClassNotFoundException {
        FileInputStream test = null;
        ObjectInputStream oos4 = null;
        try {
            test = new FileInputStream("dataV1");
            oos4 = new ObjectInputStream(test);

            this.mesTrajets = (ArrayList<Trajet>) oos4.readObject();
            this.mesVehicules = (ArrayList<Vehicule>) oos4.readObject();
            oos4.close();
            return true;
        } catch (Exception e) {
            System.out.println("Problème de lecture.");
            return false;
        }
    }

}
