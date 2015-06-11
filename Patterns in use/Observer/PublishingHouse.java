package HomeWork.HomeWork8.Task2.Observer;

import HomeWork.HomeWork8.Task2.Observer.Item.Item;
import HomeWork.HomeWork8.Task2.Observer.Item.Journal;
import HomeWork.HomeWork8.Task2.Observer.Item.Magazine;

import java.util.LinkedList;
import java.util.List;

public class PublishingHouse {
    List<Item> getNewItems() {
        List<Item> newItems = new LinkedList<>();
        newItems.add(new Journal("Журнал \"Мурзилка\" "));
        newItems.add(new Magazine("Газета \"Строитель\" "));
        newItems.add(new Journal("Журнал \"Охота\" "));
        newItems.add(new Magazine("Газета \"Вести\" "));
        return newItems;
    }
}
