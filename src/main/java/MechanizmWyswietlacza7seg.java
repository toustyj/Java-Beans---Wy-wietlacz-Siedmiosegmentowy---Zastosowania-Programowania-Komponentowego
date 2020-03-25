import java.io.Serializable;

public class MechanizmWyswietlacza7seg implements Serializable {

    // 0-a   1-b   2-c   3-d   4-e   5-f   6-g
    private boolean[] stan;

    // stworzenie mechanizmu wyswietlacza i wyzerowanie go
    public MechanizmWyswietlacza7seg(){
        stan = new boolean[7];
        wyzerujStany();
    }

    // stworzenie mechanizmu wyswietlacza i ustawienie podanego stanu
    public MechanizmWyswietlacza7seg(boolean[] podanyStan){
        stan = new boolean[7];
        if(podanyStan.length != 7) System.out.println("Błędna liczba stanów!");
        else ustawStan(podanyStan);
    }

    // stworzenie mechanizmu wyswietlacza i ustawienie podanego stanu
    public MechanizmWyswietlacza7seg(char podanyStan){
        stan = new boolean[7];
        ustawStan(podanyStan);
    }

    public void wyzerujStany(){
        for (int i = 0; i< stan.length; i++){
            stan[i] = false;
        }
    }

    // ustala niestandardowy stan wlasny
    public void ustawNiestandardowyStan(boolean[] podanyStan){
        if (podanyStan.length != 7) System.out.println("Błąd! Podano niepoprawną ilość stanów");
        else ustawStan(podanyStan);
    }

    // ustawia stan bez sprawdzenia dlugosci podanej tablicy
    private void ustawStan(boolean[] podanyStan){
        for (int i=0; i<podanyStan.length; i++)
            stan[i] = podanyStan[i];
    }

    public void ustawStan(char podanyStan){
        if (podanyStan == '0') ustaw0();
        if (podanyStan == '1') ustaw1();
        if (podanyStan == '2') ustaw2();
        if (podanyStan == '3') ustaw3();
        if (podanyStan == '4') ustaw4();
        if (podanyStan == '5') ustaw5();
        if (podanyStan == '6') ustaw6();
        if (podanyStan == '7') ustaw7();
        if (podanyStan == '8') ustaw8();
        if (podanyStan == '9') ustaw9();
        if (podanyStan == 'a' || podanyStan == 'A') ustawA();
        if (podanyStan == 'b' || podanyStan == 'B') ustawB();
        if (podanyStan == 'c' || podanyStan == 'C') ustawC();
        if (podanyStan == 'd' || podanyStan == 'D') ustawD();
        if (podanyStan == 'e' || podanyStan == 'E') ustawE();
        if (podanyStan == 'f' || podanyStan == 'F') ustawF();
    }

    public boolean[] getStan() {
        return stan;
    }

    @Deprecated
    public void setStan(boolean[] podanyStan) {
        this.stan = podanyStan;
    }

    public void ustaw1(){
        stan = new boolean[] {false,true,true,false,false,false,false};
    }
    public void ustaw2(){
        stan = new boolean[] {true,true,false,true,true,false,true};
    }
    public void ustaw3(){
        stan = new boolean[] {true,true,true,true,false,false,true};
    }
    public void ustaw4(){
        stan = new boolean[] {false,true,true,false,false,true,true};
    }
    public void ustaw5(){
        stan = new boolean[] {true,false,true,true,false,true,true};
    }
    public void ustaw6(){
        stan = new boolean[] {true,false,true,true,true,true,true};
    }
    public void ustaw7(){
        stan = new boolean[] {true,true,true,false,false,false,false};
    }
    public void ustaw8(){
        stan = new boolean[] {true,true,true,true,true,true,true};
    }
    public void ustaw9(){
        stan = new boolean[] {true,true,true,true,false,true,true};
    }
    public void ustaw0(){
        stan = new boolean[] {true,true,true,true,true,true,false};
    }
    public void ustawA(){
        stan = new boolean[] {true,true,true,false,true,true,true};
    }
    public void ustawB(){
        stan = new boolean[] {false,false,true,true,true,true,true};
    }
    public void ustawC(){
        stan = new boolean[] {true,false,false,true,true,true,false};
    }
    public void ustawD(){
        stan = new boolean[] {false,true,true,true,true,false,true};
    }
    public void ustawE(){
        stan = new boolean[] {true,false,false,true,true,true,true};
    }
    public void ustawF(){
        stan = new boolean[] {true,false,false,false,true,true,true};
    }

    // zwraca aktualny stan w formie znaku, jeśli stan niestandardowy, zwraca "!"
    public char aktualnyStan(){
        if(stan == new boolean[]{false,true,true,false,false,false,false}) return '1';
        if (stan == new boolean[] {true,true,false,true,true,false,true}) return '2';
        if (stan == new boolean[] {true,true,true,true,false,false,true}) return '3';
        if (stan == new boolean[] {false,true,true,false,false,true,true}) return '4';
        if (stan == new boolean[] {true,false,true,true,false,true,true}) return '5';
        if (stan == new boolean[] {true,false,true,true,true,true,true}) return '6';
        if (stan == new boolean[] {true,true,true,false,false,false,false}) return '7';
        if (stan == new boolean[] {true,true,true,true,true,true,true}) return '8';
        if (stan == new boolean[] {true,true,true,true,false,true,true}) return '9';
        if (stan == new boolean[] {true,true,true,true,true,true,false}) return '0';
        if (stan == new boolean[] {true,true,true,false,true,true,true}) return 'a';
        if (stan == new boolean[] {false,false,true,true,true,true,true}) return 'b';
        if (stan == new boolean[] {true,false,false,true,true,true,false}) return 'c';
        if (stan == new boolean[] {false,true,true,true,true,false,true}) return 'd';
        if (stan == new boolean[] {true,false,false,true,true,true,true}) return 'e';
        if (stan == new boolean[] {true,false,false,false,true,true,true}) return 'f';
        else return '!';
    }

}
