package HomeWork.HomeWork8.Task2.Memento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    static Object userThread = new Object();
    static Object compThread = new Object();
    private Field field = new Field();
    private Player user = new User();
    private Player computer = new Computer();

    public static void main(String[] args) {
        try {
            new Game().playLogic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void playLogic() throws IOException {
        game:
        while (true) {
            field.clear();
            userStep:
            while (true) {
                System.out.println(field.toString());
                CareTaker ct = new CareTaker();
                ct.setMemento(field.saveState());
                user.makeStep(field);
                if (checkForCompleteLines()) {
                    System.out.println("You win");
                    continue game;
                }
                int x = 3; // wait 3 seconds at most
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("для отката - нажмите Enter в течении 3-х секунд");
                long startTime = System.currentTimeMillis();
                while ((System.currentTimeMillis() - startTime) < x * 1000
                        && !in.ready()) {
                }

                if (in.ready()) {
                    in.readLine();
                    field.restoreState(ct.getMemento());
                    continue userStep;
                }
                computerStep:
                while (true) {
                    ct.setMemento(field.saveState());
                    computer.makeStep(field);
                    if (checkForCompleteLines()) {
                        System.out.println("Computer win");
                        continue game;
                    }
                    break computerStep;
                }
            }
        }
    }

    private boolean checkForCompleteLines() {
        if (field.getGameBoard()[0][0].ct != CellType.EMPTY
                && field.getGameBoard()[0][1].ct == field.getGameBoard()[0][0].ct
                && field.getGameBoard()[0][2].ct == field.getGameBoard()[0][0].ct
                ||
                field.getGameBoard()[1][0].ct != CellType.EMPTY
                        && field.getGameBoard()[1][1].ct == field.getGameBoard()[1][0].ct
                        && field.getGameBoard()[1][2].ct == field.getGameBoard()[1][0].ct
                ||
                field.getGameBoard()[2][0].ct != CellType.EMPTY
                        && field.getGameBoard()[2][1].ct == field.getGameBoard()[2][0].ct
                        && field.getGameBoard()[2][2].ct == field.getGameBoard()[2][0].ct
                ||
                field.getGameBoard()[0][0].ct != CellType.EMPTY
                        && field.getGameBoard()[1][0].ct == field.getGameBoard()[0][0].ct
                        && field.getGameBoard()[2][0].ct == field.getGameBoard()[0][0].ct
                ||
                field.getGameBoard()[0][1].ct != CellType.EMPTY
                        && field.getGameBoard()[1][1].ct == field.getGameBoard()[0][1].ct
                        && field.getGameBoard()[2][1].ct == field.getGameBoard()[0][1].ct
                ||
                field.getGameBoard()[0][2].ct != CellType.EMPTY
                        && field.getGameBoard()[1][2].ct == field.getGameBoard()[0][2].ct
                        && field.getGameBoard()[2][2].ct == field.getGameBoard()[0][2].ct
                ||
                field.getGameBoard()[0][0].ct != CellType.EMPTY
                        && field.getGameBoard()[1][1].ct == field.getGameBoard()[0][0].ct
                        && field.getGameBoard()[2][2].ct == field.getGameBoard()[0][0].ct
                ||
                field.getGameBoard()[2][0].ct != CellType.EMPTY
                        && field.getGameBoard()[1][1].ct == field.getGameBoard()[2][0].ct
                        && field.getGameBoard()[0][2].ct == field.getGameBoard()[2][0].ct) return true;
        else return false;

    }


    private void waitForInput(){

    }
}
