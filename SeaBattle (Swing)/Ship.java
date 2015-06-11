package HomeWork.HomeWork5.SeaBattle;

import java.util.LinkedList;

public class Ship {
    volatile LinkedList<Point> cellList = new LinkedList<>();
    ShipType type;

    public Ship(LinkedList<Point> cellList, ShipType type) {
        this.cellList = cellList;
        this.type = type;
    }

    public Ship() {
    }
}
