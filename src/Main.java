public class Main {
    public static void main(String[] args) {
        VocabularyList list = new VocabularyList("test.txt");
        UI ui = new UI(list);
        ui.start();
        //0.00012345 for "potato" - should give 0 points (or negative points? :D)
        //0.0000013469 for "merlon" - should give around 108-114 points depending on ngram year
    }
}