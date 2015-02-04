/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import com.sun.jmx.snmp.BerDecoder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author alex
 */
public class Game extends javax.swing.JFrame {

    /**
     * Creates new form Game
     */
    public Game() {
        Color c = new Color(122, 94, 21);
        Color l = new Color(255, 255, 255);
        setContentPane(new JLabel(new ImageIcon(getClass().getResource("/imagenes/conf.png"))));
        initComponents();
        jToggleButton1.setBackground(c);
        jToggleButton2.setBackground(c);
        jButton1.setBackground(c);
        jToggleButton1.setForeground(l);
        jButton1.setForeground(l);
        jToggleButton2.setForeground(l);
        Tablero tablero = new Tablero();
        tablero.setLocation(450, 200);
        tablero.setSize(150, 150);
        tablero.setVisible(true);
        this.add(tablero);
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
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
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

        jButton1.setText("SEE RESOLUTION TREE");

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(376, 376, 376))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(371, 371, 371))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(546, 546, 546))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables

}
