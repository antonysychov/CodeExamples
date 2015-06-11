package HomeWork.HomeWork5.SeaBattle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame {

    final static String crossImagePath = "Images/cross.png";
    public static StretchIcon crossStretch;
    public static volatile Point lastClickedPointOnLeftField;
    public static volatile Point lastClickedPointOnRightField;
    public static volatile boolean listenersEnabled;
    private static volatile Cell[][] leftArray = new Cell[10][10];
    private static volatile Cell[][] rightArray = new Cell[10][10];
    private static Color defaultColor = Color.white;
    final private JPanel JPanelLeft = new JPanel(new BorderLayout());
    final private JPanel JPanelRight = new JPanel(new BorderLayout());
    private Player user = new User();
    private Player computer = new Computer();
    private JLabel leftLabel = new JLabel("User", SwingConstants.CENTER);
    private JLabel rightLabel = new JLabel("Computer", SwingConstants.CENTER);

    private boolean run = false;

    {
        JLabel sideOffset = new JLabel();
        JLabel sideOffset1 = new JLabel();
        leftLabel.setForeground(Color.BLACK);
        JLabel baseOffset1 = new JLabel();
        JPanel field = new JPanel(new GridLayout(10, 10, -1, -1));
        sideOffset.setPreferredSize(new Dimension(60, 60));
        sideOffset1.setPreferredSize(new Dimension(60, 60));
        leftLabel.setPreferredSize(new Dimension(60, 60));
        baseOffset1.setPreferredSize(new Dimension(60, 60));
        JPanelLeft.add(sideOffset1, BorderLayout.WEST);
        JPanelLeft.add(sideOffset, BorderLayout.EAST);
        JPanelLeft.add(baseOffset1, BorderLayout.SOUTH);
        JPanelLeft.add(leftLabel, BorderLayout.NORTH);
        JPanelLeft.setPreferredSize(new Dimension(160 * 3, 90 * 6));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                leftArray[i][j] = new Cell(i, j);
                leftArray[i][j].setBackground(defaultColor);
                field.add(leftArray[i][j]);
            }
        }
        JPanelLeft.add(field, BorderLayout.CENTER);
    }

    {
        JLabel sideOffset = new JLabel();
        JLabel sideOffset1 = new JLabel();
        rightLabel.setForeground(Color.BLACK);
        JLabel baseOffset1 = new JLabel();
        JPanel field = new JPanel(new GridLayout(10, 10, -1, -1));
        sideOffset.setPreferredSize(new Dimension(60, 60));
        sideOffset1.setPreferredSize(new Dimension(60, 60));
        rightLabel.setPreferredSize(new Dimension(60, 60));
        baseOffset1.setPreferredSize(new Dimension(60, 60));
        JPanelRight.add(sideOffset1, BorderLayout.WEST);
        JPanelRight.add(sideOffset, BorderLayout.EAST);
        JPanelRight.add(baseOffset1, BorderLayout.SOUTH);
        JPanelRight.add(rightLabel, BorderLayout.NORTH);
        JPanelRight.setPreferredSize(new Dimension(160 * 3, 90 * 6));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                rightArray[i][j] = new Cell(i, j);
                rightArray[i][j].setBackground(defaultColor);
                field.add(rightArray[i][j]);
            }
        }
        JPanelRight.add(field, BorderLayout.CENTER);
    }

    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                final Cell left = leftArray[i][j];
                final Cell right = rightArray[i][j];
                left.setOpaque(true);
                left.setBorder(BorderFactory.createLineBorder(Color.black));
                left.setBackground(defaultColor);
                left.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!listenersEnabled) return;
                        lastClickedPointOnLeftField = new Point(left.x, left.y);
                    }
                });
                right.setOpaque(true);
                right.setBorder(BorderFactory.createLineBorder(Color.black));
                right.setBackground(defaultColor);
                right.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!listenersEnabled) return;
                        lastClickedPointOnRightField = new Point(right.x, right.y);
                    }
                });
            }
        }
        ;

    }

    public Game() throws HeadlessException {
        super("BattleShip 1.0");
        setContentPane(new JLabel(new StretchIcon(getClass().getResource(crossImagePath), true)));
        crossStretch = new StretchIcon(getClass().getResource(crossImagePath), true);

        //Creation of menu panel
        JMenuBar menu = new JMenuBar();
        JMenuItem newGameItem = new JMenuItem("New game");
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        JMenuItem exitItem = new JMenuItem("Quit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });
        menu.add(newGameItem);
        menu.add(exitItem);
        menu.setLayout(new FlowLayout(FlowLayout.LEFT));
        setJMenuBar(menu);

        //adding window content
        setLayout(new GridLayout(1, 2));
        int standardWindowWidth = 160 * 6;
        int standardWindowHeight = 90 * 6;
        setSize(standardWindowWidth, standardWindowHeight);
        add(JPanelLeft);
        add(JPanelRight);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game().startNewGame();
            }
        });
    }

    public static Point returnClickedPointOnLeftField() {
        enableListeners();
        while (lastClickedPointOnLeftField == null && listenersEnabled) {
        }
        if (lastClickedPointOnLeftField == null) {
            disableListeners();
            return null;
        }
        Point temp = new Point(lastClickedPointOnLeftField.x, lastClickedPointOnLeftField.y);
        lastClickedPointOnLeftField = null;
        disableListeners();
        return temp;
    }

    public static Point returnClickedPointOnRightField() {
        enableListeners();
        while (lastClickedPointOnRightField == null && listenersEnabled) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (lastClickedPointOnRightField == null) {
            disableListeners();
            return null;
        }
        Point temp = new Point(lastClickedPointOnRightField.x, lastClickedPointOnRightField.y);
        lastClickedPointOnRightField = null;
        disableListeners();
        return temp;
    }

    public static void enableListeners() {
        listenersEnabled = true;
    }

    public static void disableListeners() {
        listenersEnabled = false;
    }

    public static void outlineCellOnLeftField(int x, int y) {
        leftArray[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    public static void deOutlineCellOnLeftField(int x, int y) {
        leftArray[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    public static void colorCellOnLeftField(int x, int y, Color color) {
        leftArray[x][y].setBackground(color);
    }

    public static void deColorCellOnLeftField(int x, int y) {
        leftArray[x][y].setBackground(Game.defaultColor);
    }

    public static void colorCellOnRightField(int x, int y, Color color) {
        rightArray[x][y].setBackground(color);
    }

    public static void deColorCellOnRightField(int x, int y) {
        rightArray[x][y].setBackground(Game.defaultColor);
    }

    public static void crossCellOnLeftField(int x, int y) {
        leftArray[x][y].setIcon(Game.crossStretch);
    }

    public static void deCrossCellOnLeftField(int x, int y) {
        leftArray[x][y].setIcon(null);
    }

    public static void crossCellOnRightField(int x, int y) {
        rightArray[x][y].setIcon(Game.crossStretch);
    }

    public static void deCrossCellOnRightField(int x, int y) {
        rightArray[x][y].setIcon(null);
    }

    private void quitGame() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startNewGame() {
        disableLogicThread();
        //clean up fields
        user.clearField();
        computer.clearField();
        clearFields();
        //ships placement
        user.placeShips();
        computer.placeShips();

        playLogic();
    }

    private void playLogic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!user.field.isFilled()) {
                    System.out.println("placement");
                    delay(500);
                }
                run = true;
                userStep:
                while (run) {
                    System.out.println("user step logic");
                    leftLabel.setForeground(Color.RED);
                    rightLabel.setForeground(Color.BLACK);
                    Point c = user.makeShoot();
                    if (c == null || !run) break userStep;
                    Game.crossCellOnRightField(c.x, c.y);
                    if (computer.acceptShoot(c)) {
                        System.out.println("User got");
                        Game.colorCellOnRightField(c.x, c.y, new Color(76, 179, 210));

                        if (computer.outOfShips()) {
                            JOptionPane.showMessageDialog(null, "Вы выиграли");
                            break userStep;
                        }
                        continue userStep;
                    } else {
                        computerStep:
                        while (run) {
                            System.out.println("computer step logic");
                            rightLabel.setForeground(Color.RED);
                            leftLabel.setForeground(Color.BLACK);
                            delay(2000);
                            Point comp = computer.makeShoot();
                            if (comp == null || !run) break computerStep;
                            Game.crossCellOnLeftField(comp.x, comp.y);
                            if (user.acceptShoot(comp)) {
                                System.out.println("Computer got");
                                if (user.outOfShips()) {
                                    JOptionPane.showMessageDialog(null, "Вы проиграли");
                                    break userStep;
                                }
                                continue computerStep;
                            } else continue userStep;
                        }
                    }
                }
            }
        }).start();
    }

    private void clearFields() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                deColorCellOnLeftField(i, j);
                deColorCellOnRightField(i, j);
                deCrossCellOnLeftField(i, j);
                deCrossCellOnRightField(i, j);
                deOutlineCellOnLeftField(i, j);
            }
        }

        leftLabel.setForeground(Color.BLACK);
        rightLabel.setForeground(Color.BLACK);
    }

    private void disableLogicThread() {
        disableListeners();
        run = false;
        System.out.println("logic disabled");
    }
}

