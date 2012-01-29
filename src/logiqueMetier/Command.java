package logiqueMetier;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun.
 * 
 * Cette classe gère les informations d'une commande entrée par l'utilisateur.
 * Une commande possède deux parties : une CommandWord et un String. Les
 * commandes sont déjà vérifiées pour être valides. Si l'utilisateur entre une
 * commande invalide (un mot inconnu), alors le nom de commande est INCONNU.
 * 
 * Si la commande ne contient qu'un mot, le second mot est <null>.
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class Command {
    private CommandWord commandWord;
    private String secondWord;

    /**
     * Créer un objet Command. Le premier et le second mot doivent être entrés,
     * mais le second peut être nul.
     * 
     * @param commandWord
     *            Le CommandWord. INCONNU si le mot de commande n'a pas été
     *            reconnu.
     * @param secondWord
     *            Le second mot de la commande. Peut être nul.
     */
    public Command(CommandWord commandWord, String secondWord) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    /**
     * Retourne le mot de commande (le premier mot) de cette commande.
     * 
     * @return le mot de commande.
     */
    public CommandWord getCommandWord() {
        return commandWord;
    }

    /**
     * @return Le second mot de cette commande. Retourne null s'il n'y a pas de
     *         second mot.
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * @return true si cette commande n'a pas été comprise.
     */
    public boolean isUnknown() {
        return (commandWord == CommandWord.INCONNU);
    }

    /**
     * @return true si la commande a un second mot.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}
