package Combat;

import java.util.ArrayList;
import java.util.Arrays;

public class Units 
{
    //Dokończyć zmienić dmg do 6
    public static UnitStats Ryszard = new UnitStats("Ryszard",50,12,7,2,6,20,
    new ArrayList<Skills>(Arrays.asList(new Skills[]{AllSkills.lightAttack,AllSkills.normalAttack,AllSkills.heavyAttack})));    
    public static UnitStats Gremlin = new UnitStats("Zgred",15,6,5,1,7,15,
    new ArrayList<Skills>(Arrays.asList(new Skills[]{AllSkills.lightAttack,AllSkills.normalAttack})));
}
