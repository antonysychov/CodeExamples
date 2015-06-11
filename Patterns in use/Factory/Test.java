package HomeWork.HomeWork8.Task2.Factory;

public class Test {
    public static void main(String[] args) {
        ShapeFactory sf = new ShapeFactory();
        for (int i = 0; i < 10; i++) {
            sf.getRandomShape().printType();
        }
    }
}
