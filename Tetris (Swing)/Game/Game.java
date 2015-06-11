package Game;

import Game.Shapes.Shape;
import Game.Shapes.*;
import Utils.StretchIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JFrame {
    private final int rowsOfGlass = 18;                 //количество строк стакана
    private final int columnsOfGlass = 12;              //количество столбцов стакана
    private final int rowsNextShape = 5;                //количество строк панели со следующей фигурой
    private final int columnsNextShape = 5;             //количество столбцов панели со следующей фигурой

    private final int standardWindowWidth = 160 * 5;    //ширина стандартного размера окна
    private final int standardWindowHeight = 90 * 5;    //высота стандартного размера окна

    private boolean run = false;                        //запуск/остановка таймера тактирования опусков
    private int stepTime;                               //текущий период опусков
    private int normalStepTime = 500;                   //период опусков в нормальном режиме
    private int acceleratedStepTime = 100;              //период опусков при ускорении
    private int score;                                  //текущий счет
    private int currentShapeHShift = 0;                 //текущий горизонтальный сдвиг фигуры
    private int currentShapeVShift = 0;                 //текущий вертикальный сдвиг фигуры
    private Shape currentShape;                         //текущая фигура
    private Shape nextShape;                            //следующая фигура

    private JPanel JPanelLeft = new JPanel(new GridLayout());                                               //левая контейнер с изображением
    private JPanel JPanelCenter = new JPanel(new GridLayout(rowsOfGlass, columnsOfGlass, -1, -1));          //центральный контейнер с игровым полем
    private JPanel JPanelRight = new JPanel(new BorderLayout());                                            //правый контейнер
    private JPanel JPanelRightDown = new JPanel(new BorderLayout());                                        //контейнер с кнопкой изменения режима отображения, текущим счетом
    private JPanel JPanelRightUp = new JPanel(new BorderLayout());                                          //контейнер с панелью следующей фигурой
    private JPanel JPanelRightUpGrid = new JPanel(new GridLayout(rowsNextShape, columnsNextShape, -1, -1)); //панель со следующей фигурой

    private JPanel up = new JPanel();                   //вспомогательные панели
    private JPanel down = new JPanel();                 //вспомогательные панели
    private JPanel left = new JPanel();                 //вспомогательные панели
    private JPanel right = new JPanel();                //вспомогательные панели

    private JLabel scoreLabel = new JLabel(String.valueOf("SCORE : " + score), SwingConstants.CENTER);      //метка с текущим счетом
    private JButton changeStateButton = new JButton("Fullscreen");                                          //кнопка изменения режима отображения "Fullscreen/In window"

    private final double leftPanelSizeK = 0.3;          //коэффициент ширины левого контейнера
    private final double centerPanelSizeK = 0.4;        //коэффициент ширины центрального контейнера
    private final double rightPanelSizeK = 0.3;         //коэффициент ширины правого контейнера

    private Color defaultColor = JPanelCenter.getBackground();                                              //стандарнтый цвет стакана

    private JLabelK[][] jlm = new JLabelK[rowsOfGlass][columnsOfGlass];                                     //матрица меток в стакане
    private JLabel[][] jlmNext = new JLabel[rowsNextShape][columnsNextShape];                               //матрица меток на панели следующей фигуры

    public Game() throws HeadlessException {
        super("Tetris 1.0");

        //настройка базового контейнера JFrame окна игры
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(standardWindowWidth, standardWindowHeight);
        setResizable(false);

        //настройка левого контейнера JPanel окна игры
        JPanelLeft.setPreferredSize(new Dimension((int) (this.getWidth() * leftPanelSizeK), this.getHeight()));
        JPanelLeft.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanelLeft.add(new JLabel(new StretchIcon(getClass().getResource("/Images/Figures.png"), true)));
        JPanelLeft.setBackground(new Color(195, 236, 236));

        //настройка центрального контейнера JPanel окна игры
        JPanelCenter.setPreferredSize(new Dimension((int) (this.getWidth() * centerPanelSizeK), this.getHeight()));
        JPanelCenter.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        //заполнение метками матрицы панели со стаканом
        for (int i = 0; i < rowsOfGlass; i++) {
            for (int j = 0; j < columnsOfGlass; j++) {
                JLabelK temp = new JLabelK();
                temp.setOpaque(true);
                temp.setBorder(BorderFactory.createLineBorder(Color.black));
                jlm[i][j] = temp;
                JPanelCenter.add(jlm[i][j]);
            }
        }

        //настройка правого контейнера JPanel окна игры
        JPanelRight.setPreferredSize(new Dimension((int) (this.getWidth() * rightPanelSizeK), this.getHeight()));
        JPanelRight.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanelRight.setBackground(new Color(195, 236, 236));
        JPanelRightUp.setPreferredSize(new Dimension(JPanelRight.getWidth(), (int) (standardWindowHeight * 0.33)));
        JPanelRightUp.setBackground(new Color(195, 236, 236));
        JPanelRightDown.setBackground(new Color(195, 236, 236));
        JPanelRightUpGrid.setBorder(BorderFactory.createLineBorder(Color.black));

        //заполнение метками матрицы панели со следующей фигурой
        for (int i = 0; i < rowsNextShape; i++) {
            for (int j = 0; j < columnsNextShape; j++) {
                JLabel temp = new JLabel();
                temp.setOpaque(true);
                temp.setBorder(BorderFactory.createLineBorder(Color.black));
                jlmNext[i][j] = temp;
                JPanelRightUpGrid.add(jlmNext[i][j]);
            }
        }

        up.setBackground(new Color(195, 236, 236));
        up.setPreferredSize(new Dimension(JPanelRightUp.getWidth(), (int) (standardWindowHeight * 0.15 * 0.35)));
        up.add(new JLabel("Next shape"));
        down.setPreferredSize(new Dimension(JPanelRightUp.getWidth(), (int) (standardWindowHeight * 0.15 * 0.35)));
        down.setBackground(new Color(195, 236, 236));
        left.setPreferredSize(new Dimension((int) (standardWindowWidth * 0.27 * 0.3), 0));
        left.setBackground(new Color(195, 236, 236));
        right.setPreferredSize(new Dimension((int) (standardWindowWidth * 0.27 * 0.3), 0));
        right.setBackground(new Color(195, 236, 236));

        JPanelRightUp.add(JPanelRightUpGrid, BorderLayout.CENTER);
        JPanelRightUp.add(up, BorderLayout.NORTH);
        JPanelRightUp.add(left, BorderLayout.WEST);
        JPanelRightUp.add(down, BorderLayout.SOUTH);
        JPanelRightUp.add(right, BorderLayout.EAST);

        JPanelRight.add(JPanelRightUp, BorderLayout.NORTH);
        JPanelRight.add(JPanelRightDown, BorderLayout.CENTER);
        JPanelRightDown.add(scoreLabel, BorderLayout.CENTER);
        JPanelRightDown.add(changeStateButton, BorderLayout.NORTH);

        //добавляем все панели-контейнеры JPanel в главное окно JFrame
        add(JPanelLeft, BorderLayout.LINE_START);
        add(JPanelCenter, BorderLayout.CENTER);
        add(JPanelRight, BorderLayout.LINE_END);

        setFocusable(true);

        //добавляем обработчик нажатий кнопки изменения режима "Fullscreen/In window"
        changeStateButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    if (getExtendedState() != MAXIMIZED_BOTH) {
                                                        changeStateButton.setText("In window");
                                                        setResizable(true);
                                                        setExtendedState(MAXIMIZED_BOTH);
                                                        keepPanelsRatio();
                                                        up.setPreferredSize(new Dimension(0, (int) ((getHeight() * 0.15) * 0.35)));
                                                        down.setPreferredSize(new Dimension(0, (int) ((getHeight() * 0.15) * 0.35)));
                                                        left.setPreferredSize(new Dimension((int) (getWidth() * 0.27 * 0.3), 0));
                                                        right.setPreferredSize(new Dimension((int) (getWidth() * 0.27 * 0.3), 0));
                                                        setResizable(false);
                                                        changeStateButton.setFocusable(false);
                                                        setFocusable(true);
                                                    } else {
                                                        changeStateButton.setText("Fullscreen");
                                                        setResizable(true);
                                                        setExtendedState(NORMAL);
                                                        keepPanelsRatio();
                                                        up.setPreferredSize(new Dimension(JPanelRightUp.getWidth(),
                                                                (int) (standardWindowHeight * 0.15 * 0.35)));
                                                        down.setPreferredSize(new Dimension(JPanelRightUp.getWidth(),
                                                                (int) (standardWindowHeight * 0.15 * 0.35)));
                                                        left.setPreferredSize(new Dimension((int) (standardWindowWidth * 0.27 * 0.3), 0));
                                                        right.setPreferredSize(new Dimension((int) (standardWindowWidth * 0.27 * 0.3), 0));
                                                        setResizable(false);
                                                        changeStateButton.setFocusable(false);
                                                        setFocusable(true);
                                                    }
                                                }
                                            }
        );

        //центровка и отображение окна игры
        setLocationRelativeTo(null);
        setVisible(true);

        //добавление обработчиков для соблюдения пропорций размеров панелей при изменении размеров
        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                keepPanelsRatio();
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                keepPanelsRatio();

            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                keepPanelsRatio();
            }
        });

        //добавление обработчиков кнопок клавиатуры
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    makeHorStep(KeyEvent.VK_RIGHT);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    stepTime = acceleratedStepTime;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    makeHorStep(KeyEvent.VK_LEFT);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    rotateCheckUpdate();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    stepTime = normalStepTime;
                }
            }
        });
        //запускаем тактирующий таймер в новом потоке
        new Thread() {
            public void run() {
                while (true) {
                    while (run) {
                        try {
                            Thread.sleep(stepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        makeVerStep();
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

    public void startGame() {                                //старт игры
        stepTime = normalStepTime;
        nextShape = getRandomShape();
        currentShape = getRandomShape();
        nextShapeUpdateOnScreen();
        currentShapeHShift = (int) (Math.random() * (columnsOfGlass - currentShape.getWidth()));
        run = true;
    }

    /*getters/setters*/
    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public int getCurrentShapeVShift() {
        return currentShapeVShift;
    }

    public void setCurrentShapeVShift(int currentShapeVShift) {
        this.currentShapeVShift = currentShapeVShift;
    }

    public int getCurrentShapeHShift() {

        return currentShapeHShift;
    }

    public void setCurrentShapeHShift(int currentShapeHShift) {
        this.currentShapeHShift = currentShapeHShift;
    }

    public JLabelK[][] getJlm() {
        return jlm;
    }

    public int getRowsOfGlass() {
        return rowsOfGlass;
    }

    /*private methods*/
    private void endAndRestart() {                           //перезапуск игры
        run = false;
        Object[] options = {"Да", "Нет"};
        int n = JOptionPane.showOptionDialog(null, "Начать заново ?", "Ваш счет: " + score, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (n == 0) {
            for (int i = 0; i < jlm.length; i++) {
                for (int j = 0; j < jlm[i].length; j++) {
                    jlm[i][j].setBackground(JPanelCenter.getBackground());
                    jlm[i][j].isFilled = false;
                }
            }
            for (int i = 0; i < jlmNext.length; i++) {
                for (int j = 0; j < jlmNext[i].length; j++) {
                    jlmNext[i][j].setBackground(defaultColor);
                }
            }
            score = 0;
            startGame();
        } else System.exit(0);
    }

    private void makeVerStep() {                            //опуск фигуры вниз
        // проверка на достижение дна
        for (Shape.Point point : currentShape.getCurrentShapeCoordinates()) {
            if (point.getRow() + currentShapeVShift + 1 == (rowsOfGlass)) {
                currentShapeVShift = 0;
                currentShape = nextShape;
                nextShapeRemoveFromScreen();
                nextShape = getRandomShape();
                nextShapeUpdateOnScreen();
                currentShapeHShift = (int) (Math.random() * (columnsOfGlass - currentShape.getWidth()));
                checkForFullLinesNRemove();
                return;
            }
        }

        // проверка на упирание в кучу
        for (Shape.Point point : currentShape.getCurrentShapeCoordinates()) {
            for (int i = jlm.length - 1; i >= 0; i--) {
                for (int j = 0; j < jlm[i].length; j++) {
                    if (jlm[i][j].isFilled && ((point.getRow() + currentShapeVShift) == i - 1)
                            && (point.getColumn() + currentShapeHShift == j)
                            && !pointBelongsToShape(currentShape, new Shape.Point(i, j))
                            && (point.getRow() + currentShapeVShift) >= -1) {
                        for (Shape.Point p : currentShape.getCurrentShapeCoordinates()) {
                            if (p.getRow() + currentShapeVShift < 0) {
                                endAndRestart();
                                return;
                            }
                        }
                        currentShapeVShift = 0;
                        currentShape = nextShape;
                        nextShapeRemoveFromScreen();
                        nextShape = getRandomShape();
                        nextShapeUpdateOnScreen();
                        currentShapeHShift = (int) (Math.random() * (columnsOfGlass - currentShape.getWidth()));
                        checkForFullLinesNRemove();
                    }
                }
            }
        }
        currentShape.removeFromScreen(this);
        currentShapeVShift = currentShapeVShift + 1;
        currentShape.updateOnScreen(this);
    }

    private void makeHorStep(int keyCode) {                   //сдвиг фигуры в бок
        if (keyCode == KeyEvent.VK_RIGHT) {
            //проверка на упирание в  правую стенку
            for (Shape.Point point : currentShape.getCurrentShapeCoordinates()) {
                if (point.getColumn() + currentShapeHShift + 1 == (columnsOfGlass)) {
                    return;
                }
            }
            // проверка на упирание в кучу
            for (Shape.Point point : currentShape.getCurrentShapeCoordinates()) {
                for (int i = jlm.length - 1; i >= 0; i--) {
                    for (int j = 0; j < jlm[i].length; j++) {
                        if (jlm[i][j].isFilled && ((point.getColumn() + currentShapeHShift) == j - 1)
                                && (point.getRow() + currentShapeVShift == i)
                                && !pointBelongsToShape(currentShape, new Shape.Point(i, j))
                                && ((point.getColumn() + currentShapeHShift) >= 0
                                && (point.getColumn() + currentShapeHShift) <= columnsOfGlass)) {

                            return;
                        }
                    }
                }
            }
            currentShape.removeFromScreen(this);
            currentShapeHShift++;
            currentShape.updateOnScreen(this);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            //проверка на упирание в  правую стенку
            for (Shape.Point point : currentShape.getCurrentShapeCoordinates()) {
                if (point.getColumn() + currentShapeHShift - 1 == -1) {
                    return;
                }
            }
            // проверка на упирание в кучу
            for (Shape.Point point : currentShape.getCurrentShapeCoordinates()) {
                for (int i = jlm.length - 1; i >= 0; i--) {
                    for (int j = 0; j < jlm[i].length - 1; j++) {
                        if (jlm[i][j].isFilled && ((point.getColumn() + currentShapeHShift) == j + 1)
                                && (point.getRow() + currentShapeVShift == i)
                                && !pointBelongsToShape(currentShape, new Shape.Point(i, j))
                                && ((point.getColumn() + currentShapeHShift) >= 0
                                && (point.getColumn() + currentShapeHShift <= columnsOfGlass))) {

                            return;
                        }
                    }
                }
            }
            currentShape.removeFromScreen(this);
            currentShapeHShift--;
            currentShape.updateOnScreen(this);
        }
    }

    private void rotateCheckUpdate() {                       //проверка на возможность поворота, поворот текущей фигуры
        for (Shape.Point point : currentShape.getCurrentShapeCoordinates()) {
            if (point.getRow() + currentShapeVShift >= 0
                    && point.getRow() + currentShapeVShift <= rowsOfGlass - 1) {
                jlm[point.getRow() + currentShapeVShift][point.getColumn() + currentShapeHShift].isFilled = false;
            }
        }

        currentShape.rotateRight();
        for (Shape.Point point : currentShape.getCurrentShapeCoordinates()) {
            //проверка на упирание в кучу
            for (int i = 0; i < jlm.length; i++) {
                for (int j = 0; j < jlm[i].length; j++) {
                    if (jlm[i][j].isFilled && pointBelongsToShape(currentShape, new Shape.Point(i, j))) {
                        currentShape.rotateLeft();
                        return;
                    }
                }
            }
            //проверка на упирание в стены
            if (point.getRow() + currentShapeVShift < 0
                    || point.getRow() + currentShapeVShift >= rowsOfGlass
                    || point.getColumn() + currentShapeHShift < 0
                    || point.getColumn() + currentShapeHShift >= columnsOfGlass) {
                currentShape.rotateLeft();
                return;
            }
        }
        currentShape.rotateLeft();
        currentShape.removeFromScreen(this);
        currentShape.rotateRight();
        currentShape.updateOnScreen(this);
    }

    private Shape getRandomShape() {                        //получить случайную фигуру
        switch ((int) (Math.random() * 7)) {
            case 0:
                return (new ShapeI());
            case 1:
                return (new ShapeSquare());
            case 2:
                return (new ShapeZ());
            case 3:
                return (new ShapeT());
            case 4:
                return (new ShapeL());
            case 5:
                return (new ShapeS());
            case 6:
                return (new ShapeJ());
        }
        return null;
    }

    private boolean pointBelongsToShape(Shape shape, Shape.Point point) {  //проверка на принадлежность точки текущей фигуре с учетом сдвига
        for (int i = 0; i < shape.getCurrentShapeCoordinates().size(); i++) {
            if (point.getRow() == shape.getCurrentShapeCoordinates().get(i).getRow() + currentShapeVShift
                    && point.getColumn() == shape.getCurrentShapeCoordinates().get(i).getColumn() + currentShapeHShift)
                return true;
        }
        return false;
    }

    private void checkForFullLinesNRemove() {                       //проверка на наличие заполненных строк стакана и удаление
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> rowsToRemove = new ArrayList<Integer>();
                nextLine:
                for (int i = jlm.length - 1; i >= 0; i--) {
                    for (int j = 0; j < jlm[i].length; j++) {
                        if (!jlm[i][j].isFilled) continue nextLine;
                    }
                    rowsToRemove.add(i);
                }

                try {
                    for (int i = 0; i < rowsToRemove.size(); i++) {
                        for (int j = 0; j < jlm[i].length; j++) {
                            jlm[rowsToRemove.get(i)][j].setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.BLACK, 2),
                                    BorderFactory.createLineBorder(Color.GRAY, 1)));
                        }
                    }
                    Thread.sleep(300);
                    for (int i = 0; i < rowsToRemove.size(); i++) {
                        for (int j = 0; j < jlm[i].length; j++) {
                            jlm[rowsToRemove.get(i)][j].setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.GRAY, 2),
                                    BorderFactory.createLineBorder(Color.BLACK, 1)));
                        }
                    }
                    Thread.sleep(300);
                    for (int i = 0; i < rowsToRemove.size(); i++) {
                        for (int j = 0; j < jlm[i].length; j++) {
                            jlm[rowsToRemove.get(i)][j].setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.BLACK, 2),
                                    BorderFactory.createLineBorder(Color.GRAY, 1)));
                        }
                    }
                    Thread.sleep(300);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int k = rowsToRemove.size() - 1; k >= 0; k--) {
                    for (int i = rowsToRemove.get(k); i > 0; i--) {
                        for (int j = 0; j < jlm[i].length; j++) {
                            if (!pointBelongsToShape(currentShape, new Shape.Point(i - 1, j))) {
                                jlm[i][j].setBackground(jlm[i - 1][j].getBackground());
                                jlm[i][j].setBorder(jlm[i - 1][j].getBorder());
                                jlm[i][j].isFilled = jlm[i - 1][j].isFilled;
                            }
                        }
                    }
                    for (int j = 0; j < jlm[0].length - 1; j++) {
                        if (!pointBelongsToShape(currentShape, new Shape.Point(0, j)))
                            jlm[0][j].setBackground(defaultColor);
                    }
                }
                score += rowsToRemove.size() * 10;
                scoreLabel.setText("SCORE : " + score);
            }
        }).start();
    }

    private void nextShapeUpdateOnScreen() {                          //обновление следующей фигуры
        for (Shape.Point point : nextShape.getCurrentShapeCoordinates()) {
            jlmNext[point.getRow() + 4][point.getColumn() + 1].setBackground(nextShape.getColor());
        }
    }

    private void nextShapeRemoveFromScreen() {                        //удаление следующей фигуры
        for (Shape.Point point : nextShape.getCurrentShapeCoordinates()) {
            jlmNext[point.getRow() + 4][point.getColumn() + 1].setBackground(defaultColor);
        }
    }

    private void keepPanelsRatio() {                                  //установить размеры панелей в соответствии с коеффициентами
        JPanelLeft.setPreferredSize(new Dimension((int) (getWidth() * leftPanelSizeK), getHeight()));
        JPanelCenter.setPreferredSize(new Dimension((int) (getWidth() * centerPanelSizeK), getHeight()));
        JPanelRight.setPreferredSize(new Dimension((int) (getWidth() * rightPanelSizeK), getHeight()));
        JPanelRightUp.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * 0.33)));
    }

    /*classes*/
    public static class JLabelK extends JLabel {
        private boolean isFilled;

        public boolean isFilled() {
            return isFilled;
        }

        public void setIsFilled(boolean isFilled) {
            this.isFilled = isFilled;
        }
    }
}

