import java.util.HashMap;

public class VocabularyList {
    HashMap<String, Integer> list;

    public VocabularyList() {
        list = new HashMap<>();
    }

    public void add(String word, int value) {
        int score = value;  //add the conversion formula here.
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
        list.keySet().stream().sorted()
                .forEach(key -> System.out.println(key + ": " + list.get(key)));
        // can I make it less ugly?
    }

    public int get(String key) {
        return list.getOrDefault(key, -1);
    }
}
