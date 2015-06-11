package Game.GameObjects;

import Game.Game;
import Game.Game.Point;
import Utils.StretchIcon;

import javax.swing.*;
import java.util.LinkedList;

public class Snake {
    final private String snakeHeadUpImage = "/Images/snakehead_up.png";
    final private String snakeHeadRightImage = "/Images/snakehead_right.png";
    final private String snakeHeadDownImage = "/Images/snakehead_down.png";
    final private String snakeHeadLeftImage = "/Images/snakehead_left.png";
    final private String snakeBodyImage = "/Images/snakebody.png";

    private StretchIcon snakeHeadUpScretch = new StretchIcon(getClass().getResource(snakeHeadUpImage), true);
    private StretchIcon snakeHeadRightScretch = new StretchIcon(getClass().getResource(snakeHeadRightImage), true);
    private StretchIcon snakeHeadDownScretch = new StretchIcon(getClass().getResource(snakeHeadDownImage), true);
    private StretchIcon snakeHeadLeftScretch = new StretchIcon(getClass().getResource(snakeHeadLeftImage), true);
    private StretchIcon snakeBodyScretch = new StretchIcon(getClass().getResource(snakeBodyImage), true);

    private LinkedList<Point> bodyCoordinates = new LinkedList<Point>();
    private Point headCoordinates;
    private int direction;
    private boolean justRaised = false;

    public Snake(Game g) {
        direction = 1 + (int) (Math.random() * ((4)));
        headCoordinates = g.getRandomPoint();
        bodyCoordinates.add(headCoordinates);
        Point head = null;
        switch (direction) {
            case 1:
                for (int i = 1; i <= 4; i++) {
                    head = new Point(headCoordinates.getRow() + i, headCoordinates.getColumn());
                    bodyCoordinates.addLast(head);
                }
                break;
            case 2:
                for (int i = 1; i <= 4; i++) {
                    head = new Point(headCoordinates.getRow(), headCoordinates.getColumn() - i);
                    bodyCoordinates.addLast(head);
                }
                break;
            case 3:
                for (int i = 1; i <= 4; i++) {
                    head = new Point(headCoordinates.getRow() - i, headCoordinates.getColumn());
                    bodyCoordinates.addLast(head);
                }
                break;
            case 4:
                for (int i = 1; i <= 4; i++) {
                    head = new Point(headCoordinates.getRow(), headCoordinates.getColumn() + i);
                    bodyCoordinates.addLast(head);
                }
                break;
        }
    }

    public LinkedList<Game.Point> getBodyCoordinates() {
        return bodyCoordinates;
    }

    public void setBodyCoordinates(LinkedList<Game.Point> bodyCoordinates) {
        this.bodyCoordinates = bodyCoordinates;
    }

    public void updateOnScreen(Game g) {
        for (int i = 0; i < bodyCoordinates.size(); i++) {
            JLabel head = g.getJlm()[bodyCoordinates.get(0).getRow()][bodyCoordinates.get(0).getColumn()];
            if (i == 0) switch (direction) {
                case 1:
                    head.setIcon(snakeHeadUpScretch);
                    break;
                case 2:
                    head.setIcon(snakeHeadRightScretch);
                    break;
                case 3:
                    head.setIcon(snakeHeadDownScretch);
                    break;
                case 4:
                    head.setIcon(snakeHeadLeftScretch);
                    break;
            }
            else {
                JLabel bodyPart = g.getJlm()[bodyCoordinates.get(i).getRow()][bodyCoordinates.get(i).getColumn()];
                bodyPart.setIcon(snakeBodyScretch);
            }
        }
    }

    public void makeStep(Game g) {
        g.getJlm()[bodyCoordinates.getLast().getRow()][bodyCoordinates.getLast().getColumn()].setIcon(null);
        if (!justRaised) bodyCoordinates.removeLast();
        else justRaised = false;
        Point head = null;
        switch (direction) {
            case 1:
                head = new Point(headCoordinates.getRow() - 1, headCoordinates.getColumn());
                bodyCoordinates.addFirst(head);
                break;
            case 2:
                head = new Point(headCoordinates.getRow(), headCoordinates.getColumn() + 1);
                bodyCoordinates.addFirst(head);
                break;
            case 3:
                head = new Point(headCoordinates.getRow() + 1, headCoordinates.getColumn());
                bodyCoordinates.addFirst(head);
                break;
            case 4:
                head = new Point(headCoordinates.getRow(), headCoordinates.getColumn() - 1);
                bodyCoordinates.addFirst(head);
                break;
        }
        headCoordinates = bodyCoordinates.getFirst();
        LinkedList<Point> temp = new LinkedList<Point>(this.bodyCoordinates);
        temp.removeFirst();
        if ((headCoordinates.getRow() >= g.getRows())
                || headCoordinates.getColumn() >= g.getColumns()
                || headCoordinates.getRow() < 0 || headCoordinates.getColumn() < 0
                || (temp.contains(headCoordinates))) {
            g.stopGame();
            Object[] options = {"Да", "Нет"};
            int n = JOptionPane.showOptionDialog(null, "Начать заново ?", "Ваш счет: " + g.getScore(),
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (n == 0) g.restartGame();
            else g.dispose();
            return;
        } else if (headCoordinates.equals(g.getApple().getApplePosition())) {
            g.setScore(g.getScore() + 10);
            g.setTitle("Game.GameObjects.Snake 1.0 (текущий счет : " + g.getScore() + ")");
            justRaised = true;
            do {
                g.setApple(new Apple(g.getRandomPoint()));
            } while (bodyCoordinates.contains(g.getApple().getApplePosition()));
            g.getApple().updateOnScreen(g);
        }

        updateOnScreen(g);
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Direction - ").append(direction).append("\n");

        for (Point bodyCoordinate : bodyCoordinates) {
            sb.append(bodyCoordinate.getRow() + ", " + bodyCoordinate.getColumn() + "\n");
        }
        return sb.toString();
    }
}