package com.example.ExpressionCalculator;

import com.example.ExpressionCalculator.Utils.PolishNotation;

import java.util.LinkedList;
import java.util.List;

public class Calculator {

    public double calculate(String input) {
        List<PolishNotation.Unit> listOfUnits = PolishNotation.getPostfixNotationUnits(input);
        System.out.println(listOfUnits);

        LinkedList<ComponentNode> listOfNodes = new LinkedList<>();
        for (PolishNotation.Unit u : listOfUnits) {
            listOfNodes.add(new ComponentNode(u, null, null, null));
        }

        CompositeTree tree = new CompositeTree();
        for (int i = 0; i < listOfNodes.size(); i++) {
            if (listOfNodes.size() == 1) break;
            ComponentNode node = listOfNodes.get(i);
            if (node.key.getType() == PolishNotation.UnitType.MINUS ||
                    node.key.getType() == PolishNotation.UnitType.PLUS ||
                    node.key.getType() == PolishNotation.UnitType.DIVIDE ||
                    node.key.getType() == PolishNotation.UnitType.MULTIPLY) {
                node.left = (listOfNodes.get(i - 2));
                node.right = (listOfNodes.get(i - 1));
                if (tree.root == null) {
                    tree.root = node;
                }
                listOfNodes.remove(i - 1);
                listOfNodes.remove(i - 2);
                i = i - 2;
            }
        }
        return listOfNodes.getFirst().calculate();
    }

    private abstract static class Component {
        Component parent;
        Component left;
        Component right;
        PolishNotation.Unit key;

        abstract double calculate();

        abstract void print();
    }

    private class CompositeTree extends Component {
        Component root;

        @Override
        public void print() {
            root.print();
        }

        @Override
        public double calculate() {
            return root.calculate();
        }
    }

    private class ComponentNode extends Component {

        public ComponentNode(PolishNotation.Unit key, Component left, Component right, Component parent) {
            this.key = key;
            this.parent = parent;
            this.left = left;
            this.right = right;
            if (left != null) left.parent = this;
            if (right != null) right.parent = this;
            this.parent = null;
        }

        private void print(Component node) {
            System.out.println(node);
            if (node.right != null) print(node.right);
            if (node.left != null) print(node.left);
        }

        @Override
        public void print() {
            print(this);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", left=" + ((left != null) ? left.key : null) +
                    ", right=" + ((right != null) ? right.key : null) +
                    ", parent=" + ((parent != null) ? parent.key : null) +
                    '}';
        }

        @Override
        public double calculate() {
            switch (key.getType()) {
                case MINUS:
                    return left.calculate() - right.calculate();
                case PLUS:
                    return left.calculate() + right.calculate();
                case MULTIPLY:
                    return left.calculate() * right.calculate();
                case DIVIDE:
                    return left.calculate() / right.calculate();
                case DIGIT:
                    return Double.valueOf(key.getUnit());
            }
            try {
                throw new IllegalArgumentException("wrong character");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
}
