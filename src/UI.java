import java.util.Scanner;

public class UI {
    private Scanner scanner;
    private VocabularyList list;

    public UI() {
        scanner = new Scanner(System.in);
        list = new VocabularyList();
        // do I really need to surround it with a try-with block???
    }

    public UI(VocabularyList list) {
        scanner = new Scanner(System.in);
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
        String word = scanner.nextLine().strip().toLowerCase();
        if (!Regex.match(Regex.WORD, word)) {
            System.out.println("Not a word!");
            return;
        }
        //show definition
//        System.out.println("Were you able to explain the word correctly?");
//        String input = scanner.nextLine();
        //if "no" return;
        System.out.println("Submit Google books value");
        double ngram = Double.parseDouble(scanner.nextLine()); //will have to automate this to take ngram as input
        list.add(word, ngram);
    }

    public void deleteWord() {
        System.out.println("Insert word");
        String word = scanner.nextLine().strip().toLowerCase();
        list.delete(word);
    }
}
