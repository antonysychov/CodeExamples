package Projects.Project2.TextParts;

import Projects.Project2.TextParts.FlyWeight.Symbol;

import java.util.LinkedList;
import java.util.List;

public class Sentence extends TextElement {
    private List<SentenceElement> elements;

    public List<SentenceElement> getElements() {
        return elements;
    }

    public void setElements(List<SentenceElement> elements) {
        this.elements = elements;
    }

    public Sentence(List<SentenceElement> elements) {
        this.elements = elements;
    }

    public int getWordsCount() {
        return listOfWords().size();
    }

    public boolean containsWord(Word word) {
        for (SentenceElement se : elements) {
            if (se.getClass() == Word.class && se.equals(word)) return true;
        }
        return false;
    }

    public int wordEntriesCount(Word word) {
        int entriesCount = 0;
        for (SentenceElement se : elements) {
            if (se.getClass() == Word.class && se.equals(word)) entriesCount++;
        }
        return entriesCount;
    }

    public Word removeWord(Word w) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getClass() == Word.class) {
                Word word = (Word) elements.get(i);
                if (word.equals(w)) {
                    SentenceElement prev = (i > 0 && i < elements.size()) ? elements.get(i - 1) : null;
                    elements.remove(w);
                    if (prev != null && prev.getClass() == Symbol.class && ((Symbol) prev).isSpace())
                        elements.remove(i - 1);
                    else if (i == 0) elements.remove(i);
                    return word;
                }
            }
        }
        return null;
    }

    public List<Word> listOfWords() {
        List<Word> words = new LinkedList<>();
        for (SentenceElement se : elements) {
            if (se.getClass() == Word.class) words.add((Word) se);
        }
        return words;
    }

    public List<Symbol> listOfPunctuationMarks() {
        List<Symbol> characters = new LinkedList<>();
        for (SentenceElement se : elements) {
            if (se.getClass() == Symbol.class) characters.add((Symbol) se);
        }
        return characters;
    }

    public boolean isQuestion() {
        List<Symbol> pm = listOfPunctuationMarks();
        if (pm.size() == 0) return false;
        boolean res = pm.get(pm.size() - 1).equals(new Symbol('?'));
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence = (Sentence) o;

        return !(elements != null ? !elements.equals(sentence.elements) : sentence.elements != null);

    }

    @Override
    public int hashCode() {
        return elements != null ? elements.hashCode() : 0;
    }
}
