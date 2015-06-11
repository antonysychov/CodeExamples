package HomeWork.HomeWork8.Task2.State;

import HomeWork.HomeWork8.Task2.State.State.State;

public class Order {
    private State orderState;
    private String name;

    public Order(String name) {
        this.name = name;
    }

    public void changeState(State s) {
        this.orderState = s;
    }

    public State getOrderState() {
        return orderState;
    }

    @Override
    public String toString() {
        return name + " " + orderState;
    }
}
