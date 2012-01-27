package objets;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Map;

import logiqueMetier.Serveur;

public class Reservation implements Serializable {
    private Passager passager;
    private Trajet trajet;
    private boolean modifiable;
    private int identifiant;
    private int placesVoulues;
    private boolean prendCouchette;
    private String nomTicket;
    private Map<String, Boolean> prendRepas;

    /**
     * @param passager
     * @param trajet
     * @param modifiable
     * @param prendCouchette
     * @param prendRepas
     * @param prendClasses
     */
    public Reservation(Passager passager, Trajet trajet, boolean modifiable,
            boolean prendCouchette, Map<String, Boolean> prendRepas, int identifiant,int placesVoulues) {
        this.passager = passager;
        this.trajet = trajet;
        this.modifiable = modifiable;
        this.prendCouchette = prendCouchette;
        this.prendRepas = prendRepas;
        this.identifiant = identifiant;
        this.placesVoulues = placesVoulues;
        this.nomTicket="";
    }

    public Passager getPassager() {
        return passager;
    }

    public boolean isPrendCouchette() {
        return prendCouchette;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public int getIdentifiant() {
        return identifiant;
    }
    public void lanceTicketReservation(String ticket){
    	if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {				
				try {
					desktop.open(new File(nomTicket));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
    }
    public void genereTicket() {
        // Create file
        FileWriter fstream = null;
        try {
            fstream = new FileWriter("ticketReservation" + passager.getNom()
                    + "$" + passager.getPrenom() + ".html");
            nomTicket = "ticketReservation" + passager.getNom()+ "$" + passager.getPrenom() + ".html";
            System.out.println(nomTicket);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BufferedWriter out = new BufferedWriter(fstream);
        String mod = (modifiable) ? "Ticket Modifiable" : "";
        String couchette = (prendCouchette) ? "Avec couchette" : "";

        String repas = "";
        if (prendRepas != null) {
            for (String key : prendRepas.keySet()) {
                if (prendRepas.get(key) != null && prendRepas.get(key) == true) {
                    repas += "<th>"+key+"</th>";
                }
            }
        }

        try {
            out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\"\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">"
                    + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"fr\" >"
                    + "<head><title>Ticket de réservation</title>"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
                    + "<link rel=\"schema.DC\" href=\"http://purl.org/dc/elements/1.1/\" />"
                    + "<meta name=\"DC.title\"       content=\"Votre ticket de réservation\" />"
                    + "<meta name=\"DC.description\" content=\"Votre ticket de réservation\" />"
                    + "<meta name=\"DC.language\"    content=\"fr\" />"
                    + "<meta name=\"DC.keywords\"    content=\"ticket; réservation\" />"
                    + "<link rel=\"stylesheet\" media=\"screen\" type=\"text/css\" title=\"Ticket Reservation\" href=\"feuille.css\"/>"
                    + "</head><body><table><tr><th colspan=\"7\"><h1>Votre réservation (n° de réservation = "
                    + identifiant
                    + ") pour "+placesVoulues+" place(s)</h1></th></tr>"
                    + "<tr><th colspan=\"7\"><h2>Passager : "
                    + passager.toHtml()+"</h2></th></tr>"
                    + "<tr>"
                    + trajet.toHtml()+"</tr>"
                    + "<tr><th>Option Repas</th> "
                    + repas+"</tr>"
                    + "<tr><th>Option supplémentaire</th><th>"+mod+"</th>"
                    + "<th>"+couchette+"</th><th colspan=\"4\"> </th></tr>"
                    + "</table>"
                    + "<table><tr><th><h2>Synthèse des prix </h2></th></tr><tr>"
                    + getPrix()
                    + "</table></body></html>");

            // Close the output stream
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("IO exception");
            e.printStackTrace();
        }
    }

    public int getPlacesVoulues() {
        return placesVoulues;
    }
    public String getNomTicket(){
    	return nomTicket;
    }
    public String getPrix() {
        double prix = 0;
        int reductionFidelite = 0;
        int prixModifiable = 5;
        int prixPremiereClasse = 10;
        int prixCouchette = 5;
        int repasTotal = 0;
        int classePaye = 0;
        int couchettePaye=0;
        int modifiablePaye=0;

        prix += passager.getProfil().getPrix(); // prix en fonction du type de
                                                // passager
        if (passager.getFidelite() == true) // on baisse si il est fidèle
        {
            reductionFidelite = 10;
            prix -= reductionFidelite;
        }
        prix += (double) trajet.getVehicule().getPrix()*(double) trajet.getDistance()/80.0; // prix en fonction du
                                                          // type de transport

        for (String key : prendRepas.keySet()) {
            if (prendRepas.get(key) == true) {
                
                for(Repas repas : trajet.getVehicule().getRepas())
                {
                    if(repas.getNom().equals(key))
                    {
                        repasTotal += repas.getPrix();
                        prix += repas.getPrix();
                    }
                }
            }
        }
        
        
        if(trajet.isPremiereClasse())
        {
            classePaye=prixPremiereClasse;
            prix += prixPremiereClasse;
        }
        
        
        if (modifiable) {
            modifiablePaye=prixModifiable;
            prix += prixModifiable;
        }
        if (prendCouchette) {
            couchettePaye=prixCouchette;
            prix += prixCouchette;
        }
        
        prix*=placesVoulues;

        String texte = "<th>prix passager = " + passager.getProfil().getPrix()
                + " euros</th></tr>";
        texte += "<tr><th>réduction fidèlité = " + reductionFidelite + " euros</th></tr>";
        texte += "<tr><th>prix transport="
                + (double) trajet.getVehicule().getPrix()*(double) trajet.getDistance()/80.0 + " euros</th></tr>";
        texte += "<tr><th>prix repas = " + repasTotal + " euros</th></tr>";
        texte += "<tr><th>prix classe = " + classePaye + " euros</th></tr>";
        texte += "<tr><th>supplément changement du billet = " + modifiablePaye
                + " euros</th></tr>";
        texte += "<tr><th>prix couchette = " + couchettePaye + " euros</th></tr>";
        texte += "<tr><th><h3>prix total pour "+placesVoulues+" place(s) = " + prix + " euros</h3></th></tr>";
        return texte;
    }

    public String print() {
        String textRepas = "";
        for (String key : prendRepas.keySet()) {
            textRepas += key + "=" + prendRepas.get(key) + "#";
        }

        return new StringBuffer().append(passager.print()).append("#")
                .append(trajet.getIdentifiant()).append("#").append(modifiable)
                .append("#").append(prendCouchette).append("#")
                .append(identifiant).append("#").append(placesVoulues).append("#")
                .append(textRepas).append("\n").toString();
    }

    public boolean isModifiable() {
        return modifiable;
    }
    
    public boolean getRepas(String repas)
    {
        return prendRepas.get(repas);
    }

    public String toString() {
        String texte = "";
        String mod = (modifiable) ? "modifiable" : "non modifiable";
        texte += "Réservation (n°" + identifiant + ") du trajet n°"
                + trajet.getIdentifiant() + " (" + mod + ") par " + passager;
        return texte;
    }
}
