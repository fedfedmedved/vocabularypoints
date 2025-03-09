import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;

public class VocabularyList {
    private HashMap<String, Integer> list;
    private String filename;
    private Path path;

    public VocabularyList() {
        this("default.txt");
    }

    public VocabularyList(String filename) {
        list = new HashMap<>();
        this.filename = filename;
        this.path = Paths.get(filename);
        if(!load()) System.out.println("unable to load the list - 404 File Not Found");
    }

    public void add(String word, double ngram) {
        int score = (int) max(0,round(-100 * atan(log10(ngram) + 4)));  //ngram has four leading zero for common words.
        if (score == 0) System.out.println("You won't get points for common words. Try harder next time!");
        list.put(word.toLowerCase().strip(), score);
        // can use regex here to see if the word is indeed a single word
        // is this the correct place to do that in the program?
        // can throw an error if the word submitted is indeed not a word.
        // should warn a user that the word they entered is not a valid word.
        // what about alternate spelling for the same word? - should count the most common spelling?
        // or could add up frequencies for alternate spelling prior to converting the value.
        // where do I put the program logic? Is here okay? It really will only have the one formula.
    }

    //display sorted list
    public void printSorted() {
        list.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        // can I make it less ugly?
        System.out.println("Point total: "+total() + " points");
    }

    public double get(String key) {
        return list.getOrDefault(key, -1);
    }

    public long total() {
        return list.values().stream().mapToInt(x->x).sum();
    }

    public boolean save() {
//        if (!Files.isWritable(path)) {
//            System.out.println("Unable to write to file!");
//            return false; //a very basic precaution
        // isWritable will return false if there is no file :S
//        }
        try (PrintWriter printer = new PrintWriter(new File(filename))) {
            list.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> printer.println(entry.getKey() + ": " + entry.getValue()));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean load() {
        if (!Files.isReadable(path)) return false; //just trying stuff out
        try {
            Files.lines(path).map(line -> line.split(": "))
                    .forEach(tuple -> list.put(tuple[0], Integer.parseInt(tuple[1])));
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public void delete(String word) {
        list.remove(word);
    }
}
