package objets;

import java.util.Calendar;

public class Passager {
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
}
