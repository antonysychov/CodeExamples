package Projects.Project2.TextParts.Comparators;

import Projects.Project2.TextParts.FlyWeight.Symbol;
import Projects.Project2.TextParts.Word;

import java.util.Comparator;

public class WordVowelPartComparator implements Comparator<Word> {
    @Override
    public int compare(Word o1, Word o2) {
        int vowelsInO1 = 0;
        int vowelsInO2 = 0;
        for (Symbol c : o1.getSymbols()) {
            if (c.isVowel()) vowelsInO1++;
        }
        for (Symbol c : o2.getSymbols()) {
            if (c.isVowel()) vowelsInO2++;
        }

        double vowelsPartInO1 = (double) vowelsInO1 / o1.getSymbols().size();
        double vowelsPartInO2 = (double) vowelsInO2 / o2.getSymbols().size();
        return (int)((vowelsPartInO1 - vowelsPartInO2)*100);
    }
}
