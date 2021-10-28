

import java.io.File;
import java.util.List;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class Arbol implements TreeExpansionListener {

    private JTree jTree;
    private DefaultTreeModel modelo;

    public DefaultTreeModel getModelo() {
        return modelo;
    }

    public Arbol() {
    }

    public Arbol(JTree jTree) {
        this.jTree = jTree;
    }

    public void setJTree(JTree jTree) {
        this.jTree = jTree;
    }

    public void init() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Directorio");
        modelo = new DefaultTreeModel(top);
        jTree.setModel(modelo);
        jTree.addTreeExpansionListener(this);
        File user = new File(System.getProperty("user.home"));
        File[] directorios = user.listFiles();
        for (File f : directorios) {
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f);
            top.add(raiz);
            actualizaNodo(raiz, f);
        }
    }

    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f) {
        nodo.removeAllChildren();
        return actualizaNodo(nodo, f, 2);
    }

    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f, int profundidad) {
        File[] files = f.listFiles();
        if (files != null && profundidad > 0) {
            for (File file : files) {
                DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(file);
                actualizaNodo(nuevo, file, profundidad - 1);
                nodo.add(nuevo);
            }
        }
        return true;
    }


    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        TreePath path = event.getPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (node == null || !(node.getUserObject() instanceof File)) return;
        File f = (File) node.getUserObject();
        actualizaNodo(node, f);
        JTree tree = (JTree) event.getSource();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.nodeStructureChanged(node);
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {

    }
}
