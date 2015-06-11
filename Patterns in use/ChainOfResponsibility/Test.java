package HomeWork.HomeWork8.Task2.ChainOfResponsibility;

public class Test {

    private static Stage createChain() {
        // Build the chain of responsibility

        Stage balanceCheck = new BalanceCheckStage();
        Stage transferStage = new TransferStage();
        Stage registerStage = new RegisterStage();
        Stage chargeInterestStage = new ChargeInterestStage();

        balanceCheck.setNext(transferStage);
        transferStage.setNext(chargeInterestStage);
        chargeInterestStage.setNext(registerStage);
        return balanceCheck;
    }

    public static void main(String[] args) {
        Stage chain = createChain();
        Client sender = new Client();
        sender.setBalance(3000);
        Client acceptor = new Client();
        acceptor.setBalance(2000);

        Payment payment = new Payment(acceptor, sender, 1500);

        chain.handle(payment);

    }
}
