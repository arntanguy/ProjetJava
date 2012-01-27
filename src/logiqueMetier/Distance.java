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
    private int[][] matrice;
    private int size;

    private static int INFINI = -1;

    /**
     * Construit un objet qui représente les connexions directes possibles.
     * Paramètre : le tableau connexion tel que connexion[i][j] = le coût de
     * la connexion directe de i à j. S'il n'y a pas de connexion directe de
     * i à j, connexion[i][j] == INFINI.
     */
    public Distance(List<Trajet> list,int size) {
        this.list=list;
        listeTrajetsChemin=new ArrayList<Trajet>();
        this.size=size;
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(i==list.get(i).getDepart().getIdentifiant() && j==list.get(j).getArrivee().getIdentifiant())
                    matrice[i][j]=list.get(i).getDistance();
                else
                    matrice[i][j]=INFINI;
            }
        }
        
    }
 
    /**
     * Calcule le coût minimum de la connexion du site i au site j, en passant
     * éventuellement par d'autres sites.
     * Complexité : O(3^n) où n = le nombre de sites.
     */
    public int cout(int i, int j) {
        return cout(i,j,size-1);
    }

    /**
     * Calcule le coût minimum de la connexion du site i au site j, en passant
     * éventuellement par des sites appartenant à [0..k-1].
     * Complexité : O(3^k).
     */
    private int cout(int i, int j, int k) {
        if ( k == 0 ) {
            List<Trajet> listeTrajet=new ArrayList<Trajet>();
            for(Trajet trajet:list)
            {
                if(trajet.getDepart().getIdentifiant()==i && trajet.getArrivee().getIdentifiant()==j)
                    listeTrajet.add(trajet);
            }
            return minTrajet(listeTrajet);
        }
        return min( cout(i,j,k-1), plus( cout(i,k-1,k-1), cout(k-1,j,k-1) ));
    }

    /**
     * Calcule le minimum entre a et b où a et b sont des entiers positifs
     * ou nul ou bien égaux à l'infini.
     */
    private int min(int a, int b) {
        if ( a == INFINI ) {
            return b;
        }
        if ( b == INFINI ) {
            return a;
        }
        if ( a < b ) {
            return a;
        }
        return b;
    }
    
    private int minTrajet(List<Trajet> listeTrajet)
    {
        if(listeTrajet.size()==0)
            System.out.println("HELLO");
        int distanceMin=listeTrajet.get(0).getDistance();
        int idMin=0;
        for(int i=0; i<listeTrajet.size();i++)
        {
            if(listeTrajet.get(i).getDistance()<distanceMin)
            {
                distanceMin=listeTrajet.get(i).getDistance();
                idMin=i;
            }
        }
        listeTrajetsChemin.add(listeTrajet.get(idMin));
        return distanceMin;
    }

    /**
     * Calcule la somme de a et de b où a et b sont des entiers positifs
     * ou nul ou bien égaux à l'infini.
     */
    private int plus(int a, int b) {
        if ( a == INFINI ) {
            return INFINI;
        }
        if ( b == INFINI ) {
            return INFINI;
        }
        return a + b;
    }

    public List<Trajet> getListeTrajetsChemin() {
        return listeTrajetsChemin;
    }
}