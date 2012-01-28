package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import logiqueMetier.*;
import objets.Avion;
import objets.Bateau;
import objets.Bus;
import objets.Train;
import objets.Trajet;
import objets.Vehicule;
import objets.Ville;

import org.junit.Before;
import org.junit.Test;


public class TestInteger {
    private List<Trajet> trajet;
    private Trajet       trajet1, trajet2, trajet3, trajet4;
    private Calendar     date1, date2, date3, date4;
    private Ville        paris, montreal, tokyo, londres;
    private Vehicule     v1, v2, v3, v4;
    Serveur s;
    Admin a;

    @Before
    public void setUp() throws Exception {
        trajet = new ArrayList<Trajet>();
        date1 = Calendar.getInstance();
        date2 = Calendar.getInstance();
        date3 = Calendar.getInstance();
        date4 = Calendar.getInstance();
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
        trajet1 = new Trajet(date1, date2, paris, montreal, 20, v3, 0, false);
        trajet2 = new Trajet(date1, date3, tokyo, londres, 60, v4, 1, true);
        trajet3 = new Trajet(date1, date4, paris, londres, 100, v2, 2, false);
        trajet4 = new Trajet(date2, date3, montreal, tokyo, 140, v1, 3, true);
        trajet.add(trajet4);
        trajet.add(trajet2);
        trajet.add(trajet3);
        trajet.add(trajet1);
    }

    @Test
    public void testCompareInteger(){
        assertEquals(3, trajet.get(0).getIdentifiant());
        assertEquals(1, trajet.get(1).getIdentifiant());
        assertEquals(2, trajet.get(2).getIdentifiant());
        assertEquals(0, trajet.get(3).getIdentifiant());
        Collections.sort(trajet, new CompareInteger());
        assertEquals(0, trajet.get(0).getIdentifiant());
        assertEquals(1, trajet.get(1).getIdentifiant());
        assertEquals(2, trajet.get(2).getIdentifiant());
        assertEquals(3, trajet.get(3).getIdentifiant());
    }
}
