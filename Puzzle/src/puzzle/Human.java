/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author alex
 */
public class Human extends javax.swing.JFrame {

    Tablero tablero;
    int type;

    /**
     * Creates new form Human
     */
    public Human(int type) {
        Color c = new Color(122, 94, 21);
        Color l = new Color(255, 255, 255);
        setContentPane(new JLabel(new ImageIcon(getClass().getResource("/imagenes/conf.png"))));
        initComponents();
        setSize(1000, 612);
        jToggleButton1.setBackground(c);
        jToggleButton1.setForeground(l);
        jToggleButton2.setBackground(c);
        jToggleButton2.setForeground(l);
        jButton1.setBackground(c);
        jButton1.setForeground(l);
        this.tablero = new Tablero("a", 857604321);
        this.type = type;
        this.tablero.setLocation(450, 200);
        this.tablero.setSize(150, 150);
        this.add(tablero);
    }

    public void best_first_search() {

        Tablero Meta = new Tablero("Goal");
        ArrayList<Tupla> Open = new ArrayList<>();
        ArrayList<Tupla> Close = new ArrayList<>();
        int secTab = 0;

        Open.add(new Tupla(tablero, 0, type));

        while (!Open.isEmpty() && secTab < 2) {

            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }*/

            Tupla X = Open.remove(0);
            this.tablero = new Tablero(X.getTablero());
            secTab++;

            for (int j = 0; j < 9; j++) {

                System.out.print(tablero.fichas.get(j).getText());
                if (j == 2 || j == 5) {
                    System.out.println(" ");
                }
            }
            System.out.println("\n" + X.getNivel());
            System.out.println("\n" + X.getPeso() + "\n\n");

            if (X.getTablero().equals(Meta)) {
                System.out.println("GOOOOOOOOOOOOOOOOOOOOOAAAAAAAAAAAAL");
                return;
            } else {

                ArrayList<Tupla> hijos = generar_hijos(X.getTablero(), (X.getNivel() + 1), type);

                for (Tupla t : hijos) {
                    if (!(Open.contains(t) || Close.contains(t))) {
                        Open.add(new Tupla(t));
                    } else if (Open.contains(t)) {
                        int indice = Open.indexOf(t);
                        if (Open.get(indice).getPeso() > t.getPeso()) {
                            Open.get(indice).setPeso(t.getPeso());
                        }
                    } else if (Close.contains(t)) {
                        int indice = Close.indexOf(t);
                        if (Close.get(indice).getPeso() > t.getPeso()) {
                            Close.remove(indice);
                            Open.add(t);
                        }
                    }
                }
                Close.add(X);
                Collections.sort(Open, new Comparator<Tupla>() {
                    @Override
                    public int compare(Tupla o1, Tupla o2) {
                        if (o1.getPeso() < o2.getPeso()) {
                            return -1;
                        } else if (o1.getPeso() > o2.getPeso()) {
                            return 1;
                        }
                        return 0;
                    }
                });
            }
            tablero.setLocation(450, 200);
            tablero.setSize(150, 150);
            this.add(tablero);
            repaint();
            tablero.fichas.get(0).setText("0");
            tablero.fichas.get(0).setText("" + tablero.fichas.get(0).numero);
            tablero.repaintFichas(tablero.fichas);
        }
    }

    public ArrayList<Tupla> generar_hijos(Tablero tablero, int nivel_Padre, int type) {
        int cero = posCero(tablero);
        ArrayList<Tupla> hijos = new ArrayList<>();
        ArrayList<Integer> vec = vecinos(cero);

        for (Integer indice : vec) {
            Tablero nuevo = new Tablero(tablero);
            int val = 0;
            val = tablero.fichas.get(indice).numero;
            nuevo.fichas.get(cero).setNumero(val);
            nuevo.fichas.get(indice).setNumero(0);
            hijos.add(new Tupla(nuevo, nivel_Padre, type));
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

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToggleButton1.setText("Exit");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("SEE RESOLUTION TREE");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        jLabel11.setText("PUZZLE");

        jButton1.setText("Advice");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(434, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(396, 396, 396))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new Home().setVisible(true);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Runnable miRunnable;
        miRunnable = new Runnable() {
            public void run() {

                best_first_search();
            }
        };

        Thread hilo = new Thread(miRunnable);
        hilo.start();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables
}
