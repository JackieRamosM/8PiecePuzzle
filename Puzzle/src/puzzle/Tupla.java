/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package puzzle;

import java.util.Objects;

/**
 *
 * @author Liliana
 */
public class Tupla {
    private Tablero tablero;
    private Integer peso;

    public Tupla(Tablero tablero, Integer peso) {
        this.tablero = tablero;
        this.peso = peso;
    }
    
    public Tupla(Tupla t){
        this.tablero = new Tablero(t.getTablero());
        this.peso = t.getPeso();
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
        if(other.getTablero().equals(this.getTablero()))
            return true;
        return false;
    }
    
    
    
}
