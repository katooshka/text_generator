package main;

import model.NGramModel;

import java.io.IOException;
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

    //TODO: console interface
    //TODO: start with a new sentence
    //TODO: end on a sentence
    //TODO: move out normalize() from NGramModel
    //TODO: add order verification to NGramModel
    //TODO: move out a generator class
    //TODO: try different text

    public static void main(String[] args) throws IOException {
        String filename = ClassLoader.getSystemResource("delez.txt").getPath();
        NGramModel model = createNGramModelFromFile(filename, 2);
        List<String> text = generateText(500, model);
        for (String word : text) {
            System.out.print(word + " ");
        }
    }
}
