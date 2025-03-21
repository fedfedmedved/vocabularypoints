import java.util.Scanner;

public class UI {
    private final Scanner scanner;
    private final VocabularyList list;

    public UI(VocabularyList list) {
        scanner = new Scanner(System.in); // do I really need to surround it with a try-with block???
        this.list = list;
    }

    public void start() {
        System.out.println("Welcome to your Vocabulary points logger!");
        System.out.println("Available commands: add word; display list; delete word; save list; quit");
        while (true) {
            System.out.println("Insert command:");

            String input = scanner.nextLine();
            if (input.equals("add word")) addWord();
            if (input.equals("display list")) list.printSorted();
            if (input.equals("save list")) list.save();
            if (input.equals("delete word")) deleteWord();
            if (input.equals("quit")) {
                //save to file
                if (list.save()) {
                    System.out.println("list saved successfully");
                    break;
                }
                else System.out.println("Error: Unable to save list!");
            }
        }
    }

    public void addWord() {
        System.out.println("Insert word");
        String input = scanner.nextLine().strip(); //ngrams are case-sensitive by default
        if (!Word.match(Word.UNIGRAM, input)) { //will add support for phrases later
            System.out.println("Not a word!");
            return;
        }
        Word word = new Word(input);
        /*
        show definition
                System.out.println("Were you able to explain the word correctly?");
                String input = scanner.nextLine();
        if "no" return;
        */
        int score = word.computeScore();
        if (score == 0) {
            System.out.println("You won't get points for common words. Try harder next time!");
            return;
        }
        System.out.println("You earned " + score+" points for "+ input);
        list.add(word);
    }

    public void deleteWord() {
        System.out.println("Insert word");
        String word = scanner.nextLine().strip();
        list.delete(word);
    }
}
