package main;

import model.NGramModel;
import model.WordsSplitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public class TextGenerator {

    /**
     * Reads file, splits into words, and creates a new {@link NGramModel}.
     * <p>
     * For each {@code order + 1} group of subsequent words, we add mapping
     * from normalized first {@code order} words to the last word in the group.
     * For example, if {@code order == 2} and the text is "The dog is big.",
     * the function will split it into 4 words "The", "dog", "is", "big." and
     * adds such mappings to the model:
     * <p>
     * ("the", "dog") -> "is"
     * ("dog", "is") -> "big."
     * <p>
     * where {@code normalizer.apply("The").equals("the")} and
     * {@code sentenceEndPredicate.test("big.") == true}.
     */
    public static NGramModel createNGramModelFromFile(
            String filename, int order,
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

    /**
     * Generates text of given {@code textLength} using given {@code model}.
     * <p>
     * The generated text starts from a random model initial sequence.
     * The process of creating new word breaks if current sequence of words
     * does not have any corresponding next words in the given model.
     */
    public static List<String> generateText(int textLength, NGramModel model) {
        List<String> initialSequence = model.generateInitialSequence();
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
