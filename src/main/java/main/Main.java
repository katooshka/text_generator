package main;

import model.NGramModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import static main.TextGenerator.createNGramModelFromFile;
import static main.TextGenerator.generateText;


/**
 * Author: katooshka
 * Date: 10/20/15.
 */
public class Main {
    //TODO: not to read all the text file at one moment
    //TODO: list of words -> map from word to count; as consequence ModelBuilder

    //TODO: end on a sentence
    //TODO: move out normalize() from NGramModel
    //TODO: add order verification to NGramModel
    //TODO: move out a generator class
    //TODO: try different text

    public static void main(String[] args) throws IOException {
        Options options = readOptions(args);
        if (options == null) {
            return;
        }

        NGramModel model = createNGramModelFromFile(options.filename, options.order);
        List<String> text = generateText(options.wordCount, model);
        for (String word : text) {
            System.out.print(word + " ");
        }
    }

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
        String filename;
        int order;
        int wordCount;
    }

    private static void showUsage() {
        System.err.println("Usage: java -jar TextGenerator.jar ORDER WORD_COUNT FILENAME");
    }
}
