/*
 * Copyright (c) 2011 Shudmanul Chowdhury
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.seedform.dice;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Oct 25, 2011 
 * Randomly rolls 2 virtual dice when the user presses a key and
 * displays them on a JPanel within a JFrame.
 * 
 * @author Shudmanul Chowdhury
 */
@SuppressWarnings("serial")
public class Dice extends JPanel implements KeyListener {

    public static void main(String[] args) {
        JFrame window = new JFrame("Dice");
        window.setBounds(0, 0, 500, 500);
        window.setLayout(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(new Point((int) ((d.getWidth() / 2) - 250),
                (int) ((d.getHeight() / 2) - 250)));
        window.setResizable(false);
        window.add(new Dice());
        window.setVisible(true);
    }

    private Die die1, die2;

    public Dice() {
        setLayout(null);
        setBounds(0, 0, 500, 500);
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        die1 = new Die();
        die2 = new Die();
        rollDice();
    }

    @Override
    public void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = ((Graphics2D) g0);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        g.drawString("Press any key to roll the dice", 60, 60);
        drawDie(g, die1);
        drawDie(g, die2);
    }

    private void drawDie(Graphics g, Die d) {
        g.setColor(Color.white);
        g.fillRoundRect((int) d.getX(), (int) d.getY(), (int) d.getWidth(),
                (int) d.getHeight(), 20, 20);
        g.setColor(Color.black);

        if (d.getRoll() % 2 == 1) { // common for 1, 3, and 5
            g.fillRoundRect((int) d.getX() + 40, (int) d.getY() + 40, 20, 20, 5, 5); // centre
        }

        if (d.getRoll() > 1) { // common for 2 to 6
            g.fillRoundRect((int) d.getX() + 70, (int) d.getY() + 70, 20, 20, 5, 5); // bottom-right
            g.fillRoundRect((int) d.getX() + 10, (int) d.getY() + 10, 20, 20, 5, 5); // top-left
        }

        if (d.getRoll() > 3) { // common for 4 to 6
            g.fillRoundRect((int) d.getX() + 70, (int) d.getY() + 10, 20, 20, 5, 5); // top-right
            g.fillRoundRect((int) d.getX() + 10, (int) d.getY() + 70, 20, 20, 5, 5); // bottom-left
        }

        if (d.getRoll() == 6) { // 6 only
            g.fillRoundRect((int) d.getX() + 70, (int) d.getY() + 40, 20, 20, 5, 5); // centre-right
            g.fillRoundRect((int) d.getX() + 10, (int) d.getY() + 40, 20, 20, 5, 5); // centre-left
        }
    }

    private void rollDice() {
        do {
            die1.roll();
            die2.roll();
        } while (die1.intersects(die2));
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
        else rollDice();
    }

    @Override
    public void keyReleased(KeyEvent k) {
    }

    @Override
    public void keyTyped(KeyEvent k) {
    }

    private class Die extends Rectangle {

        private int rolled;

        public Die() {
            super(100, 100);
            roll();
        }

        public void roll() {
            setLocation(10 + (int) (Math.random() * 380), 100 + (int) (Math.random() * 270));
            rolled = (int) (Math.random() * 6) + 1;
        }

        public int getRoll() {
            return rolled;
        }

    }
}
