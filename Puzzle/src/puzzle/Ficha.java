/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

/**
 *
 * @author alex
 */
public class Ficha extends JLabel {
    int numero;

    public Ficha(int numero) {
        this.numero = numero;
    }

    public Ficha(int numero, String text) {
        super(text);
        this.numero = numero;
        setSize(50, 50);
        if(this.numero==0)
             setBackground(Color.BLACK);
        else
            setBackground(new java.awt.Color(255, 255, 255));
        setFont(new Font("Serif", Font.PLAIN, 50));
     
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setPreferredSize(new java.awt.Dimension(50, 50));
    }
    

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ficha other = (Ficha) obj;
        if(other.getNumero()==this.numero)
            return true;
        return false;
    }

    public void repaint(){
        super.repaint();
    }
}
