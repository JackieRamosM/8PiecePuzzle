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
                f = new Ficha(x, "");
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

    public Tablero(int n) {
        this.fichas = new ArrayList<Ficha>();
        for (int i = 1; i < 9; i++) {
            this.fichas.add(new Ficha(i));
        }
        this.fichas.add(new Ficha(0));
    }
    
    public Tablero(String s) {
        this.fichas = new ArrayList<Ficha>();
        this.fichas.add(new Ficha(2));
        this.fichas.add(new Ficha(8));
        this.fichas.add(new Ficha(3));
        this.fichas.add(new Ficha(1));
        this.fichas.add(new Ficha(6));
        this.fichas.add(new Ficha(4));
        this.fichas.add(new Ficha(7));
        this.fichas.add(new Ficha(0));
        this.fichas.add(new Ficha(5));
    }
    
    public Tablero(Tablero t) {
        this.fichas = new ArrayList<Ficha>();
        for (int i = 0; i < 9; i++) {
            this.fichas.add(new Ficha(t.fichas.get(i).getNumero()));
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
            System.out.print(f.numero+" ");
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

}
