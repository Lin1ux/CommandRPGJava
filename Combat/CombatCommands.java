package Combat;

import java.util.ArrayList;

import ProjectGame.OtherFunctions;

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
    public static Integer TheCheapestAction(ArrayList<Skills> Actions, Boolean IgnoreZero)
    {
        Integer min = Actions.get(0).ReturnCost();
        for(int i=1;i<Actions.size();i++)
        {
            if(Actions.get(i).ReturnCost() > 1)
            {
                if(min > Actions.get(i).ReturnCost())
                {
                    min = Actions.get(i).ReturnCost();
                }
            }
        }
        return min;
    }
    public static void Result(UnitStats Deffender,UnitStats Attacker, Integer AttackValue, Integer DefenceValue,
    Boolean ResultForAttacker,String OMessage, String DMessage,ArrayList<Status> AttackerStatus,ArrayList<Status> DefenderStatus)
    {
        Integer Diffrence = DefenceValue - AttackValue;
        System.out.println("Obrona: "+DefenceValue+" vs Atak: "+AttackValue);
        if(Diffrence<0)
        {
            Deffender.ChangeHP(Diffrence);
            if(ResultForAttacker)
            {
                System.out.println("Atak zadał "+(-1*Diffrence)+" obrażeń");
                for(int i=0;i<AttackerStatus.size();i++)
                {
                    if(!Deffender.ReturnAllStatuses().contains(AllStatus.Mechanism))
                    {
                        Deffender.AddStatus(AttackerStatus.get(i));
                        if(!AttackerStatus.get(i).equals(AllStatus.CounterAttack))
                        {
                            System.out.println("Nałożono efekt: "+AttackerStatus.get(i).ReturnName());
                        }
                    }
                }
                for(int i=0;i<DefenderStatus.size();i++)
                {
                    Attacker.AddStatus(DefenderStatus.get(i));
                }
            }
            else
            {
                System.out.println("Otrzymano "+(-1*Diffrence)+" obrażeń");
                for(int i=0;i<AttackerStatus.size();i++)                    //Statusy nakładane przez napastnika
                {
                    //System.out.println(AttackerStatus.get(i).ReturnName()+"=="+AllStatus.LifeSteal.ReturnName());
                    if(AttackerStatus.get(i).ReturnName().equals(AllStatus.LifeSteal.ReturnName()))   //Kradzież życia
                    {
                        int heal = (int) Math.ceil((double) (Diffrence*-1)/4);
                        Attacker.ChangeHP(heal);
                        System.out.println(Attacker.ReturnName()+" Leczy "+heal+" PZ");
                    }
                    else
                    {
                        if(!Deffender.ReturnAllStatuses().contains(AllStatus.Mechanism))
                        {
                            Deffender.AddStatus(AttackerStatus.get(i));
                            if(!AttackerStatus.get(i).equals(AllStatus.CounterAttack))
                            {
                                System.out.println("Otrzymano efekt: "+AttackerStatus.get(i).ReturnName());
                            }
                        }
                    }
                }
                for(int i=0;i<DefenderStatus.size();i++)            //Statusy nakładane przez obrońce
                {
                    Attacker.AddStatus(DefenderStatus.get(i));
                }
            }
        }
        else
        {
            if(ResultForAttacker)
            {
                System.out.println("Atak nie zadał żadnych obrażeń");
            }
            else
            {
                System.out.println("Atak przeciwnika nie zadaje tobie obrażeń");
            }
        }
        if(!DMessage.equals(""))
        {
            System.out.println(DMessage);
        }
        else if(!OMessage.equals(""))
        {
            System.out.println(OMessage);
        }
    }
    public static ArrayList<Status> AttackStatuses(ArrayList<Status> AttackerStatuses)
    {
        ArrayList<Status> newStatuses = new ArrayList<Status>();
        for(int i=0;i<AttackerStatuses.size();i++)
        {
            if(AttackerStatuses.get(i).equals(AllStatus.PoisonAttack)) //Trujący cios
            {
                newStatuses.add(new Status(AllStatus.Poison));
            }
            else if(AttackerStatuses.get(i).equals(AllStatus.StunAttack)) //Ogłuszający cios
            {
                newStatuses.add(new Status(AllStatus.Stun));
            }
            else if(AttackerStatuses.get(i).equals(AllStatus.LifeSteal)) //Ogłuszający cios
            {
                newStatuses.add(new Status(AllStatus.LifeSteal));
            }
        }
        return newStatuses;
    }
    public static void CombatWindow(UnitStats PlayerStats,UnitStats EnemyStats, String Visuals)
    {
        System.out.println(Visuals);
        System.out.println("+---------------++---------------+");
        System.out.println("|"+OtherFunctions.FormatValues(" ",PlayerStats.ReturnName(),15,2)+"||"+OtherFunctions.FormatValues(" ",EnemyStats.ReturnName(),15,2)+"|");
        System.out.println("|"+OtherFunctions.FormatValues(" ","PZ:"+PlayerStats.ReturnHP().toString()+"/"+PlayerStats.ReturnMaxHP().toString(),15,2)+"||"+OtherFunctions.FormatValues(" ",OtherFunctions.FormatValues(" ","PZ:"+EnemyStats.ReturnHP().toString()+"/"+EnemyStats.ReturnMaxHP().toString(),15,2),15,2)+"|");
        System.out.println("|"+OtherFunctions.FormatValues(" ","PA:"+PlayerStats.ReturnMana().toString()+"/"+PlayerStats.ReturnMaxMana().toString(),15,2)+"||"+OtherFunctions.FormatValues(" ",OtherFunctions.FormatValues(" ","PA:"+EnemyStats.ReturnMana().toString()+"/"+EnemyStats.ReturnMaxMana().toString(),15,2),15,2)+"|");
        System.out.println("+---------------++---------------+");
    }
}
