package main;

import model.NGramModel;
import model.WordsSplitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Author: katooshka
 * Date: 10/14/15.
 */

public class TextGenerator {

    public static NGramModel createNGramModelFromFile(String filename, int order,
                                                      Predicate<String> sentenceEndPredicate,
                                                      Function<String, String> normalizer) throws IOException {
        List<String> words = WordsSplitter.splitToWords(filename);
        NGramModel model = new NGramModel(sentenceEndPredicate, normalizer);
        List<String> previousWords = new ArrayList<>();
        for (String currentWord : words) {
            if (previousWords.size() == order) {
                model.add(previousWords, currentWord);
                previousWords.remove(0);
            }
            previousWords.add(currentWord);
        }
        return model;
    }

    public static List<String> generateText(int textLength, NGramModel model) {
        List<String> initialSequence = model.generateInitialSequence(); // понять почему
        List<String> result = new ArrayList<>(initialSequence);
        List<String> currentWordList = new ArrayList<>(initialSequence);
        for (int i = 0; i < textLength; i++) {
            String newWord = model.generateNextWord(currentWordList);
            if (newWord == null) {
                break;
            }
            result.add(newWord);
            currentWordList.remove(0);
            currentWordList.add(newWord);
        }
        return result;
    }
}
