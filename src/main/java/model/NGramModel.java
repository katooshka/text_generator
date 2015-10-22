package model;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Author: katooshka
 * Date: 10/19/15.
 */
public class NGramModel {
    private final Map<List<String>, List<String>> map = new HashMap<>();
    private final List<List<String>> possibleSequences = new ArrayList<>();
    private final Predicate<String> sentenceEndPredicate;
    private final Function<String, String> normalizer;
    private final Random random = new Random();

    public NGramModel(Predicate<String> sentenceEndPredicate, Function<String, String > normalizer){
        this.sentenceEndPredicate = sentenceEndPredicate;
        this.normalizer = normalizer;
    }

    public void add(List<String> previousWords, String nextWord) {
        List<String> normalizedPreviousWords = normalizeList(previousWords);
        if (!map.containsKey(normalizedPreviousWords)) {
            map.put(normalizedPreviousWords, new ArrayList<>());  // разобраться почему надо копировать
        }
        map.get(previousWords).add(nextWord);
        if (sentenceEndPredicate.test(previousWords.get(0))) {
            List<String> possibleSequence = new ArrayList<>(previousWords.subList(1, previousWords.size()));
            possibleSequence.add(nextWord);
            possibleSequences.add(possibleSequence);
        }
    }

    public String generateNextWord(List<String> previousWords) {
        List<String> normalizedPreviousWords = normalizeList(previousWords);
        if (!map.containsKey(normalizedPreviousWords)) {
            return null;
        }
        List<String> nextWords = map.get(normalizedPreviousWords);
        return nextWords.get(random.nextInt(nextWords.size()));
    }

    public List<String> generateInitialSequence() {
        return possibleSequences.get(random.nextInt(possibleSequences.size()));
    }

    private List<String> normalizeList(List<String> previousWords) {
        List<String> normalizedPreviousWords = new ArrayList<>();
        for (String previousWord : previousWords) {
            normalizer.apply(previousWord);
            normalizedPreviousWords.add(previousWord);
        }
        return normalizedPreviousWords;
    }

}
