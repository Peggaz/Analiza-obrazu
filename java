
package analiza.obrazu;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AnalizaObrazu {

    public static void main(String[] args) throws IOException {
        BufferedImage img = null;//tworzenie boiektu przechowującego zdjęcie
        
        img = ImageIO.read(new File("obraz.png"));//odczytanie obrazu
        
        int wysokosc = img.getHeight();//przypisanie wysokosci i szerokosci
        int szerokosc = img.getWidth();
        
        int piksel_gora = 0;
        int piksel_lewo = 0;
        int piksel_prawo_gora = 0;
        int etykieta = 0;//zmienne pomocnicze
        int kolor_kluczowy = -1673255;//kolor RGB zapisany w liczbie całkowitej int
        for (int wiersz = 0; wiersz < wysokosc; wiersz++){//rozpoczecie iteracji po wierszach
            for(int piksel = 0; piksel < szerokosc; piksel++){//iteracja po pikselach
                if(img.getRGB(wiersz, piksel)<kolor_kluczowy){//jezeli badany piksel nie jest bialy to:
                    if(wiersz > 0)//jezeli nie jestesmy w pierwszym wierszu to sprawdzamy wiersz wyzej
                        piksel_gora = img.getRGB(wiersz-1, piksel);
                    if(piksel > 0)//jezeli nie jestesmy w pierwszej kolumnie to sprawdzamy piksel po lewo
                        piksel_lewo = img.getRGB(wiersz, piksel-1);
                    if(piksel_gora < kolor_kluczowy)//jezeli piksel wyzej jest juz ustawiony to ustaw obecny
                        img.setRGB(wiersz, piksel, piksel_gora);
                    else if(piksel_lewo < kolor_kluczowy)//jez3eli piksel po lewo jest ustawiony to ustaw obecny
                        img.setRGB(wiersz, piksel, piksel_lewo);
                    else{//jezeli piksel po lewo i u gory nie jest ustawiony to:
                        for(int numer_piksela_prawo = piksel; numer_piksela_prawo < szerokosc; numer_piksela_prawo++){//badamy piksele na prawo od obecnego
                            if(img.getRGB(wiersz, numer_piksela_prawo) < kolor_kluczowy){//jezeli nalezy do naszej figury
                                piksel_prawo_gora = img.getRGB(wiersz-1, numer_piksela_prawo);//badamy piksel nad nim
                                if(piksel_prawo_gora < kolor_kluczowy)//jezeli piksel nad nim jest ustationy
                                    break;//zatrzymaj
                            }
                            else//jezeli piksel po prawo nie jest ustawiony oznacza to koniec figury
                                break;//zatrzymaj
                        }
                        if(piksel_prawo_gora < kolor_kluczowy)//jezeli piksel u gory udalo sie ustawic to 
                            img.setRGB(wiersz, piksel, piksel_prawo_gora);//przypisz kolor do obecnego
                        else{//jezeli nie ma ustawionych pikseli
                            etykieta++;//nowa figura
                            int szarosc = 15 *etykieta;//ustaw numer koloru
                            int kolor = (szarosc << 16) | (szarosc << 8) | szarosc;//ustawiamy szarosc
                            img.setRGB(wiersz, piksel, kolor);//przypisujemy kolor do obecnego piksela
                        }
                   }
                }
            }
        }
        File wyjscie = new File("wyjscie.png");//przygotowanie zapisu
        ImageIO.write(img, "png", wyjscie);//zapis pliku
    }
}
