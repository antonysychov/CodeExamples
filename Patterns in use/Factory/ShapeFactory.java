package HomeWork.HomeWork8.Task2.Factory;

public class ShapeFactory {
    Shape getRandomShape() {
        switch ((int) (Math.random() * 4)) {
            case 0:
                return new L();
            case 1:
                return new T();
            case 2:
                return new I();
            case 3:
                return new Z();
            default:
                return null;
        }
    }
}

abstract class Shape {
    abstract void printType();
}

class L extends Shape {
    @Override
    void printType() {
        System.out.println("L");
    }
}

class T extends Shape {
    @Override
    void printType() {
        System.out.println("T");
    }
}

class I extends Shape {
    @Override
    void printType() {
        System.out.println("I");
    }
}

class Z extends Shape {
    @Override
    void printType() {
        System.out.println("Z");
    }
}

