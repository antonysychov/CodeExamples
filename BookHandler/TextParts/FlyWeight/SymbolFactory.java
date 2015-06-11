package Projects.Project2.TextParts.FlyWeight;

import java.util.LinkedList;
import java.util.List;

public class SymbolFactory {
    private static final List<Symbol> symbols = new LinkedList<>();

    public static Symbol getSymbol(char i) {
        Symbol c = new Symbol(i);

        if (!symbols.contains(c)) {
            symbols.add(c);
        }
        int index = symbols.indexOf(c);
        return symbols.get(index);
    }

    public static int getSize() {
        return symbols.size();
    }
}
