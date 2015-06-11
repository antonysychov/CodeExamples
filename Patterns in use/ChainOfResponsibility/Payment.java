package HomeWork.HomeWork8.Task2.ChainOfResponsibility;

public class Payment {
    private Client sender;
    private Client acceptor;
    private int type;

    public Payment(Client acceptor, Client sender, float value) {
        this.acceptor = acceptor;
        this.sender = sender;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Client getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(Client acceptor) {
        this.acceptor = acceptor;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    private float value;

}
