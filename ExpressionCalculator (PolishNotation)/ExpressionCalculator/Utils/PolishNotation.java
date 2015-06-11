package com.example.ExpressionCalculator.Utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PolishNotation {

    private static String output;

    public static String transform(String input) throws IllegalArgumentException {
        if (!isValidExpression(input)) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder("");
        UnitIterator it = getIterator(input);

        LinkedList<Unit> stack = new LinkedList<>();

        while (it.hasNext()) {
            Unit currentUnit = it.next();
            switch (currentUnit.type) {
                case DIGIT:
                    sb.append(currentUnit.unit).append(',');
                    break;
                case PLUS:
                case MINUS:
                case MULTIPLY:
                case DIVIDE:

                    while (true) {
                        if (stack.isEmpty()) {
                            stack.addLast(currentUnit);
                            break;
                        }
                        int highestPriority = 0;
                        for (Unit temp : stack) {
                            if (temp.priority >= highestPriority) {
                                highestPriority = temp.priority;
                            }
                        }
                        if (highestPriority < currentUnit.priority) {
                            stack.addLast(currentUnit);
                            break;
                        } else {
                            if (!(stack.getLast().priority >= currentUnit.priority)) {
                                stack.addLast(currentUnit);
                                break;
                            }

                            while (!stack.isEmpty() && stack.getLast().priority >= currentUnit.priority) {
                                sb.append(stack.removeLast().unit).append(',');
                            }
                        }
                    }
                    break;
                case O_BRACKET:
                    stack.add(currentUnit);
                    break;
                case C_BRACKET:
                    while (!stack.isEmpty() && stack.getLast().type != UnitType.O_BRACKET) {
                        sb.append(stack.removeLast().unit).append(',');
                    }
                    stack.removeLast();
                    break;
            }
        }
        while (!stack.isEmpty()) {
            sb.append(stack.removeLast().unit).append(',');
        }

        return output = sb.toString();
    }

    private static UnitIterator getIterator(String s) {
        return new UnitIterator(s);
    }

    public static List<Unit> getPostfixNotationUnits(String s) {
        List<Unit> list = new LinkedList<>();
        UnitIterator it = getIterator(transform(s));

        while (it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }

    public static boolean isValidExpression(String s) {
        char[] characters = s.toCharArray();
        int leftBracesCount = 0;
        int rightBracesCount = 0;
        for (char c : characters) {
            if (!(Character.isDigit(c) ||
                    c == '(' || c == ')' || c == '+' || c == '.' ||
                    c == '-' || c == '*' || c == '/')) return false;
            if (c == ')' && leftBracesCount == 0) {
                return false;
            }
            if (c == '(') {
                leftBracesCount++;
            } else if (c == ')') {
                rightBracesCount++;
            }
        }
        return leftBracesCount == rightBracesCount;
    }

    private static Unit getUnit(String s, int pos) {
        for (int i = pos; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '+':
                    return new Unit(2, String.valueOf(ch), UnitType.PLUS);
                case '-':
                    return new Unit(2, String.valueOf(ch), UnitType.MINUS);
                case '*':
                    return new Unit(3, String.valueOf(ch), UnitType.MULTIPLY);
                case '/':
                    return new Unit(3, String.valueOf(ch), UnitType.DIVIDE);
                case '(':
                    return new Unit(1, String.valueOf(ch), UnitType.O_BRACKET);
                case ')':
                    return new Unit(1, String.valueOf(ch), UnitType.C_BRACKET);
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    StringBuilder sb = new StringBuilder(String.valueOf(ch));
                    int dotCount = 0;
                    while (true) {
                        if (++i >= s.length()) break;
                        ch = s.charAt(i);
                        if (ch == '.') {
                            if (dotCount > 0) throw new IllegalArgumentException("multiple dots in one digit");
                            dotCount++;
                        }
                        if (isPartOfFloat(ch)) {
                            sb.append(ch);
                        } else return new Unit(0, sb.toString(), UnitType.DIGIT);
                    }
                    return new Unit(0, sb.toString(), UnitType.DIGIT);
            }
        }
        return null;
    }

    private static boolean isPartOfFloat(char ch) {
        return Character.isDigit(ch) || ch == '.';
    }

    public static class Unit {
        private int priority;
        private UnitType type;
        private String unit;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public UnitType getType() {
            return type;
        }

        public void setType(UnitType type) {
            this.type = type;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }


        public Unit(int priority, String unit, UnitType type) {
            this.priority = priority;
            this.type = type;
            this.unit = unit;
        }

        @Override
        public String toString() {
            return "unit=" + unit;
        }
    }

    private static class UnitIterator implements Iterator<Unit> {
        private int currentPos = 0;
        private int lastSize = 0;
        private String s;

        public UnitIterator(String s) {
            this.s = s;
        }

        @Override
        public Unit next() {
            Unit unit = getUnit(s, currentPos);
            lastSize = unit.unit.length();
            currentPos += unit.unit.length();
            currentPos = (s.equals(output)) ? ++currentPos : currentPos;
            return unit;
        }

        @Override
        public boolean hasNext() {
            return getUnit(s, currentPos) != null;
        }

        //unused
        @Override
        public void remove() {

        }

    }

    public enum UnitType {
        PLUS, MINUS, MULTIPLY, DIVIDE, DIGIT, O_BRACKET, C_BRACKET
    }
}
