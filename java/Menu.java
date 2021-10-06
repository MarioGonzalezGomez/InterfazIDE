import javax.swing.*;

public class Menu {
    private JMenuBar tools;

    public Menu() {
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
        JMenuItem cerrar = new JMenuItem("Cerrar");
        JMenuItem guardar = new JMenuItem("Guardar");
        JMenuItem guardarComo = new JMenuItem("Guardar como...");
        JMenuItem imprimir = new JMenuItem("Imprimir");
        JMenuItem salir = new JMenuItem("Salir");

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
        JMenuItem copiar = new JMenuItem("Copiar");
        JMenuItem pegar = new JMenuItem("Pegar");
        JMenuItem cortar = new JMenuItem("Cortar");
        edition.add(copiar);
        edition.add(pegar);
        edition.add(cortar);
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
        JMenuItem faq = new JMenuItem("Preguntas frecuentes");
        help.add(sobreMg);
        help.add(faq);
        return help;
    }
}
