package HomeWork.HomeWork5.SeaBattle;

public abstract class Player {
    protected Field field = new Field();

    public abstract Point makeShoot();

    public abstract boolean acceptShoot(Point c);

    public abstract boolean outOfShips();

    public abstract void placeShips();

    public void clearField() {
        field.field = new Point[10][10];
        field.ships.clear();
        field.previouslyClicked.clear();
    }
}
