package HomeWork.HomeWork8.Task2.Memento;

public class Memento {
    private Cell[][] field;

    public Memento(Cell[][] field) {
        this.field = field;
    }

    public Cell[][] getState() {
        return field;
    }
}