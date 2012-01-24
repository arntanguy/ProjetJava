package objets;

import java.util.Calendar;

public class Passager {
    private String nom;
    private String prenom;
    private Calendar dateNaissance;
    private String profil;
    
    /**
     * @param nom
     * @param prenom
     * @param dateNaissance
     * @param profil
     */
    public Passager(String nom, String prenom, Calendar dateNaissance,
            String profil) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.profil = profil;
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
    public String getProfil() {
        return profil;
    }
}
