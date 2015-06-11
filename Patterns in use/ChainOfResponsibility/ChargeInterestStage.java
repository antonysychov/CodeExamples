package HomeWork.HomeWork8.Task2.ChainOfResponsibility;

public class ChargeInterestStage extends Stage{
    protected void handle(Payment p) {
        System.out.println("Charge interest% is done");
        sendNext(p);
    }
}
