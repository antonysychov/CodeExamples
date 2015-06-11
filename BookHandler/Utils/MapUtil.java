package Projects.Project2.Utils;

import java.util.*;

public class MapUtil {
    public static <K, V extends Comparable<V>> Map<K, V> sortByValue(Map<K, V> map, boolean ascDesc) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int res = (o1.getValue()).compareTo(o2.getValue());
                return ascDesc ? -res : res;
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
