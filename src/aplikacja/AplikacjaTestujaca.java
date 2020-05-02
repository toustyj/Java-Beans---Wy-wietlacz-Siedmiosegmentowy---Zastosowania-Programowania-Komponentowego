

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AplikacjaTestujaca extends JFrame implements ActionListener, KeyListener {

    private PanelWyswietlaczy panelWyswietlaczy;
    private JComboBox wybierzWyswietlaczComboBox, wybierzLiczbeComboBox, kolorComboBox, migotanieComboBox;
    private JLabel wybierzLiczbeLabel, wybierzWyswietlaczLabel, wpiszLiczbeLabel, podajLiczbeErrorLabel;
    private JLabel liczbaWyswietlaczyLabel, liczbaWolnychWyswietlaczyLabel, migotanieLabel;
    private JTextField podajLiczbeTextField;
    private UIManager uim;
    private JCheckBox czyWszystkieCheckBox;
    private JButton kolorSegmentuButton, kolorTlaButton, kolorZgaszonegoButton, zegarekButton, kolorWygaszonegoPrzezroczystyButton;
    private JRadioButton hexRadioButton, decRadioButton, octRadioButton, binRadioButton;
    private ButtonGroup systemLiczbowyGroup;

    private Integer aktualnySystemLiczbowy; // 16 - HEX, 10 - DEC, 8 OCT, 2 - BIN

    ZegarekOkno zegarekOkno;

    Integer panelX, panelY, panelWidth, panelHeight;

    // zmienne do integracji i wyswietlania poprawnie wartosci z TextFielda i ComboBoxa
    private boolean pokazujLiczbeZComboBoxa;
    private String ostatniaWartoscZTextFielda;

    private int czestotliwoscMigotania, etapMigotania;
    private boolean czyPalic;

    public AplikacjaTestujaca() throws Exception{
        setSize(600,340);
        uim.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        setLayout(null);
        setTitle("Aplikacja Testujaca Wyswietlacz 7seg");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(new Point(595, 201));
        pokazujLiczbeZComboBoxa = true;
        ostatniaWartoscZTextFielda = "";

        panelX = 120;
        panelY = 80;
        panelWidth = 460;
        panelHeight = 120;

        panelWyswietlaczy = new PanelWyswietlaczy();
        panelWyswietlaczy.setBounds(panelX,panelY,panelWidth,panelHeight);
        add(panelWyswietlaczy);

        wybierzLiczbeLabel = new JLabel("Wybierz liczbe");
        wybierzLiczbeLabel.setBounds(135,10,100,15);
        add(wybierzLiczbeLabel);

        wybierzWyswietlaczLabel = new JLabel("Wybierz wyswietlacz");
        wybierzWyswietlaczLabel.setBounds(10,10,150,15);
        add(wybierzWyswietlaczLabel);

        wybierzWyswietlaczComboBox = new JComboBox();
        wybierzWyswietlaczComboBox.setBounds(10,30,100,20);
        wybierzWyswietlaczComboBox.addItem(1);
        wybierzWyswietlaczComboBox.addItem(2);
        wybierzWyswietlaczComboBox.addItem(3);
        wybierzWyswietlaczComboBox.addItem(4);
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

        kolorComboBox = new JComboBox();
        kolorComboBox.setBounds(5,147,115,20);
        kolorComboBox.addItem("Czerwony");
        kolorComboBox.addItem("Niebieski");
        kolorComboBox.addItem("Zielony");
        kolorComboBox.addItem("Czarny");
        kolorComboBox.addItem("Zolty");
        kolorComboBox.addItem("Rozowy");
        kolorComboBox.addItem("Szary");
        kolorComboBox.addItem("Bialy");
        add(kolorComboBox);

        wpiszLiczbeLabel = new JLabel("Wpisz liczbe");
        wpiszLiczbeLabel.setBounds(10,80,100,15);
        add(wpiszLiczbeLabel);

        podajLiczbeTextField = new JTextField("");
        podajLiczbeTextField.setBounds(10,100,100,28);
        podajLiczbeTextField.setHorizontalAlignment(JTextField.CENTER);
        podajLiczbeTextField.addActionListener(this);
        add(podajLiczbeTextField);

        // ograniczenie wpisywanych klawiszy
        podajLiczbeTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(aktualnySystemLiczbowy == 16){
                    if (!((c >= '0') && (c <= '9') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE) ||
                            (czyLiterAdoF(c)))) {
                        getToolkit().beep();
                        e.consume();
                    }
                }
                else if(aktualnySystemLiczbowy == 10) {
                    if (!((c >= '0') && (c <= '9') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE))) {
                        getToolkit().beep();
                        e.consume();
                    }
                }
                else if(aktualnySystemLiczbowy == 8){
                    if (!((c >= '0') && (c <= '7') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE))) {
                        getToolkit().beep();
                        e.consume();
                    }
                }
                else if(aktualnySystemLiczbowy == 2){
                    if (!((c >= '0') && (c <= '1') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE))) {
                        getToolkit().beep();
                        e.consume();
                    }
                }
            }
        });

        podajLiczbeErrorLabel = new JLabel("");
        podajLiczbeErrorLabel.setForeground(Color.RED);
        podajLiczbeErrorLabel.setFont(new Font("Arial", Font.BOLD, 11));
        podajLiczbeErrorLabel.setBounds(10,85,300,100);
        add(podajLiczbeErrorLabel);

        liczbaWyswietlaczyLabel = new JLabel("Liczba wyswietlaczy: ");
        liczbaWyswietlaczyLabel.setForeground(Color.BLACK);
        liczbaWyswietlaczyLabel.setBounds(250,5,150,20);
        add(liczbaWyswietlaczyLabel);

        liczbaWolnychWyswietlaczyLabel = new JLabel("Wolnych wyswietlaczy: ");
        liczbaWolnychWyswietlaczyLabel.setFont(new Font("Arial", Font.BOLD, 11));
        liczbaWolnychWyswietlaczyLabel.setBounds(10,54,200,18);
        add(liczbaWolnychWyswietlaczyLabel);

        kolorSegmentuButton = new JButton("Kolor segmentu");
        kolorSegmentuButton.setFont(new Font("Arial", Font.PLAIN, 10));
        kolorSegmentuButton.setBounds(5,190,110,18);
        kolorSegmentuButton.addActionListener(this);
        add(kolorSegmentuButton);

        kolorTlaButton = new  JButton("Kolor tla");
        kolorTlaButton.setFont(new Font("Arial", Font.PLAIN, 10));
        kolorTlaButton.setBounds(5,210,110,18);
        kolorTlaButton.addActionListener(this);
        add(kolorTlaButton);

        czyWszystkieCheckBox = new JCheckBox("Czy wszystkie");
        czyWszystkieCheckBox.setBounds(10,170,100,20);
        add(czyWszystkieCheckBox);

        zegarekButton = new JButton("Zegarek");
        zegarekButton.setFont(new Font("Arial", Font.PLAIN, 11));
        zegarekButton.setBounds(380,5,100,18);
        zegarekButton.addActionListener(this);
        add(zegarekButton);

        kolorZgaszonegoButton = new JButton("Kolor zgaszonego");
        kolorZgaszonegoButton.setFont(new Font("Arial", Font.PLAIN, 9));
        kolorZgaszonegoButton.setBounds(5,230,110,18);
        kolorZgaszonegoButton.addActionListener(this);
        add(kolorZgaszonegoButton);

        kolorWygaszonegoPrzezroczystyButton = new JButton("Zgas wylaczone");
        kolorWygaszonegoPrzezroczystyButton.setFont(new Font("Arial", Font.PLAIN, 9));
        kolorWygaszonegoPrzezroczystyButton.setBounds(5,250,110,18);
        kolorWygaszonegoPrzezroczystyButton.addActionListener(this);
        add(kolorWygaszonegoPrzezroczystyButton);

        hexRadioButton = new JRadioButton("HEX");
        hexRadioButton.setBounds(235,29,50,20);
        hexRadioButton.addActionListener(this);
        hexRadioButton.setSelected(true);
        add(hexRadioButton);

        decRadioButton = new JRadioButton("DEC");
        decRadioButton.setBounds(300,29,50,20);
        decRadioButton.addActionListener(this);
        add(decRadioButton);

        octRadioButton = new JRadioButton("OCT");
        octRadioButton.setBounds(365,29,50,20);
        octRadioButton.addActionListener(this);
        add(octRadioButton);

        binRadioButton = new JRadioButton("BIN");
        binRadioButton.setBounds(430,29,50,20);
        binRadioButton.addActionListener(this);
        add(binRadioButton);

        systemLiczbowyGroup = new ButtonGroup();
        systemLiczbowyGroup.add(hexRadioButton);
        systemLiczbowyGroup.add(decRadioButton);
        systemLiczbowyGroup.add(octRadioButton);
        systemLiczbowyGroup.add(binRadioButton);

        migotanieLabel = new JLabel("Czestotliwosc migotania: ");
        migotanieLabel.setFont(new Font("Arial", Font.BOLD, 11));
        migotanieLabel.setBounds(235,54,150,20);
        add(migotanieLabel);

        migotanieComboBox = new JComboBox();
        migotanieComboBox.setBounds(380,54,80,20);
        migotanieComboBox.addItem("Brak");
        migotanieComboBox.addItem("1 na sek");
        migotanieComboBox.addItem("2 na sek");
        migotanieComboBox.addItem("4 na sek");
        migotanieComboBox.addItem("10 na sek");
        migotanieComboBox.addItem("20 na sek");
        migotanieComboBox.addActionListener(this);
        add(migotanieComboBox);

        aktualnySystemLiczbowy = 16;
        czestotliwoscMigotania = 0;
        etapMigotania = 0;
        czyPalic = true;

        setVisible(true);

        Timer timer = new Timer(5, this); // co delay milisekund pojawia sie zdarzenie
        timer.start();


    }

    public void repaint(){
        panelWyswietlaczy.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // kontrola podawania dobrych znakow
        if(source == podajLiczbeTextField){

        }

        // jesli ustawiono wartosc na ComboBoxie
        if(source == wybierzLiczbeComboBox){

            // ustaw zmienne pomocnicze
            pokazujLiczbeZComboBoxa = true;
            ostatniaWartoscZTextFielda = podajLiczbeTextField.getText();

            //zmienne pomocnicze
            String nrWyswietlacza = "";
            Integer nrWyswietlaczaInt = 0;
            Boolean czyInt = true;


            // sprawdza na jakim wyswietlaczu ma zmienic liczbe
            Integer ktoryWyswietlacz = pobierzKtoryWyswietlacz();


            // pobierz jaki znak ustawic
            String liczba = ((String) wybierzLiczbeComboBox.getSelectedItem());
            char liczbaJakoZnak = liczba.charAt(0);

            // zmienia liczbe na tą, podaną w ComboBoxie
             panelWyswietlaczy.ustawNaWyswietlaczu(ktoryWyswietlacz, liczbaJakoZnak);

            // wylacza komunikat o bledzie
            podajLiczbeErrorLabel.setText("");

        }// wybierzLiczbeComboBox

        // wyswietlanie z comboboxa + migotanie
        if(pokazujLiczbeZComboBoxa){
            if(czyPalic){
                if(!panelWyswietlaczy.getAktualnyNapis().equals("")) {
                    panelWyswietlaczy.wypiszLiczbeNaWyswietlaczach(panelWyswietlaczy.getAktualnyNapis());
                }
            }
            else {
                panelWyswietlaczy.zgasWyswietlacze();
            }
        }

        // jesli wartosc w TextFieldzie zostala zmieniona
        if(!ostatniaWartoscZTextFielda.equals(podajLiczbeTextField.getText()) && pokazujLiczbeZComboBoxa == true){
            pokazujLiczbeZComboBoxa = false;
        }

        // pokaz liczbe z TextFielda
        if(pokazujLiczbeZComboBoxa == false){
            wyswietlajLiczbeZTextField();
        }

        // ustawienie wyswietlaczy panelu
        panelWyswietlaczy.ustawIloscWyswietlaczy(panelX,panelY,getWidth(), getHeight());

        // ustalanie koloru segmentu
        if(source == kolorSegmentuButton){

            // pobranie zmiennych
            String kolor = ((String) kolorComboBox.getSelectedItem());

            // ustaw kolor wszystkim wyswietlaczom
            if(czyWszystkieCheckBox.isSelected())
                panelWyswietlaczy.ustalKolorSegmentuWszystkimWyswietlaczom(kolor);
            // ustaw kolor wybranemu wyswietlaczowi
            else {
                int ktoryWyswietlacz = pobierzKtoryWyswietlacz();
                panelWyswietlaczy.ustawKolorSegmentu(ktoryWyswietlacz, kolor);
            }
        }
        // ustalenie koloru tla
        if(source == kolorTlaButton){

            // pobranie zmiennych
            String kolor = ((String) kolorComboBox.getSelectedItem());
            Integer ktoryWyswietlacz;

            if(czyWszystkieCheckBox.isSelected())
                panelWyswietlaczy.ustalKolorTlaWszystkichWyswietlaczy(kolor);
            else {
                 ktoryWyswietlacz = pobierzKtoryWyswietlacz();
                 panelWyswietlaczy.ustalKolorTlaWyswietlacza(ktoryWyswietlacz, kolor);
            }
        }

        // ustalenie koloru wygaszonego segmentu
        if(source == kolorZgaszonegoButton){
            // pobranie zmiennych
            String kolor = ((String) kolorComboBox.getSelectedItem());
            Integer ktoryWyswietlacz;

            if(czyWszystkieCheckBox.isSelected())
                panelWyswietlaczy.ustawKolorWygaszonegoSegmentuWszystkichWyswietlaczy(kolor);
            else {
                ktoryWyswietlacz = pobierzKtoryWyswietlacz();
                panelWyswietlaczy.ustawKolorWygaszonegoSegmentuWyswietlacza(ktoryWyswietlacz, kolor);

            }
        }

        // wyswietlenie okna z zegarkiem
        if(source == zegarekButton){
            try {
                zegarekOkno = new ZegarekOkno();
                zegarekOkno.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // ustawienie na przezroczyste wygaszonych segmentow
        if(source == kolorWygaszonegoPrzezroczystyButton){
            if(czyWszystkieCheckBox.isSelected()){
                panelWyswietlaczy.ustawPrzezroczystyWygaszonySegmentWszystkichWyswietlaczy();
            }else {
                Integer ktoryWyswietlacz = pobierzKtoryWyswietlacz();
                panelWyswietlaczy.ustawPrzezroczystyWygaszonySegmentWyswietlacza(ktoryWyswietlacz);
            }
        }

        // zmiana systemow liczbowych
        if((source == hexRadioButton ) || (source == decRadioButton) || (source == octRadioButton) ||
                (source == binRadioButton)){

            int nowySystemLiczbowy = 16;

            // okreslenie na jaki system liczbowy ma nastapic zmiana
            if (source == hexRadioButton) nowySystemLiczbowy = 16;
            if (source == decRadioButton) nowySystemLiczbowy = 10;
            if (source == octRadioButton) nowySystemLiczbowy = 8;
            if (source == binRadioButton) nowySystemLiczbowy = 2;


            try {
                // jezeli podano jakis napis - konwersja
                if (panelWyswietlaczy.getAktualnyNapis() != null && !panelWyswietlaczy.getAktualnyNapis().trim().equals("")) {
                    // zmienna pomocnicze
                    String nowyNapis = panelWyswietlaczy.getAktualnyNapisBezSpacji();

                    // pobranie aktualnego napisu i jego przygotowanie
                    String aktualnyNapis = panelWyswietlaczy.getAktualnyNapisBezSpacji();

                    // jesli liczba na textfieldzie za dluga, pobierz ta z textfielda
                    if(podajLiczbeTextField.getText().length() > panelWyswietlaczy.getAktualnyNapis().length()){
                        aktualnyNapis = podajLiczbeTextField.getText();
                    }

                    // konwersja na HEX
                    if (nowySystemLiczbowy == 16) {
                        nowyNapis = konwersjaToHex(aktualnySystemLiczbowy, aktualnyNapis);
                    }
                    // konwersja na dec
                    if (nowySystemLiczbowy == 10) {
                        nowyNapis = konwersjaToDec(aktualnySystemLiczbowy, aktualnyNapis);
                    }
                    // konwersja na oct
                    if (nowySystemLiczbowy == 8){
                        nowyNapis = konwersjaToOct(aktualnySystemLiczbowy, aktualnyNapis);
                    }
                    // konwersja na bin
                    if (nowySystemLiczbowy == 2){
                        nowyNapis = konwersjaToBin(aktualnySystemLiczbowy, aktualnyNapis);
                    }


                    // ustawienie nowej liczby na textFieldzie
                    podajLiczbeTextField.setText(nowyNapis.trim());


                }
            } catch (Exception e2) {
                System.out.println("Blad podczas konwersji liczby");
            } finally {
                // aktualizacja informacji o aktualnym systemie liczbowym
                aktualnySystemLiczbowy = nowySystemLiczbowy;
                wypelnijLiczbaComboBox();
            }
        }

        // kontrola migotania
        if(czestotliwoscMigotania != 0) etapMigotania++;

        // jesli juz czas na zmiane, to zmien
        if(etapMigotania == czestotliwoscMigotania && czestotliwoscMigotania != 0) {
            czyPalic = !czyPalic;
            etapMigotania = 0;
        }

        // migotanie
        if(source == migotanieComboBox){
            String wskazaneMigotanie = (String) migotanieComboBox.getSelectedItem();
            etapMigotania = 0;

            // wylaczenie migotania
            if(wskazaneMigotanie == "Brak"){
                czestotliwoscMigotania = 0;
                czyPalic = true;
            }

            if(wskazaneMigotanie == "1 na sek"){
                czestotliwoscMigotania = 100;
            }

            if(wskazaneMigotanie == "2 na sek"){
                czestotliwoscMigotania = 50;
            }
            if(wskazaneMigotanie == "4 na sek"){
                czestotliwoscMigotania = 25;
            }
            if(wskazaneMigotanie == "10 na sek"){
                czestotliwoscMigotania = 10;
            }
            if(wskazaneMigotanie == "20 na sek"){
                czestotliwoscMigotania = 5;
            }



        }


        // aktualizacja komunikatow w aplikacji testujacej
        wyswietlLiczbeWyswietlaczy();
        wyswietlenieWolnychWyswietlaczy();
        wypelnijWyswietlaczComboBox();


    }// actionPerformed

    // funkcja wyswietlajaca
    private void wyswietlajLiczbeZTextField(){
        // przygotowanie zmiennych
        String podanaLiczba = podajLiczbeTextField.getText();
        Pattern pattern = Pattern.compile("([a-fA-F0-9]){1,99}");


        // test
        panelWyswietlaczy.setAktualnyNapis(podanaLiczba);

            // sprawdzenie, czy podano cyfry, litery od a do f
            Matcher matcher = pattern.matcher(podanaLiczba);

            // jesli liczba pasuje do wyrazenia regularnego - jest poprawana - wyświetl ją
            if(matcher.matches()){
                // jezeli nie palić - migotanie
                if(czyPalic) {
                    panelWyswietlaczy.ustawLiczbeNaWyswietlaczachIZwrocStatus(podanaLiczba);
                } else {
                    panelWyswietlaczy.zgasWyswietlacze();
                }

                // nie wyswietlaj bledu
                podajLiczbeErrorLabel.setText("");
            }
            // jeśli liczba nie pasuje do wyrażenia regularnego
            else {
                // jesli puste pole (dlatego liczba nie pasuje do wyrażenia) - zgaś wszystkie wyświetlacze
                if(podanaLiczba.length() == 0) {
                    panelWyswietlaczy.zgasWyswietlacze();
                    podajLiczbeErrorLabel.setText("");
                }
                // jeśli pole nie jest puste, a liczba nie pasuje do wyrażenia - wyswietl komunikat o błedzie
                else
                    wypiszKomunikatOBlednejLiczbie();
            }


    }

    private void wyswietlLiczbeWyswietlaczy(){
        liczbaWyswietlaczyLabel.setText("Liczba wyswietlaczy: " + panelWyswietlaczy.getIloscWyswietlaczy());
    }


    public void wyswietlenieWolnychWyswietlaczy(){

        liczbaWolnychWyswietlaczyLabel.setText("");

        if(podajLiczbeTextField.getText().length() > panelWyswietlaczy.getIloscWyswietlaczy()){
            Integer ileZnakowZaDuzo = podajLiczbeTextField.getText().length() - panelWyswietlaczy.getIloscWyswietlaczy();
            liczbaWolnychWyswietlaczyLabel.setForeground(Color.RED);
            liczbaWolnychWyswietlaczyLabel.setText("Blad! Podano o " + ileZnakowZaDuzo + " znakow za duzo!");
        } else {
            int a = panelWyswietlaczy.getIloscWyswietlaczy();
            int b = panelWyswietlaczy.getAktualnyNapis().length();
            Integer wolnychWysw = panelWyswietlaczy.getIloscWyswietlaczy() - panelWyswietlaczy.getDlugoscAktualnegoNapis();
            liczbaWolnychWyswietlaczyLabel.setForeground(Color.BLACK);
            liczbaWolnychWyswietlaczyLabel.setText("Wolnych wyswietlaczy: " + wolnychWysw);
        }
    }

    private void wypelnijWyswietlaczComboBox(){
        // pobranie liczby wyswietlaczy
        Integer liczbaWyswietlaczy = panelWyswietlaczy.getIloscWyswietlaczy();

        // jezeli liczba wyswietlaczy jest inna, niz ilosc liczb w comboboxie
        if(wybierzWyswietlaczComboBox.getItemCount() != liczbaWyswietlaczy) {

            // zastąp comboboxa nowym. uzyte bo jakaś magia sprawia,
            // ze przy normalnym usuwaniu czasem zostają losowe elementy
            remove(wybierzWyswietlaczComboBox);
            wybierzWyswietlaczComboBox = new JComboBox();
            wybierzWyswietlaczComboBox.setBounds(10,30,100,20);

            // wypelnij comboboxa
            for (int i = 1; i <= liczbaWyswietlaczy; i++) {
                wybierzWyswietlaczComboBox.addItem(i);
            }
            wybierzWyswietlaczComboBox.addActionListener(this);
            add(wybierzWyswietlaczComboBox);
        }
    }

    private void wypelnijLiczbaComboBox(){

        // sprawdzenie, czy zmieniono system liczbowy
        if(wybierzLiczbeComboBox.getItemCount() != aktualnySystemLiczbowy){
            // pomocnicza tablica liczb
            String[] cyfry = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

            // utworzenie nowego comboboxa
            remove(wybierzLiczbeComboBox);
            wybierzLiczbeComboBox = new JComboBox();
            wybierzLiczbeComboBox.setBounds(130,30,100,20);

            for(int i=0; i<aktualnySystemLiczbowy; i++){
                wybierzLiczbeComboBox.addItem(cyfry[i]);
            }

            wybierzLiczbeComboBox.addActionListener(this);
            add(wybierzLiczbeComboBox);
        }
    }

    private void wypiszKomunikatOBlednejLiczbie(){
        podajLiczbeErrorLabel.setText("Podane zle znaki!");
    }

    private Integer pobierzKtoryWyswietlacz(){
        String nrWyswietlacza = "";
        Integer nrWyswietlaczaInt = 1;
        Boolean czyInt = true;
        try {
            nrWyswietlacza = (String) wybierzWyswietlaczComboBox.getSelectedItem();
            czyInt = false;
        } catch (Exception e2){ }
        try{
            nrWyswietlaczaInt = (Integer) wybierzWyswietlaczComboBox.getSelectedItem();
        } catch (Exception e3) { }
        // zmienia liczbe na tą, podaną w ComboBoxie
        if(czyInt)
            return nrWyswietlaczaInt  - 1;
        else
            return Integer.valueOf(nrWyswietlacza) - 1;
    }

    private String konwersjaToDec(Integer aktualnySystemLiczbowy, String aktualnyNapis){
        return String.valueOf(Integer.parseInt(aktualnyNapis, aktualnySystemLiczbowy));
    }

    private String konwersjaToHex(Integer aktualnySystemLiczbowy, String aktualnyNapis){
        int dec = Integer.valueOf(konwersjaToDec(aktualnySystemLiczbowy, aktualnyNapis));
        return Integer.toHexString(dec);
    }

    private String konwersjaToOct(Integer aktualnySystemLiczbowy, String aktualnyNapis){
        int dec = Integer.valueOf(konwersjaToDec(aktualnySystemLiczbowy, aktualnyNapis));
        return Integer.toOctalString(dec);
    }

    private String konwersjaToBin(Integer aktualnySystemLiczbowy, String aktualnyNapis){
        int dec = Integer.valueOf(konwersjaToDec(aktualnySystemLiczbowy, aktualnyNapis));
        return Integer.toBinaryString(dec);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private boolean czyLiterAdoF(char c){
        if(c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' ||
           c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'E' || c == 'F')
            return true;
        else return false;
    }
}
