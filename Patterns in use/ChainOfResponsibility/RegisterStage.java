package HomeWork.HomeWork8.Task2.ChainOfResponsibility;

public class RegisterStage extends Stage{
    @Override
    protected void handle(Payment p) {
        System.out.println("Transfer information has been written to database");
    }
}
