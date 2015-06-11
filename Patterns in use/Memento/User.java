package HomeWork.HomeWork8.Task2.Memento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User extends Player {
    @Override
    void makeStep(Field f) {
        System.out.println("Введите координаты через запятую (x,y) : ");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        userStep:
        while (true) {
            String input = null;
            try {
                input = bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!input.contains(",")) {
                System.out.println("Неверный формат");
                System.out.println("Выберите другое поле ...");
                continue userStep;
            }
            String s[] = input.split(",");
            int x = Integer.valueOf(s[0]);
            int y = Integer.valueOf(s[1]);
            if (x < 0 || x > 2 || y < 0 || y > 2) {
                System.out.println("Неверные координаты");
                System.out.println("Выберите другое поле ...");
                continue userStep;
            }
            if (f.getGameBoard()[x][y].ct == CellType.EMPTY) {
                f.getGameBoard()[x][y].ct = CellType.CROSS;
                break userStep;
            } else {
                System.out.println("Выберите другое поле ...");
                continue userStep;
            }
        }
        System.out.println(f);
    }

    @Override
    public void makeBackStep(Field f) {
        Game.userThread.notify();

    }
}
