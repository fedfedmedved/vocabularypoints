import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray; //added maven dependency - didn't work :(
import org.json.JSONObject;

public class Ngram {
    private final String ngram;
    private final Word word; //word associated with the unigram, treated as an injective mapping for now.
    private double frequency; //fraction of all n-grams (for same n, where n is the # words in a phrase) in the Google books English language corpus
//    private boolean caseSensitive = true; //can use it in link builder as a parameter
//    boolean word; //is there a non-name word associated with the sequence of symbols?;
//    boolean abbreviation; //fairly complex, don't know if i want to include that

    public Ngram(String ngram, Word word) {
        this.ngram = ngram;
        this.word = word;
    }

    public Ngram(String ngram, double frequency) {
        this.ngram = ngram;
        this.frequency = frequency;
        this.word = new Word(this);
    }

    public double fetchFrequency() {
        extractValueFromJson(requestJson(urlBuilder()));
        System.out.println(frequency);
        return frequency;
    }

    public String urlBuilder() {
        return urlBuilder(word.getNgramSearch()); //default
    }

    public String urlBuilder(String search) {
        return "https://books.google.com/ngrams/json?content=" +
                search +
                "&year_start=2019" + //start year
                "&year_end=2022" +
                "&corpus=en&smoothing=3" //defaults; I don't think they are important for the single frequency
//              "&case_insensitive=false" //default; optional; can't be combined with _INF
                ;
    }

    public String requestJson(String url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body(); //[json]
            return json.substring(1, json.length()-1); //removing surrounding square brackets
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    
    public void extractValueFromJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray timeSeries = jsonObject.getJSONArray("timeseries");
            this.frequency = timeSeries.getDouble(timeSeries.length() - 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public double getFrequency() {
        return this.frequency;
    }

    public Word getWord() {
        return word;
    }

    @Override
    public String toString() {
        return this.ngram;
    }
}
