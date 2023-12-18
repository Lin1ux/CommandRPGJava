package Combat;

public class AllStatus 
{
    //Pasywne
    public static Status Mechanism = new Status("Mechaniczny","Jednostka jest odporna na zatrucie i krwawienie",Status.Passive,-1);  //Dokończyć w walce
    public static Status LifeSteal = new Status("Kradzież Życia","Trafione ataki leczą o 25% zadanych obrażeń", Status.Passive, -1); //Dokończyć w walce
    public static Status PenetrationAttack = new Status("Pzebijający Cios","Ataki ignorują 50% pancerza", Status.Passive, -1);       //Dokończyć w walce   
    public static Status StunAttack = new Status("Ogłuszający Cios","Każdy trafiony atak, ma 50% szansy na nałożenie oszołomienia, który zabiera 1 Punkt akcji na początku tury", Status.Passive, -1);  //Dokończyć w walce  
    public static Status BleedAttack = new Status("Krwawiący Cios","Każdy trafiony atak nakłada efekt krwawienia, który zadaje obrażenia w czasie", Status.Passive, -1); //Dokończyć w walce
    public static Status Thorns = new Status("Ciernie","Otrzymanie obrażeń zadaje przeciwnikowi 1 punkt obrażeń", Status.Passive, -1); //Dokończyć w walce
    
    //Na czas bitwy
    public static Status PoisonAttack = new Status("Trujący Cios","Każdy atak, który zada obrażenia nakłada efekt trucizny, który zadaje obrażenia w czasie", Status.CombatTime, -1);

    //Na kilka tur
    public static Status Poison = new Status("Zatrucie","Na początku tury otrzymuje obrażenia równe 3",Status.ShortTime,2);
    public static Status Bleed = new Status("Krwawienie","Na początku tury otrzymuje obrażenia równe 2",Status.ShortTime,3); //Dokończyć w walce
    public static Status Stun = new Status("Oszołomienie","Na początku tury traci 1 Punkt Akcji",Status.ShortTime,2);        //Dokończyć w walce
}
