package Game.Shapes;

import java.util.LinkedList;

/**
 * Created by Антон on 14.01.2015.
 */
public class ShapeJ extends Shape
{
    //формирование списка возможных фигур
    {
        listOfPossibleShapesNSizes.clear();
        LinkedList<Point> temp1 = new LinkedList<Point>();
        LinkedList<Point> temp2 = new LinkedList<Point>();
        LinkedList<Point> temp3 = new LinkedList<Point>();
        LinkedList<Point> temp4 = new LinkedList<Point>();
        temp1.add(new Point(-1, 0));
        temp1.add(new Point(-1, 1));
        temp1.add(new Point(-2, 1));
        temp1.add(new Point(-3, 1));
        listOfPossibleShapesNSizes.add(new ShapeNSize(temp1, 2, 3));
        temp2.add(new Point(-1, 0));
        temp2.add(new Point(-1, 1));
        temp2.add(new Point(-1, 2));
        temp2.add(new Point(-2, 0));
        listOfPossibleShapesNSizes.add(new ShapeNSize(temp2, 3, 2));
        temp3.add(new Point(-1, 0));
        temp3.add(new Point(-2, 0));
        temp3.add(new Point(-3, 0));
        temp3.add(new Point(-3, 1));
        listOfPossibleShapesNSizes.add(new ShapeNSize(temp3, 2, 3));
        temp4.add(new Point(-1, 2));
        temp4.add(new Point(-2, 0));
        temp4.add(new Point(-2, 1));
        temp4.add(new Point(-2, 2));
        listOfPossibleShapesNSizes.add(new ShapeNSize(temp4, 3, 2));
    }

    public ShapeJ()
    {
        currentShapeInList = (int) (Math.random() * listOfPossibleShapesNSizes.size() - 1);
        currentShapeCoordinates = listOfPossibleShapesNSizes.get(currentShapeInList).listOfPoints;
        width = listOfPossibleShapesNSizes.get(currentShapeInList).width;
        height = listOfPossibleShapesNSizes.get(currentShapeInList).height;
        color = getRandomColor();
    }
}