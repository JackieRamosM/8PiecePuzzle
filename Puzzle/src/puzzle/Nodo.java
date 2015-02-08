/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author alex
 */
public class Nodo {
    int Id;
    Tupla Dato;
    int Padre;

    public Nodo(Tupla Dato) {
        this.Dato = Dato;
    }

    public Nodo(int Id, Tupla Dato, int Padre) {
        this.Id = Id;
        this.Dato = Dato;
        this.Padre = Padre;
    }

    
    public Tupla getDato() {
        return Dato;
    }

    public void setDato(Tupla Dato) {
        this.Dato = Dato;
    }

    public int getPadre() {
        return Padre;
    }

    public void setPadre(int Padre) {
        this.Padre = Padre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Nodo other = (Nodo) obj;
        return (other.getDato().getTablero().equals(this.Dato.getTablero()) && other.getDato().getPeso()==this.getDato().getPeso());
    }

}
