import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZegarekOkno extends JFrame  {

    public Integer szerokoscOkna = 790, wysokoscOkna = 265;
    Integer panelX = 10, panelY = 10;
    private UIManager uim;

    Zegarek zegarek;

    public ZegarekOkno() throws Exception {
        setSize(szerokoscOkna, wysokoscOkna);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(new Point(595, 201));
        setTitle("Zegarek");
        setLayout(null);
        uim.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        Color kolor = new Color(214,217,223);
        getContentPane().setBackground(new Color(238,238,238));
        setBackground(kolor);


        zegarek = new Zegarek();
        zegarek.setBounds(panelX,panelY,szerokoscOkna - panelX, wysokoscOkna - panelY);
        zegarek.setKolorTla(kolor);
        add(zegarek);
        repaint();
    }


}
