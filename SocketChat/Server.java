package HomeWork.HomeWork15.Chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private int port;
    private int limit;
    private Map<String, Socket> connectedClients = new HashMap<>();

    public Server(int port, int limit) throws IOException {
        this.port = port;
        this.limit = limit;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(7777, 10);
        server.start();
    }

    private String packUsersToString(String excludedUser) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Socket> entry : connectedClients.entrySet()) {
            if (entry.getKey().equals(excludedUser)) continue;
            sb.append(entry.getKey()).append("|");
        }
        return sb.toString();
    }

    public void start() throws IOException {
        ServerSocket ss = new ServerSocket(this.port, this.limit);
        while (true) {
            Socket socket = ss.accept();
            ClientHandler c = new ClientHandler(socket);
            c.start();
        }
    }

    class ClientHandler extends Thread {
        Socket socket;
        String name;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            BufferedReader in = null;
            BufferedWriter out = null;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write("333");
                out.newLine();
                out.flush();
                Thread.sleep(300);
                name = in.readLine();
                System.out.println("name : " + name);
                connectedClients.put(name, socket);

                String request;
                String respond;
                while (true) {
                    System.out.println("server looping");
                    request = in.readLine();
                    System.out.println(request);
                    switch (request) {
                        case "111":
                            System.out.println("request for list");
                            out.write("111");
                            out.newLine();
                            out.flush();
                            String usersList = packUsersToString(name);
                            out.write(usersList);
                            out.newLine();
                            out.flush();
                            System.out.println("writing list " + usersList);
                            break;
                        case "222":
                            String[] strings = in.readLine().split("\\|");
                            String user = strings[1];
                            String message = strings[0] + "|" + name + "|" + user;
                            Socket s = connectedClients.get(user);
                            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                            out.write("222");
                            out.newLine();
                            out.write(message);
                            out.newLine();
                            out.flush();
                            br.write("222");
                            br.newLine();
                            br.write(message);
                            br.newLine();
                            br.flush();
                            System.out.println("Server accepted and sent message from  " + name + " to " + user);
                    }
                }

            } catch (SocketException e) {
                connectedClients.remove(name);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
