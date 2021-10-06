import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Servicios {
    Vista vista = new Vista();

    private void guardarArchivoComo() {
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selector.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));

        int opcion = selector.showSaveDialog(vista);
        File archivo = selector.getSelectedFile();

        try (FileWriter escritor = new FileWriter(archivo)) {
            if (opcion == JFileChooser.APPROVE_OPTION) {
                if (!archivo.getName().endsWith(".txt")) {
                    File archivoTxt = new File(archivo.getPath() + ".txt");
                }
                if (archivo != null) {
                    if (!archivo.exists()) {
                        //Habría que implementar que si ya existe, lo sobrescriba
                    }
                    //Esto debería modificarse para que el write escriba el texto
                    // que se se quiera del programa: Lo que aparezca en la textArea principal, por ejemplo
                    escritor.write("Hola");
                }
            }

        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }
}
