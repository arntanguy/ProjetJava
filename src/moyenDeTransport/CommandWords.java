package moyenDeTransport;

import java.util.HashMap;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun.
 * 
 * Cette classe énumère tous les mots de commande connues du client d'admin.
 * Elle est utilisée pour reconnaître les commandes quand elles sont entrées.
 * 
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public class CommandWords {
    // On associe chaque mot de commande valide et son CommandWord associé dans
    // un HashMap
    private HashMap<String, CommandWord> validCommands;

    /**
     * Initialise les mots de commande valides.
     */
    public CommandWords() {
        validCommands = new HashMap<String, CommandWord>();
        for (CommandWord command : CommandWord.values()) {
            if (command != CommandWord.INCONNU) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * Trouve le CommandWord associé avec un mot de commande.
     * 
     * @param commandWord
     *            Le mot a recherché.
     * @return Le CommandWord correspondant au mot de commande, ou INCONNU si ce
     *         n'est pas un mot de commande valide.
     */
    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = validCommands.get(commandWord);
        if (command != null) {
            return command;
        } else {
            return CommandWord.INCONNU;
        }
    }

    /**
     * Vérifie si un String donné est un mot de commande valide.
     * 
     * @return true si le String donné est un mot de commande valide, false
     *         sinon.
     */
    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }

    /**
     * Retourne toutes les commandes valides sous forme de String
     */
    public String getCommandList() {
        String retour = "";
        for (String command : validCommands.keySet()) {
            retour += command + "  ";
        }
        retour += "\n";
        return retour;
    }
}
