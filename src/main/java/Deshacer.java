import lombok.Data;

import javax.swing.*;
import javax.swing.undo.*;

@Data
public class Deshacer extends JFrame {
    private final UndoManager deshacer;

    public Deshacer() {
        deshacer = new UndoManager();
        deshacer.setLimit(20);
    }

    public void deshacerTexto() {
        if (deshacer.canUndo())
            deshacer.undo();
    }

    public void RehacerTexto() {
        if (deshacer.canRedo())
            deshacer.redo();
    }

}
