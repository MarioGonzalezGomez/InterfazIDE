import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame {
    private JPanel panelPrincipal;
    private JTextArea editor;
    public Vista(){initComponents();}
    private void initComponents(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //añadir elementos
        this.pack();
        // añadir action listeners
    }
}
