package logiqueMetier;

import java.io.IOException;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun. Cette classe lance le client d'administration.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
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
        ClientAdmin ca = new ClientAdmin(3);
        ca.launch();
    }
}
