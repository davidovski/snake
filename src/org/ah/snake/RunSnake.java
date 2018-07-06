package org.ah.snake;

public class RunSnake {

    public static void main(String[] args) {
        Snake s = new Snake();
        try {
            s.start();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
