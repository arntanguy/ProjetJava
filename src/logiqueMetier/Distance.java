package logiqueMetier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objets.Trajet;

public class Distance {

    private List<Trajet> list; // c[i][j] = le cout de la connextion directe de i à j
                       // si elle existe, l'infini sinon
    private List<Trajet> listeTrajetsChemin;
    private Trajet[][] matrice;
    private int sizeTrajets;
    private int sizeVilles;
    
    /**
     * Construit un objet qui représente les connexions directes possibles.
     * Paramètre : le tableau connexion tel que connexion[i][j] = le coût de
     * la connexion directe de i à j. S'il n'y a pas de connexion directe de
     * i à j, connexion[i][j] == INFINI.
     */
    public Distance(List<Trajet> list,int sizeTrajets,int sizeVilles) {
        this.list=list;
        listeTrajetsChemin=new ArrayList<Trajet>();
        this.sizeTrajets=sizeTrajets;
        this.sizeVilles=sizeVilles;
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
 
    /**
     * Calcule le coût minimum de la connexion du site i au site j, en passant
     * éventuellement par d'autres sites.
     * Complexité : O(3^n) où n = le nombre de sites.
     */
    public List<Trajet> cout(int i, int j) {
        return cout(i,j,sizeVilles);
    }

    /**
     * Calcule le coût minimum de la connexion du site i au site j, en passant
     * éventuellement par des sites appartenant à [0..k-1].
     * Complexité : O(3^k).
     */
    private List<Trajet> cout(int i, int j, int k) {
        if ( k == 0 ) {
            //System.out.println("i="+i+",j="+j);
            List<Trajet> listeMatrice=new ArrayList<Trajet>();
            if(matrice[i][j]!=null)
                listeMatrice.add(matrice[i][j]);
            
            return listeMatrice;
        }
        List<Trajet> cout1=cout(i,j,k-1);
        List<Trajet> cout2=plus(cout(i,k-1,k-1),cout(k-1,j,k-1));
        List<Trajet> cout=min(cout1,cout2);
        
        /*if(cout==cout1)
        {
            minTrajet(i,j);
        }
        else
        {
            minTrajet(i,k-1);
            minTrajet(k-1,j);
        }*/
        
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
    
    private void minTrajet(int i, int j)
    {
        if(matrice[i][j]==null)
            return;
        List<Trajet> listeTrajet=new ArrayList<Trajet>();
        for(Trajet trajet:list)
        {
            if(trajet.getDepart().getIdentifiant()==i && trajet.getArrivee().getIdentifiant()==j)
                listeTrajet.add(trajet);
        }
        
        int distanceMin=listeTrajet.get(0).getDistance();
        int idMin=0;
        System.out.println("HELLO 0");
        for(int k=0; k<listeTrajet.size();k++)
        {
            System.out.println("HELLO 0");
            if(listeTrajet.get(k).getDistance()<distanceMin)
            {
                System.out.println("HELLO 1");
                distanceMin=listeTrajet.get(k).getDistance();
                idMin=k;
            }
        }
        if(!listeTrajetsChemin.contains(listeTrajet.get(idMin)))
            listeTrajetsChemin.add(listeTrajet.get(idMin));
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
        trajets.addAll(a);
        trajets.addAll(b);
        return trajets;
    }

    public List<Trajet> getListeTrajetsChemin() {
        return listeTrajetsChemin;
    }
}