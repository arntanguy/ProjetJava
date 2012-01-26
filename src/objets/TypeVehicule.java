package objets;

public enum TypeVehicule {
    AVION("avion"), BUS("bus"), BATEAU("bateau"), TRAIN("train");

    private String nom;

    TypeVehicule(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
