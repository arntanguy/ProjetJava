package objets;

public enum TypeVehicule {
    avion("avion",500),bus("bus",2),bateau("bateau",200),train("train",10);
    
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
