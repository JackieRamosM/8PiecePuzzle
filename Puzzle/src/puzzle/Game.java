/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import com.sun.jmx.snmp.BerDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alex
 */
public class Game extends javax.swing.JFrame {

    /**
     * Creates new form Game
     */
    public Game() {
        initComponents();
        Tablero tablero = new Tablero();
        Tablero tablero2 = new Tablero("hola");
        Tupla t3 = new Tupla(tablero2, fichas_fuera(tablero2));
        tablero.setLocation(100, 100);
        tablero.setSize(150, 150);
        tablero.setVisible(true);
        this.add(tablero);
        ArrayList<Tupla> t = backtrack(t3);
        System.out.println("" + t.get(t.size() - 1).getTablero().fichas.get(0));
        //best_first_search(tablero);

    }

    public void best_first_search(Tablero tablero) {
        Tablero Meta = new Tablero(0);
        ArrayList<Tupla> Open = new ArrayList<Tupla>();
        ArrayList<Tupla> Close = new ArrayList<Tupla>();

        Open.add(new Tupla(tablero, fichas_fuera(tablero)));
        while (!Open.isEmpty()) {
            Tupla X = Open.remove(0);
            if (X.getTablero().equals(Meta)) {
                return;
            } else {
                ArrayList<Tupla> hijos = generar_hijos(X.getTablero());
                for (Tupla t : hijos) {
                    if (!(Open.contains(t) || Close.contains(t))) {
                        Open.add(new Tupla(t));
                    } else if (Open.contains(t)) {

                    } else {
                    }

                }
            }
        }
    }

    public ArrayList<Tupla> backtrack(Tupla t) {
        Tablero Meta = new Tablero(0);
        ArrayList<Tupla> SL = new ArrayList<>();
        ArrayList<Tupla> NSL = new ArrayList<>();
        ArrayList<Tupla> DE = new ArrayList<>();
        Tupla CS = new Tupla(t);
        SL.add(new Tupla(t));
        NSL.add(new Tupla(t));
        while (!NSL.isEmpty()) {
            if (CS.getTablero().equals(Meta)) {
                return SL;
            }
            ArrayList<Tupla> hijos = generar_hijos(CS.getTablero());
            excluir_hijos(hijos, DE, SL, NSL);
            if (hijos.isEmpty()) {
                while (!SL.isEmpty() && CS.equals(SL.get(0))) {
                    DE = agregar(DE, CS);
                    SL.remove(0);
                    NSL.remove(0);
                    CS = new Tupla(NSL.get(0));
                }
                SL.add(new Tupla(CS));
            } else {
                NSL = agregar(NSL, hijos);
                CS = new Tupla(NSL.get(0));
                SL.add(new Tupla(CS));
            }
        }
        return SL;
    }

    private ArrayList<Tupla> agregar(ArrayList<Tupla> Actual, ArrayList<Tupla> Final) {
        ArrayList<Tupla> nueva = new ArrayList<>();
        for (Tupla t : Final) {
            nueva.add(new Tupla(t));
        }
        for (Tupla t : Actual) {
            nueva.add(new Tupla(t));
        }
        return nueva;
    }

    private ArrayList<Tupla> agregar(ArrayList<Tupla> Actual, Tupla Final) {
        ArrayList<Tupla> nueva = new ArrayList<>();
        nueva.add(new Tupla(Final));
        for (Tupla t : Actual) {
            nueva.add(new Tupla(t));
        }
        return nueva;
    }

    private void excluir_hijos(ArrayList<Tupla> hijos, ArrayList<Tupla> DE, ArrayList<Tupla> SL, ArrayList<Tupla> NSL) {
        int i = 0;
        ArrayList<Integer> rm = new ArrayList<>();
        for (Tupla t : hijos) {
            if (DE.contains(t) || SL.contains(t) || NSL.contains(t)) {
                rm.add(i);
            }
            i++;
        }
        for (Integer j : rm) {
            try {
                hijos.remove(j.intValue());
            } catch (Exception e) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + j);
                System.out.println(rm.size());
                System.out.println(hijos.size());
            }

        }
    }

    public ArrayList<Tupla> generar_hijos(Tablero tablero) {
        int cero = posCero(tablero);
        ArrayList<Tupla> hijos = new ArrayList<>();
        ArrayList<Integer> vec = vecinos(cero);

        for (Integer indice : vec) {
            Tablero nuevo = new Tablero(tablero);
            int val = 0;
            val = tablero.fichas.get(indice).numero;
            System.out.println("vec=" + indice + " val=" + val + " cero=" + cero);
            System.out.println(nuevo.fichas.get(cero).numero + " " + nuevo.fichas.get(indice).numero);
            nuevo.fichas.get(cero).setNumero(val);
            nuevo.fichas.get(indice).setNumero(0);
            System.out.println(nuevo.fichas.get(cero).numero + " " + nuevo.fichas.get(indice).numero);
            hijos.add(new Tupla(nuevo, fichas_fuera(nuevo)));
        }

        for (int i = 0; i < hijos.size(); i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(hijos.get(i).getTablero().fichas.get(j).numero + "");
            }
            System.out.println("");

        }

        return hijos;
    }

    public int posCero(Tablero t) {
        int indice = 0;
        for (int i = 0; i < 9; i++) {
            if (t.fichas.get(i).numero == 0) {
                indice = i;
            }
        }
        return indice;
    }

    public ArrayList<Integer> vecinos(int cero) {
        ArrayList<Integer> vec = new ArrayList<>();

        switch (cero) {
            case 0:
                vec.add(1);
                vec.add(3);
                break;
            case 1:
                vec.add(0);
                vec.add(2);
                vec.add(4);
                break;
            case 2:
                vec.add(1);
                vec.add(5);
                break;
            case 3:
                vec.add(0);
                vec.add(4);
                vec.add(6);
                break;
            case 4:
                vec.add(1);
                vec.add(3);
                vec.add(5);
                vec.add(7);
                break;
            case 5:
                vec.add(2);
                vec.add(4);
                vec.add(8);
                break;
            case 6:
                vec.add(3);
                vec.add(7);
                break;
            case 7:
                vec.add(4);
                vec.add(6);
                vec.add(8);
                break;
            case 8:
                vec.add(5);
                vec.add(7);
                break;
        }
        return vec;
    }

    public int fichas_fuera(Tablero tablero) {
        int cont = 0;
        for (int i = 0; i < 8; i++) {
            if (tablero.fichas.get(i).numero != i + 1) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel11.setText("PUZZLE");

        jToggleButton1.setText("Advice");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("Exit");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(254, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(253, 253, 253))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jToggleButton1)
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jToggleButton2)
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel11)
                .addGap(28, 28, 28)
                .addComponent(jToggleButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                .addComponent(jToggleButton2)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        this.setVisible(false);
        new Home().setVisible(true);
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel11;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables

}
