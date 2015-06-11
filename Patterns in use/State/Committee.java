package HomeWork.HomeWork8.Task2.State;

import HomeWork.HomeWork8.Task2.State.State.*;

import java.util.HashMap;
import java.util.Map;

public class Committee {

    private int maxId = 0;
    private Map<Integer, Order> orders = new HashMap<>();

    public int submitOrder(Order order) {
        order.changeState(new Created());
        orders.put(++maxId, order);
        considerOrder(maxId);
        return maxId;
    }

    public State getOrderState(int id) {
        return orders.get(id).getOrderState();
    }

    public Order recallOrder(int id) {
        orders.get(id).changeState(new Recalled());
        return orders.remove(id);
    }

    public Order getOrder(int id) {
        return orders.get(id);
    }

    private void removeOrder(int id) {
        orders.remove(id);
    }

    private void considerOrder(final int id) {
        orders.get(id).changeState(new Considered());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((int) (Math.random() * 3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        postponeOrder(id);
                        break;
                    case 1:
                        declineOrder(id);
                        break;
                    case 2:
                        confirmOrder(id);
                        break;
                }
            }
        }).start();
    }

    private void postponeOrder(int id) {
        orders.get(id).changeState(new Postponed());
    }

    private void confirmOrder(int id) {
        orders.get(id).changeState(new Confirmed());
    }

    private void declineOrder(int id) {
        orders.get(id).changeState(new Declined());
    }

}
