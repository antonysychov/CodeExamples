package HomeWork.HomeWork8.Task2.Strategy.Characters;

import HomeWork.HomeWork8.Task2.Strategy.Movements.Flying;

public class Harpy extends Character {
    {
        movement = new Flying();
    }

    public Harpy(String name, int strength, int health) {
        super(name, strength, health);
    }

    public void fly() {
        move();
    }
}
