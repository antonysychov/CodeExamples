package HomeWork.HomeWork8.Task2.ChainOfResponsibility;

abstract class Stage {

    private Stage next;

    public void setNext(Stage logger) {
        next = logger;
    }

    abstract protected void handle(Payment p);

    protected void sendNext(Payment p) {
        if (next != null) {
            next.handle(p);
        } else {
            System.out.println("Платеж не был обработан");
        }
    }
}
