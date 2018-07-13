package org.ah.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RunSnake {
    public static Snake game;

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Snake");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        BorderLayout lay = new BorderLayout();
        int s = (int) (dim.getHeight() / 4);
        frame.setSize(s, s);
        frame.setLocation((int) ((dim.getWidth() / 2) - (s / 2)), (int) ((dim.getHeight() / 2) - (s / 2)));
        JPanel p = new JPanel();
        p.setLayout(lay);

        p.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 10));

        JLabel l = new JLabel("Snake");
        l.setFont (new Font("Courier", Font.PLAIN, 40));

        JButton exit = new JButton("close");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (game != null) {
                    game.destroy();
                }
               System.exit(0);
            }
        });
        decorate(exit);


        JButton bEasy = new JButton("Easy");
        bEasy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (game != null) {
                    game.destroy();
                }
                game = new Snake(18, 0.5f);
                try {
                    game.start(1f);
                } catch (InterruptedException e1) {
                }
            }
        });
        decorate(bEasy);

        JButton bNormal= new JButton("Normal");
        bNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (game != null) {
                    game.destroy();
                }
                game = new Snake(14, 0.1f);
                try {
                    game.start(1.7f);
                } catch (InterruptedException e1) {
                }
            }
        });
        decorate(bNormal);

        JButton bHard= new JButton("Hard");
        bHard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (game != null) {
                    game.destroy();
                }
                game = new Snake(10, 0.5f);
                try {
                    game.start(2f);
                } catch (InterruptedException e1) {
                }
            }
        });
        decorate(bHard);
        JPanel selection = new JPanel();
        selection.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 4));

        selection.setLayout(new GridLayout(3, 1));

        selection.add(bEasy);
        selection.add(bNormal);
        selection.add(bHard);
        p.add(l, BorderLayout.LINE_START);
        p.add(selection, BorderLayout.LINE_END);
        p.add(exit, BorderLayout.PAGE_END);
        frame.add(p);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.pack();
        frame.setVisible(true);
    }

    public static void decorate(JComponent c) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        c.setForeground(Color.BLACK);

        c.setBackground(Color.GRAY);
        c.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
    }

}
