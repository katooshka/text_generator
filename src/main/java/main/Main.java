package main;

import model.NGramModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static main.TextGenerator.createNGramModelFromFile;
import static main.TextGenerator.generateText;

public class Main {
    //TODO: not to read all the text file at one moment
    //TODO: list of words -> map from word to count; as consequence ModelBuilder

    //TODO: end on a sentence
    //TODO: add order verification to NGramModel
    //TODO: move out a generator class
    //TODO: try different text

    /**
     * Creates new NGramModel, generates text and prints it.
     */
    public static void main(String[] args) throws IOException {
        Options options = readOptions(args);
        if (options == null) {
            return;
        }

        Predicate<String> sentenceEndPredicate = (word) -> word.endsWith(".");
        Function<String, String> normalizer = String::toLowerCase;
        NGramModel model = createNGramModelFromFile(options.filename, options.order, sentenceEndPredicate, normalizer);
        List<String> text = generateText(options.wordCount, model);
        for (String word : text) {
            System.out.print(word + " ");
        }
    }

    /**
     * Parses given arguments. Outputs information about possible errors
     */
    private static Options readOptions(String[] args) {
        if (args.length != 3) {
            showUsage();
            return null;
        }
        try {
            Options options = new Options();
            options.order = Integer.parseInt(args[0]);
            options.wordCount = Integer.parseInt(args[1]);
            options.filename = args[2];
            if (Files.notExists(Paths.get(options.filename))) {
                System.err.println("File doesn't exist: " + options.filename);
                showUsage();
                return null;
            }
            return options;
        } catch (NumberFormatException e) {
            showUsage();
            return null;
        }
    }

    private static class Options {
        /**
         * Path to the base text
         */
        String filename;
        /**
         * Number of words used to generate a next word
         */
        int order;
        /**
         * Generated text length
         */
        int wordCount;
    }

    private static void showUsage() {
        System.err.println("Usage: java -jar TextGenerator.jar ORDER WORD_COUNT FILENAME");
    }
}
