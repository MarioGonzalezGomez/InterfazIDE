import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;


public class Vista extends JFrame {
    Servicios serv = new Servicios();
    Menu menu;
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

    String rutaBug = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "icons" + File.separator + "bug.png";
    String rutaHammer = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "icons" + File.separator + "hammer.png";
    String rutaPlay = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "icons" + File.separator + "jugar.png";
    String rutaStop = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "icons" + File.separator + "senal-de-stop.png";
    String rutaFuente = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "fonts" + File.separator + "powerline.ttf";

    ImageIcon bug = new ImageIcon(rutaBug);
    ImageIcon hammer = new ImageIcon(rutaHammer);
    ImageIcon play = new ImageIcon(rutaPlay);
    ImageIcon detener = new ImageIcon(rutaStop);

    public Vista() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (!editor.getText().isEmpty()) {
                    int respuesta = JOptionPane.showInternalConfirmDialog(null, "Si sales ahora perderás los cambios no guardados. \n ¿Estás seguro de que quieres salir?");
                    if (respuesta == 0) {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
        this.setPreferredSize(new Dimension(900, 700));

        arbol = new Arbol();
        explorador = new JTree();
        arbol.setJTree(explorador);
        arbol.init();

        explorador.setBackground(new Color(255, 195, 252));


        editor = new JTextArea();
        editor.setBackground(new Color(253, 227, 247));
        Font nuevaTipo = ponerTipo();
        editor.setFont(nuevaTipo);
        ponerPortapapeles();
        consola = new JTextArea();
        consola.setBackground(new Color(255, 195, 252));


        jspEditor = new JScrollPane();
        jspConsola = new JScrollPane();
        jspExplorador = new JScrollPane();

        jspExplorador.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jspExplorador.setViewportView(explorador);
        jspEditor.setViewportView(editor);
        jspConsola.setViewportView(consola);

        menu = new Menu(editor);

        this.setJMenuBar(menu.getTools());

        panelTools = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tools = new JMenuBar();
        barra = new JSeparator(JSeparator.VERTICAL);
        JButton compilar = new JButton();
        compilar.setIcon(hammer);
        compilar.addActionListener(x -> {
            try {
                compilar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        JButton run = new JButton();
        run.setIcon(play);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consola.setText(ejecutar());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton debug = new JButton();
        debug.setIcon(bug);
        JButton stop = new JButton();
        stop.setIcon(detener);

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

        archivoEditor.setDividerLocation(170);
        archivoEditor.setOneTouchExpandable(true);
        archivoEditor.setMinimumSize(new Dimension(50, 50));
        editorConsola.setDividerLocation(400);
        editorConsola.setOneTouchExpandable(true);
        editorConsola.setMinimumSize(new Dimension(50, 50));

        panelPrincipal.add(editorConsola, BorderLayout.CENTER);
        panelPrincipal.setOpaque(true);

        borrable = new JTextArea("Barra de estado...............................");
        borrable.setFont(nuevaTipo);
        panelPrincipal.add(borrable, BorderLayout.SOUTH);


        this.pack();

        editor.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }


            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    editor.setText(editor.getText() + "   ");
                }
            }


            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        unDo = new Deshacer();
        editor.getDocument().addUndoableEditListener(unDo.getDeshacer());
    }

    private void ponerPortapapeles() {
        Clipboard portaPapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
        if (editor.getSelectedText() != null) {
            StringSelection seleccion = new StringSelection("" + editor.getSelectedText());
            portaPapeles.setContents(seleccion, seleccion);
        }
        Transferable datos = portaPapeles.getContents(null);
        try {
            if (datos != null && datos.isDataFlavorSupported(DataFlavor.stringFlavor))
                editor.replaceSelection("" + datos.getTransferData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException | IOException ex) {
            System.err.println(ex);
        }

    }

    private Font ponerTipo() {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(rutaFuente)).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (IOException | FontFormatException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private void ejecutarComandos(String comando) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        // Process process = null;
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        System.out.println(comando);
        if (isWindows) {
            builder.command("cmd.exe", " /c ", comando);
        } else {
            builder.command("sh", " -c ", comando);
        }
        builder.start();
        // builder.redirectError();
        //builder.redirectOutput();
    }

    private @Nullable File compilar() throws IOException {
        try {
            File archivo;
            if (menu.getDoc().getRuta() == null) {
                archivo = serv.guardarArchivoComo(editor).getRuta();
                menu.getDoc().setRuta(archivo);
            } else {
                serv.guardar(menu.getDoc(), editor);
                archivo = menu.getDoc().getRuta();
            }
            ejecutarComandos("javac " + "\"" + archivo.getAbsolutePath() + "\"");

            return archivo;
        } catch (IOException e) {
            e.printStackTrace();
            ejecutarComandos("echo " + "\"ha ocurrido un error\"");
            return null;
        }
    }

    private String ejecutar() throws IOException {
        String result = "";
        Process process = null;
        try {
            File archivo = compilar();
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                process = Runtime.getRuntime().exec("cmd /c java " + "\"" + archivo.getAbsolutePath().replaceAll("java", "class") + "\"");
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                result = br.readLine();
                while (result != null) {
                    consola.append(result + "\n");
                    result = br.readLine();
                }
            } else {
                compilar();
                process = Runtime.getRuntime().exec("sh -c java " + archivo.getAbsolutePath());
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                result = br.readLine();
                while (result != null) {
                    consola.append(result + "\n");
                    result = br.readLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
