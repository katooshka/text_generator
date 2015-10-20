package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: katooshka
 * Date: 10/20/15.
 */
public class WordsSplitter {

    public static List<String> splitToWords(String filename) throws IOException {
        Path path = Paths.get(filename);
        List<String> lines = Files.readAllLines(path);
        List<String> words = new ArrayList<>();
        for (String line : lines){
            for (String word : splitString(line)){
                words.add(word);
            }
        }
        return words;
    }

    private static List<String> splitString(String line){
        List<String> words = new ArrayList<>();
        line = line.replaceAll("[)(\"]", "");
        for (String word : line.split(" +")){
            if (hasAlphabeticSymbols(word)){
                words.add(word);
            }
        }
        return words;
    }

    private static boolean hasAlphabeticSymbols(String word){
        for (int i = 0; i < word.length(); i++) {
            if (Character.isAlphabetic(word.charAt(i))){
                return true;
            }
        }
        return false;
    }
}
