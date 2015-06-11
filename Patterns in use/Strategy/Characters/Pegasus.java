package HomeWork.HomeWork8.Task2.Strategy.Characters;

import HomeWork.HomeWork8.Task2.Strategy.Movements.Flying;
import HomeWork.HomeWork8.Task2.Strategy.Movements.Walking;

public class Pegasus extends Character {
    public Pegasus(String name, int strength, int health) {
        super(name, strength, health);
    }

    public void walk() {
        movement = new Walking();
        move();
    }

    public void fly() {
        movement = new Flying();
        move();
    }
}
