package HomeWork.HomeWork5.SeaBattle;

import java.awt.*;
import java.util.LinkedList;

public class Field {
    static volatile boolean isFilled;
    volatile Point[][] field = new Point[10][10];
    volatile LinkedList<Ship> ships = new LinkedList<Ship>();
    Color fieldColor = Color.white;
    LinkedList<Point> previouslyClicked = new LinkedList<>();

    boolean belongToShip(Point cell) {
        for (Ship ship : ships) {
            for (Point temp : ship.cellList) {
                if (cell.x == temp.x && cell.y == temp.y) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean touchesShip(Point cell) {
        for (Ship ship : ships) {
            for (Point temp : ship.cellList) {
                if (((cell.x == temp.x + 1 || cell.x == temp.x - 1) && (cell.y == temp.y))
                        || ((cell.y == temp.y + 1 || cell.y == temp.y - 1) && (cell.x == temp.x))) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean canCreateSpecificShip(ShipType type) {
        int count = 0;
        for (Ship ship : ships) {
            if (ship.type == type) {
                count++;
            }
        }
        switch (count) {
            case 0:
                return true;
            case 1:
                if (type == ShipType.FOUR_DECKER) return false;
                else return true;
            case 2:
                if (type == ShipType.THREE_DECKER) return false;
                else return true;
            case 3:
                if (type == ShipType.TWO_DECKER) return false;
                else return true;
            case 4:
                if (type == ShipType.ONE_DECKER) return false;
                else return true;
            default:
                return false;
        }
    }

    boolean canCreateHigherDeckShip(ShipType type) {
        if (type.ordinal() == 3) return false;
        for (int i = type.ordinal() + 1; i < ShipType.values().length; i++) {
            if (canCreateSpecificShip(ShipType.values()[i])) {
                return true;
            }
        }
        return false;
    }

    boolean canCreateShip() {
        int fourDecker = 0;
        int twoDecker = 0;
        int threeDecker = 0;
        int oneDecker = 0;
        for (Ship ship : ships) {
            if (ship.type == ShipType.FOUR_DECKER) {
                fourDecker++;
            }
            if (ship.type == ShipType.THREE_DECKER) {
                threeDecker++;
            }
            if (ship.type == ShipType.TWO_DECKER) {
                twoDecker++;
            }
            if (ship.type == ShipType.ONE_DECKER) {
                oneDecker++;
            }
        }
        return (fourDecker < 1 || threeDecker < 2 || twoDecker < 3 || oneDecker < 4);
    }

    boolean touchesPrevClicked(Point cell) {
        for (Point temp : previouslyClicked) {
            if ((((temp.x == cell.x + 1 || temp.x == cell.x - 1) && (temp.y == cell.y)))
                    || (((temp.y == cell.y + 1 || temp.y == cell.y - 1) && (temp.x == cell.x)))) {
                return true;
            }
        }
        return false;
    }

    void showShip(Ship ship) {
        for (Point temp : ship.cellList) {
            Game.colorCellOnLeftField(temp.x, temp.y, new Color(76, 179, 210));
            Game.deOutlineCellOnLeftField(temp.x, temp.y);
        }
    }

    void addShipFromPrevClicked() {
        Ship temp = new Ship();
        for (Point cell : previouslyClicked) {
            cell.isFilled = true;
            temp.cellList.add(cell);
        }
        temp.type = ShipType.values()[previouslyClicked.size() - 1];
        ships.add(temp);
        previouslyClicked.clear();
        showShip(temp);
    }

    public boolean isFilled() {
        int filledPoints = 0;
        for (Ship ship : ships) {
            for (Point cell : ship.cellList) {
                if (cell.isFilled) filledPoints++;
            }
        }
        return filledPoints == 20;
    }

    public boolean isEmpty() {
        int filledPoints = 0;
        for (Ship ship : ships) {
            for (Point cell : ship.cellList) {
                if (cell.isFilled) filledPoints++;
            }
        }
        return filledPoints == 0;
    }

}

