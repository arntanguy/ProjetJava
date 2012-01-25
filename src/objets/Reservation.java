package objets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import logiqueMetier.Serveur;

public class Reservation {
    private Passager passager;
    private Trajet trajet;
    private boolean modifiable;

    private boolean prendCouchette;
    private Map<String, Boolean> prendRepas;
    private Map<String, Boolean> prendClasses;

    /**
     * @param passager
     * @param trajet
     * @param modifiable
     * @param prendCouchette
     * @param prendRepas
     * @param prendClasses
     */
    public Reservation(Passager passager, Trajet trajet, boolean modifiable,
            boolean prendCouchette, Map<String, Boolean> prendRepas,
            Map<String, Boolean> prendClasses) {
        this.passager = passager;
        this.trajet = trajet;
        this.modifiable = modifiable;
        this.prendCouchette = prendCouchette;
        this.prendRepas = prendRepas;
        this.prendClasses = prendClasses;
    }

    public void genereTicket() {
            // Create file
            FileWriter fstream = null;
            try {
                fstream = new FileWriter("ticketReservation"+passager.getNom()+"$"+passager.getPrenom()+".html");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            BufferedWriter out = new BufferedWriter(fstream);
            String mod=(modifiable) ? "Ticket Modifiable" : "";
            String couchette=(prendCouchette) ? "Avec couchette" : "";
            String classes="";
            if(prendClasses!=null)
            {
                for(String key :prendClasses.keySet())
                {
                    if(prendClasses.get(key) !=null && prendClasses.get(key)==true)
                    {
                        classes+=key+"<br />";
                    }
                }
            }
            
            String repas="";
            if(prendRepas!=null)
            {
                for(String key :prendRepas.keySet())
                {
                    if(prendRepas.get(key) !=null && prendRepas.get(key)==true)
                    {
                        repas+=key+"<br />";
                    }
                }
            }
            
            try {
                out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\"\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">" +
                		"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"fr\" >" +
                		"<head><title>Ticket de réservation</title>" +
                		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
                		"<link rel=\"schema.DC\" href=\"http://purl.org/dc/elements/1.1/\" />" +
                		"<meta name=\"DC.title\"       content=\"Votre ticket de réservation\" />" +
                		"<meta name=\"DC.description\" content=\"Votre ticket de réservation\" />" +
                		"<meta name=\"DC.language\"    content=\"fr\" />" +
                		"<meta name=\"DC.keywords\"    content=\"ticket; réservation\" />" +
                		"</head><body><h1>Votre réservation</h1>" +
                		"Passager : "+passager+" "+
                		"Trajet : "+trajet+" "+
                		mod+"<br />"+couchette+"<br />"+
                		"Repas : "+repas+"<br />"+
                		"Classes : "+classes+"<br />"+
                		"Synthèse des prix : "+
                		getPrix()+
                        "</body></html>");
            
            // Close the output stream
            out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.err.println("IO exception");
                e.printStackTrace();
            }
    }
    
    public String getPrix()
    {
        int prix=0;
        int reductionFidelite=0;
        int prixModifiable=5;
        int prixCouchette=5;
        int repasTotal=0;
        int classesTotal=0;
        
        prix+=passager.getProfil().getPrix(); //prix en fonction du type de passager
        if(passager.getFidelite()==true) //on baisse si il est fidèle
        {
            reductionFidelite=10;
            prix-=reductionFidelite;
        }
        prix+=trajet.getVehicule().getType().getPrix(); //prix en fonction du type de transport
        for(ClassesRepas cr:trajet.getVehicule().getRepas())
        {
            repasTotal+=cr.getPrix();
            prix+=cr.getPrix();
        }
        for(ClassesRepas cr:trajet.getVehicule().getClasses())
        {
            classesTotal+=cr.getPrix();
            prix+=cr.getPrix();
        }
        if(modifiable)
        {
            prix+=prixModifiable;
        }
        if(prendCouchette)
        {
            prix+=prixCouchette;
        }
        
        String texte="prix passager="+passager.getProfil().getPrix()+" euros<br/>";
        texte+="réduction fidèlité="+reductionFidelite+" euros<br/>";
        texte+="prix type de transport="+trajet.getVehicule().getType().getPrix()+" euros<br/>";
        texte+="prix repas="+repasTotal+" euros<br/>";
        texte+="prix classe="+classesTotal+" euros<br/>";
        texte+="supplément changement du billet="+prixModifiable+" euros<br/>";
        texte+="prix couchette="+prixCouchette+" euros<br/>";
        texte+="prix total="+prix+" euros<br/>";
        return texte;
    }
}