import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class VocabularyList {
    private final HashMap<String, Word> list;
    private final String filename;

    public VocabularyList(String filename) {
        list = new HashMap<>();
        this.filename = filename;
        if(!load(Paths.get(filename))) System.out.println("unable to load the list - 404 File Not Found");
    }

    public void add(Word word) {
        list.put(word.getNgram().toString(), word);
    }

    //display sorted list
    public void printSorted() {
        list.values().stream()
                .sorted()
                .forEach(word -> System.out.println(word.getNgram() + ": " + word.getScore()));
        System.out.println("Point total: "+ total() + " points");
    }

    public long total() {
        return list.values().stream().mapToInt(Word::getScore).sum();
    }

    public boolean save() {
//        if (!Files.isWritable(path)) {
//            System.out.println("Unable to write to file!");
//            return false; //a very basic precaution
        // isWritable will return false if there is no file :S
//        }
        try (PrintWriter printer = new PrintWriter(filename)) {
            list.values().stream()
                    .sorted()
                    .map(Word::getNgram)
                    .forEach(ngram -> printer.println(ngram + ": " + ngram.getFrequency()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean load(Path path) {
        if (!Files.isReadable(path)) return false; //just trying stuff out
        try (Stream<String> lines = Files.lines(path)) {
            lines.map(line -> line.split(": "))
                    .map(tuple -> new Ngram(tuple[0], Double.parseDouble(tuple[1])))
                    .forEach(ngram -> add(ngram.getWord()));
            list.values().parallelStream().forEach(Word::computeScore); // could time it to see if it's faster
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
