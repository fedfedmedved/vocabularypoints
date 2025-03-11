import java.util.regex.*;
import java.util.ArrayList;
import java.time.LocalDate;
import static java.lang.Math.*;

public class Word implements Comparable<Word> {
    // class for providing verification and filtering as well as merging
    // as well as dealing with alternate spellings and proper names.
    // rudimentary for now. Rename class to "Dictionary" later?
    public static final String UNIGRAM =  "[\\w&&[^_\\d]]+"; //matching non-hyphenated unigrams
    //    private String definition; to be implemented later
    private int score;
    private final Ngram ngram;
    private ArrayList<String> altSpellings; //TBI later
    private final LocalDate dateAdded;

    public Word(String unigram) {
        this.dateAdded = LocalDate.now();
        this.ngram = new Ngram(unigram, this);
        computeScore(); //it is definitely going to get more complicated than this
    }

    public Word(Ngram ngram) {
        this.dateAdded = LocalDate.now(); //this is wrong :(
        this.ngram = ngram;
    }

    public String getNgramSearch() {
        if (altSpellings == null) {
            return ngram+"_INF"; //inflection search; will be annoying for phrases
        } else {
            return ngram +"+"+ altSpellings.stream()
                    .reduce((a,b) -> a+"+"+b)
                    .get();
        }
    }

    public int computeScore() {
        double frequency = ngram.getFrequency();
        if (frequency == 0) {
            frequency = ngram.fetchFrequency();
        }
        /* feels like I could write this better*/
        if (frequency == 0) { //I should probably throw an exception here or before
            System.out.println(" Cannot compute score: failed to fetch frequency!");
            return -1;
        }

        this.score = (int) max(0,round(-100 * atan(log10(ngram.getFrequency()) + 4.9)));  //the cutoff value for "common words". Values between 4.8 and 4.95 seem reasonable.
        return this.score;
    }

    public Ngram getNgram() {
        return ngram;
    }

    public int getScore() {
        return score;
    }

    public static boolean match(String regex, String string) {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    @Override
    public String toString() {
        return ngram + ": " + score + " points" + ", added on " + dateAdded;
    }

    @Override
    public int compareTo(Word other) {
        return other.score - this.score; // I will probably replace this with several comparators to choose from.
    }
}
