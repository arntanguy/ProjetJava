package logiqueMetier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objets.Trajet;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class Distance {

    private List<Trajet> list;
    private Trajet[][] matrice;
    private int sizeTrajets;
    private int sizeVilles;

    public Distance(List<Trajet> list, int sizeTrajets, int sizeVilles) {
        this.list = list;
        this.sizeTrajets = sizeTrajets;
        this.sizeVilles = sizeVilles;

        matrice = new Trajet[sizeVilles][sizeVilles];

        for (int i = 0; i < sizeVilles; i++) {
            for (int j = 0; j < sizeVilles; j++) {
                matrice[i][j] = null;
            }
        }

        for (int i = 0; i < sizeTrajets; i++) {
            if (list.get(i) != null) {

                if (matrice[list.get(i).getDepart().getIdentifiant()][list
                        .get(i).getArrivee().getIdentifiant()] == null)
                    matrice[list.get(i).getDepart().getIdentifiant()][list
                            .get(i).getArrivee().getIdentifiant()] = list
                            .get(i);
                else {
                    if (matrice[list.get(i).getDepart().getIdentifiant()][list
                            .get(i).getArrivee().getIdentifiant()]
                            .getDistance() > list.get(i).getDistance()) {
                        matrice[list.get(i).getDepart().getIdentifiant()][list
                                .get(i).getArrivee().getIdentifiant()] = list
                                .get(i);
                    }
                }
            }
        }

    }

    public List<Trajet> cout(int i, int j) {
        return cout(i, j, sizeVilles);
    }

    private List<Trajet> cout(int i, int j, int k) {
        if (k == 0) {
            List<Trajet> listeMatrice = new ArrayList<Trajet>();
            if (matrice[i][j] != null)
                listeMatrice.add(matrice[i][j]);

            return listeMatrice;
        }
        List<Trajet> cout1 = cout(i, j, k - 1);
        List<Trajet> cout2 = plus(cout(i, k - 1, k - 1), cout(k - 1, j, k - 1));
        List<Trajet> cout = min(cout1, cout2);

        return cout;
    }

    private List<Trajet> min(List<Trajet> a, List<Trajet> b) {
        if (a == null || a.size() == 0) {
            return b;
        }
        if (b == null || b.size() == 0) {
            return a;
        }
        int aDistance = 0;
        for (Trajet t : a) {
            aDistance += t.getDistance();
        }

        int bDistance = 0;
        for (Trajet t : b) {
            bDistance += t.getDistance();
        }

        if (aDistance < bDistance) {
            return a;
        }
        return b;
    }

    private List<Trajet> plus(List<Trajet> a, List<Trajet> b) {
        if (a == null || a.size() == 0) {
            return null;
        }
        if (b == null || b.size() == 0) {
            return null;
        }

        List<Trajet> trajets = new ArrayList<Trajet>();
        if (a.get(a.size() - 1).getDateArrivee()
                .before(b.get(0).getDateDepart())) {
            trajets.addAll(a);
            trajets.addAll(b);
        }
        return trajets;
    }
}