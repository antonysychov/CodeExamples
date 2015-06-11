package Game.GameObjects;

import Game.Game;
import Game.Game.Point;
import Utils.StretchIcon;

import javax.swing.*;

public class Apple {
    private final String appleImagePath = "/Images/apple.png";
    private Point applePosition;

    public Apple(Point p) {
        applePosition = p;
    }

    public void updateOnScreen(Game g) {
        StretchIcon appleStretch = new StretchIcon(getClass().getResource(appleImagePath), true);
        JLabel position = g.getJlm()[applePosition.getRow()][applePosition.getColumn()];
        position.setIcon(appleStretch);
    }

    public Point getApplePosition() {
        return applePosition;
    }

    public void setApplePosition(Point applePosition) {
        this.applePosition = applePosition;
    }
}