/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandlines;

import DaL.DotsAndLines;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Darek Xperia
 */
public class DotsAndLinesTest extends JFrame {

    DotsAndLinesTest() {
        JPanel p = new JPanel();
        this.add(p);
        setSize(new Dimension(800, 800));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        DotsAndLines jp = new DotsAndLines(784, 360, 100, 100);
        DotsAndLines djp = new DotsAndLines(784, 360, 500, 70);
        jp.setSize(new Dimension(getWidth(), getHeight()));
        p.add(jp);
        p.add(djp);
        new Thread(jp).start();
        new Thread(djp).start();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // TODO code application logic here
        JFrame main = new DotsAndLinesTest();
    }

}
