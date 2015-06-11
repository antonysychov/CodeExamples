package HomeWork.HomeWork8.Task2.Observer;

import HomeWork.HomeWork8.Task2.Observer.Item.Item;

import java.util.LinkedList;
import java.util.List;

public class PostOffice {
    private List<Subscriber> subscribers = new LinkedList<>();
    private List<Item> newItems = new LinkedList<>();
    private OutboxOffice outboxOffice = new OutboxOffice();

    private void send(Item item, Subscriber subscriber) {
        System.out.println(item.name + "was sent to " + subscriber.name);
    }

    public void acceptNewItems(PublishingHouse ph) {
        List<Item> newItems = ph.getNewItems();
        this.newItems.addAll(newItems);
        outboxOffice.inform();
        this.newItems.clear();
    }

    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
    }

    public void removeSubscriber(Subscriber s) {
        subscribers.remove(s);
    }

    private class OutboxOffice {
        public void inform() {
            for (Subscriber s : subscribers) {
                for (Item i : newItems) {
                    send(i, s);
                }
            }
        }
    }

}
