package logiqueMetier;

/**
 * Cette enum fait partie de l'application d'un système de réservation de moyens
 * de transport en commun.
 * 
 * Représentations de tous les mots de commande valides du client d'admin
 * accompagné d'un String correspondant au language choisi.
 * 
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public enum CommandWord {
    // Une valeur pour chaque mot de commande accompagné de son String pour
    // l'interface utilisateur.
    CONSULTER("consulter"), AJOUTER("ajouter"), MODIFIER("modifier"), SUPPRIMER(
            "supprimer"), RECHERCHER("rechercher"), RESERVER("reserver"), AIDE(
            "aide"), QUITTER("quitter"), INCONNU("?");

    // Le String de commande.
    private String commandString;

    /**
     * Initialise avec le mot de commande correspondant.
     * 
     * @param commandString
     *            Le String de commande.
     */
    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    /**
     * @return Le mot de commande en tant que String.
     */
    public String toString() {
        return commandString;
    }
}
