package HomeWork.HomeWork8.Task2.Observer;

public class Subscriber {
    public String name;
    public String address;

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
