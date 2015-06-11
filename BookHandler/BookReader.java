package Projects.Project2;

import Projects.Project2.TextParts.FlyWeight.Symbol;
import Projects.Project2.TextParts.FlyWeight.SymbolFactory;
import Projects.Project2.TextParts.Paragraph;
import Projects.Project2.TextParts.Sentence;
import Projects.Project2.TextParts.SentenceElement;
import Projects.Project2.TextParts.Word;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BookReader {
    private InputStream reader = null;

    public BookReader(File filePath) throws FileNotFoundException {
        this.reader = new FileInputStream(filePath);
    }

    public BookReader(String string) {
        this.reader = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    public Book readBook() {
        List<Paragraph> paragraphs = new LinkedList<>();
        List<Sentence> sentences = new LinkedList<>();
        List<SentenceElement> sentenceUnits = new LinkedList<>();
        List<Symbol> characters = new LinkedList<>();
        Character prevSymbol = null;

        CharacterIterator characterIterator = new CharacterIterator();
        while (characterIterator.hasNext()) {
            Symbol c = SymbolFactory.getSymbol(characterIterator.next());
            switch (c.getC()) {
                case ' ':
                case '\t':
                case ',':
                case ';':
                case ':':
                    if (!characters.isEmpty()) {
                        Word word = new Word(characters);
                        sentenceUnits.add(word);
                    }
                    if (prevSymbol!='.' && prevSymbol!='?' && prevSymbol!='!')
                    sentenceUnits.add(c);
                    characters = new LinkedList<>();
                    break;
                case '.':
                case '?':
                case '!':
                    Word word = new Word(characters);
                    sentenceUnits.add(word);
                    sentenceUnits.add(c);
                    Sentence sentence = new Sentence(new LinkedList<>(sentenceUnits));
                    sentences.add(sentence);
                    characters = new LinkedList<>();
                    sentenceUnits = new LinkedList<>();
                    break;
                case '\r':
                    continue;
                case '\n':
                    if (!characters.isEmpty()) {
                        word = new Word(characters);
                        sentenceUnits.add(word);
                    }
                    if (!sentenceUnits.isEmpty()) {
                        sentence = new Sentence(sentenceUnits);
                        sentences.add(sentence);
                    }
                    Paragraph paragraph = new Paragraph(sentences);
                    paragraphs.add(paragraph);
                    characters = new LinkedList<>();
                    sentenceUnits = new LinkedList<>();
                    sentences = new LinkedList<>();
                    break;
                default:
                    characters.add(c);
            }
            if (!characterIterator.hasNext()) {
                if (!characters.isEmpty()) {
                    Word word = new Word(characters);
                    sentenceUnits.add(word);
                }
                if (!sentenceUnits.isEmpty()) {
                    Sentence sentence = new Sentence(sentenceUnits);
                    sentences.add(sentence);
                }
                Paragraph paragraph = new Paragraph(sentences);
                paragraphs.add(paragraph);
                characters = new LinkedList<>();
                sentenceUnits = new LinkedList<>();
                sentences = new LinkedList<>();
            }
            prevSymbol = c.getC();
        }
        return new Book(paragraphs);
    }

    private class CharacterIterator implements Iterator<java.lang.Character> {
        @Override
        public boolean hasNext() {
            try {
                return (reader.available() > 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public java.lang.Character next() {
            try {
                return (char) reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
}
