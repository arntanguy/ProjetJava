package objets;

import java.io.Serializable;
import java.util.Calendar;

import logiqueMetier.Serveur;

public class Passager implements Serializable {
    private String nom;
    private String prenom;
    private Calendar dateNaissance;
    private Profil profil;
    private boolean fidelite;
    
    /**
     * @param nom
     * @param prenom
     * @param dateNaissance
     * @param profil
     */
    public Passager(String nom, String prenom, Calendar dateNaissance,
            Profil profil, boolean fidelite) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.profil = profil;
        this.fidelite = fidelite;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public Calendar getDateNaissance() {
        return dateNaissance;
    }
    public Profil getProfil() {
        return profil;
    }
    public boolean getFidelite() {
        return fidelite;
    }
    
    public String toString()
    {
        String f=(getFidelite()) ? "(avec la carte fidélité)" :"";
        return getNom()+" "+getPrenom()+" (né le "+Serveur.calendarToDate(getDateNaissance())+") \nTarif : "+getProfil().toString()+" "+f;
    }
    
    public String print() {
        return new StringBuffer().append(nom).append("#").append(prenom).append("#").append(Serveur.calendarToDate(dateNaissance)).append("#").append(profil).append("#").append(fidelite).toString();
    }
}
