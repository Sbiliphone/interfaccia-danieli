import com.Elements.Colata;
import com.Exeptions.PrototypeSystemAvoidance;
import com.Managers.ColataManager;

import java.awt.Frame;
import javax.swing.*;

public class ListaColate extends JFrame {
    JTable table = new JTable();
    Colata[] colate = new Colata();

    public ListaColate(boolean mostra) {
        String[] columns = new String[]{"Eseguita", "Orario", "Colata", "Tipo Acciaio", "Peso", ""};
        JButton info = new JButton();
        info.setText("Info");
        this.table = new JTable((Object[][]) colate, columns);
        this.add(new JScrollPane(this.table));
        this.pack();
    }

    public JTable getTable() {
        return this.table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
