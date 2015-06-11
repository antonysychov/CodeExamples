package HomeWork.HomeWork8.Task2.FlyWeight;

import java.util.LinkedList;
import java.util.List;

public class Colony {

    private int generationIntensity = 0;
    private volatile int currentStep = 1;
    private volatile List<Microbe> microbes = new LinkedList<>();

    public Colony(int generationIntensity) {
        this.generationIntensity = generationIntensity;
        startGeneration();
        stepAutoChange();
    }

    public int getPopulation() {
        return microbes.size();
    }

    private void startGeneration() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentStep <= 4) {
                    int population = getPopulation();
                    switch (currentStep) {
                        case 1:
                            for (int i = 0; i < population + 1; i++) {
                                microbes.add(MicrobeFactory.getMicrobe(((int) (Math.random() * 50))));
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            for (int i = 0; i < population * 2 + 1; i++) {
                                microbes.add(MicrobeFactory.getMicrobe(((int) (Math.random() * 50))));
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            for (int i = 0; i < population + 1; i++) {
                                if (microbes.size() != 0)
                                    microbes.remove(((int) (Math.random() * microbes.size())));
                            }
                            break;
                    }
                }
            }
        }).start();

    }

    private void stepAutoChange() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentStep <= 4) {

                    System.out.println("Фаза размножения: " + currentStep);


                    try {
                        Thread.sleep(generationIntensity * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Количество фактических объектов: " + MicrobeFactory.getSize());
                    System.out.println("Количество бактерий: " + getPopulation());
                    System.out.println("----------------------------------");
                    ++currentStep;

                }
            }
        }).start();
    }
}
