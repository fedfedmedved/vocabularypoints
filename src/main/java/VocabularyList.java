import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

    public void add(String word, int score) {
        list.put(word, score);
    }

    //display sorted list
    public void printSorted() {
        list.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        // can I make it less ugly?
        System.out.println("Point total: "+ total() + " points");
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
        try (PrintWriter printer = new PrintWriter(filename)) {
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
        try (Stream<String> lines = Files.lines(path)) {
            lines.map(line -> line.split(": "))
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
