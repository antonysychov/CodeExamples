package HomeWork.HomeWork5.SeaBattle;

import java.util.LinkedList;

public class Computer extends Player {

    public Computer() {
    }

    @Override
    public void placeShips() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field.field[i][j] = new Point(i, j);
            }
        }
        //adding ships (randomly)
        LinkedList<Point> oneDecker1 = new LinkedList<Point>();
        LinkedList<Point> oneDecker2 = new LinkedList<Point>();
        LinkedList<Point> oneDecker3 = new LinkedList<Point>();
        LinkedList<Point> oneDecker4 = new LinkedList<Point>();
        LinkedList<Point> twoDecker1 = new LinkedList<Point>();
        LinkedList<Point> twoDecker2 = new LinkedList<Point>();
        LinkedList<Point> twoDecker3 = new LinkedList<Point>();
        LinkedList<Point> threeDecker1 = new LinkedList<Point>();
        LinkedList<Point> threeDecker2 = new LinkedList<Point>();
        LinkedList<Point> fourDecker = new LinkedList<Point>();
        oneDecker1.add(field.field[4][3]);
        oneDecker2.add(field.field[9][0]);
        oneDecker3.add(field.field[7][5]);
        oneDecker4.add(field.field[4][6]);
        field.ships.add(new Ship(oneDecker1, ShipType.ONE_DECKER));
        field.ships.add(new Ship(oneDecker2, ShipType.ONE_DECKER));
        field.ships.add(new Ship(oneDecker3, ShipType.ONE_DECKER));
        field.ships.add(new Ship(oneDecker4, ShipType.ONE_DECKER));
        twoDecker1.add(field.field[8][2]);
        twoDecker1.add(field.field[8][3]);
        twoDecker2.add(field.field[6][8]);
        twoDecker2.add(field.field[7][8]);
        twoDecker3.add(field.field[1][6]);
        twoDecker3.add(field.field[1][7]);
        field.ships.add(new Ship(twoDecker1, ShipType.TWO_DECKER));
        field.ships.add(new Ship(twoDecker2, ShipType.TWO_DECKER));
        field.ships.add(new Ship(twoDecker3, ShipType.TWO_DECKER));
        threeDecker1.add(field.field[0][0]);
        threeDecker1.add(field.field[0][1]);
        threeDecker1.add(field.field[0][2]);
        threeDecker2.add(field.field[9][6]);
        threeDecker2.add(field.field[9][7]);
        threeDecker2.add(field.field[9][8]);
        field.ships.add(new Ship(threeDecker1, ShipType.THREE_DECKER));
        field.ships.add(new Ship(threeDecker2, ShipType.THREE_DECKER));
        fourDecker.add(field.field[3][1]);
        fourDecker.add(field.field[4][1]);
        fourDecker.add(field.field[5][1]);
        fourDecker.add(field.field[6][1]);
        field.ships.add(new Ship(fourDecker, ShipType.FOUR_DECKER));

        for (Ship ship : field.ships) {
            for (Point point : ship.cellList) {
                point.isFilled = true;
            }
        }
    }

    @Override
    public void clearField() {
        field.field = new Point[10][10];
        field.ships.clear();
        field.previouslyClicked.clear();
    }

    @Override
    public boolean acceptShoot(Point c) {

        for (int i = 0; i < field.field.length; i++) {
            for (int j = 0; j < field.field[i].length; j++) {
                Point temp = field.field[i][j];
                if (temp.isFilled && c.x == temp.x && c.y == temp.y) {
                    temp.isFilled = false;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean outOfShips() {
        return field.isEmpty();
    }

    @Override
    public Point makeShoot() {
        int xRandom = (int) (Math.random() * 9);
        int yRandom = (int) (Math.random() * 9);
        return new Point(xRandom, yRandom);
    }

}
