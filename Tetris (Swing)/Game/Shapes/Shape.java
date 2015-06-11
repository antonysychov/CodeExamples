package Game.Shapes;

import Game.Game;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Антон on 12.01.2015.
 */
public class Shape {
    protected LinkedList<ShapeNSize> listOfPossibleShapesNSizes = new LinkedList<ShapeNSize>();   //список возможных фигур

    protected int currentShapeInList;                         //номер текущей фигуры из списка

    protected LinkedList<Point> currentShapeCoordinates;      //координаты текущей фигуры

    protected int width;                                      //ширина текущей фигуры
    protected int height;                                     //высота текущей фигуры

    protected Color color = Color.BLACK;

    protected static Color getRandomColor() {                   //получить случайный цвет
        switch ((int) (Math.random() * 7)) {
            case 0:
                return new Color(0x4CB3D2);
            case 1:
                return new Color(0xED92E1);
            case 2:
                return new Color(0xF9F900);
            case 3:
                return new Color(0xFFC306);
            case 4:
                return new Color(0xFE6247);
            case 5:
                return new Color(0x71F52C);
            case 6:
                return new Color(0x3E4DF5);
        }
        return null;
    }

    public void rotateLeft()                               //повернуть фигуру налево
    {
        currentShapeInList = (currentShapeInList - 1) >= 0 ? currentShapeInList - 1 : listOfPossibleShapesNSizes.size() - 1;
        currentShapeCoordinates = listOfPossibleShapesNSizes.get(currentShapeInList).listOfPoints;
        width = listOfPossibleShapesNSizes.get(currentShapeInList).width;
        height = listOfPossibleShapesNSizes.get(currentShapeInList).height;
    }

    public void rotateRight() {                           //повернуть фигуру направо
        currentShapeInList = (currentShapeInList + 1) <= (listOfPossibleShapesNSizes.size() - 1) ? currentShapeInList + 1 : 0;
        currentShapeCoordinates = listOfPossibleShapesNSizes.get(currentShapeInList).listOfPoints;
        width = listOfPossibleShapesNSizes.get(currentShapeInList).width;
        height = listOfPossibleShapesNSizes.get(currentShapeInList).height;
    }

    public void updateOnScreen(Game screen){           //обновить фигуру на экране
        for (Point point : currentShapeCoordinates) {
            if (point.row + screen.getCurrentShapeVShift() >= 0 && point.row + screen.getCurrentShapeVShift() <= screen.getRowsOfGlass() - 1) {
                screen.getJlm()[point.row + screen.getCurrentShapeVShift()][point.column + screen.getCurrentShapeHShift()].setBackground(color);
                screen.getJlm()[point.row + screen.getCurrentShapeVShift()][point.column + screen.getCurrentShapeHShift()].setIsFilled(true);
            }
        }
    }

    public void removeFromScreen(Game screen)         //удалить фигуру на экране
    {
        for (Point point : currentShapeCoordinates) {
            if (point.row + screen.getCurrentShapeVShift() >= 0 && point.row + screen.getCurrentShapeVShift() <= screen.getRowsOfGlass() - 1) {
                screen.getJlm()[point.row + screen.getCurrentShapeVShift()][point.column + screen.getCurrentShapeHShift()].setBackground(screen.getDefaultColor());
                screen.getJlm()[point.row + screen.getCurrentShapeVShift()][point.column + screen.getCurrentShapeHShift()].setIsFilled(false);
            }
        }
    }

    /**
     * Created by Антон on 12.01.2015.
     */
    public static class Point {
        private int row;
        private int column;
        private Color color = Color.BLACK;

        public Point(int row, int column) {
            this.column = column;
            this.row = row;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }

    public LinkedList<ShapeNSize> getListOfPossibleShapesNSizes() {
        return listOfPossibleShapesNSizes;
    }

    public void setListOfPossibleShapesNSizes(LinkedList<ShapeNSize> listOfPossibleShapesNSizes) {
        this.listOfPossibleShapesNSizes = listOfPossibleShapesNSizes;
    }

    public int getCurrentShapeInList() {
        return currentShapeInList;
    }

    public void setCurrentShapeInList(int currentShapeInList) {
        this.currentShapeInList = currentShapeInList;
    }

    public LinkedList<Point> getCurrentShapeCoordinates() {
        return currentShapeCoordinates;
    }

    public void setCurrentShapeCoordinates(LinkedList<Point> currentShapeCoordinates) {
        this.currentShapeCoordinates = currentShapeCoordinates;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    protected static class ShapeNSize {
        protected LinkedList<Point> listOfPoints = new LinkedList<Point>();
        protected int width;
        protected int height;

        public ShapeNSize(LinkedList<Point> listOfPoints, int width, int height) {
            this.listOfPoints = listOfPoints;
            this.width = width;
            this.height = height;
        }
    }
}
