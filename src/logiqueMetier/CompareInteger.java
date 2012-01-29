package logiqueMetier;

import java.util.Comparator;

import objets.Trajet;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class CompareInteger implements Comparator<Trajet> {
    public int compare(Trajet t1, Trajet t2) {
        if (t1.getPrix() > t2.getPrix())
            return 1;
        else if (t1.getPrix() < t2.getPrix())
            return -1;
        else
            return 0;
    }
}