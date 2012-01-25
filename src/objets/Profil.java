package objets;

public enum Profil {
    enfant("Enfant",1),enfantNonAccompagne("Enfant non accompagné",1),jeune("Jeune",2),etudiant("Étudiant",3),adulte("Adulte",5),senior("Sénior",4),couple("Couple",7),famille("Famille",10);
    
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
