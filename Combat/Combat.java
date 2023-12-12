package Combat;

import java.util.ArrayList;
import java.util.Scanner;

import ProjectGame.OtherFunctions;

public class Combat 
{
    String name;
    String visual;
    UnitStats EnemyStats;
    
    public Combat(String newName,String newVisual,Integer newHP,Integer newDMG,Integer newMana,Integer newArmor,Integer newSpeed,Integer newGold)
    {
        SetName(newName);
        SetVisual(newVisual);
        EnemyStats = new UnitStats(newHP,newDMG,newMana,newArmor,newSpeed,newGold);
        EnemyStats.SetName(newName);
    }    
    public Combat(String newName,String newVisual,UnitStats enemy)
    {
        SetName(newName);
        SetVisual(newVisual);
        EnemyStats = enemy;
        EnemyStats.SetName(newName);
    }    
    public void CombatScript(Scanner s,UnitStats PlayerStats)
    {
        
        UnitStats DefaultEStats = EnemyStats;
        UnitStats DefaultPStats = PlayerStats; 
        ArrayList<Skills> EnemyActions = new ArrayList<Skills>();
        ArrayList<Skills> PlayerActions = new ArrayList<Skills>();
        String Action = "";

        Boolean EndFight = false;
        Boolean PlayerAttack;
        Boolean NextCharacter = false;
        if(PlayerStats.ReturnSpeed()>EnemyStats.ReturnSpeed()) //Do kogo należy pierwszy atak
        {
            PlayerAttack = true;
        }
        else
        {
            PlayerAttack = false;
        }
        for(int i=0;i<4;i++)
        {
            int x;
            x = OtherFunctions.RandInt(0,PlayerStats.AmountOfSkills());
            PlayerActions.add(PlayerStats.ReturnSkillByIndex(x));
            x = OtherFunctions.RandInt(0,EnemyStats.AmountOfSkills());
            EnemyActions.add(EnemyStats.ReturnSkillByIndex(x));
        }
        PlayerActions.add(AllSkills.Wait);
        EnemyActions.add(AllSkills.Wait);
        OtherFunctions.clearScreen();
        while(!EndFight)
        {
            CombatCommands.CombatWindow(PlayerStats, EnemyStats, visual);
            if(PlayerAttack)    //Atak Gracza
            {
                System.out.println("Dostępne akcje (Ofensywa)");
                System.out.println(CombatCommands.AllSkillInfo(PlayerActions));
                System.out.println("Podaj numer akcji (1-"+PlayerActions.size()+")");
                Action = s.nextLine();
                OtherFunctions.clearScreen();
                if(Action.equals("1") || Action.equals("2") || Action.equals("3") || Action.equals("4") || Action.equals("5")) //Odpowiednia komenda
                {
                    if(Integer.valueOf(Action) <= PlayerActions.size()) //Czy komenda mieści się w zakresie
                    {
                        Integer x = Integer.valueOf(Action) - 1;
                        if(PlayerActions.get(x).ReturnCost() <= PlayerStats.ReturnMana())   //Czy stać na umięjętność
                        {
                            if(!PlayerActions.get(x).equals(AllSkills.Wait))    //Czy nie jest akcją końca tury
                            {
                                Integer AttackValue = 0;
                                Integer DefenceValue = 0;
                                
                                System.out.println(PlayerActions.get(x).ReturnName("O"));
                                PlayerStats.ChangeMana(-PlayerActions.get(x).ReturnCost());
                                
                                //Atak Gracza
                                if(PlayerActions.get(x).equals(AllSkills.normalAttack)) //Zwykły atak
                                {
                                    AttackValue = new Integer(PlayerStats.ReturnDMG());
                                }
                                else if(PlayerActions.get(x).equals(AllSkills.lightAttack)) //Lekki atak
                                {
                                    AttackValue = new Integer(( (int) Math.ceil((double) PlayerStats.ReturnDMG()/2)));
                                }
                                else if(PlayerActions.get(x).equals(AllSkills.heavyAttack)) //Ciężki atak
                                {
                                    AttackValue = new Integer(( (int) Math.ceil((double) PlayerStats.ReturnDMG()*1.5)));
                                }
                                //Obrona Przeciwnika
                                //Losowanie akcji defensywnej przeciwnika
                                Boolean DeffenceActionDone = false;
                                while(!DeffenceActionDone)
                                {
                                    Integer y = OtherFunctions.RandInt(0,EnemyActions.size());
                                    if(EnemyActions.get(y).ReturnCost() <= EnemyStats.ReturnMana())
                                    {
                                        System.out.println(PlayerActions.get(x).ReturnName("O")+" vs "+EnemyActions.get(y).ReturnName("D"));
                                        EnemyStats.ChangeMana(-EnemyActions.get(y).ReturnCost());
                                        if(EnemyActions.get(y).equals(AllSkills.normalAttack)) //Zwykły atak
                                        {
                                            DefenceValue = EnemyStats.ReturnDMG() + EnemyStats.ReturnArmor();
                                        }
                                        if(EnemyActions.get(y).equals(AllSkills.lightAttack)) //Lekki atak
                                        {
                                            Integer Chance = OtherFunctions.RandInt(0,20);
                                            if(Chance < 10+EnemyStats.ReturnSpeed()-PlayerStats.ReturnSpeed())
                                            {
                                                DefenceValue = AttackValue;
                                            }
                                            else
                                            {
                                                DefenceValue = EnemyStats.ReturnArmor();
                                            }
                                        }
                                        else if(EnemyActions.get(y).equals(AllSkills.Wait))
                                        {
                                            DefenceValue = EnemyStats.ReturnArmor();
                                        }
                                        CombatCommands.Result(EnemyStats,AttackValue,DefenceValue,true);
                                        DeffenceActionDone = true;
                                    }
                                }
                                PlayerActions.remove(PlayerActions.get(x));
                                //Losowanie akcji defensywnej prz
                            }
                            else
                            {
                                for(int i=PlayerActions.size();i<4;i++)
                                {
                                    int n;
                                    n = OtherFunctions.RandInt(0,PlayerStats.AmountOfSkills());
                                    PlayerActions.add(PlayerStats.ReturnSkillByIndex(n));
                                }
                                PlayerStats.ResetMana();
                                PlayerAttack = false;
                                PlayerActions.remove(PlayerActions.get(x));
                                PlayerActions.add(AllSkills.Wait);
                            }
                        }
                        else
                        {
                            System.out.println("Nie posiadasz wystarczającej liczby punktów akcji");
                        }
                    }
                    else
                    {
                        System.out.println("Nie znana komenda.");
                    }
                }
                else
                {
                    System.out.println("Nie znana komenda.");
                }
            }
            else //Atak przeciwnika
            {
                OtherFunctions.clearScreen();
                Boolean OffenceActionDone = false;
                while(!OffenceActionDone)
                {
                    Integer y = OtherFunctions.RandInt(0,EnemyActions.size()-1);
                    //Sprawdzenie czy można użyć akcji
                    if(EnemyStats.ReturnMana() < CombatCommands.TheCheapestAction(EnemyActions,true) || EnemyActions.size() <= 1)
                    {
                        //Koniec tury przeciwnika
                        for(int i=EnemyActions.size();i<4;i++)
                        {
                            int n;
                            n = OtherFunctions.RandInt(0,EnemyStats.AmountOfSkills());
                            EnemyActions.add(EnemyStats.ReturnSkillByIndex(n));
                        }
                        EnemyStats.ResetMana();
                        EnemyActions.remove(EnemyActions.get(y));
                        EnemyActions.add(AllSkills.Wait);
                        
                        OffenceActionDone = true;
                        PlayerAttack = true;

                        OtherFunctions.clearScreen();
                        System.out.println(name+": Kończy turę");
                    }
                    else
                    {
                        if(EnemyActions.get(y).ReturnCost() <= EnemyStats.ReturnMana())
                        {
                            //Atak Przeciwnika
                            Integer AttackValue = 0;
                            Integer DefenceValue = 0;
                            
                            EnemyStats.ChangeMana(-EnemyActions.get(y).ReturnCost());        //Zmniejszenie punktów akcji
                            //Atak Przeciwnika
                            if(EnemyActions.get(y).equals(AllSkills.normalAttack)) //Zwykły atak
                            {
                                AttackValue = new Integer(EnemyStats.ReturnDMG());
                            }
                            else if(EnemyActions.get(y).equals(AllSkills.lightAttack)) //Lekki atak
                            {
                                AttackValue = new Integer(( (int) Math.ceil((double) EnemyStats.ReturnDMG()/2)));
                            }
                            else if(EnemyActions.get(y).equals(AllSkills.heavyAttack)) //Ciężki atak
                            {
                                AttackValue = new Integer(( (int) Math.ceil((double) EnemyStats.ReturnDMG()*1.5)));
                            }
                            //Akcja Defensywna gracza
                            Boolean PlayerActionDone = false;
                            while(!PlayerActionDone)
                            {
                                //Dane
                                System.out.println(EnemyActions.get(y).ReturnName("O")+" - Wartość Ataku = "+AttackValue);    //Wypisanie akcji
                                CombatCommands.CombatWindow(PlayerStats, EnemyStats, visual);    //Wyświetlenie okna walki
                                System.out.println("Dostępne akcje (Defensywa)");
                                System.out.println(CombatCommands.AllSkillInfo(PlayerActions));
                                System.out.println("Podaj numer akcji (1-"+PlayerActions.size()+")");
                                Action = s.nextLine();
                                if(Action.equals("1") || Action.equals("2") || Action.equals("3") || Action.equals("4") || Action.equals("5")) //Odpowiednia komenda
                                {
                                    if(Integer.valueOf(Action) <= PlayerActions.size()) //Czy komenda mieści się w zakresie
                                    {
                                        Integer x = Integer.valueOf(Action) - 1;
                                        if(PlayerActions.get(x).ReturnCost() <= PlayerStats.ReturnMana())   //Czy stać na umięjętność
                                        {
                                            PlayerStats.ChangeMana(-PlayerActions.get(x).ReturnCost());     //Zużycie punktów Akcji
                                            if(!PlayerActions.get(x).equals(AllSkills.Wait))                //Czy nie jest akcją końca tury
                                            {
                                                //Wybranie odpowiedniej akcji obronnej
                                                if(PlayerActions.get(x).equals(AllSkills.normalAttack))     //Obrona
                                                {
                                                    DefenceValue = PlayerStats.ReturnDMG() + PlayerStats.ReturnArmor();
                                                }
                                                if(PlayerActions.get(x).equals(AllSkills.lightAttack))      //Unik
                                                {
                                                    Integer Chance = OtherFunctions.RandInt(0,20);
                                                    if(Chance < 10+PlayerStats.ReturnSpeed()-EnemyStats.ReturnSpeed())
                                                    {
                                                        DefenceValue = AttackValue;
                                                    }
                                                    else
                                                    {
                                                        DefenceValue = PlayerStats.ReturnArmor();
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                //Przyjęcie ciosu
                                                DefenceValue = PlayerStats.ReturnArmor();
                                            }
                                            OtherFunctions.clearScreen();
                                            System.out.println(EnemyActions.get(y).ReturnName("O")+" vs "+PlayerActions.get(x).ReturnName("D"));
                                            if(!PlayerActions.get(x).equals(AllSkills.Wait))
                                            {
                                                PlayerActions.remove(PlayerActions.get(x));
                                            }
                                            PlayerActionDone = true;
                                        }
                                        else
                                        {
                                            System.out.println("Nie wystarczająca liczba punktów akcji");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Nie znana komenda");
                                    }
                                }
                                else
                                {
                                    System.out.println("Nie znana komenda");
                                }
                            }
                            CombatCommands.Result(PlayerStats,AttackValue,DefenceValue,false);
                        }
                        else
                        {
                            System.out.println("Brak Many");
                        }
                    }
                }
            }
        }
    }
    private void SetName(String newName)
    {
        this.name = newName;
    }
    private void SetVisual(String newVisual)
    {
        visual = newVisual;
    }
}
