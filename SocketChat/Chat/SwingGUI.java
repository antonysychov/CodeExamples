package HomeWork.HomeWork15.Chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwingGUI extends JFrame implements GUI {
    private int windowWidth = (int) (160 / 1.25);
    private int windowHeight = (int) (90 / 1.25);

    private Client bindClient;
    private String chosenClient;
    private String chatName;
    private Map<String, String> messagesByUser = new HashMap<String, String>();

    private JTextArea history = new JTextArea() {{
        setBackground(new Color(241, 255, 248));
        setFont(Font.getFont(Font.DIALOG));
        setEditable(false);
    }};
    private JTextField editor = new JTextField() {{
        setBackground(new Color(241, 255, 248));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        bindClient.sendLine(editor.getText(), chosenClient);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    editor.setText("");
                }
            }
        });
    }};

    private DefaultListModel<String> usersModel = new DefaultListModel<>();
    private JList<String> connectedClients = new JList<String>(usersModel) {{
        setBackground(new Color(253, 255, 242));
        addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (chosenClient != null) {
                        messagesByUser.replace(chosenClient, history.getText());
                    }
                    System.out.println(chosenClient = connectedClients.getSelectedValue());
                    if (!messagesByUser.containsKey(chosenClient)) {
                        messagesByUser.put(chosenClient, "");
                    }
                    history.setText(messagesByUser.get(chosenClient));
                }
            }
        });
    }};

    final private JScrollPane JPanelLeftUp = new JScrollPane(history) {{
        this.setPreferredSize(new Dimension(windowWidth * 3, (int) (windowHeight * 4.5)));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }};
    final private JScrollPane JPanelLeftDown = new JScrollPane(editor) {{
        this.setPreferredSize(new Dimension(windowWidth * 3, windowHeight));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }};
    final private JPanel JPanelLeft = new JPanel(new BorderLayout()) {{
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(windowWidth * 4, windowHeight * 6));
        this.add(JPanelLeftUp, BorderLayout.NORTH);
        this.add(JPanelLeftDown, BorderLayout.SOUTH);
    }};
    final private JPanel JPanelRightUP = new JPanel() {{
        JLabel users = new JLabel("Users Online");
        this.add(users);
    }};
    final private JPanel JPanelRightDown = new JPanel() {{
        this.setPreferredSize(new Dimension(windowWidth * 2, (int) (windowHeight * 5.3)));
        connectedClients.setPreferredSize(new Dimension(windowWidth * 2, (int) (windowHeight * 5.3)));
        this.add(connectedClients, BorderLayout.SOUTH);
    }};
    final private JPanel JPanelRight = new JPanel(new BorderLayout()) {{
        this.setLayout(new BorderLayout());
        this.add(JPanelRightUP, BorderLayout.CENTER);
        this.add(JPanelRightDown, BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(windowWidth * 2, windowHeight * 6));
    }};

    {
        this.setLayout(new BorderLayout());
        this.add(JPanelLeft, BorderLayout.CENTER);
        this.add(JPanelRight, BorderLayout.EAST);

        setSize(windowWidth * 6, windowHeight * 6);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void setName(String name) {
        chatName = name;
        this.setTitle(name);
    }

    @Override
    public void showForm() {
        setVisible(true);
    }

    @Override
    public void updateClients(List<String> clients) {
        for (String client : clients) {
            if (!usersModel.contains(client)) {
                usersModel.addElement(client);
            }
        }

        int size = usersModel.size();
        for (int i = 0; i < size; i++) {
            if (!clients.contains(usersModel.elementAt(i))) {
                usersModel.remove(i);
                i--;
                size--;
            }
        }
    }

    @Override
    public void updateHistory(String newMessage) {
        String message = newMessage.split("\\|")[0];
        String from = newMessage.split("\\|")[1];
        String to = newMessage.split("\\|")[2];
        String appendMessage = from.equals(chatName) ? (message + "\n") : (String.format("%1$50s", message + "\n"));

        if (from.equals(chatName)) {
            if (!messagesByUser.containsKey(to)) messagesByUser.put(to, "");
            if (chosenClient != null && chosenClient.equals(to)) {
                history.append(appendMessage);
            } else {
                messagesByUser.replace(to, messagesByUser.get(to) + appendMessage);
            }
        } else {
            if (!messagesByUser.containsKey(from)) messagesByUser.put(from, "");
            if (chosenClient != null && chosenClient.equals(from)) {
                history.append(appendMessage);
            } else {
                messagesByUser.replace(from, messagesByUser.get(from) + appendMessage);
            }
        }

    }

    @Override
    public void bindClient(Client c) {
        this.bindClient = c;
    }
}
