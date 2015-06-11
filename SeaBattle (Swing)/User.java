package HomeWork.HomeWork5.SeaBattle;

public class User extends Player {
    public User() {

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
    public void placeShips() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field.field[i][j] = new Point(i, j);
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                label:
                while (true) {
                    if (!field.canCreateShip()) break label;
                    Point temp = Game.returnClickedPointOnLeftField();
                    if (temp == null) break label;
                    temp = field.field[temp.x][temp.y];
                    if (!field.belongToShip(temp) && !field.touchesShip(temp)) {
                        if (field.previouslyClicked.isEmpty()) {
                            if (field.canCreateShip()) {
                                field.previouslyClicked.add(temp);
                                Game.outlineCellOnLeftField(temp.x, temp.y);
                                if (!field.canCreateHigherDeckShip(ShipType.values()[field.previouslyClicked.size() - 1])) {
                                    field.addShipFromPrevClicked();
                                }
                            }
                        } else if (field.touchesPrevClicked(temp)) {
                            if (field.canCreateShip()
                                    && (field.canCreateHigherDeckShip(ShipType.values()[field.previouslyClicked.size() - 1]))) {
                                field.previouslyClicked.add(temp);
                                Game.outlineCellOnLeftField(temp.x, temp.y);
                                if (!field.canCreateHigherDeckShip(ShipType.values()[field.previouslyClicked.size() - 1])) {
                                    field.addShipFromPrevClicked();
                                }
                            }
                        } else if (!field.touchesPrevClicked(temp)) {
                            if (field.canCreateSpecificShip(ShipType.values()[field.previouslyClicked.size() - 1])) {
                                field.addShipFromPrevClicked();
                            } else {
                                for (Point cell : field.previouslyClicked) {
                                    Game.deColorCellOnLeftField(cell.x, cell.y);
                                }
                                field.previouslyClicked.clear();
                            }
                        }
                    }
                    if (field.isFilled()) {
                        Field.isFilled = true;
                    }
                }
            }
        }).start();

    }

    @Override
    public Point makeShoot() {
        return Game.returnClickedPointOnRightField();
    }

}
