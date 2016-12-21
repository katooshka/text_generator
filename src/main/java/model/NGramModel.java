package model;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class NGramModel {

    /**
     * Contains sequence of words as key and list of another words, that follow this sequence int the base text
     */
    private final Map<List<String>, List<String>> map = new HashMap<>();

    /**
     * Contains list of words sequences that can be used to start generating text
     */
    private final List<List<String>> possibleSequences = new ArrayList<>();

    private final Predicate<String> sentenceEndPredicate;
    private final Function<String, String> normalizer;
    private final Random random = new Random();

    public NGramModel(Predicate<String> sentenceEndPredicate, Function<String, String> normalizer) {
        this.sentenceEndPredicate = sentenceEndPredicate;
        this.normalizer = normalizer;
    }

    /**
     * Puts given {@code word} as value and corresponding list of
     * {@code previousWords} as key into the NGramModel map.
     * <p>
     * Previous words are converted to normalized form before being put into the map.
     * If the first word of {@code previousWords} is the last word of the sentence,
     * another previous words and {@code nextWord} are added to the possible sequence
     * of words that can be used to start text generation.
     * For example:
     * <p>
     * (prevWord1, prevWord2, prevWord3) -> nextWord
     * If prevWord1 is the last word in the sentence, prevWord2, prevWord3 and nextWord
     * will be added to the list of possible start sequences.
     */
    public void add(List<String> previousWords, String nextWord) {
        List<String> normalizedPreviousWords = normalizeList(previousWords);
        if (!map.containsKey(normalizedPreviousWords)) {
            map.put(normalizedPreviousWords, new ArrayList<>());
        }
        map.get(normalizedPreviousWords).add(nextWord);
        if (sentenceEndPredicate.test(previousWords.get(0))) {
            List<String> possibleSequence =
                    new ArrayList<>(previousWords.subList(1, previousWords.size()));
            possibleSequence.add(nextWord);
            possibleSequences.add(possibleSequence);
        }
    }

    /**
     * Returns a random word that corresponds to the given {@code previousWords} in the model map.
     */
    public String generateNextWord(List<String> previousWords) {
        List<String> normalizedPreviousWords = normalizeList(previousWords);
        if (!map.containsKey(normalizedPreviousWords)) {
            return null;
        }
        List<String> nextWords = map.get(normalizedPreviousWords);
        return nextWords.get(random.nextInt(nextWords.size()));
    }

    /**
     * Returns randomly chosen possible sequence of words for the first step of text generation.
     */
    public List<String> generateInitialSequence() {
        return possibleSequences.get(random.nextInt(possibleSequences.size()));
    }

    /**
     * Converts list of {@code previousWords} into the form suitable for further work
     */
    private List<String> normalizeList(List<String> previousWords) {
        List<String> normalizedPreviousWords = new ArrayList<>();
        for (String previousWord : previousWords) {
            normalizedPreviousWords.add(normalizer.apply(previousWord));
        }
        return normalizedPreviousWords;
    }

}
