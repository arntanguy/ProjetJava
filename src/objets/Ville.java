package objets;

public enum Ville {
    paris("Paris"),marseille("Marseille"),nice("Nice"),bordeaux("Bordeaux"),tokyo("Tokyo"),newYork("New York");
    
    // Le String de commande.
    private String ville;

    /**
     * Initialise avec le mot de commande correspondant.
     * 
     * @param commandString
     *            Le String de commande.
     */
    Ville(String ville) {
        this.ville= ville;
    }

    /**
     * @return Le mot de commande en tant que String.
     */
    public String toString() {
        return ville;
    }
}
