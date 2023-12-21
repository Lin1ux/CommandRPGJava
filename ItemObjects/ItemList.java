package ItemObjects;

public class ItemList 
{
    //Przedmioty
    public static Items compass = new Items("Kompas","Wskazuje jakąś pozycje");
    public static Items HPPotion = new Items("Mikstura Zdrowia","Odnawia 25 Punktów Zdrowia",2);
    public static Items Drink = new Items("Pancerianska","Słodki napój wysoko alkoholowy",10);
    public static Items Poison = new Items("Trucizna","Jad węża, który można użyć do zatrucia broni",2);
    public static Items Healing = new Items("Medalion Życia","Uczy umięjętności Leczenie (uczy automatycznie)",50);
    public static Items Chest = new Items("Skrzynka","Szczelna mała skrzyneczka",30);
    public static Items WaterChest = new Items("Skrzynka z Wodą","Szczelna mała skrzyneczka wypełniona wodą",35);

    //Bronie
    public static Weapons dagger = new Weapons("Sztylet", "Dość stary ale wciąż wystarczająco ostry", 6,0);
    public static Weapons shortSword = new Weapons("Krótki miecz","Lekki miecz",12,0);
    public static Weapons Sword = new Weapons("Miecz","Najpopularniejsza wersja najpopularniejszej broni",14,30);
    public static Weapons LongSword = new Weapons("Długi miecz","Ciężki miecz",18,50);
    public static Weapons Hammer = new Weapons("Mlot","Broń, która oszołamia przeciwnika",14,40);    //Atak nakłada oszołomienie (oszołomienie - zabiera 1 PA na początku tury)
    public static Weapons Spear = new Weapons("Wlocznia","Broń, zaprojektowana do przebijania ciężkich pancerzy",12,40); //Ignoruje 50% pancerza
    
    //Zbroje
    public static Armor leatherCoat = new Armor("Skórzany płaszcz","Stary zużyty płaszcz, który wciąż pełni funkcję ochronne",2,6);
    public static Armor chainmail = new Armor("Kolczuga","Powszechna zbroja używana przez lekko uzbrojonych żołnierzy",5,6,30);
    public static Armor plateArmor = new Armor("Stalowa Zbroja","Ceniona za wysoki poziom ochrony",7,5,50);
    public static Armor scaleArmor = new Armor("Łuskowa Zbroja","Lekka zbroja zrobiona z łusek dużego gada",3,6,15);
    public static Armor ThornArmor = new Armor("Kolczasta Zbroja","Kolczasta zbroja, która może zranić napastnika",5,5,50);
}
