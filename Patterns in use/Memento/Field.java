package HomeWork.HomeWork8.Task2.Memento;

public class Field {
    private static Cell[][] gameBoard = new Cell[3][3];

    public Cell[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Cell[][] gameBoard) {
        Field.gameBoard = gameBoard;
    }

    public void clear() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = new Cell(CellType.EMPTY);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                sb.append(gameBoard[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Memento saveState() {
        Cell[][] temp = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp[i][j] = new Cell(gameBoard[i][j].ct);
            }
        }
        return new Memento(temp);
    }

    public void restoreState(Memento memento) {
        gameBoard = memento.getState();
    }
}
