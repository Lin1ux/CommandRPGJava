package Combat;

import java.util.ArrayList;
import java.util.Arrays;

public class Units 
{
    //Dokończyć zmienić dmg do 6
    public static UnitStats Ryszard = new UnitStats("Ryszard",50,6,7,2,6,50,
new ArrayList<Skills>(Arrays.asList(new Skills[]{AllSkills.lightAttack/*,AllSkills.normalAttack,AllSkills.heavyAttack*/})),
    new ArrayList<Status>());    
    public static UnitStats Gramlin = new UnitStats("Zgred",100,0,5,1,7,15,
    new ArrayList<Skills>(Arrays.asList(new Skills[]{AllSkills.lightAttack,AllSkills.normalAttack})),
    new ArrayList<Status>());
}
