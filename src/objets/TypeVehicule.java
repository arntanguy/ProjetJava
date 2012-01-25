package objets;

public enum TypeVehicule {
    avion(500), bus(2), bateau(200), train(10);

    private int prixStandard;

    /**
     * Initialise avec le mot de commande correspondant.
     * 
     * @param commandString
     *            Le String de commande.
     */
    TypeVehicule(int prixStandard) {
        this.prixStandard = prixStandard;
    }

    public int getPrix() {
        return prixStandard;
    }

}
