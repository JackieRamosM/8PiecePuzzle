/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 */
public class Tablero extends JPanel {

    ArrayList<Ficha> fichas;

    public Tablero() {
        Random rnd = new Random();
        int x, i = 0;
        this.fichas = new ArrayList<Ficha>();
        this.setLayout(new GridLayout(3, 3));
        this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        while (i < 9) {
            x = generarAleatorios(0, 8);
            Ficha f;
            if (x == 0) {
                f = new Ficha(x, "0");
            } else {
                f = new Ficha(x, "" + x);
            }
            if (!this.fichas.contains(f)) {
                this.fichas.add(f);
                add(f);
                i++;
            }
        }
    }

    public Tablero(String s) {
        this.fichas = new ArrayList<Ficha>();
        for (int i = 1; i < 9; i++) {
            this.fichas.add(new Ficha(i));
        }
        this.fichas.add(new Ficha(0));
    }

    public Tablero(String s, int a) {
        int x;
        this.fichas = new ArrayList<>();
        this.setLayout(new GridLayout(3, 3));
        this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        for (int i = 0; i < 9; i++) {
            //this.fichas.add(new Ficha(a % 10));
            x= a%10;
            Ficha f;
            if (x == 0) {
                f = new Ficha(x, "0");
            } else {
                f = new Ficha(x, "" + x);
            }
            this.fichas.add(f);
            add(f);
            a = a / 10;
        }

    }

    public Tablero(int n) {
        this.fichas = new ArrayList<Ficha>();
        switch (n) {
            case 1:
                n = 432321210;
                break;
            case 2:
                n = 323212101;
                break;
            case 3:
                n = 234123012;
                break;
            case 4:
                n = 321210321;
                break;
            case 5:
                n = 212101212;
                break;
            case 6:
                n = 123012123;
                break;
            case 7:
                n = 210321432;
                break;
            case 8:
                n = 010212323;
                break;
        }
        for (int i = 0; i < 9; i++) {
            this.fichas.add(new Ficha(n % 10));
            n = n / 10;
        }
    }

    public Tablero(Tablero t) {
        int x;
        this.fichas = new ArrayList<>();
        this.setLayout(new GridLayout(3, 3));
        this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        for (int i = 0; i < 9; i++) {
            x = t.getFichas().get(i).numero;
            Ficha f;
            if (x == 0) {
                f = new Ficha(x, "0");
            } else {
                f = new Ficha(x, "" + x);
            }
            this.fichas.add(f);
            add(f);
        }
    }

    public int generarAleatorios(int min, int max) {
        int num = -min + (int) (Math.random() * ((max - (-min)) + 1));
        return num;
    }

    public ArrayList<Ficha> getFichas() {
        return fichas;
    }

    public void setFichas(ArrayList<Ficha> fichas) {
        this.fichas = fichas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.fichas);
        return hash;
    }

    @Override
    public String toString() {
        for (Ficha f : this.fichas) {
            System.out.print(f.numero + " ");
        }
        return " ";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tablero other = (Tablero) obj;
        for (int i = 0; i < 9; i++) {
            if (other.fichas.get(i).numero != this.fichas.get(i).numero) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    public void repaintFichas(ArrayList<Ficha> fs) {
        for (Ficha ficha : fs) {
            ficha.repaint();
        }
    }

}
