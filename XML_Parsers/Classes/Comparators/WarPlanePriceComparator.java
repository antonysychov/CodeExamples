package Projects.Project3.Classes.Comparators;

import Projects.Project3.Classes.WarPlane;

import java.util.Comparator;

public class WarPlanePriceComparator implements Comparator<WarPlane> {
    @Override
    public int compare(WarPlane o1, WarPlane o2) {
        return (int)(o1.getPrice() - o2.getPrice());
    }
}
