package org.ah.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Snake implements NativeKeyListener {
    private Color bgColor;
    private int tileSize;
    private Player player;

    private int gameWidth = 8;

    public Snake() {
        bgColor = new Color(1.0f, 1.0f, 1.0f, 0.5f);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        tileSize = (int) (dim.getHeight() / gameWidth);
        System.out.println(tileSize);
        player = new Player(this);

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

//        f.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                f.setShape(new RoundRectangle2D.Double(0, 0, f.getWidth(), f.getHeight(), 2, 2));
//
//            }
//        });
        f.setLocation(x * tileSize, x * tileSize);
        f.getContentPane().setBackground(c);
        f.pack();
        f.setVisible(true);
        return f;
    }

    public void moveObject(JFrame f, int x, int y) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int nx = (int) ((f.getLocationOnScreen().getX() + (x * tileSize)) % dim.getWidth());
        int ny = (int) ((f.getLocationOnScreen().getY() + (y * tileSize)) % dim.getHeight());
        nx = Math.round(nx / tileSize) * tileSize;
        ny = Math.round(ny / tileSize) * tileSize;
        f.setLocation(nx, ny);
    }


    public void fixPosition(JFrame f) {
        f.setLocation(new Point(Math.round(f.getLocationOnScreen().x / tileSize) * tileSize, Math.round(f.getLocationOnScreen().y / tileSize) * tileSize));
    }

    public void fixPosition(Point p) {
        p.setLocation(new Point(Math.round(p.x / tileSize) * tileSize, Math.round(p.y / tileSize) * tileSize));
    }
    public void setObjectPosition(JFrame f, int x, int y) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int nx = (int) (( (x * tileSize)) % dim.getWidth());
        int ny = (int) (( (y * tileSize)) % dim.getHeight());
        nx = Math.round(nx / tileSize) * tileSize;
        ny = Math.round(ny / tileSize) * tileSize;
        f.setLocation(nx, ny);
    }

    public Point getPosition(JFrame f) {
        return new Point(Math.round(f.getLocationOnScreen().x / tileSize), Math.round(f.getLocationOnScreen().y / tileSize));
    }

    public void updateGame() {
        player.update();
    }

    public void start() throws InterruptedException {



        try {
            GlobalScreen.registerNativeHook();

        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

        }
        GlobalScreen.addNativeKeyListener(this);


        player.start();
        Thread loop = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    updateGame();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        loop.start();

    }

    public void nativeKeyPressed(NativeKeyEvent event) {
        if (NativeKeyEvent.getKeyText(event.getKeyCode()).equals("A")) {
            player.setDx(-1);
            player.setDy(0);
        }
        if (NativeKeyEvent.getKeyText(event.getKeyCode()).equals("D")) {
            player.setDx(1);
            player.setDy(0);
        }
        if (NativeKeyEvent.getKeyText(event.getKeyCode()).equals("S")) {
            player.setDx(0);
            player.setDy(1);
        }
        if (NativeKeyEvent.getKeyText(event.getKeyCode()).equals("W")) {
            player.setDx(0);
            player.setDy(-1);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent event) {
    }

    public void nativeKeyTyped(NativeKeyEvent event) {
    }
}
