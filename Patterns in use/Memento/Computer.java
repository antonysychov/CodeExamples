package HomeWork.HomeWork8.Task2.Memento;

public class Computer extends Player {
    @Override
    public void makeBackStep(Field f) {
        Game.compThread.notify();
    }

    @Override
    void makeStep(Field f) {
        System.out.println("Соперник ходит ... ");
        compStep:
        while (true) {
            int x = (int) (Math.random() * 3);
            int y = (int) (Math.random() * 3);
            if (f.getGameBoard()[x][y].ct == CellType.EMPTY) {
                f.getGameBoard()[x][y].ct = CellType.CIRCLE;
                break compStep;
            } else {
                continue compStep;
            }
        }
    }
}

