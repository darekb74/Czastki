/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandlines;

import DaL.DotsAndLines;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Darek Xperia
 */
public class DotsAndLinesTest extends JFrame {

    /**
     * @param args the command line arguments
     */
    DotsAndLinesTest() {
        setSize(new Dimension(800, 400));
        JPanel jp = new JPanel();
        jp.setSize(new Dimension(getWidth(), getHeight()));
        add(jp);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        DotsAndLines anim = new DotsAndLines(jp, 100, 100);
        anim.start();
    }

    public static void main(String[] args) {
        // TODO code application logic here
        JFrame main = new DotsAndLinesTest();
    }

}
