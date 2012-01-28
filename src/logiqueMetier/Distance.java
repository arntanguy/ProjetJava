package logiqueMetier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objets.Trajet;

public class Distance {

    private List<Trajet> list; // c[i][j] = le cout de la connextion directe de i à j
                       // si elle existe, l'infini sinon
    private Trajet[][] matrice;
    private int sizeTrajets;
    private int sizeVilles;
    private int departId;
    private int arriveeId; 
    private int intervalleVoulue; 
    private Calendar dateDepart;
    /**
     * Construit un objet qui représente les connexions directes possibles.
     * Paramètre : le tableau connexion tel que connexion[i][j] = le coût de
     * la connexion directe de i à j. S'il n'y a pas de connexion directe de
     * i à j, connexion[i][j] == INFINI.
     */
    public Distance(List<Trajet> list,int sizeTrajets,int sizeVilles,int departId,int arriveeId,int intervalleVoulue,Calendar dateDepart) {
        this.list=list;
        this.sizeTrajets=sizeTrajets;
        this.sizeVilles=sizeVilles;
        this.departId=departId;
        this.arriveeId=arriveeId;
        this.intervalleVoulue=intervalleVoulue;
        this.dateDepart=dateDepart;
        
        matrice=new Trajet[sizeVilles][sizeVilles];
        
        for(int i=0;i<sizeVilles;i++)
        {
            for(int j=0;j<sizeVilles;j++)
            {
                matrice[i][j]=null;
            }
        }
        
        for(int i=0;i<sizeTrajets;i++)
        {
            if(list.get(i)!=null)
            {
                Calendar departRetard = (Calendar) list.get(i)
                        .getDateDepart().clone();
                departRetard.add(Calendar.HOUR, intervalleVoulue);
                Calendar departAvance = (Calendar) list.get(i)
                        .getDateDepart().clone();
                departAvance.add(Calendar.HOUR, -intervalleVoulue);
                
                if(list.get(i).getDepart().getIdentifiant()!=departId)
                {
                    if(matrice[list.get(i).getDepart().getIdentifiant()][list.get(i).getArrivee().getIdentifiant()]==null)
                        matrice[list.get(i).getDepart().getIdentifiant()][list.get(i).getArrivee().getIdentifiant()]=list.get(i);
                    else
                    {
                        if(matrice[list.get(i).getDepart().getIdentifiant()][list.get(i).getArrivee().getIdentifiant()].getDistance()>list.get(i).getDistance())
                        {
                            matrice[list.get(i).getDepart().getIdentifiant()][list.get(i).getArrivee().getIdentifiant()]=list.get(i);
                        }       
                    }
                }
                else
                {
                    if(dateDepart.before(departRetard) && dateDepart.after(departAvance))
                    {
                        if(matrice[list.get(i).getDepart().getIdentifiant()][list.get(i).getArrivee().getIdentifiant()]==null)
                            matrice[list.get(i).getDepart().getIdentifiant()][list.get(i).getArrivee().getIdentifiant()]=list.get(i);
                        else
                        {
                            if(matrice[list.get(i).getDepart().getIdentifiant()][list.get(i).getArrivee().getIdentifiant()].getDistance()>list.get(i).getDistance())
                            {
                                matrice[list.get(i).getDepart().getIdentifiant()][list.get(i).getArrivee().getIdentifiant()]=list.get(i);
                            }
                        }
                    }
                }
                
                
            }
        }
        
    }
 
    /**
     * Calcule le coût minimum de la connexion du site i au site j, en passant
     * éventuellement par d'autres sites.
     * Complexité : O(3^n) où n = le nombre de sites.
     */
    public List<Trajet> cout() {
        if(matrice[list.get(departId).getDepart().getIdentifiant()][list.get(departId).getArrivee().getIdentifiant()]!=null)
            return cout(departId,arriveeId,sizeVilles);
        else 
            return null;
    }

    /**
     * Calcule le coût minimum de la connexion du site i au site j, en passant
     * éventuellement par des sites appartenant à [0..k-1].
     * Complexité : O(3^k).
     */
    private List<Trajet> cout(int i, int j, int k) {
        if ( k == 0 ) {
            List<Trajet> listeMatrice=new ArrayList<Trajet>();
            if(matrice[i][j]!=null)
                listeMatrice.add(matrice[i][j]);
            
            return listeMatrice;
        }
        List<Trajet> cout1=cout(i,j,k-1);
        List<Trajet> cout2=plus(cout(i,k-1,k-1),cout(k-1,j,k-1));
        List<Trajet> cout=min(cout1,cout2);
        

        return cout;
    }

    /**
     * Calcule le minimum entre a et b où a et b sont des entiers positifs
     * ou nul ou bien égaux à l'infini.
     */
    private List<Trajet> min(List<Trajet> a, List<Trajet> b) {
        if ( a == null || a.size()==0) {
            return b;
        }
        if ( b == null || b.size()==0) {
            return a;
        }
        int aDistance=0;
        for(Trajet t:a)
        {
            aDistance+=t.getDistance();
        }
        
        int bDistance=0;
        for(Trajet t:b)
        {
            bDistance+=t.getDistance();
        }
        
        if ( aDistance < bDistance ) {
            return a;
        }
        return b;
    }

    /**
     * Calcule la somme de a et de b où a et b sont des entiers positifs
     * ou nul ou bien égaux à l'infini.
     */
    private List<Trajet> plus(List<Trajet> a, List<Trajet> b) {
        if ( a == null  || a.size()==0) {
            return null;
        }
        if ( b == null  || b.size()==0) {
            return null;
        }
        
        List<Trajet> trajets = new ArrayList<Trajet>();
        if(a.get(a.size()-1).getDateArrivee().before(b.get(0).getDateDepart()))
        {
            trajets.addAll(a);
            trajets.addAll(b);
        }
        return trajets;
    }
}