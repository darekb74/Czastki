/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandlines;

import DaL.DotsAndLines;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Darek Xperia
 */
public class DotsAndLinesTest extends JFrame {

    private DotsAndLines jp;

    DotsAndLinesTest() {
        setSize(new Dimension(800, 400));
        jp = new DotsAndLines(784, 360, -200, 100);
        jp.setSize(new Dimension(getWidth(), getHeight()));
        add(jp);
        new Thread(jp).start();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // TODO code application logic here
        JFrame main = new DotsAndLinesTest();
    }

}
