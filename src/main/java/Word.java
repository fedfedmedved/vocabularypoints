import java.util.regex.*;
import java.util.ArrayList;
import java.time.LocalDate;
import static java.lang.Math.*;

public class Word {
    // class for providing verification and filtering as well as merging
    // as well as dealing with alternate spellings and proper names.
    // rudimentary for now. Rename class to "Dictionary" later?
    public static final String REGEX = "([\\w&&[^_]]+)" //Group 1: non-hyphenated unigrams
            + "([\\-[\\w&&[^_]]+]*)"; //Group 2: hyphenated part(s)
    private String definition;
    private int score;
    private Unigram unigram;
    private ArrayList<Unigram> altSpellings;
    private final LocalDate dateAdded; //the idea is to use frequencies from different years when the data becomes available.

    public Word(String unigram) {
        this.dateAdded = LocalDate.now();
        this.unigram = new Unigram(unigram); //subtracting 1 to make things simpler for now
    }

    public void computeScore() {
        if (altSpellings == null) {
            if (unigram.getFrequency() == 0) return; //that means there had been an error
            this.score = (int) max(0,round(-100 * atan(log10(unigram.getFrequency()) + 4.9)));  //the cutoff value for "common words". Values between 4.8 and 4.95 seem reasonable.
//            System.out.println(this.score);
            if (this.score == 0) System.out.println("You won't get points for common words. Try harder next time!");
//            list.put(word.toLowerCase().strip(), score);
            System.out.println("You get " + score+" points for "+ unigram);
        }
    }

    public Unigram getNgram() {
        return unigram;
    }

    public int getScore() {
        return score;
    }

    public static boolean match(String regex, String string) {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

}
