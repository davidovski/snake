package org.ah.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Snake implements KeyListener {
    private Color bgColor;
    private int tileSize;
    private Player player;

    private int gameWidth = 8;
    private int gameHeight = 16;

    private long a = 0;

    private JFrame apple;

    private Random random = new Random();

    public Snake() {
        bgColor = new Color(1.0f, 1.0f, 1.0f, 0.5f);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        tileSize = (int) (dim.getHeight() / gameHeight);
        System.out.println(tileSize);
        player = new Player(this);

        gameWidth = (int) Math.round(dim.getWidth() / tileSize);

        apple = createObject(gameWidth / 2, gameHeight / 2, Color.RED);

    }

    public JFrame createObject(int x, int y, Color c) {
        Dimension d = new Dimension(tileSize, tileSize);

        final JFrame f = new JFrame("object");
        f.setSize(tileSize, tileSize);
        JPanel p = new JPanel();

        p.setBackground(c);
        JLabel l = new JLabel("");
        l.setPreferredSize(d);
        p.add(l);
        p.setMinimumSize(d);

        f.setUndecorated(true);
        f.setBackground(c);
        f.setSize(tileSize, tileSize);
        f.add(p);

        // f.addComponentListener(new ComponentAdapter() {
        // @Override
        // public void componentResized(ComponentEvent e) {
        // f.setShape(new RoundRectangle2D.Double(0, 0, f.getWidth(), f.getHeight(), 2, 2));
        //
        // }
        // });
        f.setLocation(x * tileSize, x * tileSize);
        f.getContentPane().setBackground(c);
        f.pack();
        f.setVisible(true);
        return f;
    }

    public void fixPosition(JFrame f) {
        f.setLocation(new Point(Math.round(f.getLocationOnScreen().x / tileSize) * tileSize, Math.round(f.getLocationOnScreen().y / tileSize) * tileSize));
    }

    public void fixPosition(Point p) {
        p.setLocation(new Point(Math.round(p.x / tileSize) * tileSize, Math.round(p.y / tileSize) * tileSize));
    }

    public void moveObject(JFrame f, int x, int y) {
        Point position = getPosition(f);
        setObjectPosition(f, position.x + x, position.y + y);
    }

    public void setObjectPosition(JFrame f, int x, int y) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if (x > gameWidth - 1) {
            x -= gameWidth;
        } else if (x < 0) {
            x += gameWidth;
        }

        if (y > gameHeight - 1) {
            y -= gameHeight;
        } else if (y < 0) {
            y += gameHeight;
        }
        int nx = (((x * tileSize)));
        int ny = (((y * tileSize)));
        nx = Math.round(nx / tileSize) * tileSize;
        ny = Math.round(ny / tileSize) * tileSize;
        f.setLocation(nx, ny);
    }

    public Point getPosition(JFrame f) {
        return new Point(Math.round(f.getLocationOnScreen().x / tileSize), Math.round(f.getLocationOnScreen().y / tileSize));
    }

    public void updateGame() {
        a++;
        player.update(a % 4 == 0);
        player.getHeadFrame().requestFocus();
        Point pp = getPosition(player.getHeadFrame());
        Point ap = getPosition(apple);
        if (pp.x == ap.x && pp.y == ap.y) {
            player.addBody();
            setObjectPosition(apple, random.nextInt(gameWidth), random.nextInt(gameHeight));
        }

    }

    public void start() throws InterruptedException {

        // try {
        // GlobalScreen.registerNativeHook();
        //
        // } catch (NativeHookException ex) {
        // System.err.println("There was a problem registering the native hook.");
        // System.err.println(ex.getMessage());
        //
        // }

        player.start();
        player.getHeadFrame().addKeyListener(this);
        Thread loop = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    updateGame();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        loop.start();

    }

    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void keyPressed(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
            player.setDx(-1);
            player.setDy(0);
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
            player.setDx(1);
            player.setDy(0);
        }

        if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
            player.setDx(0);
            player.setDy(1);
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
            player.setDx(0);
            player.setDy(-1);
        }

        if (KeyEvent.getKeyText(e.getKeyCode()).equals("Q")) {
            System.exit(0);
        }

    }

    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
