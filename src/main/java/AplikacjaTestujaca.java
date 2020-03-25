import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AplikacjaTestujaca extends JFrame implements ActionListener {

    private Wyswietlacz7seg wyswietlacz1, wyswietlacz2,wyswietlacz3, wyswietlacz4;
    private JComboBox wybierzWyswietlaczComboBox, wybierzLiczbeComboBox;
    private JLabel wybierzLiczbeLabel, wybierzWyswietlaczLabel, wpiszLiczbeLabel, podajLiczbeErrorLabel;
    private JTextField podajLiczbeTextField;
    private UIManager uim;

    // zmienne do integracji i wyswietlania poprawnie wartosci z TextFielda i ComboBoxa
    private boolean pokazujLiczbeZComboBoxa;
    private String ostatniaWartoscZTextFielda;

    public AplikacjaTestujaca() throws Exception{
        setSize(600,400);
        uim.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        setLayout(null);
        setTitle("Aplikacja Testujaca Wyswietlacz 7seg");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(new Point(595, 201));
        setResizable(false);
        pokazujLiczbeZComboBoxa = true;
        ostatniaWartoscZTextFielda = "";

        wyswietlacz1 = new Wyswietlacz7seg();
        wyswietlacz1.setBounds(480,60,100,200);
        add(wyswietlacz1);

        wyswietlacz2 = new Wyswietlacz7seg();
        wyswietlacz2.setBounds(360,60,100,200);
        add(wyswietlacz2);

        wyswietlacz3 = new Wyswietlacz7seg();
        wyswietlacz3.setBounds(240,60,100,200);
        add(wyswietlacz3);

        wyswietlacz4 = new Wyswietlacz7seg();
        wyswietlacz4.setBounds(120,60,100,200);
        add(wyswietlacz4);

        wybierzLiczbeLabel = new JLabel("Wybierz liczbe");
        wybierzLiczbeLabel.setBounds(135,10,100,15);
        add(wybierzLiczbeLabel);

        wybierzWyswietlaczLabel = new JLabel("Wybierz wyswietlacz");
        wybierzWyswietlaczLabel.setBounds(10,10,150,15);
        add(wybierzWyswietlaczLabel);

        wybierzWyswietlaczComboBox = new JComboBox();
        wybierzWyswietlaczComboBox.setBounds(10,30,100,20);
        wybierzWyswietlaczComboBox.addItem("1");
        wybierzWyswietlaczComboBox.addItem("2");
        wybierzWyswietlaczComboBox.addItem("3");
        wybierzWyswietlaczComboBox.addItem("4");
        wybierzWyswietlaczComboBox.addActionListener(this);
        add(wybierzWyswietlaczComboBox);


        wybierzLiczbeComboBox = new JComboBox();
        wybierzLiczbeComboBox.setBounds(130,30,100,20);
        wybierzLiczbeComboBox.addItem("0");
        wybierzLiczbeComboBox.addItem("1");
        wybierzLiczbeComboBox.addItem("2");
        wybierzLiczbeComboBox.addItem("3");
        wybierzLiczbeComboBox.addItem("4");
        wybierzLiczbeComboBox.addItem("5");
        wybierzLiczbeComboBox.addItem("6");
        wybierzLiczbeComboBox.addItem("7");
        wybierzLiczbeComboBox.addItem("8");
        wybierzLiczbeComboBox.addItem("9");
        wybierzLiczbeComboBox.addItem("A");
        wybierzLiczbeComboBox.addItem("B");
        wybierzLiczbeComboBox.addItem("C");
        wybierzLiczbeComboBox.addItem("D");
        wybierzLiczbeComboBox.addItem("E");
        wybierzLiczbeComboBox.addItem("F");
        wybierzLiczbeComboBox.addActionListener(this);
        add(wybierzLiczbeComboBox);

        wpiszLiczbeLabel = new JLabel("Wpisz liczbe");
        wpiszLiczbeLabel.setBounds(10,80,100,15);
        add(wpiszLiczbeLabel);

        podajLiczbeTextField = new JTextField("");
        podajLiczbeTextField.setBounds(10,100,100,28);
        podajLiczbeTextField.setHorizontalAlignment(JTextField.CENTER);
        podajLiczbeTextField.addActionListener(this);
        add(podajLiczbeTextField);

        podajLiczbeErrorLabel = new JLabel("");
        podajLiczbeErrorLabel.setForeground(Color.RED);
        podajLiczbeErrorLabel.setFont(new Font("Arial", Font.BOLD, 15));
        podajLiczbeErrorLabel.setBounds(120,225,300,100);
        add(podajLiczbeErrorLabel);


        setVisible(true);

        Timer timer = new Timer(20, this); // co delay milisekund pojawia sie zdarzenie
        timer.start();
        
    }

    public void repaint(){
        wyswietlacz1.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();


        // jesli ustawiono wartosc na ComboBoxie
        if(source == wybierzLiczbeComboBox){

            // ustaw zmienne pomocnicze
            pokazujLiczbeZComboBoxa = true;
            ostatniaWartoscZTextFielda = podajLiczbeTextField.getText();

            // sprawdza na jakim wyswietlaczu ma zmienic liczbe
            int nrWyswietlacza = Integer.valueOf((String) wybierzWyswietlaczComboBox.getSelectedItem());

            // zmienia liczbe na tą, podaną w ComboBoxie
            ustawLiczbeNaWybranymWyswietlaczuZComboBoxa(nrWyswietlacza);

            // wylacza komunikat o bledzie
            podajLiczbeErrorLabel.setText("");

        }// wybierzLiczbeComboBox

        // jesli wartosc w TextFieldzie zostala zmieniona
        if(!ostatniaWartoscZTextFielda.equals(podajLiczbeTextField.getText()) && pokazujLiczbeZComboBoxa == true){
            pokazujLiczbeZComboBoxa = false;
        }

        // pokaz liczbe z TextFielda
        if(pokazujLiczbeZComboBoxa == false){
            wyswietlajLiczbeZTextField();
        }



    }// actionPerformed

    // funkcja wyswietlajaca
    private void wyswietlajLiczbeZTextField(){
        // przygotowanie zmiennych
        String podanaLiczba = podajLiczbeTextField.getText();
        Pattern pattern = Pattern.compile("([a-fA-F0-9]){1,4}");



        // jeśli liczba nie ma od 1 do 4 znaków - wyświetl komunikat o błędzie
        if(podanaLiczba.length() > 4){
            podajLiczbeErrorLabel.setText("Liczba musi miec do 4 znakow!");
        }

        else {
            // sprawdzenie, czy podano cyfry, litery od a do f
            Matcher matcher = pattern.matcher(podanaLiczba);

            // jesli liczba pasuje do wyrazenia regularnego - jest poprawana - wyświetl ją
            if(matcher.matches()){
                wypiszLiczbeNaWyswietlaczach(podanaLiczba);

                // nie wyswietlaj bledu
                podajLiczbeErrorLabel.setText("");
            }
            // jeśli liczba nie pasuje do wyrażenia regularnego
            else {
                // jesli puste pole (dlatego liczba nie pasuje do wyrażenia) - zgaś wszystkie wyświetlacze
                if(podanaLiczba.length() == 0) {
                    wyswietlacz1.wylaczWyswietlacz();
                    wyswietlacz2.wylaczWyswietlacz();
                    wyswietlacz3.wylaczWyswietlacz();
                    wyswietlacz4.wylaczWyswietlacz();
                    podajLiczbeErrorLabel.setText("");
                }
                // jeśli pole nie jest puste, a liczba nie pasuje do wyrażenia - wyswietl komunikat o błedzie
                else
                    podajLiczbeErrorLabel.setText("Liczba jest niepoprawna!");

            }
        }


    }

    private void wypiszLiczbeNaWyswietlaczach(String podanaLiczba){

        // zgaś wyświetlacze
        wyswietlacz1.wylaczWyswietlacz();
        wyswietlacz2.wylaczWyswietlacz();
        wyswietlacz3.wylaczWyswietlacz();
        wyswietlacz4.wylaczWyswietlacz();

        int dlugoscLiczby = podanaLiczba.length();

        switch (dlugoscLiczby){
            case 0:
                break;
            case 1 :
                wyswietlacz1.ustaw(podanaLiczba.charAt(0));
                break;
            case 2:
                wyswietlacz2.ustaw(podanaLiczba.charAt(0));
                wyswietlacz1.ustaw(podanaLiczba.charAt(1));

                break;
            case 3:
                wyswietlacz3.ustaw(podanaLiczba.charAt(0));
                wyswietlacz2.ustaw(podanaLiczba.charAt(1));
                wyswietlacz1.ustaw(podanaLiczba.charAt(2));
                break;
            case 4:
                wyswietlacz4.ustaw(podanaLiczba.charAt(0));
                wyswietlacz3.ustaw(podanaLiczba.charAt(1));
                wyswietlacz2.ustaw(podanaLiczba.charAt(2));
                wyswietlacz1.ustaw(podanaLiczba.charAt(3));
                break;

            default:
                System.out.println("wypiszLiczbeNaWyswietlaczach: default?!?!?");
                podajLiczbeErrorLabel.setText("wypiszLiczbeNaWyswietlaczach: default?!?!?");
                break;
        }
    }


    private void ustawLiczbeNaWybranymWyswietlaczuZComboBoxa(int nrWyswietlacza){
        String wybranyZnak = (String) wybierzLiczbeComboBox.getSelectedItem();

        if(nrWyswietlacza == 1)
            wyswietlacz1.ustaw(wybranyZnak.charAt(0));
        else if (nrWyswietlacza == 2)
            wyswietlacz2.ustaw(wybranyZnak.charAt(0));
        else if (nrWyswietlacza == 3)
            wyswietlacz3.ustaw(wybranyZnak.charAt(0));
        else if (nrWyswietlacza == 4)
            wyswietlacz4.ustaw(wybranyZnak.charAt(0));
    }

    public void licznik(int opoznienie) throws InterruptedException {
        wyswietlacz1.ustaw1();
        TimeUnit.MILLISECONDS.sleep(opoznienie);

        wyswietlacz1.ustaw2();
        TimeUnit.MILLISECONDS.sleep(opoznienie);

        wyswietlacz1.ustaw3();
        TimeUnit.MILLISECONDS.sleep(opoznienie);

        wyswietlacz1.ustaw4();
        TimeUnit.MILLISECONDS.sleep(opoznienie);

        wyswietlacz1.ustaw5();
        TimeUnit.MILLISECONDS.sleep(opoznienie);

        wyswietlacz1.ustaw6();
        TimeUnit.MILLISECONDS.sleep(opoznienie);

        wyswietlacz1.ustaw7();
        TimeUnit.MILLISECONDS.sleep(opoznienie);

        wyswietlacz1.ustaw8();
        TimeUnit.MILLISECONDS.sleep(opoznienie);

        wyswietlacz1.ustaw9();
        TimeUnit.MILLISECONDS.sleep(opoznienie);
    }
}
