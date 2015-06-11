package HomeWork.HomeWork8.Task2.ChainOfResponsibility;

class BalanceCheckStage extends Stage {
    protected void handle(Payment p) {
        if (p.getSender().getBalance() <= p.getValue()) {
            System.out.println("Balance isn't enough");
            return;
        }
        sendNext(p);
    }
}
