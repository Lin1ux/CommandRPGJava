package Combat;

import java.util.ArrayList;

public class CombatCommands {
    //Odczytuje nazwy z klasy Skills
    //--------------------------------------------------------
    public static ArrayList<String> SkillNames(ArrayList<Skills> SkillList,String mode)
    {
        ArrayList<String> Names = new ArrayList<>();
        if(mode.equals("O"))
        {
            for(int i=0;i<SkillList.size();i++)
            {
                Names.add(SkillList.get(i).ReturnName("O"));
            }
        }
        else if(mode.equals("D"))
        {
            for(int i=0;i<SkillList.size();i++)
            {
                Names.add(SkillList.get(i).ReturnName("D"));
            }
        }
        return Names;
    }
    //Odczytuje informacje o zdolności z klasy Skills
    //--------------------------------------------------------
    public static String AllSkillInfo(ArrayList<Skills> SkillList)
    {
        String Names = new String("");
        for(Integer i=0;i<SkillList.size();i++)
        {
            Integer j = i+1;
            Names += (j.toString()+") "+SkillList.get(i).ReturnName("O")+" | "+SkillList.get(i).ReturnName("D")+ " (" + SkillList.get(i).ReturnCost()+")\n");
        }
        return Names;
    }
    public static void Result(UnitStats Deffender, Integer AttackValue, Integer DefenceValue)
    {
        Integer Diffrence = DefenceValue - AttackValue;
        System.out.println("Obrona: "+DefenceValue+" vs Atak: "+AttackValue);
        if(Diffrence<0)
        {
            Deffender.ChangeHP(Diffrence);
            System.out.println("Atak zadał "+Diffrence+" Obrażeń");
        }
        else
        {
            System.out.println("Atak zakończył się niepowedzeniem");
        }
    }
}
