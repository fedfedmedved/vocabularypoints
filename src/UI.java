import java.util.Scanner;

public class UI {
    Scanner scanner;
    VocabularyList list;

    public UI() {
        scanner = new Scanner(System.in);
        list = new VocabularyList();
        // do I really need to surround it with a try-with block???
    }

    public void start() {
        while (true) {
            System.out.println("Welcome to your Vocabulary points logger!");
            System.out.println("Available commands: add word; display list; quit");
            System.out.println("Insert command:");

            String input = scanner.nextLine();
            if (input.equals("add word")) addWord();
            if (input.equals("display list")) list.printSorted();
            if (input.equals("quit")) break;
        }
    }

    public void addWord() {
        System.out.println("Insert word");
        String word = scanner.nextLine();
        //show definition
//        System.out.println("Were you able to explain the word correctly?");
//        String input = scanner.nextLine();
        //if "no" return;
        System.out.println("Submit Google books value");
        int value = Integer.parseInt(scanner.nextLine());
        list.add(word, value);
    }
}
