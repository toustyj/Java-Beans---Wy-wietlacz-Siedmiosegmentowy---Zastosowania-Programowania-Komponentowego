import com.sun.xml.internal.stream.StaxErrorReporter;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PanelWyswietlaczy extends JPanel {

    private LinkedList<Wyswietlacz7seg> wyswietlacze;
    private Integer aktualnyRozmiarX, aktualnyRozmiarY;
    private String aktualnyNapis;
    private Integer iloscWyswietlaczy;
    private Color kolorTla;


    private final static Integer domyslnaWysokoscZOdstepem = 119;
    private final static Integer domyslnyOdstepDolny = 240;

    public PanelWyswietlaczy(){
        setLayout(null);

        wyswietlacze = new LinkedList<Wyswietlacz7seg>();
        kolorTla = new Color(238,238,238); // domyslny kolor
        setBackground(kolorTla);
        aktualnyNapis = "";

    }

    // ustawia wybrany znak na wskazanym wyswietlaczu
    public void ustawNaWyswietlaczu(Integer numerWyswietlacza, char znak){
        wyswietlacze.get(numerWyswietlacza).ustaw(znak);

        // podmiana znaku w Stringu aktualnyNapis
        StringBuilder builder = new StringBuilder(aktualnyNapis);
        builder.setCharAt(numerWyswietlacza, znak);
        aktualnyNapis = builder.toString();



        //wyswietlacze.get(numerWyswietlacza-1).setStan(znak);
        for(int i=0; i<wyswietlacze.size(); i++){
            wyswietlacze.get(i).repaint();
        }
        repaint();
    }


     /**
      * metoda ta modyfikuje rozmiar panelu oraz ilosc wyswietlaczy w oparciu o podane zmienne.
      * w stworzonej aplikacji testujacej wywolywana jest co x milisekund, aby odpowiadac na manipulacje
      * rozmiarem ekranu przez uzytkownika
      */
    public void ustawIloscWyswietlaczy(Integer x, Integer y, Integer width, Integer height){

        // obliczenie wielkosci wyswietlaczy
        Integer realWidth = width - x;
        Integer realHeight = height - y;

        // ile wyswietlaczy sie miesci
        Integer ileRzedow = realHeight / (domyslnyOdstepDolny);
        iloscWyswietlaczy = (realWidth / (domyslnaWysokoscZOdstepem)) * ileRzedow;

        // ustawienie wielkosci wyswietlacza
        ustalRozmiarPanelu(realWidth, realHeight);

        // jesli za duzo/malo wyswietlaczy, usun/dodaj odpowiednia ilosc
        skorygujIloscWyswietlaczy();

        // aktualizacja aktualnegoNapisu
        char[] newChar = new char[iloscWyswietlaczy];
        for(int i = 0; i< iloscWyswietlaczy; i++){
            newChar[i] = ' ';
        }
        for(int i = 0; i< iloscWyswietlaczy; i++){
            if(aktualnyNapis.length() > i) {
                if (aktualnyNapis.charAt(i) != ' ') {
                    newChar[i] = aktualnyNapis.charAt(i);
                }
            }
        }

         aktualnyNapis = String.valueOf(newChar);

        // dodanie wyswietlaczy
        umiejscowienieWyswietlaczy(ileRzedow);
        // narysowanie na nowo
        repaint();

    }

    public void ustawIloscWyswietlaczyBezKontroliRozdzielczosciEkranu(Integer ileWyswietlaczy, Integer ileRzedow){
        // ustawienie ilosci wyswietlaczy
        iloscWyswietlaczy = ileWyswietlaczy;

        // aktualizacja aktualnegoNapisu
        char[] newChar = new char[iloscWyswietlaczy];
        for(int i = 0; i< iloscWyswietlaczy; i++){
            newChar[i] = ' ';
        }
        for(int i = 0; i< iloscWyswietlaczy; i++){
            if(aktualnyNapis.length() > i) {
                if (aktualnyNapis.charAt(i) != ' ') {
                    newChar[i] = aktualnyNapis.charAt(i);
                }
            }
        }
        aktualnyNapis = String.valueOf(newChar);

        // dodanie wyswietlaczy
        skorygujIloscWyswietlaczy();
        umiejscowienieWyswietlaczy(ileRzedow);
        // narysowanie na nowo
        repaint();
    }

    private void skorygujIloscWyswietlaczy() {
        if(wyswietlacze.size() > iloscWyswietlaczy){
            for(int i = wyswietlacze.size(); i>iloscWyswietlaczy; i--){
                if(i != 0) {
                    // usun wyswietlacz
                    remove(wyswietlacze.get(i - 1));
                    wyswietlacze.remove(i - 1);
                    // usuniecie ostatniego znaku
                    if(aktualnyNapis.length()>0) aktualnyNapis = removeLastCharacter(aktualnyNapis);
                    // wypisanie nowej liczby
                    wypiszLiczbeZTextFielda(aktualnyNapis);
                }
            }
        }

        // jesli za malo wyswietlaczy, stworz nowe wyswietlacze
        if(wyswietlacze.size() < iloscWyswietlaczy){

            for(int i = wyswietlacze.size(); i<iloscWyswietlaczy; i++){
                wyswietlacze.add(new Wyswietlacz7seg());

            }
        }
    }

    private void ustalRozmiarPanelu(Integer realWidth, Integer realHeight) {
        if(aktualnyRozmiarX != null || aktualnyRozmiarY != null ) {
            // jezeli wielkosc sie zmienila
            if (!realWidth.equals(aktualnyRozmiarX) || !realHeight.equals(aktualnyRozmiarY)) {
                aktualnyRozmiarX = realWidth;
                aktualnyRozmiarY = realHeight;
                setSize(aktualnyRozmiarX, aktualnyRozmiarY);
            }
        }
        // jezeli nie ma miejsca na ekranie na panel
        else if(realHeight <= 0 || realWidth <= 0){
            aktualnyRozmiarY = 0;
            aktualnyRozmiarX = 0;
        }
        else {
            aktualnyRozmiarX = realWidth;
            aktualnyRozmiarY = realHeight;
            setSize(aktualnyRozmiarX, aktualnyRozmiarY);
        }
    }

    // wykonuje repaint na komponencie oraz wszystkich wyswietlaczach
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.RED);

        for(Wyswietlacz7seg wys : wyswietlacze){
            wys.repaint();
        }

    }

    // wygasza wszystkie posiadane wyswietlacze
    public void zgasWyswietlacze(){
        for (Wyswietlacz7seg wyswietlacz7seg : wyswietlacze) {
            wyswietlacz7seg.wylaczWyswietlacz();
        }
    }

    // zwraca ilosc wyswietlaczy (wielkosc tablicy wyswietlacze)
    public Integer iloscWyswietlaczy(){
        return wyswietlacze.size();
    }

    // (jesli podano wartosc z ComboBoxa - czysci wyswietlacze) wpisuje na wyswietlacze podana liczbe
    public void wypiszLiczbeZTextFielda(String podanaLiczba){
        // przypisanie do tablicy znakow podanego Stringa
        // (aby latwiej bylo operowac)
        char[] znaki = podanaLiczba.toCharArray();

        // przygotowanie StringBuildera uzytego pozniej do budowania napisu
        StringBuilder builder = new StringBuilder(aktualnyNapis);

        //
        for(int i=0; i<podanaLiczba.length(); i++){
            wyswietlacze.get(i).ustaw(znaki[i]);
            builder.setCharAt(i, znaki[i]);
        }

        // aktualizacja zmiennej z aktualnym napisem
        aktualnyNapis = builder.toString();

        // aktualizacja wygladu
        repaint();
    }

    // metoda zajmujaca sie ulozeniem wyswietlaczy na ekrnaie
    private void umiejscowienieWyswietlaczy(Integer ileRzedow){

        int x = 0;
        int y = 0;
        int nrWyswietlacza = 0;
        for(int i=0; i<ileRzedow; i++) {
            // jesli liczba wyswietlaczy parzysta
            if(iloscWyswietlaczy%2 == 0) {
                for (int j = 0; j < iloscWyswietlaczy / ileRzedow; j++) {
                    //wyswietlacze.get(i).ustaw('9');
                    add(wyswietlacze.get(nrWyswietlacza)).setBounds(x, y, 104, 204);
                    x += domyslnaWysokoscZOdstepem;
                    nrWyswietlacza++;
                }
            }
            else {
                int ileWRzedzie = (int) Math.ceil(Double.valueOf(iloscWyswietlaczy) / Double.valueOf(ileRzedow));
                for (int j = 0; j < ileWRzedzie; j++) {
                    if(nrWyswietlacza < wyswietlacze.size()) {
                        add(wyswietlacze.get(nrWyswietlacza)).setBounds(x, y, 104, 204);
                        x += domyslnaWysokoscZOdstepem;
                        nrWyswietlacza++;
                    }
                }
            }
            x = 0;
            y += domyslnyOdstepDolny;


        }
    }

    // walidacja liczby i wpisanie jej do wyswietlaczy
    public String ustawLiczbeNaWyswietlaczachIZwrocStatus(String liczba){
        Pattern pattern = Pattern.compile("([a-fA-F0-9]){1,99}");


        // sprawdzenie, czy liczba zmiesci sie w wyswietlaczach
        if(liczba.length() > wyswietlacze.size()){
            return "Blad! Liczba moze miec teraz do " + wyswietlacze.size() + " znakow";
        }

        else {
            // sprawdzenie, czy podano cyfry, litery od a do f
            Matcher matcher = pattern.matcher(liczba);

            // jesli liczba pasuje do wyrazenia regularnego - jest poprawana - wyświetl ją
            if(matcher.matches()){
                wypiszLiczbeNaWyswietlaczach(liczba);

            }
            // jeśli liczba nie pasuje do wyrażenia regularnego
            else {
                // jesli puste pole (dlatego liczba nie pasuje do wyrażenia) - zgaś wszystkie wyświetlacze
                if(liczba.length() == 0) {
                    zgasWyswietlacze();
                    return "";
                }
                // jeśli pole nie jest puste, a liczba nie pasuje do wyrażenia - wyswietl komunikat o błedzie
                else
                    return "Podana liczba jest nieprawidlowa";

            }
        }



        return "";
    }

    public void wypiszLiczbeNaWyswietlaczach(String podanaLiczba){

        // zgaś wyświetlacze
        zgasWyswietlacze();

        int dlugoscLiczby = podanaLiczba.length();

        // wypisz liczbe na wyswietlaczach
        if(dlugoscLiczby <= iloscWyswietlaczy){
            wypiszLiczbeZTextFielda(podanaLiczba);
        }


    }

    public Integer getIloscWyswietlaczy() {
        return iloscWyswietlaczy;
    }

    public String getAktualnyNapis() {
        return aktualnyNapis;
    }

    public int getDlugoscAktualnegoNapis(){
        int ile=0;
        for(int i=0; i<aktualnyNapis.length(); i++){
            if(aktualnyNapis.charAt(i) != ' '){
                ile++;
            }
        }
        return ile;
    }

    // usuwa ostatni znak Stringa
    public static String removeLastCharacter(String str) {
        String result = null;
        if ((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 1);
        }
        return result;
    }

    // ustawia aktualny stan na wyswietlaczach w oparciu o zmienną aktualnyNapis
    private void ustawAktualnyStanNaWyswietlaczach(){
        for(int i = 0; i< iloscWyswietlaczy; i++){

            //jesli na wyswietlaczu nic ma sie nie palic
            if(aktualnyNapis.charAt(i) == ' '){
                wyswietlacze.get(i).wylaczWyswietlacz();
            }
            // jesli cos na wyswietlaczu ma sie palic
            else {
                wyswietlacze.get(i).setStan(aktualnyNapis.charAt(i));
            }
        }

    }

    public void ustawKolorSegmentu(int numerWyswietlacza, Color color){
        // jezeli taki wyswietlacz istnieje
        if(numerWyswietlacza <= wyswietlacze.size()){
            wyswietlacze.get(numerWyswietlacza).setKolorPalacegoSieSegmentu(color);
            wyswietlacze.get(numerWyswietlacza).repaint();
        }
    }

    public void ustawKolorSegmentu(int numerWyswietlacza, String nazwaKoloru){

        Color color = ustalKolor(nazwaKoloru);

        // jezeli taki wyswietlacz istnieje i podano prawidlowy kolor
        if(numerWyswietlacza <= wyswietlacze.size() && color != null){
            wyswietlacze.get(numerWyswietlacza).setKolorPalacegoSieSegmentu(color);
            wyswietlacze.get(numerWyswietlacza).repaint();
        }
    }

    public void ustalKolorSegmentuWszystkimWyswietlaczom(String nazwaKoloru){
        Color color = ustalKolor(nazwaKoloru);

        // ustawienie koloru wszystkim wyswietlaczom
        for(int i=0; i<wyswietlacze.size(); i++){
            wyswietlacze.get(i).setKolorPalacegoSieSegmentu(color);
        }
    }

    public void ustalKolorTlaWyswietlacza(int numerWyswietlacza, String nazwaKoloru){
        Color color = ustalKolor(nazwaKoloru);

        // jezeli taki wyswietlacz istnieje i podano prawidlowy kolor
        if(numerWyswietlacza <= wyswietlacze.size() && color != null){
            wyswietlacze.get(numerWyswietlacza).setKolorTla(color);
            wyswietlacze.get(numerWyswietlacza).repaint();
        }
    }

    public void ustalKolorTlaWszystkichWyswietlaczy(String nazwaKoloru){
        Color color = ustalKolor(nazwaKoloru);

        for(int i=0; i<wyswietlacze.size(); i++){
            wyswietlacze.get(i).setKolorTla(color);
        }
    }

    public void ustawKolorWygaszonegoSegmentuWyswietlacza(int numerWyswietlacza, String nazwaKoloru){
        Color color = ustalKolor(nazwaKoloru);

        // jezeli taki wyswietlacz istnieje i podano prawidlowy kolor
        if(numerWyswietlacza <= wyswietlacze.size() && color != null){
            wyswietlacze.get(numerWyswietlacza).setKolorWylaczonegoSegmentu(color);
            wyswietlacze.get(numerWyswietlacza).repaint();
        }
    }

    public void ustawKolorWygaszonegoSegmentuWszystkichWyswietlaczy(String nazwaKoloru){
        Color color = ustalKolor(nazwaKoloru);

        for(int i=0; i<wyswietlacze.size(); i++){
            wyswietlacze.get(i).setKolorWylaczonegoSegmentu(color);
        }
    }

    public void ustawPrzezroczystyWygaszonySegmentWyswietlacza(int numerWyswietlacza){
        wyswietlacze.get(numerWyswietlacza).setKolorWylaczonegoJakoPrzezroczysty();
    }

    public void ustawPrzezroczystyWygaszonySegmentWszystkichWyswietlaczy(){
        for(int i=0; i<wyswietlacze.size(); i++){
            wyswietlacze.get(i).setKolorWylaczonegoJakoPrzezroczysty();
        }
    }

    private Color ustalKolor(String nazwaKoloru){
        Color color = null;
        if(nazwaKoloru.equals("Czerwony")){
            color = Color.RED;
        }
        if(nazwaKoloru.equals("Niebieski")){
            color = Color.BLUE;
        }
        if(nazwaKoloru.equals("Zielony")){
            color = Color.GREEN;
        }
        if(nazwaKoloru.equals("Czarny")){
            color = Color.BLACK;
        }
        if(nazwaKoloru.equals("Zolty")){
            color = Color.YELLOW;
        }
        if(nazwaKoloru.equals("Rozowy")){
            color = Color.PINK;
        }
        if(nazwaKoloru.equals("Szary")){
            color = new Color(220,220,220);
        }
        if(nazwaKoloru.equals("Bialy")){
            color = Color.WHITE;
        }
        return color;
    }

    public Color getKolorTla() {
        return kolorTla;
    }

    public void setKolorTla(Color kolorTla) {
        this.kolorTla = kolorTla;
        setBackground(kolorTla);
        repaint();
    }

    public LinkedList<Wyswietlacz7seg> getWyswietlacze() {
        return wyswietlacze;
    }

    public void setWyswietlacze(LinkedList<Wyswietlacz7seg> wyswietlacze) {
        this.wyswietlacze = wyswietlacze;
    }

    public void setAktualnyNapis(String aktualnyNapis) {
        // jezeli napis za dlugi, wpisz tylko te znaki, ktore sie mieszcza
        if(aktualnyNapis.length() > wyswietlacze.size()){
            char[] skroconyNapis = new char[wyswietlacze.size()];
            for(int i=0; i<skroconyNapis.length; i++){
                skroconyNapis[i] = aktualnyNapis.charAt(i);
            }
        }
        // jesli caly napis sie miesci
        else
            this.aktualnyNapis = aktualnyNapis;
    }

    public Integer getAktualnyRozmiarX() {
        return aktualnyRozmiarX;
    }

    public void setAktualnyRozmiarX(Integer aktualnyRozmiarX) {
        this.aktualnyRozmiarX = aktualnyRozmiarX;
    }

    public Integer getAktualnyRozmiarY() {
        return aktualnyRozmiarY;
    }

    public void setAktualnyRozmiarY(Integer aktualnyRozmiarY) {
        this.aktualnyRozmiarY = aktualnyRozmiarY;
    }

    public void setIloscWyswietlaczy(Integer iloscWyswietlaczy) {
        this.iloscWyswietlaczy = iloscWyswietlaczy;
    }

    public static Integer getDomyslnaWysokoscZOdstepem() {
        return domyslnaWysokoscZOdstepem;
    }

    public static Integer getDomyslnyOdstepDolny() {
        return domyslnyOdstepDolny;
    }

    public String getAktualnyNapisBezSpacji(){
        return new String(aktualnyNapis.replace(" ", ""));
    }

    public void test(){
        repaint();
    }
}
