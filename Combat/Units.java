package Combat;

import java.util.ArrayList;
import java.util.Arrays;

public class Units 
{
    public static UnitStats Ryszard = new UnitStats("Ryszard",50,6,9,2,6,0,
new ArrayList<Skills>(Arrays.asList(new Skills[]{AllSkills.lightAttack,AllSkills.normalAttack,AllSkills.heavyAttack})),
    new ArrayList<Status>());    
    
    public static UnitStats Gramlin = new UnitStats("Zgred",30,6,5,1,7,15,
    new ArrayList<Skills>(Arrays.asList(new Skills[]{AllSkills.lightAttack,AllSkills.normalAttack})),
    new ArrayList<Status>());

    public static UnitStats Raptor = new UnitStats("Raptor",35,12,8,2,10,20,
new ArrayList<Skills>(Arrays.asList(new Skills[]{AllSkills.lightAttack,AllSkills.normalAttack,AllSkills.biteAttack,AllSkills.jumpAttack})),
    new ArrayList<Status>());

    public static UnitStats BloodThister = new UnitStats("Krwiopijec",50,14,8,5,5,35,
new ArrayList<Skills>(Arrays.asList(new Skills[]{AllSkills.counterAttack,AllSkills.heavyAttack,AllSkills.normalAttack})),
    new ArrayList<Status>(Arrays.asList(new Status[]{AllStatus.LifeSteal,AllStatus.Thorns})));

    public static UnitStats Golem = new UnitStats("Golem",100,10,12,5,1,50,
new ArrayList<Skills>(Arrays.asList(new Skills[]{AllSkills.heavyAttack,AllSkills.StunAttack,AllSkills.LaserAttack})),
    new ArrayList<Status>(Arrays.asList(new Status[]{AllStatus.Mechanism})));
}
