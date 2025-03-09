import java.util.regex.*;

public class Regex {
    // class for providing verification and filtering as well as merging
    // as well as dealing with alternate spellings and proper names.
    // rudimentary for now. Rename class to "Dictionary" later?
    public static final String WORD = "([\\w&&[^_]]+)" //Group 1: non-hyphenated words
            + "([\\-[\\w&&[^_]]+]*)"; //Group 2: hyphenated part(s)

    public static boolean match(String regex, String string) {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

}
