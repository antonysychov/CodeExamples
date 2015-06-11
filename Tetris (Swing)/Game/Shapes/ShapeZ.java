package Game.Shapes;

import java.util.LinkedList;

/**
 * Created by Антон on 12.01.2015.
 */
public class ShapeZ extends Shape
{
    //формирование списка возможных фигур
    {
        listOfPossibleShapesNSizes.clear();
        LinkedList<Point> temp1 = new LinkedList<Point>();
        LinkedList<Point> temp2 = new LinkedList<Point>();
        temp1.add(new Point(-1, 0));
        temp1.add(new Point(-2, 0));
        temp1.add(new Point(-2, 1));
        temp1.add(new Point(-3, 1));
        listOfPossibleShapesNSizes.add(new ShapeNSize(temp1, 2, 3));
        temp2.add(new Point(-1, 1));
        temp2.add(new Point(-1, 2));
        temp2.add(new Point(-2, 0));
        temp2.add(new Point(-2, 1));
        listOfPossibleShapesNSizes.add(new ShapeNSize(temp2, 3, 2));
    }

    public ShapeZ()
    {
        currentShapeInList = (int) (Math.random() * listOfPossibleShapesNSizes.size() - 1);
        currentShapeCoordinates = listOfPossibleShapesNSizes.get(currentShapeInList).listOfPoints;
        width = listOfPossibleShapesNSizes.get(currentShapeInList).width;
        height = listOfPossibleShapesNSizes.get(currentShapeInList).height;
        color = getRandomColor();
    }
}
