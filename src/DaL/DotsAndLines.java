/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaL;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Darek Xperia
 */
public class DotsAndLines extends Thread {

    private ArrayList<Punkt>[][] punktyM;
    private ArrayList<Punkt> punkty = new ArrayList<>();
    private int iloscPunktow;
    private int maksOdleglosc;
    private int width, height;
    private Container tablica;
    private long update = 0;
    private Random generator = new Random();
    private Punkt mysz;
    //private long dT;

    public DotsAndLines(Container tablica, int iloscPunktow, int maksOdleglosc) {
        update = System.currentTimeMillis();
        this.tablica = tablica;
        this.iloscPunktow = Math.abs(iloscPunktow) > 200 ? 200 : Math.abs(iloscPunktow);
        this.maksOdleglosc = Math.abs(maksOdleglosc) > 100 ? 100 : Math.abs(maksOdleglosc);
        this.width = tablica.getWidth();
        this.height = tablica.getHeight();
        punktyM = new ArrayList[width + 1][height + 1];  // nowe listy punktów
        for (int i = 0; i < iloscPunktow; i++) {
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);
            Punkt p = new Punkt(x, y);
            punkty.add(p); // dodajemy punkt do listy 
            if (punktyM[x][y] == null) {
                punktyM[x][y] = new ArrayList<>();
            }
            punktyM[x][y].add(p); // dodajemy punty do konkretnej listy w siatce 
        }

        MouseAdapter mouseListener = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mysz == null) {
                    mysz = new Punkt(0, 0);
                    punkty.add(mysz);
                }
                if (e.getX() >= 0 && e.getX() <= width && e.getY() >= 0 && e.getY() <= height) {
                    mysz.x = e.getX();
                    mysz.y = e.getY();
                } else {
                    punkty.remove(mysz);
                    mysz = null;
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                punkty.remove(mysz);
                mysz = null;
            }
        };
        tablica.addMouseListener(mouseListener);
        tablica.addMouseMotionListener(mouseListener);
    }

    private void ruszajPunkty() {
        //dT = System.currentTimeMillis();
        BufferedImage tlo = new BufferedImage(tablica.getWidth(), tablica.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        //tablica.getParent().repaint();
        Graphics t = tlo.getGraphics();
        t.setColor(Color.DARK_GRAY);
        t.fillRect(0, 0, tablica.getWidth(), tablica.getHeight());
        Graphics g = tablica.getGraphics();
        t.setColor(Color.white);
        for (Punkt p : (ArrayList<Punkt>) punkty.clone()) {
            if (!p.equals(mysz)) {
                p.ruszaj();
            }
            t.fillOval(p.x - 2, p.y - 2, 4, 4);
        }
        if (mysz != null) {
            t.fillOval(mysz.x - 2, mysz.y - 2, 4, 4);
        }
        // obiekty ruszone
        przepiszListy();
        // rysuj linie
        rysujLinie(t);

        g.drawImage(tlo, 0, 0, tablica);
        t.dispose();
        g.dispose();
        //dT = System.currentTimeMillis() - dT;
        //System.out.println(dT);
    }

    private void rysujLinie(Graphics g) {
        for (Punkt e : (ArrayList<Punkt>) punkty.clone()) {
            for (int x = (e.x / maksOdleglosc - 1 < 0 ? 0 : e.x / maksOdleglosc - 1); x <= (e.x / maksOdleglosc + 1 > width / maksOdleglosc ? width / maksOdleglosc : e.x / maksOdleglosc + 1); x++) {
                for (int y = (e.y / maksOdleglosc - 1 < 0 ? 0 : e.y / maksOdleglosc - 1); y <= (e.y / maksOdleglosc + 1 > height / maksOdleglosc ? height / maksOdleglosc : e.y / maksOdleglosc + 1); y++) {
                    if (punktyM[x][y] != null) {
                        for (Punkt el : punktyM[x][y]) {
                            if (!e.equals(el)) {
                                int dist = (int) Math.hypot(e.x - el.x, e.y - el.y);
                                if (dist <= maksOdleglosc) {
                                    // rysuj linię
                                    float tmpC = Math.abs(1.0f - (1.0f * (float) dist / (float) maksOdleglosc));
                                    g.setColor(new Color(0.9f, 0.9f, 0.9f, tmpC));
                                    g.drawLine(e.x, e.y, el.x, el.y);
                                }
                            }
                        }
                    }
                }
            }
            // usuń z listy
            if (punktyM[e.x / maksOdleglosc][e.y / maksOdleglosc] != null) {
                punktyM[e.x / maksOdleglosc][e.y / maksOdleglosc].remove(e);
            }
        }
    }

    private void przepiszListy() {
        punktyM = new ArrayList[width / maksOdleglosc + 1][height / maksOdleglosc + 1];  // nowe listy punktów
        for (Punkt e : punkty) {
            int x = (int) e.getX() / maksOdleglosc;
            int y = (int) e.getY() / maksOdleglosc;
            if (punktyM[x][y] == null) {
                punktyM[x][y] = new ArrayList<>();
            }
            punktyM[x][y].add(e); // dodajemy punty do konkretnej listy 
        }
    }

    @Override
    public void run() {
        while (true) {
            long cTime = System.currentTimeMillis();
            if (update + 30 < cTime) {
                ruszajPunkty();
                update = cTime;

            }
        }
    }

    private class Punkt extends Point {

        private Point wektorRuchu;

        public Punkt(int x, int y) {
            super(x, y);
            wektorRuchu = new Point((1 + generator.nextInt(3)) * (generator.nextBoolean() ? 1 : -1),
                    (1 + generator.nextInt(3)) * (generator.nextBoolean() ? 1 : -1));
        }

        public void ruszaj() {
            Punkt pozycja = new Punkt(this.x, this.y);
            pozycja.translate(wektorRuchu.x, wektorRuchu.y);
            // sprawdzamy, czy nie nastąpiło odbicie
            if (pozycja.x > width || pozycja.x < 0) { // odbicie w poziomie
                wektorRuchu.x *= -1;
                //sprawdzamy ponownie
                ruszaj();
                return;
            }
            if (pozycja.y > height || pozycja.y < 0) { // odbicie w pionie
                wektorRuchu.y *= -1;
                //sprawdzamy ponownie
                ruszaj();
                return;
            }
            // ok, przesuwamy
            this.x = pozycja.x;
            this.y = pozycja.y;
        }
    }
}
