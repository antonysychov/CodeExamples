package Game.Shapes;

import java.util.LinkedList;

/**
 * Created by Антон on 12.01.2015.
 */
public class ShapeI extends Shape
{
    //формирование списка возможных фигур
    {
        listOfPossibleShapesNSizes.clear();
        LinkedList<Point> temp1 = new LinkedList<Point>();
        LinkedList<Point> temp2 = new LinkedList<Point>();
        temp1.add(new Point(-1, 0));
        temp1.add(new Point(-1, 1));
        temp1.add(new Point(-1, 2));
        temp1.add(new Point(-1, 3));
        listOfPossibleShapesNSizes.add(new ShapeNSize(temp1, 4, 1));
        temp2.add(new Point(-1, 0));
        temp2.add(new Point(-2, 0));
        temp2.add(new Point(-3, 0));
        temp2.add(new Point(-4, 0));
        listOfPossibleShapesNSizes.add(new ShapeNSize(temp2, 1, 4));
    }

    public ShapeI()
    {
        currentShapeInList = (int) (Math.random() * listOfPossibleShapesNSizes.size() - 1);
        currentShapeCoordinates = listOfPossibleShapesNSizes.get(currentShapeInList).listOfPoints;
        width = listOfPossibleShapesNSizes.get(currentShapeInList).width;
        height = listOfPossibleShapesNSizes.get(currentShapeInList).height;
        color = getRandomColor();
    }
}
