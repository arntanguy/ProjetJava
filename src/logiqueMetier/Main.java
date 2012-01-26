package logiqueMetier;

import java.io.IOException;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun. Cette classe lance le client d'administration.
 * 
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public class Main {

    /**
     * Lance le client d'administration.
     * 
     * @param args
     * @throws Exception
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws IOException,
            ClassNotFoundException, Exception {
        ClientAdmin ca = new ClientAdmin(2);
        //ClientReservation ca = new ClientReservation(3);

        ca.launch();
    }

}
