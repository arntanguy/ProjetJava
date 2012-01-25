package objets;

public enum TypeVehicule {
    AVION("avion",500),BUS("bus",2),BATEAU("bateau",200),TRAIN("train",10);
    
    private String nom;
    private int prixStandard;
    
    TypeVehicule(String nom,int prixStandard) {
        this.nom = nom;
        this.prixStandard = prixStandard;
    }
    
    public int getPrix() {
        return prixStandard;
    }
    
    public String toString() {
        return nom;
    }
}
