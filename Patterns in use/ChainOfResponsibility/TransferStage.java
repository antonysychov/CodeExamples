package HomeWork.HomeWork8.Task2.ChainOfResponsibility;

public class TransferStage extends Stage {

    @Override
    protected void handle(Payment p) {
        Client sender = p.getSender();
        float oldSenderBalance = sender.getBalance();
        float newSenderBalance = oldSenderBalance-p.getValue();
        Client acceptor = p.getAcceptor();
        float oldAcceptorBalance = acceptor.getBalance();
        float newAcceptorBalance = oldAcceptorBalance+p.getValue();
        sender.setBalance(newSenderBalance);
        acceptor.setBalance(newAcceptorBalance);
        System.out.println("Money transfer has been executed");
        sendNext(p);
    }
}
