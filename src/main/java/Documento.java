import lombok.Data;

import java.io.File;

@Data
public class Documento {
    String titulo;
    File ruta;
    String type;
    String contenido;

    public Documento() {

    }

    public Documento(String titulo, File ruta, String type, String contenido) {
        this.titulo = titulo;
        this.ruta = ruta;
        this.type = type;
        this.contenido = contenido;
    }

}
