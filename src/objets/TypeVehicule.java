package objets;

public enum TypeVehicule {
    AVION("AVION",500),BUS("BUS",2),BATEAU("BATEAU",200),TRAIN("TRAIN",10);
    
    private String nom;
    private int prixStandard;
    
    TypeVehicule(String nom,int prixStandard) {
        this.nom = nom;
        this.prixStandard = prixStandard;
    }
    
    public int getPrix() {
        return prixStandard;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String toString() {
        return nom;
    }
}
