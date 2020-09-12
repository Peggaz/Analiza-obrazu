import numpy as np
import matplotlib.pyplot as plt
from matplotlib.pyplot import imread
import matplotlib.image as mpimg


img = imread("obraz.png")#wczytanie obrazu
plt.imshow(img)#przygotowanie widoku podglądowego(usun "#" z poczatku lini by zadzialalo)
plt.show()#wygląd poglądowy
numer_wiersza = -1 #zmienne potrzebne do wykonania
numer_piksela = -1
etykieta = 0
piksel_gora = (1., 1., 1.)
piksel_lewo = (1, 1, 1)
piksel_gora_prawo = (1, 1, 1)
def czy_bialy(a): #sprawdza czy obiekt jest bialy, lub taki przypomina, w przypadku bieli zwraca 1
    for i in a:
        if i < 0.85:
            return 0
    return 1

for wiersz in img:#glowna pentla
    numer_piksela = -1#zeruje numer piksela
    numer_wiersza += 1#modyfikacja numeru wiersza
    for piksel in wiersz:#przechodzi po wszystkich pikselach
        numer_piksela += 1#modyfikacja numeru piksela
        if( czy_bialy(piksel) == 0):#sprawdza czy rozwazany piksel jest bialy, jezeli jest to konczymy
            if (numer_wiersza > 0):#sprawdzenie wiesza wyzej
                piksel_gora = img[numer_wiersza - 1][numer_piksela]#przypisanie koloru piksela wyzej
            if(numer_piksela > 0):
                piksel_lewo = img[numer_wiersza][numer_piksela - 1]#to samo co wyzej tylko po lewej stronie
            if(czy_bialy(piksel_lewo) == 0 ):#jezeli piksel po lewo nie jest bialy to przypisz ten sam kolor do obecnego rpzypadku
                img[numer_wiersza][numer_piksela] = piksel_lewo
            elif(czy_bialy(piksel_gora)==0):
                img[numer_wiersza][numer_piksela] = piksel_gora#w przeciwnym przypadku gdzie piksel u gory nie jest bialyprzypisz jego kolor do obecnego
            else:#jezeli piksel u gory i po prawo sa biale
                for numer_piksela_prawo in range(len(wiersz)-numer_piksela):#badamy piksele w prawo kore sa w naszeym obekcie
                    if(czy_bialy(img[numer_wiersza][numer_piksela_prawo+numer_piksela]) == 0):#jezeli piksel po prawo nie jest bialy
                        piksel_gora_prawo = img[numer_wiersza - 1][numer_piksela_prawo+numer_piksela]
                        if(czy_bialy(piksel_gora_prawo) == 0):#sprawdzamy piksele nad naszym wierszem obiektu
                            break#jezeli jest to zatrzymaj pentle
                    else:
                        break#jezeli pikel po prawo jest bialy zatrzymaj
                if (czy_bialy(piksel_gora_prawo) == 0):
                    img[numer_wiersza][numer_piksela] = piksel_gora_prawo#jezeli piksele w wierszu powyzej nalezace do obiektu sa rozne od bialego to przypisz kolor do obecnego
                else:#jezeli jest to nowa figura
                    etykieta += 1 #ustaw nowa etykiete
                    szarosc = etykieta / 20#ustaw nowy kolor oparty o numer etykiety
                    img[numer_wiersza][numer_piksela] = (szarosc, szarosc, szarosc)#przypisz nowy kolor do pierwszego piksela nowej znalezonej figury

plt.imshow(img)#przygotowanie widoku podglądowego(usun "#" z poczatku lini by zadzialalo)
plt.show()#wygląd poglądowy
mpimg.imsave("wyjscie.png", img)#zapis do pliku
