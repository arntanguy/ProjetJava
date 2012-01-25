package test;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import objets.Avion;
import objets.Bateau;
import objets.Bus;
import objets.Train;
import objets.Trajet;
import objets.TypeVehicule;
import objets.Vehicule;
import objets.Ville;

import org.junit.Before;

public class TestRecherche {

    private List<Trajet> trajet;
    private Trajet trajet1, trajet2, trajet3, trajet4;
    private Calendar date1, date2, date3, date4;
    private Ville paris, montreal, tokyo, londres;
    private Vehicule v1, v2, v3, v4;
    @Before
    public void setUp() throws Exception {
        trajet = new ArrayList<Trajet>();
        date1.set(2012, Calendar.DECEMBER, 13);
        date2.set(2012, Calendar.DECEMBER, 14);
        date3.set(2012, Calendar.DECEMBER, 15);
        date4.set(2012, Calendar.DECEMBER, 16);
        paris = new Ville("Paris", 0);
        montreal = new Ville("Montreal", 1);
        tokyo = new Ville("Tokyo", 2);
        londres = new Ville("Londres", 3);
        v1 = new Avion("Coco", 50, 0);
        v2 = new Bateau("Coco1", 50, 1);
        v3 = new Bus("Coco2", 50, 2);
        v4 = new Train("Coco3", 50, 3);
        trajet1 = new Trajet(date1, date2, paris,montreal, v3, 0);
        trajet2 = new Trajet(date1, date3, tokyo,londres, v4, 1);
        trajet3 = new Trajet(date1, date4, paris,londres, v2, 2);
        trajet4 = new Trajet(date2, date3, montreal, tokyo, v1, 3);
        trajet.add(trajet1);
        trajet.add(trajet2);
        trajet.add(trajet3);
        trajet.add(trajet4);
    }

}
