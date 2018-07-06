package org.ah.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

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
    private JFrame player;
    private int dx;
    private int dy;

    private int gameWidth = 16;

    public Snake() {
        bgColor = new Color(1.0f, 1.0f, 1.0f, 0.5f);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        tileSize = (int) (dim.getHeight() / gameWidth);
        System.out.println(tileSize);
        player = createObject(2, 2, Color.GREEN);
        dx = 1;
        dy = 0;
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

        f.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                f.setShape(new RoundRectangle2D.Double(0, 0, f.getWidth(), f.getHeight(), 2, 2));

            }
        });
        f.setLocation(x * tileSize, x * tileSize);
        f.getContentPane().setBackground(c);
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

    public Point getPosition(JFrame f) {
        return new Point(Math.round(f.getLocationOnScreen().x / tileSize), Math.round(f.getLocationOnScreen().y / tileSize));
    }

    public void updateGame() {
        moveObject(player, dx, dy);
        System.out.println(dx + " / " + dy);

    }

    public void start() throws InterruptedException {



        try {
            GlobalScreen.registerNativeHook();

        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(this);

        player.pack();
        player.setVisible(true);

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
            dx = -1;
            dy = 0;
        }
        if (NativeKeyEvent.getKeyText(event.getKeyCode()).equals("D")) {
            dx = 1;
            dy = 0;
        }
        if (NativeKeyEvent.getKeyText(event.getKeyCode()).equals("S")) {
            dx = 0;
            dy = 1;
        }
        if (NativeKeyEvent.getKeyText(event.getKeyCode()).equals("W")) {
            dx = 0;
            dy = -1;
        }
    }

    public void nativeKeyReleased(NativeKeyEvent event) {
    }

    public void nativeKeyTyped(NativeKeyEvent event) {
    }
}
