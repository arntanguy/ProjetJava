package logiqueMetier;

import java.util.Scanner;

/**
 * Cette classe fait partie de l'application d'un système de réservation de
 * moyens de transport en commun.
 * 
 * Ce parser lit ce qui est entré par l'utilisateur et essaie de l'interpréter
 * comme une commande du client d'admin. Chaque fois qu'il est appelé, il lit un
 * ligne du terminal et essaie de l'interpréter comme un commande comportant
 * deux mots. Il retourne la commande comme un objet de la classe Command.
 * 
 * Le parser contient une liste de mots de commande connus. Il compare ce que
 * l'utilisateur a entré par rapport aux commandes connues. Si ce qui est entré
 * n'est pas une commande connue, il retourne un objet Command marqué comme
 * étant une commande inconnue.
 * 
 * @author Ceschel Marvin and Bourdin Théo
 * @version 2011.12.04
 */

public class Parser {
    private CommandWords commands; // liste de tous les mots de commande valides
    private Scanner reader; // source de l'entrée des commandes

    /**
     * Créer un parser pour lire à partir d'une fenêtre de terminal.
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return La prochaine commande de l'utilisateur.
     */
    public Command getCommand() {
        String inputLine; // récupère la ligne d'entrée complète
        String word1 = null;
        String word2 = null;

        System.out.print("> "); // affiche l'invite de commande

        inputLine = reader.nextLine();

        // Trouve jusqu'à deux mots sur la ligne
        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next(); // récupère le premier mot
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next(); // récupère le deuxième mot
                // on ignore le reste de la ligne
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }

    /**
     * @return Une liste des mots de commandes valides.
     */
    public String getCommands() {
        return commands.getCommandList();
    }
}
