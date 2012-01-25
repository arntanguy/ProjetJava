package objets;

public enum Profil {
    ENFANT("Enfant",1),ENFANTNONACCOMPAGNE("Enfant non accompagné",1),JEUNE("Jeune",2),
    ETUDIANT("Étudiant",3),ADULTE("Adulte",5),SENIOR("Sénior",4),COUPLE("Couple",7),FAMILLE("Famille",10);
    
 // Le String de commande.
    private String commandString;
    private int prixStandard;
    
    /**
     * Initialise avec le mot de commande correspondant.
     * 
     * @param commandString
     *            Le String de commande.
     */
    Profil(String commandString, int prixStandard) {
        this.commandString = commandString;
        this.prixStandard= prixStandard;
    }
    
    public int getPrix()
    {
        return prixStandard;
    }

    /**
     * @return Le mot de commande en tant que String.
     */
    public String toString() {
        return commandString;
    }
}
