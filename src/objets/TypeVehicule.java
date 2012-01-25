package objets;

public enum TypeVehicule {
    avion("avion"),bus("bus"),bateau("bateau"),train("train");
    
    private String nom;

    TypeVehicule(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return nom;
    }
}
