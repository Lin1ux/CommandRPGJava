package ItemObjects;

public class ItemList 
{
    //Przedmioty
    public static Items compass = new Items("Kompas","Kompas, który wskazuje inny kierunek niż północ");
    //Bronie
    public static Weapons dagger = new Weapons("Sztylet", "Dość stary ale wciąż wystarczająco ostry", 6);
    public static Weapons shortSword = new Weapons("krotki_miecz","",12);
    //Zbroje
    public static Armor leatherCoat = new Armor("Skórzany_płaszcz","Stary zużyty płaszcz, który wciąż pełni funkcję ochronne",2,6);
    public static Armor chainmail = new Armor("Kolczuga","Powszechna zbroja używana przez lekko uzbrojonych żołnierzy",5,6,30);
    public static Armor plateArmor = new Armor("Stalowa_Zbroja","Ceniona za wysoki poziom ochrony",7,5,50);
    public static Armor scaleArmor = new Armor("Łuskowa_Zbroja","Lekka zbroja zrobiona z łusek dużego gada",3,6,15);
}
