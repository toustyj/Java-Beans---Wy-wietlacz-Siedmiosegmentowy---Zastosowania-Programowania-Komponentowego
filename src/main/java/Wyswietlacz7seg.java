import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Wyswietlacz7seg extends JPanel implements Serializable{

    private MechanizmWyswietlacza7seg mechanizmWyswietlacza;

    public Wyswietlacz7seg(){
        mechanizmWyswietlacza = new MechanizmWyswietlacza7seg();
        przygotowanieWyswietlacza();
    }

    // stworzenie wyswietlacza z ustaleniem stanu
    public Wyswietlacz7seg(boolean[] stan){
        mechanizmWyswietlacza = new MechanizmWyswietlacza7seg(stan);
        przygotowanieWyswietlacza();
    }

    // stworzenie wyswietlacza z ustaleniem stanu
    public Wyswietlacz7seg(char stan){
        mechanizmWyswietlacza = new MechanizmWyswietlacza7seg(stan);
        przygotowanieWyswietlacza();
    }

    private void przygotowanieWyswietlacza(){
        setSize(100,200);
    }

    public void wylaczWyswietlacz(){
        mechanizmWyswietlacza.wyzerujStany();
        repaint();
    }

    public void ustaw(char stan){
        mechanizmWyswietlacza.ustawStan(stan);
        repaint();
    }

    public void ustaw1(){
        mechanizmWyswietlacza.ustaw1();
        repaint();
    }
    public void ustaw2(){
        mechanizmWyswietlacza.ustaw2();
        repaint();
    }
    public void ustaw3(){
        mechanizmWyswietlacza.ustaw3();
        repaint();
    }
    public void ustaw4(){
        mechanizmWyswietlacza.ustaw4();
        repaint();
    }
    public void ustaw5(){
        mechanizmWyswietlacza.ustaw5();
        repaint();
    }
    public void ustaw6(){
        mechanizmWyswietlacza.ustaw6();
        repaint();
    }
    public void ustaw7(){
        mechanizmWyswietlacza.ustaw7();
        repaint();
    }
    public void ustaw8(){
        mechanizmWyswietlacza.ustaw8();
        repaint();
    }
    public void ustaw9(){
        mechanizmWyswietlacza.ustaw9();
        repaint();
    }
    public void ustawA(){
        mechanizmWyswietlacza.ustawA();
        repaint();
    }
    public void ustawB(){
        mechanizmWyswietlacza.ustawB();
        repaint();
    }
    public void ustawC(){
        mechanizmWyswietlacza.ustawC();
        repaint();
    }
    public void ustawD(){
        mechanizmWyswietlacza.ustawD();
        repaint();
    }
    public void ustawE(){
        mechanizmWyswietlacza.ustawE();
        repaint();
    }
    public void ustawF(){
        mechanizmWyswietlacza.ustawF();
        repaint();
    }

    // narysowanie wyswietlacza, należy wywołać po każdej zmianie stanu
    public void paintComponent(Graphics g){

        // NOTE: super.paintControler(g) w momencie serializacji wyrzuca wyjątek oraz wypisuje
        // na konsoli stacktrace. Nie ma to wpływu na działanie samej serializacji - przebiega ona
        // pomyślnie, możliwe jest deserializowanie obiektu. Przechwycenie wyjątku zostało stworzone
        // w celu nie wypisywania na konsoli zbędnego stosu.
        try {
            super.paintComponent(g);
            g.setColor(Color.RED);


        // segment a
        if(mechanizmWyswietlacza.getStan()[0] == true) {
            g.fillRect(0, 0, 100, 8);
        }
        // segment b
        if(mechanizmWyswietlacza.getStan()[1] == true){
            g.fillRect(92,0,8,100);
        }
        // segment c
        if(mechanizmWyswietlacza.getStan()[2] == true){
            g.fillRect(92,100,8,100);
        }
        // segment d
        if(mechanizmWyswietlacza.getStan()[3] == true) {
            g.fillRect(0, 192, 100, 8);
        }
        // segment e
        if(mechanizmWyswietlacza.getStan()[4] == true) {
            g.fillRect(0, 100, 8, 100);
        }
        // segment f
        if(mechanizmWyswietlacza.getStan()[5] == true) {
            g.fillRect(0, 0, 8, 100);
        }
        // segment g
        if(mechanizmWyswietlacza.getStan()[6] == true) {
            g.fillRect(0, 100, 100, 8);
        }

        } catch (NullPointerException ex){
            //System.out.println("paintComponent: ... serializacja ... ");
        }
    }

    public MechanizmWyswietlacza7seg getMechanizmWyswietlacza() {
        return mechanizmWyswietlacza;
    }

    public void setMechanizmWyswietlacza(MechanizmWyswietlacza7seg mechanizmWyswietlacza) {
        this.mechanizmWyswietlacza = mechanizmWyswietlacza;
    }

    public void serializuj(String nazwaPliku){
        ObjectOutputStream outputStream = null;
        // przygotowanie sciezki do folderu z plikami
        String sciezka = pobierzLokalizacjeProjektu() +"/serializacja/" + nazwaPliku;
        try {
            // zapisanie obiektu do pliku
            outputStream = new ObjectOutputStream(new FileOutputStream(sciezka + ".dat"));
            outputStream.writeObject(this);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void deserializuj(String nazwaPliku){
        Wyswietlacz7seg pobranyWyswietlacz = null;
        // przygotowanie sciezki pliku
        String sciezka = pobierzLokalizacjeProjektu() +"/serializacja/" + nazwaPliku + ".dat";
        try {
            // otworzenie strumieni
            FileInputStream fileIn = new FileInputStream(sciezka);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            try {
                // odczytanie obiektu
                pobranyWyswietlacz = (Wyswietlacz7seg) in.readObject();
            } catch (ClassNotFoundException ex) {
                System.out.println("deserializuj: ClassNotFoundException");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // ustawienie stanu mechanizmu wyswietlacza
        this.setMechanizmWyswietlacza(pobranyWyswietlacz.getMechanizmWyswietlacza());
        // narysowanie wyswietlacza na podstawie pobranego stanu
        repaint();
    }

    private String pobierzLokalizacjeProjektu() {
        return System.getProperty("user.dir");
    }
}
