package Combat;

public class AllSkills 
{
    public static Skills normalAttack = new Skills("Atak", "Obrona",3,
    "Atak zadający 100% obrażeń",
    "Blok redukujący 100% obrażeń posiadanej broni ");
    public static Skills lightAttack = new Skills("Szybkie Uderzenie", "Unik",2,
    "Atak zadający 50% obrażeń ale nie da się go uniknąć",
    "Posiada 50% na uniknięcie obrażeń. Każda przewaga nad szybkością przeciwnika zwiększa szansę o 5%");
    public static Skills heavyAttack = new Skills("Potężny Cios", "Blok",5,
    "Potężny atak zadający 150% obrażeń",
    "Blokuje obrażenia równe bronii i posiada 50% na zmniejszenie 1 Punktu Akcji przeciwnika");
    public static Skills StunAttack = new Skills("Uderzenie Rękojeścią", "Gniew",2,
    "Atak zadający 20% obrażeń i redukujący Punkty Akcji przeciwnika o 2",
    "Generuje 5 (ostatecznie 3) Punkty Akcji"); //Dokończyć w walce
    public static Skills Wait = new Skills("Zakończ", "Przyjęcie",0,
    "Kończy turę ataku",
    "Nie reaguje na atak");
}
