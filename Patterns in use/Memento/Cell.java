package HomeWork.HomeWork8.Task2.Memento;

class Cell implements Cloneable {

    CellType ct;

    public Cell(CellType ct) {
        this.ct = ct;
    }

    @Override
    public String toString() {
        switch (ct) {
            case CROSS:
                return "x ";
            case CIRCLE:
                return "o ";
            case EMPTY:
                return "_ ";
            default:
                return "";
        }
    }

}