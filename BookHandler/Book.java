package Projects.Project2;

import Projects.Project2.TextParts.Paragraph;
import Projects.Project2.TextParts.Sentence;
import Projects.Project2.TextParts.Word;

import java.util.LinkedList;
import java.util.List;

public class Book {
    private List<Paragraph> paragraphs;

    public Book(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public List<Sentence> getSentences() {
        List<Sentence> sentences = new LinkedList<>();
        for (Paragraph p : getParagraphs()) {
            for (Sentence s : p.getSentences()) {
                sentences.add(s);
            }
        }
        return sentences;
    }

    public List<Word> getWords() {
        List<Word> words = new LinkedList<>();
        List<Sentence> sentences = getSentences();
        for (Sentence s : sentences) {
            words.addAll(s.listOfWords());
        }
        return words;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Paragraph p : paragraphs){
            sb.append(p);
            sb.append('\r');
            sb.append('\n');
        }
        return sb.toString();
    }
}