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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
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
    org.jdom.Document documentVilles = getDoc("MesVilles.xml");
    Element racineVilles = getElem(documentVilles);
    org.jdom.Document documentVehicules = getDoc("MesVehicules.xml");
    Element racineVehicules = getElem(documentVehicules);
    org.jdom.Document documentTrajets = getDoc("MesTrajets.xml");
    Element racineTrajets = getElem(documentTrajets);
    org.jdom.Document documentReservations = getDoc("MesReservations.xml");
    Element racineReservations = getElem(documentReservations);
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
    public Element getElem(org.jdom.Document document){
    	try{
	    Element racine = document.getRootElement();
	    return racine;
    	}
        catch(Exception e){
	    System.out.println("erreur" + e.getMessage());
	    return null;
    	}
    }
    public org.jdom.Document getDoc(String fichier){
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
    public void enregistreVilles(){
    	Ville ville;
    	racineVilles.removeContent();
    	for(int i = 0; i < super.mesVilles.size(); i++){
    		ville = super.getVille(i);
    		Element bal = new Element("ville");
			
			racineVilles.addContent(bal);
		
			Attribute classeville = new Attribute("id", String.valueOf(ville.getIdentifiant()));
			bal.setAttribute(classeville);
			
			Attribute classeville2 = new Attribute("Nom",ville.getVille());
			bal.setAttribute(classeville2);
    	}
    	enregistreXml("MesVilles.xml", documentVilles);
    }
    public void enregistreVehicules(){
    	Vehicule vehicule;
    	racineVehicules.removeContent();
    		for(int i = 0; i < super.mesVehicules.size(); i++){	
    			vehicule = super.getVehicule(i);
    			Element bal = new Element("vehicule");
    			racineVehicules.addContent(bal);
    		
    			Attribute classevehicule = new Attribute("id", String.valueOf(vehicule.getIdentifiant()));
    			bal.setAttribute(classevehicule);
    		
    			Attribute classevehicule2 = new Attribute("Nom",vehicule.getVehicule());
    			bal.setAttribute(classevehicule2);
    			
    			Element typeVehicule = new Element("typeDuVehicule");
    			String typeDuVehicule = ""+vehicule.getType();
    			typeVehicule.setText(typeDuVehicule);
    			bal.addContent(typeVehicule);
    			
    			Element nombresPlaces = new Element("nombreDePlaces");
    			nombresPlaces.setText(String.valueOf(vehicule.getCapacite()));
    			bal.addContent(nombresPlaces);
    		}
    	enregistreXml("MesVehicules.xml", documentVehicules);	
    }
    public void enregistreTrajets(){
    	Trajet trajet;
    	racineTrajets.removeContent();
    		for(int i = 0; i < super.mesTrajets.size(); i++){	
    			trajet = super.getTrajet(i);
    			Element bal = new Element("trajet");
    			racineTrajets.addContent(bal);
    		
    			Attribute classetrajet = new Attribute("id", String.valueOf(trajet.getIdentifiant()));
    			bal.setAttribute(classetrajet);
    		
    			Element dateDepart = new Element("dateDepart");
    			Calendar dateDeDepart = Calendar.getInstance();
				dateDeDepart = trajet.getDateDepart();
    			dateDepart.setText(super.calendarToDate(dateDeDepart));
    			bal.addContent(dateDepart);
    			
    			Element horaireDepart = new Element("horaireDepart");
    			horaireDepart.setText(super.calendarToTime(dateDeDepart));
    			bal.addContent(horaireDepart);
    			
    			Element dateArrivee = new Element("dateArrivee");
    			Calendar dateDarrivee = Calendar.getInstance();
				dateDarrivee = trajet.getDateArrivee();
    			dateArrivee.setText(super.calendarToDate(dateDarrivee));
    			bal.addContent(dateArrivee);
    			
    			Element horaireArrivee = new Element("horaireArrivee");
    			horaireArrivee.setText(super.calendarToTime(dateDarrivee));
    			bal.addContent(horaireArrivee);
    		
    			Ville ville = trajet.getDepart();
    			Element idDepart = new Element("idVilleDepart");
    			idDepart.setText(String.valueOf(ville.getIdentifiant()));
    			bal.addContent(idDepart);
    			
    			Ville villeAr = trajet.getArrivee();
    			Element idArrivee = new Element("idVilleArrivee");
    			idArrivee.setText(String.valueOf(villeAr.getIdentifiant()));
    			bal.addContent(idArrivee);
    			
    			Element distance = new Element("distance");
    			distance.setText(String.valueOf(trajet.getDistance()));
    			bal.addContent(distance);
    			
    			Vehicule vehiculeDeTransport = trajet.getVehicule();
    			Element idVehicule = new Element("idVehicule");
    			idVehicule.setText(String.valueOf(vehiculeDeTransport.getIdentifiant()));
    			bal.addContent(idVehicule);
    			
    			Element placesRestantes = new Element("placesRestantes");
    			placesRestantes.setText(String.valueOf(trajet.getPlacesRestantes()));
    			bal.addContent(placesRestantes);
    		}
    	enregistreXml("MesTrajets.xml", documentTrajets);	
    }
    public void enregistreReservations(){
    	Reservation reservation;
    	racineReservations.removeContent();
    		for(int i = 0; i < super.mesReservations.size(); i++){	
    			reservation = super.getReservation(i);
    			Element bal = new Element("reservation");
    			racineReservations.addContent(bal);
    		
    			Attribute classetrajet = new Attribute("id", String.valueOf(reservation.getIdentifiant()));
    			bal.setAttribute(classetrajet);
    		
    			Passager passager = reservation.getPassager();
    			Element nomPassager = new Element("nomPassager");
    			nomPassager.setText(passager.getNom());
    			bal.addContent(nomPassager);
    			
    			Element prenomPassager = new Element("prenomPassager");
    			prenomPassager.setText(passager.getPrenom());
    			bal.addContent(prenomPassager);
    			
    			Calendar dateDeNaissance = Calendar.getInstance();
				dateDeNaissance = passager.getDateNaissance();
    			Element dateNaissance = new Element("dateNaissance");
    			dateNaissance.setText(calendarToDate(dateDeNaissance));
    			bal.addContent(dateNaissance);
    			
    			Profil profil = passager.getProfil();
    			Element profilPassager = new Element("profilPassager");
    			profilPassager.setText(profil.getProfil());
    			bal.addContent(profilPassager);
    			
    			Element prix = new Element("prix");
    			prix.setText(String.valueOf(profil.getPrix()));
    			bal.addContent(prix);
    			
    			Element fidelite = new Element("fidelite");
    			fidelite.setText(String.valueOf(passager.getFidelite()));
    			bal.addContent(fidelite);
    		    
    			Trajet trajet = reservation.getTrajet();
    			Element idTrajet = new Element("idTrajet");
    			idTrajet.setText(String.valueOf(trajet.getIdentifiant()));
    			bal.addContent(idTrajet);
    		    
    			Element modifiable = new Element("modifiable");
    			modifiable.setText(String.valueOf(reservation.isModifiable()));
    			bal.addContent(modifiable);
    		    
    			Element placesVoulues = new Element("placesVoulues");
    			placesVoulues.setText(String.valueOf(reservation.getPlacesVoulues()));
    			bal.addContent(placesVoulues);
    		    
    			Element prendCouchette = new Element("prendCouchette");
    			prendCouchette.setText(String.valueOf(reservation.isPrendCouchette()));
    			bal.addContent(prendCouchette);	
    			
    			for(ClassesRepas classe : reservation.getTrajet().getVehicule().getClasses())
    			{
	    				Element prendClasses = new Element(classe.getNom());
	        			prendClasses.setText(String.valueOf(reservation.getClasse(classe.getNom())));
	        			bal.addContent(prendClasses);
    			}
    			
    			for(ClassesRepas repas : reservation.getTrajet().getVehicule().getRepas())
    			{
    				Element prendRepas = new Element(repas.getNom());
        			prendRepas.setText(String.valueOf(reservation.getRepas(repas.getNom())));
        			bal.addContent(prendRepas);
    			}
    		}
    	enregistreXml("MesReservations.xml", documentReservations);	
    }
    public void chargerVilles() throws Exception{
    	List liste = racineVilles.getChildren("ville");
    	//On crée un Iterator sur notre liste
    	Iterator i = liste.iterator();
    	while(i.hasNext())
    	    {
    		Element courant = (Element)i.next();
    		String id = courant.getAttributeValue("id");
    		String nom = courant.getAttributeValue("Nom");
    		addVille(new Ville(nom, Integer.valueOf(id)));
        }
    }
    public void chargerVehicules() throws Exception{
    	List liste = racineVehicules.getChildren("vehicule");
    	//On crée un Iterator sur notre liste
    	Iterator i = liste.iterator();
    	while(i.hasNext())
    	    {
    		Element courant = (Element)i.next();
    		String id = courant.getAttributeValue("id");
    		String nom = courant.getAttributeValue("Nom");
    		String type = courant.getChild("typeDuVehicule").getText();
    		String nbPlaces = courant.getChild("nombreDePlaces").getText();
	    		if(TypeVehicule.valueOf(type)==TypeVehicule.AVION){
	    			addVehicule(new Avion(nom, Integer.valueOf(nbPlaces), Integer.valueOf(id)));
	    		}
	    		else if(TypeVehicule.valueOf(type)==TypeVehicule.BATEAU){
	    			addVehicule(new Bateau(nom, Integer.valueOf(nbPlaces), Integer.valueOf(id)));
	    		}
	    		else if(TypeVehicule.valueOf(type)==TypeVehicule.BUS){
	    			addVehicule(new Bus(nom, Integer.valueOf(nbPlaces), Integer.valueOf(id)));
	    		}
	    		else if(TypeVehicule.valueOf(type)==TypeVehicule.TRAIN){
	    			addVehicule(new Train(nom, Integer.valueOf(nbPlaces), Integer.valueOf(id)));
	    		}
    	    }
    }
    public void chargerTrajets() throws Exception{
    	List liste = racineTrajets.getChildren("trajet");
    	//On crée un Iterator sur notre liste
    	Iterator i = liste.iterator();
    	while(i.hasNext())
    	    {
    		Element courant = (Element)i.next();
    		String id = courant.getAttributeValue("id");
    		String dateDeDepart = courant.getChild("dateDepart").getText();
    		String horaireDeDepart = courant.getChildText("horaireDepart");
    		String dateDArrivee = courant.getChild("dateArrivee").getText();
    		String horaireDArrivee = courant.getChild("horaireArrivee").getText();
    		String idVilleDeDepart = courant.getChild("idVilleDepart").getText();
    		String idVilleDArrivee = courant.getChild("idVilleDepart").getText();
    		String distance = courant.getChild("distance").getText();
    		String idDuVehicule = courant.getChild("idVehicule").getText();
    		String nbPlacesRestantes = courant.getChild("placesRestantes").getText();
    		
    		Calendar dateDepart = Calendar.getInstance();
			dateDepart = textToCalendar(dateDeDepart, horaireDeDepart);
			Calendar dateArrivee = Calendar.getInstance();
			dateArrivee = textToCalendar(dateDArrivee, horaireDArrivee);
			Ville villeDepart = getVille(Integer.valueOf(idVilleDeDepart));
			Ville villeDArrivee = getVille(Integer.valueOf(idVilleDArrivee));
			Vehicule vehicule = getVehicule(Integer.valueOf(idDuVehicule));
			addTrajet(new Trajet(dateDepart, dateArrivee, villeDepart, villeDArrivee, Integer.valueOf(distance), vehicule, Integer.valueOf(id), Integer.valueOf(nbPlacesRestantes)));
	    	
    	    }
    }
    public void chargerReservations() throws Exception{
    	List liste = racineReservations.getChildren("reservation");
    	//On crée un Iterator sur notre liste
    	Iterator i = liste.iterator();
    	while(i.hasNext())
    	    {
    		Element courant = (Element)i.next();    	           
    		String id = courant.getAttributeValue("id");
    		String nomPassager = courant.getAttributeValue("nomPassager");
    		String prenomPassager = courant.getAttributeValue("prenomPassager");
    		String dateNaissance = courant.getAttributeValue("dateNaissance");
    		String profilPassager = courant.getAttributeValue("profil");
    		String prix = courant.getAttributeValue("prix");
    		String fidelite = courant.getAttributeValue("fidelite");
    	    String identifiant = courant.getAttributeValue("identifiant");
    		String placesVoulues = courant.getAttributeValue("placesVoulues");
    		String idTrajet = courant.getAttributeValue("idTrajet");
    		String modifiable = courant.getAttributeValue("modifiable");
    		String prendCouchette = courant.getAttributeValue("prendCouchette");
    		Profil profil = null;
            for (Profil value : Profil.values()) {
                if (profilPassager.equals(String.valueOf(value))) {
                    profil = value;
                }
            }
    		Calendar dateNaissancePassager = Calendar.getInstance();
			dateNaissancePassager = textToCalendar(dateNaissance, "00:00");
    		Passager passager = new Passager(nomPassager, prenomPassager, dateNaissancePassager, profil, Boolean.valueOf(fidelite));
			Trajet trajet = getTrajet(Integer.valueOf(idTrajet));
			
            Map<String, Boolean> prendRepas = new HashMap<String, Boolean>();
            Map<String, Boolean> prendClasses = new HashMap<String, Boolean>();
            for(int j=0;j<trajet.getVehicule().getClasses().size();j++)
            {
            	prendClasses.put(trajet.getVehicule().getClasses().get(j).getNom(), Boolean.valueOf(trajet.getVehicule().getClasses().get(j).getNom().getText()));
            }
			super.addReservation(new Reservation(passager, trajet, Boolean.valueOf(modifiable), Boolean.valueOf(prendCouchette), prendRepas, prendClasses, Integer.valueOf(id), Integer.valueOf(placesVoulues));
    	    }
    }
    void enregistreXml(String fichier, org.jdom.Document doc) {
		try {
	    	XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	    	sortie.output(doc, new FileOutputStream(fichier));
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
    public boolean sauvegarder() throws IOException{
    	enregistreVehicules();
    	enregistreVilles();
    	enregistreTrajets();
    	enregistreReservations();
    	return true;
    }
    /**
     * Lance le chargement des listes de trajet et de véhicule du serveur
     * Méthode abstraite implémentée par ServeurV1 ou ServeurV2
     * 
     * @return true si le chargement a réussi, false sinon
     * @throws Exception
     */
    public boolean charger() throws Exception{
    	chargerVilles();
    	chargerVehicules();
    	chargerTrajets();
    	chargerReservations();
    	
    	return true;
    }
}