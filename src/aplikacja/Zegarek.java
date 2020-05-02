import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;

public class Zegarek extends JPanel implements ActionListener {

    PanelWyswietlaczy panelGodziny;
    PanelWyswietlaczy panelMinuty;
    PanelWyswietlaczy panelSekundy;

    Integer godzina, minuta, sekunda;

    Color kolorTla;

    private final int szerokosc = 800;
    private final int wysokosc = 300;

    public Zegarek(){
        setSize(szerokosc,wysokosc);
        setLayout(null);
        kolorTla = new Color(238,238,238);
        setBackground(kolorTla); // domyslny kolor

        przygotujZegarek();

        Timer timer = new Timer(1000, this); // co sekunde aktualizacja
        timer.start();

        repaint();
    }

    private void ustalCzas(){
         godzina = ZonedDateTime.now().getHour();
         minuta = ZonedDateTime.now().getMinute();
         sekunda = ZonedDateTime.now().getSecond();
    }

    private void ustawWyswietlacze(){
        Character znak0, znak1;

        // ustalenie godziny
        if(godzina<10){
            // jezeli godzina mniejsza niz 10, to na wyswietlaczu nr 0 ustaw 0
            panelGodziny.ustawNaWyswietlaczu(0,'0');
             znak1 = (godzina.toString()).charAt(0);
            panelGodziny.ustawNaWyswietlaczu(1,znak1);
        } else {
            String godz = String.valueOf(godzina);
            znak0 = godz.charAt(0);
            znak1 = godz.charAt(1);
            panelGodziny.ustawNaWyswietlaczu(0,znak0);
            panelGodziny.ustawNaWyswietlaczu(1,znak1);
        }

        if(minuta<10){
            // jezeli godzina mniejsza niz 10, to na wyswietlaczu nr 0 ustaw 0
            panelMinuty.ustawNaWyswietlaczu(0,'0');
            znak1 = (minuta.toString()).charAt(0);
            panelMinuty.ustawNaWyswietlaczu(1,znak1);
        } else {
            String godz = String.valueOf(minuta);
            znak0 = godz.charAt(0);
            znak1 = godz.charAt(1);
            panelMinuty.ustawNaWyswietlaczu(0,znak0);
            panelMinuty.ustawNaWyswietlaczu(1,znak1);
        }

        if(sekunda<10){
            // jezeli godzina mniejsza niz 10, to na wyswietlaczu nr 0 ustaw 0
            panelSekundy.ustawNaWyswietlaczu(0,'0');
            znak1 =  (sekunda.toString()).charAt(0);
            panelSekundy.ustawNaWyswietlaczu(1,znak1);
        } else {
            String godz = String.valueOf(sekunda);
            znak0 = godz.charAt(0);
            znak1 = godz.charAt(1);
            panelSekundy.ustawNaWyswietlaczu(0,znak0);
            panelSekundy.ustawNaWyswietlaczu(1,znak1);
        }

    }

    private void przygotujZegarek(){
        int szerokoscJednegoWyswietlacza = szerokosc/3;
        panelGodziny = new PanelWyswietlaczy();
        panelGodziny.setBounds(0,0,szerokoscJednegoWyswietlacza,wysokosc);
        panelGodziny.ustawIloscWyswietlaczy(0,0,szerokoscJednegoWyswietlacza,wysokosc);
        add(panelGodziny);

        panelMinuty = new PanelWyswietlaczy();
        panelMinuty.setBounds(szerokoscJednegoWyswietlacza,0,szerokoscJednegoWyswietlacza,wysokosc);
        panelMinuty.ustawIloscWyswietlaczy(szerokoscJednegoWyswietlacza,0,szerokoscJednegoWyswietlacza*2,wysokosc);
        add(panelMinuty);

        panelSekundy = new PanelWyswietlaczy();
        panelSekundy.setBounds(szerokoscJednegoWyswietlacza*2,0,szerokoscJednegoWyswietlacza,wysokosc);
        panelSekundy.ustawIloscWyswietlaczy(szerokoscJednegoWyswietlacza*2,0,szerokoscJednegoWyswietlacza*3,wysokosc);
        add(panelSekundy);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ustalCzas();
        ustawWyswietlacze();
        repaintWyswietlaczy();

        // narysowanie kropek miedzy wyswietlaczami
        int szer = szerokosc/3;
        g.setColor(Color.BLACK);
        g.fillRect(0,0,1000,100);


    }

    private void repaintWyswietlaczy(){
        panelGodziny.repaint();
        panelMinuty.repaint();
        panelSekundy.repaint();
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public Color getKolorTla() {
        return kolorTla;
    }

    public void setKolorTla(Color kolorTla) {
        this.kolorTla = kolorTla;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

    public PanelWyswietlaczy getPanelGodziny() {
        return panelGodziny;
    }

    public void setPanelGodziny(PanelWyswietlaczy panelGodziny) {
        this.panelGodziny = panelGodziny;
    }

    public PanelWyswietlaczy getPanelMinuty() {
        return panelMinuty;
    }

    public void setPanelMinuty(PanelWyswietlaczy panelMinuty) {
        this.panelMinuty = panelMinuty;
    }

    public PanelWyswietlaczy getPanelSekundy() {
        return panelSekundy;
    }

    public void setPanelSekundy(PanelWyswietlaczy panelSekundy) {
        this.panelSekundy = panelSekundy;
    }

    public Integer getGodzina() {
        return godzina;
    }

    public void setGodzina(Integer godzina) {
        this.godzina = godzina;
    }

    public Integer getMinuta() {
        return minuta;
    }

    public void setMinuta(Integer minuta) {
        this.minuta = minuta;
    }

    public Integer getSekunda() {
        return sekunda;
    }

    public void setSekunda(Integer sekunda) {
        this.sekunda = sekunda;
    }
}
