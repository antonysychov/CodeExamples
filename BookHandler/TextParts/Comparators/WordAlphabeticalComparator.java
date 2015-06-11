package Projects.Project2.TextParts.Comparators;

import Projects.Project2.TextParts.Word;

import java.util.Comparator;

public class WordAlphabeticalComparator implements Comparator<Word> {
    @Override
    public int compare(Word o1, Word o2) {
        char o1FirstChar = o1.getSymbols().get(0).getC();
        char o2FirstChar = o2.getSymbols().get(0).getC();

        return Character.toLowerCase(o1FirstChar) - Character.toLowerCase(o2FirstChar);
    }
}
