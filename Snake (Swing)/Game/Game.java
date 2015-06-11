package Game;

import Game.GameObjects.Apple;
import Game.GameObjects.Snake;
import Utils.ConfigAndStart;
import Utils.StretchIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Game extends JFrame {
    final int rows = 30;
    final int columns = 56;
    final String backImagePath = "/Images/grass.jpg";

    private Snake snake;
    private Apple apple;
    private boolean run = false;
    private int moveTime;
    private ConfigAndStart dialog;
    private int score = 0;
    private JLabel[][] jlm = new JLabel[rows][columns];

    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Game() throws HeadlessException {
        super("Snake 1.0");
        setLayout(new BorderLayout());
        setContentPane(new JLabel(new StretchIcon(getClass().getResource(backImagePath), true)));
        setLayout(new GridLayout(rows, columns));
        setSize(160 * 5, 90 * 5);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (snake.getDirection() != 3) snake.setDirection(1);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (snake.getDirection() != 4) snake.setDirection(2);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (snake.getDirection() != 1) snake.setDirection(3);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (snake.getDirection() != 2) snake.setDirection(4);
                }
            }
        });

        new Thread() {
            public void run() {
                while (true) {
                    while (run) {
                        try {
                            Thread.sleep(moveTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        snake.makeStep(Game.this);
                    }
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game().startGame();
            }
        });
    }

    public Point getRandomPoint() {
        int rowN = 5 + (int) (Math.random() * ((rows - 10)));
        int columnN = 5 + (int) (Math.random() * ((columns - 10)));
        return new Point(rowN, columnN);
    }

    void startGame() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                jlm[i][j] = new JLabel();
                add(jlm[i][j]);
            }
        }
        snake = new Snake(this);
        snake.updateOnScreen(this);
        do {
            apple = new Apple(getRandomPoint());
        } while (snake.getBodyCoordinates().contains(apple.getApplePosition()));
        apple.updateOnScreen(this);
        dialog = new ConfigAndStart();
        if (!dialog.repeat) {
            System.exit(0);
            return;
        }

        if (dialog.level.equals("Легкий")) moveTime = 300;
        else if (dialog.level.equals("Средний")) moveTime = 100;
        else if (dialog.level.equals("Сложный")) moveTime = 50;
        run = true;

    }

    public void restartGame() {
        score = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                jlm[i][j].setIcon(null);
            }
        }
        snake = new Snake(this);
        snake.updateOnScreen(this);
        do {
            apple = new Apple(getRandomPoint());
        } while (snake.getBodyCoordinates().contains(apple.getApplePosition()));
        apple.updateOnScreen(this);
        dialog = new ConfigAndStart();
        if (!dialog.repeat) {
            System.exit(0);
            return;
        }
        if (dialog.level.equals("Легкий")) moveTime = 300;
        else if (dialog.level.equals("Средний")) moveTime = 100;
        else if (dialog.level.equals("Сложный")) moveTime = 50;
        run = true;
    }

    public void stopGame() {
        run = false;
    }

    public JLabel[][] getJlm() {
        return jlm;
    }

    public void setJlm(JLabel[][] jlm) {
        this.jlm = jlm;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Created by Антон on 11.01.2015.
     */
    public static class Point {
        private int row;
        private int column;

        public Point(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (row != point.row) return false;
            return column == point.column;

        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + column;
            return result;
        }
    }
}

