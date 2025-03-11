public class Main {
    public static void main(String[] args) {
        VocabularyList list = new VocabularyList("test.txt");
//        load(Paths.get("test_old.txt"), list);
        UI ui = new UI(list);
        ui.start();
    }
}
    /* used this for changing the text file format
    public static boolean load(Path path, VocabularyList list) {
        if (!Files.isReadable(path)) return false; //just trying stuff out
        try (Stream<String> lines = Files.lines(path)) {
            lines.map(line -> new Word(line.split(": ")[0]))
                    .forEach(list::add);
//            list.values().parallelStream().forEach(Word::computeScore); // could time it to see if it's faster
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}

     */