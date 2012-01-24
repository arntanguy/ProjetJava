package objets;

import java.util.Calendar;

import logiqueMetier.Serveur;

/**
*Classe qui permet de créer et gérer un client
*@author Manier Vincent -- Fauvel-Jaeger Olivier
*@version 1.0
*/
public class Client {
	
    private String nom;
    private String prenom;
    private Calendar nais;
    private int id;
    /**
     *Contructeur générique
     *Créé un client avec un nom, un prenom et sa date de naissance.
     *@param String : nom, String : prenom, DateNais : Date de naissance.
     *@return
     */
    public Client(String nom, String prenom, Calendar nais,int id) {
	this.nom = nom;
	this.prenom = prenom;
	this.nais = nais;
	this.id = id;
    }
    /**
     *Constructeur par défaut
     *@param String : nom, String : prenom
     */
    public Client(String nom, String prenom) {
	this.nom = nom;
	this.prenom = prenom;
	this.nais = Serveur.textToCalendar("00/00/0000","00:00");
    }
    public String getNom() {
	return nom;
    }
    /**
     *Méthode qui renvoie le prénom d'une personne
     *@param
     *@return String : prenom de la personne
     */
    public String getPrenom() {
	return prenom;
    }
    /**
     *Méthode qui renvoie l'id d'une personne
     *@param
     *@return String : id de la personne
     */
    public int getId() {
	return id;
    }
    /**
     *Méthode qui modifie le nom d'une personne
     *@param String : nom
     *@return
     */
    public void setNom(String nom) {
	this.nom = nom;
    }
    /**
     *Méthode qui modifie le prenom d'une personne 
     *@param String : prenom de la personne
     *@return
     */
    public void setPrenom(String prenom) {
	this.prenom = prenom;
    }
    /**
     *Méthode qui modifie l'id d'une personne
     *@param UUID : id
     *@return
     */
    public void setId(int id) {	
	this.id=id;
    }
    /**
     *Méthode qui modifie la date de naissance entière
     *@param DateNais
     *@return
     */
    public void setNais(Calendar nais) {
	this.nais = nais;
    }
    /**
     *Méthode qui renvoie la date de naissance d'une personne
     *@param
     *@return DateNais : date de naissance sous forme d'un toString.
     */
    public Calendar getNais() {
	return nais;
    }
    /**Méthode qui permet d'afficher un Client
     *@param
     *@return String : toString
     */
    public String toString() {
	String aux =	"Mme/Mr "+ nom + " " + prenom + "\n";
	if(this.nais !=null) {
	    aux = aux + "Date de naissance : " + this.nais.toString() + "\n";
	}
	aux = aux + "ID : " + id + "\n";
	return aux;
    }
}
