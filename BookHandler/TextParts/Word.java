package Projects.Project2.TextParts;

import Projects.Project2.TextParts.FlyWeight.Symbol;
import Projects.Project2.TextParts.FlyWeight.SymbolFactory;

import java.util.List;

public class Word extends SentenceElement {
    private List<Symbol> word;

    public Word(List<Symbol> word) {
        this.word = word;
    }

    public List<Symbol> getSymbols() {
        return word;
    }

    public void setWord(List<Symbol> word) {
        this.word = word;
    }

    public int getSize() {
        return word.size();
    }

    public int symbolEntries(char c) {
        Symbol symbol = SymbolFactory.getSymbol(c);
        int entriesCount = 0;
        for (Symbol s : word) {
            if (s.equalsDespiteCase(symbol)) {
                entriesCount++;
            }
        }
        return entriesCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Symbol c : word) {
            sb.append(c);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;
        if (!(word.size() == word1.word.size())) return false;
        for (int i = 0; i < word.size(); i++) {
            if (!java.lang.Character.valueOf(java.lang.Character.toLowerCase(word.get(i).getC())).
                    equals(java.lang.Character.valueOf(java.lang.Character.toLowerCase(word1.word.get(i).getC())))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
