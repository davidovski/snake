package org.ah.snake;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;

public class Player  {
    private Snake game;
    private JFrame headFrame;

    private Map<JFrame, Point> body;

    private Point lastPos;

    private int dx = 0;
    private int dy = 0;
    public Player(Snake game) {
        this.game = game;
        headFrame = game.createObject(4, 2, Color.GREEN);
        lastPos = new Point(4, 2);
        dx = 1;
        dy = 0;

        body = new LinkedHashMap<JFrame, Point>();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();
        addBody();

    }

    public void addBody() {
        List<Entry<JFrame, Point>> entryList =
                new ArrayList<Map.Entry<JFrame, Point>>(body.entrySet());
        Entry<JFrame, Point> lastEntry = null;
        try {
        lastEntry = entryList.get(entryList.size()-1);
        } catch (Exception e) {

        }
        Point k;
        if (lastEntry != null) {
            k = lastEntry.getValue();
        } else {
            k = lastPos;
        }
        body.put(game.createObject(k.x, k.y, Color.GREEN.darker()), new Point(k.x, k.y));

    }

    public void start() {
    }

    public void update() {
        lastPos = game.getPosition(headFrame);
        game.moveObject(headFrame, dx, dy);
        Point toset = lastPos;
        for (Entry<JFrame, Point> entry : body.entrySet()) {
            entry.setValue(game.getPosition(entry.getKey()));
            game.setObjectPosition(entry.getKey(), toset.x, toset.y);
            toset = entry.getValue();
        }



    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {

        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
