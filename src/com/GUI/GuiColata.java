import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GuiColata extends JFrame {
    boolean spunta = true;
    ListaColate listaColate = new ListaColate(true);
    public GuiColata() {
        super("Colate");
        this.setLayout(new GridBagLayout());
        this.setLocation(200, 200);
        this.setSize(900, 300);
        JPanel panel1 = new JPanel();
        panel1.add(listaColate.getTable());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        JLabel mostraColate = new JLabel();
        mostraColate.setText("Mostra tutte le colate");
        JCheckBox checkBox = new JCheckBox("Mostra tutte le colate");
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cambiaLista(spunta);
                if(spunta == false){
                    spunta = true;
                }else{
                    spunta = false;
                }
            }
        });
        JButton creaColata = new JButton("Crea Colata");
        creaColata.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                nascondi();
                new GuiCreazioneColata();
            }
        });
        panel1.add(checkBox);
        panel1.add(creaColata);
        this.add(panel1);
    }

    public void nascondi(){
        this.setVisible(false);
    }

    public void cambiaLista(boolean vista){
        if(vista){
            listaColate = new ListaColate(false);
        } else{
            listaColate = new ListaColate(true);
        }
    }
}
