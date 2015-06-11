package HomeWork.HomeWork15.Chat;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Client {
    private GUI gui = new SwingGUI() {{
        bindClient(Client.this);
    }};
    private String ipAddress;
    private short port;
    private String chatNick;
    private List<String> messages = new LinkedList<>();


    Socket socket;
    BufferedReader in;
    BufferedWriter out;

    public Client(String ipAddress, short port, String chatNick) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.chatNick = chatNick;
        this.gui.setName(chatNick);
    }

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        Client c1 = new Client("127.0.0.1", (short) (7777), "Patrick");
        c1.startClient();
    }

    public void startClient() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
                                         @Override
                                         public void run() {
                                             gui.showForm();
                                             new Thread(new Runnable() {
                                                 @Override
                                                 public void run() {
                                                     try {
                                                         socket = connectServer();

                                                         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                                         out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                                                         while (!in.ready() || !in.readLine().equals("333")) {
                                                         }

                                                         out.write(chatNick);
                                                         out.newLine();
                                                         out.flush();

                                                         while (true) {
                                                             System.out.println("Client looping");
                                                             List<String> clients = getServerUsersList();
                                                             updateUsersList(clients);

                                                             try {
                                                                 Thread.sleep(500);
                                                             } catch (InterruptedException e) {
                                                                 e.printStackTrace();
                                                             }

                                                             if (in.ready()) {
                                                                 System.out.println("ready to get message");
                                                                 String s1 = (in.readLine());
                                                                 if (s1.equals("222")) {
                                                                     s1 = in.readLine();
                                                                     messages.add(s1);
                                                                     gui.updateHistory(s1);
                                                                     System.out.println("accepted message " + s1);
                                                                 }
                                                             }
                                                         }

                                                     } catch (IOException e) {
                                                         e.printStackTrace();
                                                     }
                                                 }
                                             }
                                             ).start();
                                         }
                                     }
        );
    }

    private Socket connectServer() throws IOException {
        return new Socket(ipAddress, port);
    }

    private List<String> getServerUsersList() throws IOException {
        List<String> users = null;

        out.write("111");
        out.newLine();
        out.flush();

        while (!in.ready() || !in.readLine().equals("111")) {
        }
        String list = in.readLine();
        users = Arrays.asList(list.split("\\|"));
        System.out.println("accepted list " + users);
        return users;
    }

    private void updateUsersList(List<String> clients) throws IOException {
        gui.updateClients(clients);
    }

    void sendLine(String message, String clientName) throws IOException {
        out.write("222");
        out.newLine();
        out.flush();
        out.write(message + "|" + clientName);
        out.newLine();
        out.flush();
        System.out.println("sent message to " + clientName);
    }

    @Override
    public String toString() {
        return chatNick;
    }

}
