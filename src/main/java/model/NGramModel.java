package model;

import java.util.*;

/**
 * Author: katooshka
 * Date: 10/19/15.
 */
public class NGramModel {
    private Map<List<String>, List<String>> map = new HashMap<>();
    private List<List<String>> possibleSequences = new ArrayList<>();
    private Random random = new Random();

    public void add(List<String> previousWords, String nextWord) {
        List<String> normalizedPreviousWords = normalizeList(previousWords);
        if (!map.containsKey(normalizedPreviousWords)) {
            map.put(normalizedPreviousWords, new ArrayList<String>());  // разобраться почему надо копировать
        }
        map.get(previousWords).add(nextWord);
        List<String> previousWordsCopy = new ArrayList<>(previousWords);  // разобраться как не делать двойных копий
        possibleSequences.add(previousWordsCopy);
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
            normalize(previousWord);
            normalizedPreviousWords.add(previousWord);
        }
        return normalizedPreviousWords;
    }

    private String normalize(String word) {
        return word.toLowerCase();
    }
}
