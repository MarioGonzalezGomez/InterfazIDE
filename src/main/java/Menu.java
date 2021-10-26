import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Data
public class Menu {
    Servicios serv = new Servicios();
    Deshacer doUndo = new Deshacer();

    private JMenuBar tools;
    private JTextArea editor;
    private Documento doc = null;

    public Menu(JTextArea editor) {
        this.editor = editor;
        this.tools = crearMenu();
    }

    private JMenuBar crearMenu() {
        JMenuBar menu = new JMenuBar();

        JMenu archivo = menuArchivo();
        menu.add(archivo);

        JMenu edition = menuEdition();
        menu.add(edition);

        JMenu vistas = menuVistas();
        menu.add(vistas);

        JMenu help = menuHelp();
        menu.add(help);

        return menu;
    }

    private JMenu menuArchivo() {
        JMenu archivo = new JMenu("Archivo");

        JMenu nuevo = new JMenu("Nuevo");
        JMenuItem abrir = new JMenuItem("Abrir");
        abrir.addActionListener(ae -> {
            try {
                serv.abrirArchivo(editor);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        JMenuItem cerrar = new JMenuItem("Cerrar");

        JMenuItem guardar = new JMenuItem("Guardar");

        guardar.addActionListener(ae -> {
            if (doc == null) {
                doc = serv.guardarArchivoComo(editor);
            } else {
                serv.guardar(doc, editor);
            }
        });

        JMenuItem guardarComo = new JMenuItem("Guardar como...");
        guardarComo.addActionListener(ae -> {
            doc = serv.guardarArchivoComo(editor);
        });

        JMenuItem imprimir = new JMenuItem("Imprimir");
        imprimir.addActionListener(ae -> {
            try {
                editor.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        });
        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener(ae -> System.exit(0));

        archivo.add(nuevo);
        archivo.add(abrir);
        archivo.add(cerrar);
        archivo.add(guardar);
        archivo.add(guardarComo);
        archivo.add(imprimir);
        archivo.add(salir);
        return archivo;
    }

    private JMenu menuEdition() {
        JMenu edition = new JMenu("Edición");
        JMenuItem deshacer = new JMenuItem("Deshacer");
        deshacer.addActionListener(ae -> {
            doUndo.deshacerTexto();

        });
        JMenuItem rehacer = new JMenuItem("Rehacer");
        rehacer.addActionListener(ae -> {
            doUndo.RehacerTexto();
        });
        JMenuItem copiar = new JMenuItem("Copiar");
        JMenuItem cortar = new JMenuItem("Cortar");
        JMenuItem pegar = new JMenuItem("Pegar");
        JMenuItem eliminar = new JMenuItem("Eliminar");

        edition.add(deshacer);
        edition.add(rehacer);
        edition.add(copiar);
        edition.add(cortar);
        edition.add(pegar);
        edition.add(eliminar);
        return edition;
    }

    private JMenu menuVistas() {
        JMenu vistas = new JMenu("Vista");
        JMenu herramientas = new JMenu("Barras de herramientas");
        JMenu apariencia = new JMenu("Apariencias");
        vistas.add(herramientas);
        vistas.add(apariencia);
        return vistas;
    }

    private JMenu menuHelp() {
        JMenu help = new JMenu("Help");
        JMenuItem sobreMg = new JMenuItem("Sobre MGCode");
        //sobreMg.addActionListener(ae -> JOptionPane.showMessageDialog(null, "MGG Code es un IDE desarrollado en clase de DI, buscando ser una aplicación simple para escribir y compilar tu código"));

        JMenuItem faq = new JMenuItem("Preguntas frecuentes");
        faq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop enlace = Desktop.getDesktop();
                try {
                    enlace.browse(new URI("https://www.jetbrains.com/help/idea/2021.2/getting-started.html"));
                } catch (IOException | URISyntaxException ex) {
                    ex.getMessage();
                }
            }
        });
        help.add(sobreMg);
        help.add(faq);
        return help;
    }

}