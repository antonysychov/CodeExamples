package HomeWork.HomeWork8.Task2.Strategy.Characters;

import HomeWork.HomeWork8.Task2.Strategy.Movements.Movement;

public abstract class Character {
    protected Movement movement;
    protected String name;
    protected int strength;
    protected int health;

    public Character(String name, int strength, int health) {
        this.name = name;
        this.strength = strength;
        this.health = health;
    }

    protected void move() {
        System.out.print(name + " is ");
        movement.move();
    }

}
