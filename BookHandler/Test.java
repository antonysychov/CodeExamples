package Projects.Project2;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        BookReader reader = new BookReader(new File("src\\Projects\\Project2\\Files\\BookExample"));
        Book book = reader.readBook();
        BookHandler handler = new BookHandler(book);

        handler.printBook(System.out);
        //handler.printMaxSentencesWithSameWord(System.out);
        //handler.printSentencesOrderedByWordsCount(System.out);
        //handler.printUniqueWordOfFirstSentence(System.out);
        //handler.printUniqueWordsInQuestions(4, System.out);
        //handler.printSentencesWithSwappedEnds(System.out);
        handler.printAllWordsInAlphabeticOrder(System.out);
        //handler.printWordsOrderedByVowelsPart(System.out);
        //handler.printWordsOrderedByFirstVowel(System.out);
        //handler.deleteEntrancesOfFirstLetter();
        //handler.printBook(System.out);
        //handler.printBiggestPalindrome(System.out);
        //handler.deleteWordsWithSpecLengthStartingWithConsonant(4);
        //System.out.println(book);
        //handler.deleteMaxLengthSubstring('p', 'p');
        //handler.printBook(System.out);
        //handler.printSortedWordsByEntriesCount(book, book.getWords(), System.out);
        //handler.printWordsSortedByCharCountAsc('i', System.out);
        //handler.replaceWordsWithString(1, 7, "adad adad");

        //System.out.println(book);
        //System.out.println(book);
    }
}
