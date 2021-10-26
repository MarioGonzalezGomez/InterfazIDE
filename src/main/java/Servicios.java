import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

@Data
@NoArgsConstructor
public class Servicios {
    public Documento abrirArchivo(JTextArea editor) throws IOException {
        JFileChooser selector = new JFileChooser();
        selector.setCurrentDirectory(new File(System.getProperty("user.home")));
        selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selector.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
        selector.setAcceptAllFileFilterUsed(false);

        int option = selector.showOpenDialog(editor);
        File archivo = selector.getSelectedFile();


        if (option == JFileChooser.APPROVE_OPTION) {

            Documento doc = new Documento();
            doc.setRuta(archivo);
            doc.setTitulo(archivo.getName());

            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(archivo)));
            String line = br.readLine();
            String texto = "";
            while (line != null) {
                texto = texto + line + "\n";
                line = br.readLine();
            }
            doc.setContenido(texto);
            editor.setText(doc.getContenido());
            return doc;
        } else {
            JOptionPane.showMessageDialog(null, "Archivo no valido", "Información", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void guardar(Documento doc, JTextArea editor) {
        String text;
        if (editor.getText() != null) {
            text = editor.getText();
            doc.setContenido(text);
        } else {
            text = "";
        }
        File archivo = doc.getRuta();
        try (FileWriter escritor = new FileWriter(archivo)) {
            escritor.write(text);
            JOptionPane.showMessageDialog(null, "Archivo guardado", "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }

    public Documento guardarArchivoComo(JTextArea editor) {
        Documento documento = new Documento();
        String text;
        if (editor.getText() != null) {
            text = editor.getText();
            documento.setContenido(text);
        } else {
            text = "";
        }

        JFileChooser selector = new JFileChooser();
        selector.setCurrentDirectory(new File(System.getProperty("user.home")));
        selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selector.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
        selector.setAcceptAllFileFilterUsed(false);

        int option = selector.showSaveDialog(editor);
        File archivo = selector.getSelectedFile();


        if (option == JFileChooser.APPROVE_OPTION) {
            if (!archivo.getName().endsWith(".txt")) {
                archivo = new File(archivo.getPath() + ".txt");

            }
            if (!archivo.exists()) {
                try (FileWriter escritor = new FileWriter(archivo)) {
                    escritor.write(text);
                    JOptionPane.showMessageDialog(null, "Archivo guardado", "Información", JOptionPane.INFORMATION_MESSAGE);
                    documento.setTitulo(archivo.getName());
                    documento.setRuta(archivo);
                    //Deberia poder setearse si es de tipo java o python, aunque quizá sea mejor en otro lado
                    //documento.setType("txt");
                } catch (IOException ex) {
                    System.out.println("Error " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "El archivo " + archivo.getName()
                        + " ya existe, seleccione otro nombre para el archivo", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return documento;


    }
}
