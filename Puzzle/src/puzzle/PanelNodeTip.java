/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.tree.*;

public class PanelNodeTip {

    public JScrollPane getContent(ArrayList<Nodo> Arbol) {
        Icon icon = UIManager.getIcon("Tree.closedIcon");
        ArrayList<DefaultMutableTreeNode> datos= new ArrayList<>();
        DefaultMutableTreeNode root
                = new DefaultMutableTreeNode(
                        new PanelNode(Arbol.get(0), false, icon));
        
        
        datos.add(root);
        for(int i=1;i<Arbol.size();i++){
            int id=Arbol.get(i).getPadre();
            DefaultMutableTreeNode tr=new DefaultMutableTreeNode(buscarPadre(datos,id));
            tr.add(new DefaultMutableTreeNode(new PanelNode(Arbol.get(i), true, icon)));
            datos.add(tr);
        }

        JTree tree = new JTree(new DefaultTreeModel(root));

        tree.setEditable(true);
        return new JScrollPane(tree);
    }

    private DefaultMutableTreeNode buscarPadre(ArrayList<DefaultMutableTreeNode> datos, int id) {
        for(DefaultMutableTreeNode tr:datos){
            if(((PanelNode)tr.getUserObject()).getN().getPadre()==id)
                return tr;
        }
        return null;
    }
    
    class PanelNode {
        Nodo n;
        boolean value;
        Icon icon;

        public PanelNode(Nodo n, boolean b, Icon icon) {
            this.n = n;
            this.value = b;
            this.icon = icon;
        }

        public String toString() {
            return "[" + n.Dato.toString() + "]" + " H= " + n.Dato.getPeso();
        }

        public Nodo getN() {
            return n;
        }

        public void setN(Nodo n) {
            this.n = n;
        }
        

    }
}
