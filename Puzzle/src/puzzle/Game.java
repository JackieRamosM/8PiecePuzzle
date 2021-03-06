/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import com.sun.jmx.snmp.BerDecoder;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import sun.reflect.generics.tree.Tree;

/**
 *
 * @author alex
 */
public class Game extends javax.swing.JFrame {

    ArrayList<Nodo> Arbol;
    Tablero tablero;
    int type;
    int num = 13;
    int b = 0;

    /**
     * Creates new form Game
     */
    public Game(int type, int tabinicial) {
        Color c = new Color(122, 94, 21);
        Color l = new Color(255, 255, 255);
        setContentPane(new JLabel(new ImageIcon(getClass().getResource("/imagenes/conf.png"))));
        initComponents();
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/imagenes/goal.jpg")));
        jToggleButton2.setBackground(c);
        jButton2.setBackground(c);
        jButton2.setForeground(l);
        jToggleButton2.setForeground(l);
        if (tabinicial == 0) {
            this.tablero = new Tablero();
        } else {
            int in = invertirnum(tabinicial);
            this.tablero = new Tablero("a", in);
        }
        this.type = type;
        this.tablero.setLocation(508, 120);
        this.tablero.setSize(350, 350);
        this.tablero.setBackground(l);
        this.add(tablero);

    }

    public void best_first_search() {

        int id = 0, b = 1, padre;
        Tablero Meta = new Tablero("Goal");
        ArrayList<Tupla> Open = new ArrayList<>();
        ArrayList<Tupla> Close = new ArrayList<>();
        Arbol = new ArrayList<>();
        Tupla X = new Tupla(tablero, 0, type);
        Arbol.add(new Nodo(id, X, 0));
        Open.add(X);
        while (!Open.isEmpty()) {

            X = Open.remove(0);
            int ind = Arbol.indexOf(new Nodo(X));
            padre = Arbol.get(ind).getId();

            if (X.getTablero().equals(Meta)) {
                jLabel1.setText("Finally solved!");
                System.out.println("GOOOOOOOOOOOOOOOOOOOOOAAAAAAAAAAAAL");
                buscarCamino(Arbol);
                return;
            } else {

                ArrayList<Tupla> hijos = generar_hijos(X.getTablero(), (X.getNivel() + 1), type);
                for (Tupla t : hijos) {
                    id++;
                    Arbol.add(new Nodo(id, t, padre));
                }

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

        }
    }

    public int buscarMeta(Tablero Meta, ArrayList<Nodo> Arbol) {
        int i = 0;
        for (Nodo n : Arbol) {
            if (n.getDato().getTablero().equals(Meta)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public void buscarCamino(ArrayList<Nodo> Arbol) {
        ArrayList<Nodo> Solucion = new ArrayList<>();
        Tablero Meta = new Tablero("Goal");
        int indice = buscarMeta(Meta, Arbol);
        int padre;
        Nodo n = Arbol.get(indice);
        Solucion.add(n);
        do {
            padre = n.getPadre();
            n = buscarPadre(padre, Arbol);
            if (n != null) {
                Solucion = agregarSol(Solucion, n);
            }
        } while (padre != 0);
        animacion(Solucion);
    }

    public ArrayList<Nodo> agregarSol(ArrayList<Nodo> Solucion, Nodo n) {
        ArrayList<Nodo> Sol2 = new ArrayList<>();
        Sol2.add(n);
        for (Nodo n1 : Solucion) {
            Sol2.add(n1);
        }
        return Sol2;
    }

    public Nodo buscarPadre(int padre, ArrayList<Nodo> Arbol) {
        for (Nodo n : Arbol) {
            if (n.getId() == padre) {
                return n;
            }
        }
        return null;
    }

    public void animacion(ArrayList<Nodo> Arbol) {
        Tablero Meta = new Tablero("Goal");
        for (Nodo n : Arbol) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.tablero.cambiarFichas(n.getDato().getTablero());
            tablero.repaintFichas(tablero.fichas);
            repaint();
            if (tablero.equals(Meta)) {
                JOptionPane.showMessageDialog(null, "Wooooooooo! I rock \\m/");
                return;
            }
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

        jLabel11 = new javax.swing.JLabel();
        jToggleButton2 = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        jLabel11.setText("PUZZLE");

        jToggleButton2.setText("Exit");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jButton2.setText("START");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Press Start");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(80, 532, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(200, 200, 200))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(219, 219, 219))))
            .addGroup(layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(479, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        this.setVisible(false);
        new Home().setVisible(true);
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        b = 1;
        jLabel1.setText("Wait: Best First in action!");
        jButton2.setVisible(false);
        Runnable miRunnable;
        miRunnable = new Runnable() {
            public void run() {

                best_first_search();
            }
        };

        Thread hilo = new Thread(miRunnable);
        hilo.start();

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables

    private int invertirnum(int tabinicial) {
        int inv = 0;
        for (int i = 0; i < 9; i++) {
            inv = inv * 10;
            inv = inv + (tabinicial % 10);
            tabinicial = tabinicial / 10;
        }
        return inv;

    }

}
