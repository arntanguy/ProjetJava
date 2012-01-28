package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import logiqueMetier.Serveur;
import logiqueMetier.ServeurV1;

import objets.Avion;
import objets.Bateau;
import objets.Bus;
import objets.Train;
import objets.Trajet;
import objets.Vehicule;
import objets.Ville;

import org.junit.Before;
import org.junit.Test;

public class TestServeur {
    private Trajet       trajet1, trajet2, trajet3, trajet4;
    private Calendar     date1, date2, date3, date4;
    private Ville        paris, montreal, tokyo, londres;
    private Vehicule     v1, v2, v3, v4;
    Serveur s;

    @Before
    public void setUp() throws Exception {
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
        v4 = new Train("Coco3", 10, 3);
    }
    
    @Test
    public void TestServeurConflit() throws Exception {
        s = new ServeurV1();
        trajet3 = new Trajet(date1, date4, paris, londres, 100, v2, 2, false);
        trajet4 = new Trajet(date2, date3, montreal, tokyo, 140, v2, 3, true);
        s.addVehicule(v2);
        s.addTrajet(trajet3);
        assertEquals(true, s.checkConflict(trajet4));
        s.addVehicule(v1);
        trajet4 = new Trajet(date2, date3, montreal, tokyo, 140, v1, 3, true);
        assertEquals(false, s.checkConflict(trajet4));
    }
    
    @Test
    public void TestServeurOrdre(){
        s = new ServeurV1();
        trajet1 = new Trajet(date2, date1, paris, montreal, 20, v3, 0, false);
        trajet2 = new Trajet(date1, date3, tokyo, londres, 60, v4, 1, true);
        assertTrue(s.checkOrdre(trajet2));
        assertFalse(s.checkOrdre(trajet1));
    }

    @Test
    public void TestServeurReserver() throws Exception{
        s = new ServeurV1();
        s.addVehicule(v4);
        trajet2 = new Trajet(date1, date3, tokyo, londres, 60, v4, 1, true);
        s.addTrajet(trajet2);
        assertEquals(10, trajet2.getVehicule().getCapacite());
        assertEquals(10, trajet2.getPlacesRestantes());
        assertEquals(true, trajet2.restePlaces(1));
        assertTrue(s.reserver(trajet2, 1));
        assertFalse(s.reserver(trajet2, 20));
    }
    
    @Test
    public void TestServeurRecherche() throws Exception{
        s = new ServeurV1();
        trajet3 = new Trajet(date1, date4, paris, londres, 100, v2, 2, false);
        trajet4 = new Trajet(date2, date3, montreal, tokyo, 140, v1, 3, true);
        s.addVehicule(v1);
        s.addVehicule(v2);
        s.addTrajet(trajet3);
        s.addTrajet(trajet4);
        List<Trajet> trajet = s.rechercherTrajet(paris, londres, v2, 0, date1, 12, false, false, true);
        assertEquals(2, trajet.get(0).getIdentifiant());       
        
    }
    
    @Test
    public void TestServeurRemoveV() throws Exception{
        s = new ServeurV1();
        s.addVehicule(v1);
        s.addVehicule(v2);
        s.addVehicule(v3);
        s.addVehicule(v4);
        s.removeVehicule(1);
        assertEquals(null, s.getVehicule(v1.getIdentifiant()));
        trajet1 = new Trajet(date1, date2, paris, montreal, 20, v3, 0, false);
        trajet2 = new Trajet(date1, date2, tokyo, londres, 60, v2, 1, true);
        trajet3 = new Trajet(date3, date4, paris, londres, 100, v2, 2, false);
        trajet4 = new Trajet(date2, date3, montreal, tokyo, 140, v1, 3, true);
        s.addTrajet(trajet1);
        s.addTrajet(trajet2);
        s.addTrajet(trajet3);
        s.addTrajet(trajet4);
    }
    
    @Test
    public void TestServeurModifier() throws Exception{
        s = new ServeurV1();
        s.addVehicule(v1);
        s.addVehicule(v2);
        s.addVehicule(v3);
        trajet1 = new Trajet(date1, date2, paris, montreal, 20, v3, 0, false);
        trajet2 = new Trajet(date1, date2, tokyo, londres, 60, v2, 1, true);
        trajet3 = new Trajet(date3, date4, paris, londres, 100, v2, 2, false);
        trajet4 = new Trajet(date2, date3, montreal, tokyo, 140, v1, 3, true);
        s.addTrajet(trajet1);
        s.addTrajet(trajet2);
        s.addTrajet(trajet3);
        s.modifierTrajet(trajet3, trajet4);
        assertEquals(3, s.getTrajets().get(2).getIdentifiant());
        s.modifierVehicule(v3, v4);
        assertEquals(3, s.getVehicules().get(2).getIdentifiant());
        s.addVille(londres);
        s.addVille(paris);
        s.addVille(montreal);
        s.modifierVille(montreal, tokyo);
        assertEquals(2, s.getVilles().get(2).getIdentifiant());
    }
}
