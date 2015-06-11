package Projects.Project2;

import Projects.Project2.TextParts.Comparators.SentenceWordsCountComparator;
import Projects.Project2.TextParts.Comparators.WordAlphabeticalComparator;
import Projects.Project2.TextParts.Comparators.WordFirstVowelAlphabComparator;
import Projects.Project2.TextParts.Comparators.WordVowelPartComparator;
import Projects.Project2.TextParts.FlyWeight.Symbol;
import Projects.Project2.TextParts.Sentence;
import Projects.Project2.TextParts.SentenceElement;
import Projects.Project2.TextParts.Word;
import Projects.Project2.Utils.ManachersAlgorithm;
import Projects.Project2.Utils.MapUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class BookHandler {
    private Book book;

    public BookHandler(Book book) {
        this.book = book;
    }

    /**
     * method prints book to specific output stream
     *
     * @param os - output stream
     * @throws IOException
     */
    public void printBook(OutputStream os) throws IOException {
        String s = String.valueOf(book);
        printString(s, os);
    }

    /**
     * prints maximal quantity of sentences containing same word to specific output stream
     *
     * @param os - output stream
     * @throws IOException
     */
    public void printMaxSentencesWithSameWord(OutputStream os) throws IOException {
        Map<Integer, String> s = maxSentencesWithSameWord();
        printMap(s, os);
    }

    /**
     * prints sentences ordered by words quantity to specific output stream
     *
     * @param os - output stream
     * @throws IOException
     */
    public void printSentencesOrderedByWordsCount(OutputStream os) throws IOException {
        List<Sentence> sentences = sentencesOrderedByWordsCount();
        printList(sentences, os);
    }

    /**
     * prints first sentence word that doesn't exist in other sentences to specific output stream
     *
     * @param os - output stream
     * @throws IOException
     */
    public void printUniqueWordOfFirstSentence(OutputStream os) throws IOException {
        String s = String.valueOf(getWordAbsentInOtherSentences());
        printString(s, os);
    }

    /**
     * prints unique words in questions to specific output stream
     *
     * @param length - length of words to seek
     * @param os     - output stream
     * @throws IOException
     */
    public void printUniqueWordsInQuestions(int length, OutputStream os) throws IOException {
        List<Word> words = uniqueWordsInQuestions(length);
        printList(words, os);
    }

    /**
     * prints all sentences with swapped first and last word
     *
     * @param os - output stream
     * @throws IOException
     */
    public void printSentencesWithSwappedEnds(OutputStream os) throws IOException {
        List<Sentence> sentences = swapFirstAndLastWord();
        printList(sentences, os);
    }

    /**
     * prints all words in alphabetic order, every first new letter is red coloured
     *
     * @param os - output stream
     * @throws IOException
     */
    public void printAllWordsInAlphabeticOrder(OutputStream os) throws IOException {
        List<String> result = colouredWordsInAlphOrder();
        printList(result, os);
    }

    /**
     * prints words ordered by vowels part in word (vowels count/word size)
     *
     * @param os - output stream
     * @throws IOException
     */
    public void printWordsOrderedByVowelsPart(OutputStream os) throws IOException {
        List<Word> words = getWordsOrderedByVowelsPart();
        printList(words, os);
    }

    /**
     * prints words ordered by leading vowel in words
     *
     * @param os - output stream
     * @throws IOException
     */
    public void printWordsOrderedByFirstVowel(OutputStream os) throws IOException {
        List<Word> words = getWordsOrderedByFirstVowel();
        printList(words, os);
    }

    /**
     * prints the longest palindrome in the book
     *
     * @param os - output stream
     * @throws IOException
     */
    public void printBiggestPalindrome(OutputStream os) throws IOException {
        String s = findBiggestPalindrome();
        printString(s, os);
    }

    /**
     * deletes entrances of the first letter in all words
     */
    public void deleteEntrancesOfFirstLetter() {
        List<Word> words = book.getWords();
        for (Word w : words) {
            List<Symbol> symbols = w.getSymbols();
            Symbol first = symbols.get(0);
            for (int i = 1; i < symbols.size(); i++) {
                if (symbols.get(i).equalsDespiteCase(first)) {
                    symbols.remove(i);
                }
            }
        }
    }

    /**
     * deletes maximal substring starting and ending with specific characters
     *
     * @param begin - starting character of substring
     * @param end   - closing character of substring
     */
    public void deleteMaxLengthSubstring(char begin, char end) {
        List<Sentence> sentences = book.getSentences();
        for (Sentence s : sentences) {
            String string = s.toString();
            int fromBegin = string.indexOf(begin);
            int fromEnd = string.lastIndexOf(end);
            if (fromEnd < fromBegin) {
                String temp = string.substring(0, fromBegin)
                        + string.substring(fromEnd + 1, string.length());
                BookReader br = new BookReader(temp);
                BookHandler bh = new BookHandler(br.readBook());
                s.setElements(bh.book.getSentences().get(0).getElements());
            }
        }
    }

    /**
     * deletes all words with specific length and starting with consonant
     *
     * @param wordLength - word length
     */
    public void deleteWordsStartingWithConsonant(int wordLength) {
        List<Sentence> sentences = book.getSentences();
        for (Sentence sentence : sentences) {
            for (int j = 0; j < sentence.listOfWords().size(); j++) {
                Word w = sentence.listOfWords().get(j);
                if (w.getSize() == wordLength && w.getSymbols().get(0).isConsonant()) {
                    sentence.removeWord(w);
                }
            }
        }
    }

    /**
     * prints words sorted by entries in the book
     *
     * @param book  - the book
     * @param words - list of words
     * @param os    - specific output stream
     * @throws IOException
     */
    public void printWordsSortedByEntriesCount(Book book, List<Word> words, OutputStream os) throws IOException {
        Map<Word, Integer> map = sortWordsByEntriesCountDesc(book, words);
        printMap(map, os);
    }

    /**
     * prints words sorted in descending order by number of character entries
     *
     * @param c  - character
     * @param os - output stream
     * @throws IOException
     */
    public void printWordsSortedByCharCountDesc(char c, OutputStream os) throws IOException {
        Map<Word, Integer> map = wordsSortedByLetterEntriesCount(c, true);
        printMap(map, os);
    }

    /**
     * prints words sorted in ascending order by number of character entries
     *
     * @param c  - character
     * @param os - output stream
     * @throws IOException
     */
    public void printWordsSortedByCharCountAsc(char c, OutputStream os) throws IOException {
        Map<Word, Integer> map = wordsSortedByLetterEntriesCount(c, false);
        printMap(map, os);
    }

    /**
     * replaces specific length words in specific sentence with given string
     *
     * @param sentenceNumber - number of the sentence
     * @param wordsLength - length of the words
     * @param string - given string
     */
    public void replaceWordsWithString(int sentenceNumber, int wordsLength, String string) {
        Sentence s = book.getSentences().get(sentenceNumber);
        Book text = new BookReader(string).readBook();
        List<SentenceElement> se = text.getParagraphs().get(0).getSentences().get(0).getElements();
        List<Word> words = s.listOfWords();
        for (Word w : words) {
            if (w.getSize() == wordsLength) {
                while (s.containsWord(w)) {
                    int wordIndex = s.getElements().indexOf(w);
                    s.getElements().remove(wordIndex);
                    s.getElements().addAll(wordIndex, se);
                }
            }
        }
    }

    private Map<Word, Integer> wordsSortedByLetterEntriesCount(char c, boolean ascDesc) {
        List<Word> words = book.getWords();
        Map<Word, Integer> map = new HashMap<>();
        for (Word word : words) {
            map.put(word, word.symbolEntries(c));
        }
        map = MapUtil.sortByValue(map, ascDesc);

        //sorting
        Map<Word, Integer> linkedMap = new LinkedHashMap<>();
        int i = (new LinkedHashMap<>(map)).entrySet().iterator().next().getValue();
        TreeMap<Word, Integer> treeMap = new TreeMap<>(new WordAlphabeticalComparator());

        for (Map.Entry<Word, Integer> entry : map.entrySet()) {
            if (entry.getValue() == i) {
                treeMap.put(entry.getKey(), entry.getValue());
            } else {
                linkedMap.putAll(treeMap);
                treeMap = new TreeMap<>(new WordAlphabeticalComparator());
                treeMap.put(entry.getKey(), entry.getValue());
            }
            i = entry.getValue();
        }
        if (!treeMap.isEmpty()) linkedMap.putAll(treeMap);
        return linkedMap;
    }

    private Map<Word, Integer> sortWordsByEntriesCountDesc(Book text, List<Word> words) {
        Map<Word, Integer> result = new HashMap<>();
        for (Word w : words) {
            int entries = 0;
            for (Sentence s : book.getSentences()) {
                entries += s.wordEntriesCount(w);
            }
            result.put(w, entries);
            result = MapUtil.sortByValue(result, true);
        }
        return result;
    }

    private List<Word> getWordsOrderedByFirstVowel() {
        List<Word> words = book.getWords();
        Collections.sort(words, new WordFirstVowelAlphabComparator());
        return words;
    }

    private List<Word> getWordsOrderedByVowelsPart() {
        List<Word> words = book.getWords();
        Collections.sort(words, new WordVowelPartComparator());
        return words;
    }

    private List<Sentence> swapFirstAndLastWord() {
        List<Sentence> sentences = book.getSentences();
        for (Sentence s : sentences) {
            List<Word> words = s.listOfWords();
            Word first = words.get(0);
            Word last = words.get(words.size() - 1);
            List<Symbol> firstWord = first.getSymbols();
            List<Symbol> lastWord = words.get(words.size() - 1).getSymbols();
            first.setWord(lastWord);
            last.setWord(firstWord);
        }
        return sentences;
    }

    private List<Word> uniqueWordsInQuestions(int length) {
        List<Sentence> sentences = book.getSentences();
        for (int i = 0; i < sentences.size(); i++) {
            Sentence s = sentences.get(i);
            if (s.isQuestion()) {
                sentences.remove(s);
            }
        }
        Set<Word> words = new HashSet<>();
        for (Sentence s : sentences) {
            for (Word w : s.listOfWords()) {
                if (w.getSize() == length) words.add(w);
            }
        }
        return new LinkedList<>(words);
    }

    private Word getWordAbsentInOtherSentences() {
        List<Word> wordsOfFirstSentence = book.getParagraphs().get(0).getSentences().get(0).listOfWords();
        List<Sentence> sentences = book.getSentences();
        words:
        for (Word word : wordsOfFirstSentence) {
            for (int j = 1; j < sentences.size(); j++) {
                if (sentences.get(j).containsWord(word)) continue words;
            }
            return word;
        }
        return null;
    }

    private Map<Integer, String> maxSentencesWithSameWord() {
        Map<Integer, String> result = new HashMap<>();
        int resCount = 0;
        String resWord = null;
        List<Sentence> sentences = book.getSentences();
        int temp = 0;
        for (int i = 0; i < sentences.size(); i++) {
            for (int j = 0; j < sentences.get(i).listOfWords().size(); j++) {
                Word word = sentences.get(i).listOfWords().get(j);
                temp = 0;
                for (Sentence sentence : sentences) {
                    if (sentence.containsWord(word)) temp++;
                }
                if (temp > resCount) {
                    resCount = temp;
                }
                resWord = word.toString();
            }
        }
        result.put(resCount, resWord);
        return result;
    }

    private List<Sentence> sentencesOrderedByWordsCount() {
        List<Sentence> sentences = book.getSentences();
        Collections.sort(sentences, new SentenceWordsCountComparator());
        return sentences;
    }

    private <T> void printList(List<T> list, OutputStream os) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        for (T t : list) {
            writer.write(t.toString());
            writer.write("\n");
            writer.write("\r");
        }
        writer.flush();
    }

    private <T, I> void printMap(Map<T, I> map, OutputStream os) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        for (Map.Entry<T, I> entry : map.entrySet()) {
            writer.write(entry.toString());
            writer.write('\n');
        }
        writer.flush();
    }

    private void printString(String s, OutputStream os) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        writer.write(s);
        writer.flush();
    }

    private List<Word> wordsInAlphabeticOrder() {
        List<Word> words = book.getWords();
        Collections.sort(words, new WordAlphabeticalComparator());
        return words;
    }

    private List<String> colouredWordsInAlphOrder() {
        List<String> result = new LinkedList<>();
        List<Word> words = wordsInAlphabeticOrder();
        Word temp = new Word(new LinkedList<Symbol>() {
            {
                add(new Symbol((char) 0));
            }
        });
        WordAlphabeticalComparator wac = new WordAlphabeticalComparator();
        for (Word w : words) {
            if (wac.compare(w, temp) > 0) {
                result.add("\033[31m" + w.toString() + "\033[0m");
                temp = w;
            } else
                result.add(w.toString());
        }
        return result;
    }

    private String findBiggestPalindrome() {
        return ManachersAlgorithm.findLongestPalindrome(book.toString());
    }

}
