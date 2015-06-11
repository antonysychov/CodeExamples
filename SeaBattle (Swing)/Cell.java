package HomeWork.HomeWork5.SeaBattle;

import javax.swing.*;

public class Cell extends JLabel {
    int x;
    int y;

    boolean isFilled;
    boolean isCrossed;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
