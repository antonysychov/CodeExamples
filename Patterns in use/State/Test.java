package HomeWork.HomeWork8.Task2.State;

import java.util.LinkedList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Committee c = new Committee();
        List<Integer> submittedOrders = new LinkedList<>();
        submittedOrders.add(c.submitOrder(new Order("Order for educational grant")));
        submittedOrders.add(c.submitOrder(new Order("Order for funding studying project")));
        submittedOrders.add(c.submitOrder(new Order("Order for funding laboratory modernisation")));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Integer i : submittedOrders) {
            System.out.println(c.getOrder(i));
        }
    }
}
