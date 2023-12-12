package Combat;

public class AllSkills 
{
    public static Skills normalAttack = new Skills("Atak", "Obrona",3,
    "Atak zadający pełne obrażenia",
    "Blok redukujący obrażenia posiadanej broni ");
    public static Skills lightAttack = new Skills("Szybkie Uderzenie", "Unik",2,
    "Atak zadający połowe obrażeń ale nie da się go uniknąć",
    "Posiada 50% na uniknięcie obrażeń. Każda przewaga nad szybkością przeciwnika zwiększa szansę o 5%");
    public static Skills heavyAttack = new Skills("Potężny Cios", "Blok",5,
    "Potężny atak zadający 1.5 obrażeń",
    "Blokuje obrażenia równe bronii i posiada 50% na zmniejszenie punktu akcji przeciwnika");
    public static Skills Wait = new Skills("Zakończ", "Przyjęcie",0,
    "Kończy turę ataku",
    "Nie reaguje na atak");
}
