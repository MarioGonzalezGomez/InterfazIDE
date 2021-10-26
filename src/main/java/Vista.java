import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static javax.swing.text.StyleConstants.Orientation;

public class Vista extends JFrame {
    private JPanel panelPrincipal;

    private JSplitPane editorConsola;
    private JSplitPane archivoEditor;

    private JScrollPane jspEditor;
    private JScrollPane jspConsola;
    private JScrollPane jspExplorador;

    private JTextArea editor;
    private Deshacer unDo;
    private Arbol arbol;
    private JTree explorador;
    private JTextArea consola;

    private JPanel panelTools;
    private JMenuBar tools;
    private JSeparator barra;

    private JTextArea borrable;


    public Vista() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(700, 500));

        arbol = new Arbol();
        explorador = new JTree();
        arbol.setJTree(explorador);
        arbol.init();

        editor = new JTextArea();
        consola = new JTextArea();

        jspEditor = new JScrollPane();
        jspConsola = new JScrollPane();
        jspExplorador = new JScrollPane();

        jspExplorador.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jspExplorador.setViewportView(explorador);
        jspEditor.setViewportView(editor);
        jspConsola.setViewportView(consola);

        Menu menu = new Menu(editor);

        this.setJMenuBar(menu.getTools());

        panelTools = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tools = new JMenuBar();
        barra = new JSeparator(JSeparator.VERTICAL);
        JButton compilar = new JButton("Com");
        JButton run = new JButton("Run");
        JButton debug = new JButton("Bug");
        JButton stop = new JButton("Stop");

        tools.add(barra);
        tools.add(compilar);
        tools.add(run);
        tools.add(debug);
        tools.add(stop);
        tools.add(barra);

        panelTools.add(tools);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        this.add(panelPrincipal);


        panelPrincipal.add(panelTools, BorderLayout.NORTH);


        archivoEditor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jspExplorador, jspEditor);
        editorConsola = new JSplitPane(JSplitPane.VERTICAL_SPLIT, archivoEditor, jspConsola);

        archivoEditor.setDividerLocation(500);
        archivoEditor.setOneTouchExpandable(true);
        archivoEditor.setMinimumSize(new Dimension(50, 50));
        editorConsola.setDividerLocation(500);
        editorConsola.setOneTouchExpandable(true);
        editorConsola.setMinimumSize(new Dimension(50, 50));

        panelPrincipal.add(editorConsola, BorderLayout.CENTER);

        borrable = new JTextArea("Texto genérico de barra de estado que ya cambiaré");
        panelPrincipal.add(borrable, BorderLayout.SOUTH);


        this.pack();

        editor.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }


            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    editor.setText(editor.getText() + "    ");
                }
            }


            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        unDo = new Deshacer();
        editor.getDocument().addUndoableEditListener(unDo.getDeshacer());
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextArea getEditor() {
        return editor;
    }

    public void setEditor(JTextArea editor) {
        this.editor = editor;
    }

}
