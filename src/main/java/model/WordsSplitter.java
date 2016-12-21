package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordsSplitter {

    /**
     * Reads text from file, splitting it into words.
     */
    public static List<String> splitToWords(String filename) throws IOException {
        Path path = Paths.get(filename);
        List<String> lines = Files.readAllLines(path);
        List<String> words = new ArrayList<>();
        for (String line : lines) {
            for (String word : splitString(line)) {
                words.add(word);
            }
        }
        return words;
    }

    /**
     * Splits a text line into words.
     * <p>
     * Removes brackets and quotes from the text, and returns all the words with all adjacent symbols.
     * The groups of symbols without alphabetic symbols are ignored.
     * For example:
     * "Oct. 26month 2017 new year," -> ["Oct.", "26month", "new", "year,"]
     */
    private static List<String> splitString(String line) {
        List<String> words = new ArrayList<>();
        line = line.replaceAll("[)(\"]", "");
        for (String word : line.split(" +")) {
            if (hasAlphabeticSymbols(word)) {
                words.add(word);
            }
        }
        return words;
    }

    /**
     * Returns true if at least one symbol is alphabetic. Returns false for an empty string.
     */
    private static boolean hasAlphabeticSymbols(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (Character.isAlphabetic(word.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
