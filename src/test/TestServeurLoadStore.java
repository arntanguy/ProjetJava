package test;

import static org.junit.Assert.*;

import java.util.Calendar;

import objets.Avion;
import objets.Trajet;
import objets.Vehicule;
import objets.Ville;

import org.junit.Before;
import org.junit.Test;

import logiqueMetier.*;


public class TestServeurLoadStore {
    Serveur s1 = null;
    Serveur s2 = null;
    Serveur s3 = null;
    private Trajet       trajet1;
    private Calendar     date1, date2;
    private Ville        paris, montreal;
    private Vehicule     v1;

    @Before
    public void setUp() {
        s1 = new ServeurV1();
        s2 = new ServeurV2();
        s3 = new ServeurV3();
        date1 = Calendar.getInstance();
        date2 = Calendar.getInstance();
        date1.set(2012, Calendar.DECEMBER, 13);
        date2.set(2012, Calendar.DECEMBER, 14);
        paris = new Ville("Paris", 0);
        montreal = new Ville("Montreal", 1);
        v1 = new Avion("Coco", 50, 0);
    }
    
    @Test
    public void TestServeurV1() throws Exception{
        s1.addVehicule(v1);
        trajet1 = new Trajet(date1, date2, paris, montreal, 20, v1, 5, false);
        s1.addTrajet(trajet1);
        s1.sauvegarder();
        s1.removeTrajet(trajet1);
        s1.charger();
        
        assertEquals(5, s1.getTrajets().get(0).getIdentifiant());
    }
    
    @Test
    public void TestServeurV2() throws Exception{
        s2.addVehicule(v1);
        trajet1 = new Trajet(date1, date2, paris, montreal, 20, v1, 5, false);
        s2.addTrajet(trajet1);
        s2.sauvegarder();
        s2.removeTrajet(trajet1);
        s2.charger();
        
        assertEquals(5, s2.getTrajets().get(0).getIdentifiant());
    }
    
    @Test
    public void TestServeurV3() throws Exception{
        s3.addVehicule(v1);
        trajet1 = new Trajet(date1, date2, paris, montreal, 20, v1, 5, false);
        s3.addTrajet(trajet1);
        s3.sauvegarder();
        s3.removeTrajet(trajet1);
        s3.charger();
        
        assertEquals(5, s3.getTrajets().get(0).getIdentifiant());
    }
}
