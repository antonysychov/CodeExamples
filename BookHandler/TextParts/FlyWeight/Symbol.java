package Projects.Project2.TextParts.FlyWeight;

import Projects.Project2.TextParts.SentenceElement;

public class Symbol extends SentenceElement {
    private char c;

    public Symbol(char c) {
        this.c = c;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public boolean isVowel() {
        switch (c) {
            case 'à':
            case 'î':
            case 'ó':
            case 'û':
            case 'ý':
            case 'ÿ':
            case 'å':
            case '¸':
            case 'þ':
            case 'è':
            case 'e':
            case 'y':
            case 'u':
            case 'i':
            case 'o':
            case 'a':
                return true;
            default:
                return  false;

        }
    }

    public boolean isPunctuationMark() {
        switch (c) {
            case ',':
            case '.':
            case '!':
            case '?':
            case ':':
            case ';':
                return true;
            default:
                return false;
        }
    }

    public boolean isConsonant() {
        return Character.isAlphabetic(c) && !isVowel();
    }

    public boolean isSpace() {
        return Character.isSpaceChar(c);
    }

    public boolean equalsDespiteCase(Symbol s) {
        char sc = s.getC();
        return c == Character.toUpperCase(sc) || c == Character.toLowerCase(sc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol character = (Symbol) o;
        return c == character.c;

    }

    @Override
    public int hashCode() {
        return (int) Character.toLowerCase(c);
    }

    @Override
    public String toString() {
        return String.valueOf(c);
    }
}
