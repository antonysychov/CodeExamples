package HomeWork.HomeWork8.Task2.FlyWeight;

import java.util.HashMap;

public class MicrobeFactory {
    private static final HashMap<Integer, Microbe> microbeMap = new HashMap<>();

    public static Microbe getMicrobe(Integer i) {
        Microbe m = microbeMap.get(i);

        if (m == null) {
            m = new Microbe(i);
            microbeMap.put(i, m);
        }
        return m;
    }

    public static int getSize() {
        return microbeMap.size();
    }
}
