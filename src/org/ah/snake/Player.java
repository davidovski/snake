package org.ah.snake;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JFrame;

public class Player {
    private Snake game;
    private JFrame headFrame;

    private Map<JFrame, Point> body;

    private Point lastPos;
    private Random random = new Random();

    private int dx = 0;
    private int dy = 0;
    private boolean died;
    private long a = 0;
    private long speed = 2;
    private int tail = 0;


    public Player(Snake game, float startSpeed) {
        speed = (long) startSpeed;
        this.game = game;
        headFrame = game.createObject(4, 2, Color.GREEN);
        lastPos = new Point(3, 2);
        dx = 1;
        dy = 0;

        body = new LinkedHashMap<JFrame, Point>();

        headFrame.setFocusable(true);
        headFrame.requestFocus();
        game.moveObject(headFrame, 0, 1);

    }

    public void addBody() {
        List<Entry<JFrame, Point>> entryList = new ArrayList<Map.Entry<JFrame, Point>>(body.entrySet());
        Entry<JFrame, Point> lastEntry = null;
        try {
            lastEntry = entryList.get(entryList.size() - 1);
        } catch (Exception e) {

        }
        Point k;
        if (lastEntry != null) {
            k = lastEntry.getValue();
        } else {
            k = lastPos;
        }
        Color darker = new Color(0, (int) ((Math.cos(tail / 4d) + 1d) * 127d), 0);
        tail++;
        body.put(game.createObject(k.x, k.y, darker), new Point(k.x, k.y));

    }

    public void start() {
    }

    public void update(float speedincr) {
        a++;

        if (died) {

        } else {

            if (a > 10 / speed) {

                a = 0;
                lastPos = game.getPosition(headFrame);
                Point p = game.moveObject(headFrame, dx, dy);
                Point toset = lastPos;
                for (Entry<JFrame, Point> entry : body.entrySet()) {

                    entry.setValue(game.getPosition(entry.getKey()));
                    game.setObjectPosition(entry.getKey(), toset.x, toset.y);
                    toset = entry.getValue();

                }
                Point ap = game.getPosition(game.getApple());
                Point pp = game.getPosition(headFrame);
                if (p.x == ap.x && p.y == ap.y) {
                    AudioController.play("snare.wav");
                } else {
                    AudioController.play("drum.wav");
                }
                if (pp.x == ap.x && pp.y == ap.y) {
                    addBody();
                    speed += speedincr;
                    a = 100;
                    game.setObjectPosition(game.getApple(), random.nextInt(game.getGameWidth()), random.nextInt(game.getGameHeight()));
                }

            }
            for (Entry<JFrame, Point> entry : body.entrySet()) {

                Point position = game.getPosition(entry.getKey());
                Point position2 = game.getPosition(headFrame);

                if (position.x == position2.x && position.y == position2.y) {
                    died = true;
                    game.destroy();
                }
            }
        }
    }

    public void destroy() {
        Iterator<Entry<JFrame, Point>> it = body.entrySet().iterator();
        while (it.hasNext()) {
            Entry<JFrame, Point> next = it.next();
            next.getKey().setVisible(false);
            next.getKey().dispose();
            it.remove();
        }

        headFrame.setVisible(false);
        headFrame.dispose();
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        if (this.dy != -dy) {

            this.dy = dy;
        }
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        if (this.dx != -dx) {
            this.dx = dx;
        }
    }

    public JFrame getHeadFrame() {
        return headFrame;
    }

    public void setHeadFrame(JFrame headFrame) {
        this.headFrame = headFrame;
    }
}
