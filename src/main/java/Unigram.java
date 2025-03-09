import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray; //added maven dependency - didn't work :(
import org.json.JSONObject;

public class Unigram {
    private String unigram;
    private String url;
    private double frequency; //fraction of all n-grams (for same n, where n is the # words in a phrase) in the Google books English language corpus
    private boolean caseSensitive = true; //can use it in link builder as a parameter
//    boolean word; //is there a non-name word associated with the sequence of symbols?;
//    boolean abbreviation; //fairly complex, don't know if i want to include that
    //json

    public Unigram(String unigram) {
        this.unigram = unigram;
        this.url = urlBuilder();
    }

    public String requestJson() {
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

    public String urlBuilder() {
        return new StringBuilder()
                .append("https://books.google.com/ngrams/json?content=")
                .append(unigram)
                .append("_INF&year_start=2019") //start year
                .append("&year_end=2022")
//                .append(year) //end year should be last full year?
                .append("&corpus=en&smoothing=3") //are these params relevant for the json?
//                .append("&case_insensitive=false") //default, optional
                .toString();
    }

    @Override
    public String toString() {
        return this.unigram;
    }
}
