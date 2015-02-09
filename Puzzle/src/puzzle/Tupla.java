/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Liliana
 */
public class Tupla {

    private Tablero tablero;
    private Integer peso;
    private int nivel;

    public Tupla(Tablero tablero, int nivel, int type) {
        this.tablero = new Tablero(tablero);
        this.nivel = nivel;
        if (type == 1) {
            this.peso = heuristic_outofplace();
        } else {
            this.peso = heuristic_sumdistance();
        }

    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Tupla(Tupla t) {
        this.tablero = new Tablero(t.getTablero());
        this.peso = t.getPeso();
        this.nivel = t.nivel;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tupla other = (Tupla) obj;
        if (other.getTablero().equals(this.getTablero())) {
            return true;
        }
        return false;
    }

    public int heuristic_outofplace() {
        int h = 0;
        for (int i = 0; i < 8; i++) {
            if (this.tablero.fichas.get(i).numero != i + 1) {
                h++;
            }
        }
        return h + this.nivel;
    }

    public int heuristic_sumdistance() {
        int acum = 0;
        for (int i = 1; i <= 8; i++) {
            acum = acum + distance(i, this.tablero.getFichas().indexOf(new Ficha(i)));
        }
        return acum + this.nivel;
    }

    public int distance(int numero, int index1) {
        Tablero tab = new Tablero(numero);
        return tab.getFichas().get(index1).numero;
    }

    @Override
    public String toString() {
        return tablero.fichas.get(0)+","+tablero.fichas.get(1)+","+tablero.fichas.get(2)+" \\ "+
                tablero.fichas.get(3)+","+tablero.fichas.get(4)+","+tablero.fichas.get(5)+" \\ "+
                tablero.fichas.get(6)+","+tablero.fichas.get(7)+","+tablero.fichas.get(8)+"";
    }
    
    
}
