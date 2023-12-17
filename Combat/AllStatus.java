package Combat;

public class AllStatus 
{
    public static Status PoisonAttack = new Status("Trujący Cios","Każdy atak, który zada obrażenia nakłada efekt trucizny, który zadaje obrażenia w czasie", Status.CombatTime, -1);
    public static Status Poison = new Status("Zatrucie","Na początku tury otrzymuje obrażenia równe 5",Status.ShortTime,2);
}
