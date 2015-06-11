package Projects.Project2.TextParts.Comparators;

import Projects.Project2.TextParts.FlyWeight.Symbol;
import Projects.Project2.TextParts.Word;

import java.util.Comparator;
import java.util.List;

public class WordFirstVowelAlphabComparator implements Comparator<Word> {
    @Override
    public int compare(Word o1, Word o2) {
        List<Symbol> charactersO1 = o1.getSymbols();
        List<Symbol> charactersO2 = o2.getSymbols();

        Symbol o1FirstVowel = null;
        Symbol o2FirstVowel = null;

        for (Symbol c : charactersO1) {
            if (c.isVowel()) {
                o1FirstVowel = c;
                break;
            }
        }

        for (Symbol c : charactersO2) {
            if (c.isVowel()) {
                o2FirstVowel = c;
                break;
            }
        }

        if (o1FirstVowel == null || o2FirstVowel == null) {
            if (o1FirstVowel == null && o2FirstVowel == null) return 0;
            if (o2FirstVowel == null) return Character.toLowerCase(o1FirstVowel.getC());
            return -Character.toLowerCase(o2FirstVowel.getC());
        }

        int diff = Character.toLowerCase(o1FirstVowel.getC()) -
                Character.toLowerCase(o2FirstVowel.getC());
        return diff;
    }
}
