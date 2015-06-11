package HomeWork.HomeWork15.Chat;

import java.util.List;

public interface GUI {
    void showForm();
    void updateClients(List<String> clients);
    void updateHistory (String s);
    void bindClient(Client c);
    void setName(String name);
}
