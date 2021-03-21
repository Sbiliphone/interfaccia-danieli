import com.Elements.Colata;
import com.Exeptions.PrototypeSystemAvoidance;
import com.Managers.ColataManager;
import com.Prototypes.ColataPrototype;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiCreazioneColata extends JFrame {
    public GuiCreazioneColata() {
        super("Colate");
        this.setLocation(200, 200);
        this.setSize(700, 200);
        JPanel panel = new JPanel();
        JTextField oraInizio = new JTextField("Ora Inizio Colata");
        JTextField id = new JTextField("ID (Facoltativo)");
        String[] acciai = new String[]{"Inox", "Altro", "Altro", "Altro"};
        JComboBox listaAcciai = new JComboBox(acciai);
        JTextField peso = new JTextField("Peso");
        JButton aggiungi = new JButton("Aggiungi");
        aggiungi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                nascondi();
                aggiungiColata();
            }
        });
        panel.add(oraInizio);
        panel.add(id);
        panel.add(listaAcciai);
        panel.add(peso);
        panel.add(aggiungi);
        this.add(panel);
        this.setResizable(true);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void aggiungiColata(){

    }

    public void nascondi(){
        this.setVisible(false);
        /*
        Aggiunta dei valori alla colata
         */
        new GuiColata();
    }
}
