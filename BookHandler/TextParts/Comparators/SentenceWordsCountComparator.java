package Projects.Project2.TextParts.Comparators;

import Projects.Project2.TextParts.Sentence;

import java.util.Comparator;

public class SentenceWordsCountComparator implements Comparator<Sentence> {

    @Override
    public int compare(Sentence o1, Sentence o2) {
        return o1.getWordsCount() - o2.getWordsCount();
    }
}
