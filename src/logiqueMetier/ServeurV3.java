package logiqueMetier;
/**
*Classe qui permet de créer, modifier et lire les fichiers xml
*Elle créer aussi les pages au format HTML
*@author 
*@version 1.0
*/
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Iterator;
import java.io.FileOutputStream;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.filter.*;
import org.jdom.Attribute;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import objets.*;
public class ServeurV3 extends Serveur {
    public ServeurV3() {
        super();
    }
    static org.jdom.Document documentmesVehicules = getDoc("mesVehicule.xml");
    static Element racinemesVehicules = getElem(documentmesVehicules);
    static org.jdom.Document documentmesVilles = getDoc("mesVilles.xml");
    static Element racinemesVilles = getElem(documentmesVilles);
    static org.jdom.Document documentmesTrajets = getDoc("mesTrajets.xml");
    static Element racinemesTrajets = getElem(documentmesTrajets);
    private static int idGeneral=0;
    /**
     * Renvoie un Element construit a partir du fichier a l'adresse donné.
     *@param fichier
     *Adresse du fichier xml a lire.
     *@return L'element contenu dans le fichier xml.
     *@throws JDOMException
     *Si le fichier ne contient pas d'inforamtions xml.
     *@throws IOException
     *Si le fichier est inaccessible.
     */
    static public Element getElem(org.jdom.Document document){
    	try{
	    Element racine = document.getRootElement();
	    return racine;
    	}
        catch(Exception e){
	    System.out.println("erreur" + e.getMessage());
	    return null;
    	}
    }
    static public org.jdom.Document getDoc(String fichier){
    	try{
	    SAXBuilder sxb = new SAXBuilder();
	    org.jdom.Document document = sxb.build(new File(fichier));
	    return  document;
    	}
        catch(Exception e){
	    System.out.println("erreur" + e.getMessage());
	    return null;
    	}
    }
    /**
     *Méthode qui créer les fichiers HTML
     */
    public static void creerHTML(String xml, String xsl, String html) throws Exception{
    	// Création de la source DOM
    	DocumentBuilderFactory fabriqueD = DocumentBuilderFactory.newInstance();
    	DocumentBuilder constructeur = fabriqueD.newDocumentBuilder();
    	File fileXml = new File(xml);
    	org.w3c.dom.Document document =  constructeur.parse(fileXml);
    	Source source = new DOMSource(document);
    	
    	// Création du fichier de sortie
    	File fileHtml = new File(html);
    	Result resultat = new StreamResult(fileHtml);
    	
    	// Configuration du transformer
    	TransformerFactory fabriqueT = TransformerFactory.newInstance();
    	StreamSource stylesource = new StreamSource(xsl);
    	Transformer transformer = fabriqueT.newTransformer(stylesource);
    	transformer.setOutputProperty(OutputKeys.METHOD, "html");
    	
    	// Transformation
    	transformer.transform(source, resultat);
    }
    public void enregistreVehicules(){
    	Vehicule vehicule;
    		for(int i = 0; i <= super.mesVehicules.size(); i++){	
    			vehicule = super.getVehicule(i);
    			Element bal = new Element("vehicule");
    			racinemesVehicules.removeContent(bal);
    			racinemesVehicules.addContent(bal);
    		
    			Attribute classevehicule = new Attribute("id", String.valueOf(vehicule.getIdentifiant()));
    			bal.setAttribute(classevehicule);
    		
    			Attribute classevehicule2 = new Attribute("Type",vehicule.getVehicule());
    			bal.setAttribute(classevehicule2);
    		
    			Element nombresPlaces = new Element("Nombre_de_Places");
    			nombresPlaces.setText(String.valueOf(vehicule.getCapacite()));
    			bal.addContent(nombresPlaces);
    		}
    	enregistreNewVehicule("mesVehicule.xml");	
    }
    static void afficheVehicule() {
		try {
	    	XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    	sortie.output(documentmesVehicules, System.out);
		}
		catch (java.io.IOException e){}
    }
    static void enregistreNewVehicule(String fichier) {
		try {
	    	XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    	sortie.output(documentmesVehicules, new FileOutputStream(fichier));
		}
		catch (java.io.IOException e){}
    }
    public void enregistreVilles(){
    	Ville  ville;
    	for(int i = 0; i <= super.mesVilles.size(); i++){
    		ville = super.getVille(i);
    		Element bal = new Element("ville");
			racinemesVehicules.removeContent(bal);
			racinemesVehicules.addContent(bal);
		
			Attribute classeville = new Attribute("id", String.valueOf(ville.getIdentifiant()));
			bal.setAttribute(classeville);
		
			Attribute classeville2 = new Attribute("Nom",ville.getVille());
			bal.setAttribute(classeville2);
    	}
    	enregistreNewVilles("mesVilles.xml");
    }
    static void afficheVilles() {
		try {
	    	XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    	sortie.output(documentmesVilles, System.out);
		}
		catch (java.io.IOException e){}
    }
    static void enregistreNewVilles(String fichier) {
		try {
	    	XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    	sortie.output(documentmesVilles, new FileOutputStream(fichier));
		}
		catch (java.io.IOException e){}
    }
    public void enregistreTrajets(){
    	Trajet trajet;
    		for(int i = 0; i <= super.mesTrajets.size(); i++){	
    			trajet = super.getTrajet(i);
    			Element bal = new Element("trajet");
    			racinemesTrajets.removeContent(bal);
    			racinemesTrajets.addContent(bal);
    		
    			Attribute classetrajet = new Attribute("id", String.valueOf(trajet.getIdentifiant()));
    			bal.setAttribute(classetrajet);
    		
    			Element dateDepart = new Element("Date_de_Départ");
    			dateDepart.setText(String.valueOf(trajet.getDateDepart()));
    			bal.addContent(dateDepart);
    			
    			Element dateArrivee = new Element("Date_d_Arrivee");
    			dateArrivee.setText(String.valueOf(trajet.getDateArrivee()));
    			bal.addContent(dateArrivee);
    			
    			Element lieuDepart = new Element("Lieu_de_Depart");
    			lieuDepart.setText(String.valueOf(trajet.getDepart()));
    			bal.addContent(lieuDepart);
    			
    			Element lieuArrivee = new Element("Lieu_d_Arrivee");
    			lieuArrivee.setText(String.valueOf(trajet.getArrivee()));
    			bal.addContent(lieuArrivee);
    			
    			Element vehicule = new Element("Vehicule");
    			vehicule.setText(String.valueOf(trajet.getVehicule()));
    			bal.addContent(vehicule);
    			
    			Element placesRestantes = new Element("Places_Restantes");
    			placesRestantes.setText(String.valueOf(trajet.getPlacesRestantes()));
    			bal.addContent(placesRestantes);
    		}
    	enregistreNewTrajets("mesTrajets.xml");	
    }
    static void afficheTrajets() {
		try {
	    	XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    	sortie.output(documentmesTrajets, System.out);
		}
		catch (java.io.IOException e){}
    }
    static void enregistreNewTrajets(String fichier) {
		try {
	    	XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    	sortie.output(documentmesTrajets, new FileOutputStream(fichier));
		}
		catch (java.io.IOException e){}
    }
    /**
     * Lance la sauvegarde des listes de trajet et de véhicule sur le serveur
     * Méthode abstraite implémentée par ServeurV1 ou ServeurV2
     * 
     * @return true si la sauvegarde a réussi, false sinon
     * @throws IOException
     */
    public boolean sauvegarder() throws IOException{}

    /**
     * Lance le chargement des listes de trajet et de véhicule du serveur
     * Méthode abstraite implémentée par ServeurV1 ou ServeurV2
     * 
     * @return true si le chargement a réussi, false sinon
     * @throws Exception
     */
    public boolean charger() throws Exception
    {}
    
    public static boolean verifVehicule(String nom) {
    	if(nom.equals("Avion") || nom.equals("Bateau") || nom.equals("Bus") || nom.equals("Train")) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public static void newVehicule(String nom, int cap){
    	Vehicule vehicule;
    	if(nom.equals("Avion")) {
    		vehicule = new Avion(nom, cap, idGeneral);
    		}
    	else if(nom.equals("Bus")){
    		vehicule = new Bus(nom, cap, idGeneral);
    		}
    	else if(nom.equals("Bateau")){
    		vehicule = new Bateau(nom, cap, idGeneral);
    	}
    	else if(nom.equals("Train")){
    		vehicule = new Train(nom, cap, idGeneral);
    	}
    	else {
    		vehicule = new Avion();
    	}
		Element bal = new Element("vehicule");
		racinemesVehicules.addContent(bal);
	
		Attribute classevehicule = new Attribute("id", String.valueOf(getIdVehicule()));
		bal.setAttribute(classevehicule);
	
		Attribute classevehicule2 = new Attribute("Type",nom);
		bal.setAttribute(classevehicule2);
	
		Element nombresPlaces = new Element("Nombre_de_Places");
		nombresPlaces.setText(String.valueOf(vehicule.getCap()));
		bal.addContent(nombresPlaces);
    }
    
    
    static int getCapVehicule(int id)
    {
	List liste = racineVoyage.getChildren("vehicule");
	//On crée un Iterator sur notre liste
	Iterator i = liste.iterator();
	while(i.hasNext())
	    {
		Element courant = (Element)i.next();
		String id2 = courant.getAttributeValue("id");
		if(id.equals(id2)) {
		    return (Integer.parseInt(courant.getChild("Nombre_de_Places").getText()));
		}
		else {}
	    }
	return -1;
    }
    static int getIdVehicule()
    {
	int indice=0;
	List liste = racineVehicules.getChildren("vehicule");
	Iterator i = liste.iterator();
	while(i.hasNext())
	    {
		Element courant = (Element)i.next();
		indice ++;
	    }
	idGeneral = indice;
	return indice;
    }
    
        
	 static void afficheVoyage() {
	try {
	    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    sortie.output(documentVoyage, System.out);
	}
	catch (java.io.IOException e){}
    }
    static void enregistreVoyage(String fichier) {
	try {
	    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    sortie.output(documentVoyage, new FileOutputStream(fichier));
	}catch (java.io.IOException e){}
    }
      
    public static void newClient(Client client){
	Element clientElt = new Element("Client");
	racineVoyage.addContent(clientElt);
   		
	Attribute clientAttribute = new Attribute("id",client.getId().toString());
	clientElt.setAttribute(clientAttribute);
		
	Element nom = new Element("Nom");
	nom.setText(client.getNom());
	clientElt.addContent(nom);
		
	Element prenom = new Element("Prenom");
	prenom.setText(client.getPrenom());
	clientElt.addContent(prenom);
		
	Element nais = new Element("Date_de_naissance");
	nais.setText(client.getNais().toString());
	clientElt.addContent(nais);
		
	enregistreVoyage("Voyage.xml");
    }
    /**
     *Méthode qui permet d'afficher un avion
     *@param
     @return
    */

    public static void newTrajet(Trajet trajet){
	Element trajetElt = new Element("Trajet");
	racineVoyage.addContent(trajetElt);
	Attribute trajetAttribute = new Attribute("id",trajet.getId().toString());
	trajetElt.setAttribute(trajetAttribute);
		
	Element avion = new Element("idAvion");
	//avion.setText(trajet.getAvion().getIdVehicule());
	trajetElt.addContent(avion);
		
	Element date = new Element("date");
	date.setText(trajet.getDate().toString());
	trajetElt.addContent(date);
		
	Element dateAr = new Element("dateAr");
	dateAr.setText(trajet.getDateAr().toString());
	trajetElt.addContent(dateAr);
		
	Element aeroDep = new Element("aeroDep");
	aeroDep.setText(trajet.getAeroDep());
	trajetElt.addContent(aeroDep);
		
	Element aeroAr = new Element("aeroAr");
	aeroAr.setText(trajet.getAeroAr());
	trajetElt.addContent(aeroAr);
		
	Element nbDispo = new Element("nbDispo");
	nbDispo.setText(String.valueOf(trajet.getNbDispo()));
	trajetElt.addContent(nbDispo);
		
	enregistreVoyage("Voyage.xml");
    }
    /**
     *Méthode qui permet d'afficher le fichier xml du trajet
     *@param
     *@return
     */
    static void afficheTrajet() {
	try {
	    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    sortie.output(documentVoyage, System.out);
	}
	catch (java.io.IOException e){}
    }
    public static void newReservation(Reservation reservation){
	Element reservationElt = new Element("Reservation");
	racineVoyage.addContent(reservationElt);
		
	Attribute classe = new Attribute("id",reservation.getId().toString());
	reservationElt.setAttribute(classe);
		
	Element idClient = new Element("idClient");
	idClient.setText(reservation.getIdClient().toString());
	reservationElt.addContent(idClient);
		
	Element idTrajet = new Element("idTrajet");
	idTrajet.setText(reservation.getIdTrajet().toString());
	reservationElt.addContent(idTrajet);
		
	enregistreVoyage("Voyage.xml");
    }
    /**
     *Méthode qui permet d'afficher le contenu xml d'une reservation
     *@param
     *@return
     */
    static void afficheRes() {
	try {
	    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    sortie.output(documentVoyage, System.out);
	}
	catch (java.io.IOException e) {}
    }
    static boolean verifIdAvion(String id)
    {
	List liste = racineVoyage.getChildren("Avion");
	Iterator i = liste.iterator();
	while(i.hasNext())
	    {
		Element courant = (Element)i.next();
		String id2 = courant.getAttributeValue("idAvion");
		if(id.equals(id2))
		    {
			return true;
		    }
	    }
	return false;
    }
    static boolean verifNom(String nom)
    {
	if(nom.length()>=3)
	    {
		return true;
	    }
	else 
	    {
		return false;
	    }
    }	
    static boolean verifNbPlaces(int places)
    {
	if(places>10 && places<900)
	    {
		return true;
	    }
	else
	    {
		return false;
	    }
    }
    
    static void idClient()
    {
	List liste = racineVoyage.getChildren("Client");
	//On crée un Iterator sur notre liste
	Iterator i = liste.iterator();
	while(i.hasNext())
	    {
		//On recrée l'Element courant à chaque tour de boucle afin de
		//pouvoir utiliser les méthodes propres aux Element comme :
		//selectionner un noeud fils, modifier du texte, etc...
		Element courant = (Element)i.next();
		//On affiche le nom de l'element courant
		System.out.println(courant.getAttributeValue("id"));
	    }
    }
    static void afficheAvion(String elem)
    {
	int indice=0;
	List liste = racineVoyage.getChildren("Avion");
	//On crée un Iterator sur notre liste
	Iterator i = liste.iterator();
	while(i.hasNext())
	    {
		//On recrée l'Element courant à chaque tour de boucle afin de
		//pouvoir utiliser les méthodes propres aux Element comme :
		//selectionner un noeud fils, modifier du texte, etc...
		Element courant = (Element)i.next();
		//On affiche le nom de l'element courant
		System.out.println(indice+" : "+courant.getChild(elem).getText());
		indice++;
	    }
    }
    static void afficheAffTrajet()
    {
	int indice=0;
	List liste = racineVoyage.getChildren("Trajet");
	//On crée un Iterator sur notre liste
	Iterator i = liste.iterator();
	while(i.hasNext())
	    {
		//On recrée l'Element courant à chaque tour de boucle afin de
		//pouvoir utiliser les méthodes propres aux Element comme :
		//selectionner un noeud fils, modifier du texte, etc...
		Element courant = (Element)i.next();
		//On affiche le nom de l'element courant
		System.out.println(indice+" : "+courant.getChild("aeroDep").getText()+"-"+courant.getChild("aeroAr").getText()+"\n"+courant.getChild("date").getText()+courant.getChild("dateAr").getText());
		indice++;
	    }
    }
    static void afficheTrajet(String elem)
    {
	int indice=0;
	List liste = racineVoyage.getChildren("Trajet");
	//On crée un Iterator sur notre liste
	Iterator i = liste.iterator();
	while(i.hasNext())
	    {
		//On recrée l'Element courant à chaque tour de boucle afin de
		//pouvoir utiliser les méthodes propres aux Element comme :
		//selectionner un noeud fils, modifier du texte, etc...
		Element courant = (Element)i.next();
		//On affiche le nom de l'element courant
		System.out.println(indice+" : "+courant.getChild(elem).getText());
		indice++;
	    }
    }
}
}
