package Combat;

import java.util.ArrayList;
import java.util.Scanner;

import ProjectGame.OtherFunctions;

public class Combat 
{
    private String name;
    private String visual;
    private UnitStats EnemyStats;
    
    public Combat(String newName,String newVisual,Integer newHP,Integer newDMG,Integer newMana,Integer newArmor,Integer newSpeed,Integer newGold)
    {
        SetName(newName);
        SetVisual(newVisual);
        EnemyStats = new UnitStats(newName,newHP,newDMG,newMana,newArmor,newSpeed,newGold);
    }    
    public Combat(String newName,String newVisual,UnitStats enemy)
    {
        SetName(newName);
        SetVisual(newVisual);
        EnemyStats = enemy;
        EnemyStats.SetName(newName);
    }    
    public Boolean CombatScript(Scanner s,UnitStats PlayerStats)
    {
        Boolean CombatWon = true;

        UnitStats DefaultPStats = PlayerStats; 
        ArrayList<Skills> EnemyActions = new ArrayList<Skills>();
        ArrayList<Skills> PlayerActions = new ArrayList<Skills>();
        String Action = "";
        String AdditionalOMessage = "";
        String AdditionalDMessage = "";

        Boolean EndFight = false;
        Boolean PlayerAttack;
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
            if(PlayerAttack)    //Atak Gracza
            {
                //Statusy
                AdditionalOMessage = "";
                AdditionalDMessage = "";
                //Nakładanie statusów
                for(int i=0;i<PlayerStats.AmountOfStatuses();i++)
                {
                    if(PlayerStats.ReturnStatusByIndex(i).ReturnName().equals(AllStatus.Poison.ReturnName()))
                    {
                        PlayerStats.ChangeHP(-3);
                        System.out.println("Otrzymujesz 3 obrażenia od trucizny");
                    }
                    else if(PlayerStats.ReturnStatusByIndex(i).ReturnName().equals(AllStatus.Bleed.ReturnName()))
                    {
                        PlayerStats.ChangeHP(-2);
                        System.out.println("Otrzymujesz 2 obrażenia od krwawienia");
                    }
                }
                PlayerStats.ShortStatusDown(); //Zmniejszenie czasu trwania krótkich statusów
                //Sprawdzenie czy statusy nie zabiły gracza
                if(EnemyStats.ReturnHP() <=0) 
                {
                    CombatWon = false;
                    EndFight = true;
                    continue;
                }
                CombatCommands.CombatWindow(PlayerStats, EnemyStats, visual);
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
                                ArrayList<Status> newPlayerStatus = new ArrayList<Status>();    //Status nakładany przez gracza
                                ArrayList<Status> newEnemyStatus = new ArrayList<Status>();     //Status nakładany przez przeciwnika

                                PlayerStats.ChangeMana(-PlayerActions.get(x).ReturnCost());
                                
                                //Atak Gracza
                                if(PlayerActions.get(x).equals(AllSkills.normalAttack)) //Zwykły atak
                                {
                                    AttackValue = new Integer(PlayerStats.ReturnDMG());
                                    newPlayerStatus = CombatCommands.AttackStatuses(PlayerStats.ReturnAllStatuses());
                                }
                                else if(PlayerActions.get(x).equals(AllSkills.lightAttack)) //Lekki atak
                                {
                                    AttackValue = new Integer(( (int) Math.ceil((double) PlayerStats.ReturnDMG()/2)));
                                    newPlayerStatus = CombatCommands.AttackStatuses(PlayerStats.ReturnAllStatuses());
                                }
                                else if(PlayerActions.get(x).equals(AllSkills.heavyAttack)) //Ciężki atak
                                {
                                    AttackValue = new Integer(( (int) Math.ceil((double) PlayerStats.ReturnDMG()*1.5)));
                                    newPlayerStatus = CombatCommands.AttackStatuses(PlayerStats.ReturnAllStatuses());
                                }
                                else if(PlayerActions.get(x).equals(AllSkills.StunAttack)) //Ogłuszający atak
                                {
                                    AttackValue = new Integer(( (int) Math.ceil((double) PlayerStats.ReturnDMG()/5)));
                                    newPlayerStatus = CombatCommands.AttackStatuses(PlayerStats.ReturnAllStatuses());
                                    AdditionalOMessage = EnemyStats.ReturnName()+" traci 2 Punkty Akcji";
                                }
                                else if(PlayerActions.get(x).equals(AllSkills.counterAttack)) //krwawiacy atak
                                {
                                    AttackValue = new Integer(PlayerStats.ReturnDMG());
                                    newPlayerStatus = CombatCommands.AttackStatuses(PlayerStats.ReturnAllStatuses());
                                    newPlayerStatus.add(AllStatus.Bleed);
                                }
                                else if(PlayerActions.get(x).equals(AllSkills.Heal))
                                {
                                    AttackValue = 10;
                                }
                                //Obrona Przeciwnika
                                //Losowanie akcji defensywnej przeciwnika
                                Boolean DefenceActionDone = false;
                                while(!DefenceActionDone)
                                {
                                    Integer y = 0;
                                    if(PlayerActions.get(x).equals(AllSkills.Heal))
                                    {
                                        for(int i=0;i<EnemyActions.size();i++)
                                        {
                                            if(EnemyActions.get(i).equals(AllSkills.Wait))
                                            {
                                                y = i;
                                                i = EnemyActions.size();
                                            }
                                        }
                                    }
                                    else
                                    {
                                        y = OtherFunctions.RandInt(0,EnemyActions.size());
                                    }
                                    if(EnemyActions.get(y).ReturnCost() <= EnemyStats.ReturnMana())
                                    {
                                        System.out.println(PlayerActions.get(x).ReturnName("O")+" vs "+EnemyActions.get(y).ReturnName("D"));
                                        EnemyStats.ChangeMana(-EnemyActions.get(y).ReturnCost());
                                        if(EnemyActions.get(y).equals(AllSkills.normalAttack)) //Zwykły blok
                                        {
                                            DefenceValue = EnemyStats.ReturnDMG();
                                            if(!PlayerStats.ReturnAllStatuses().contains(AllStatus.PenetrationAttack))
                                            {
                                                DefenceValue += EnemyStats.ReturnArmor();
                                            }
                                            else
                                            {
                                                DefenceValue += ( (int) Math.ceil((double) EnemyStats.ReturnArmor()/2));
                                            }
                                        }
                                        else if(EnemyActions.get(y).equals(AllSkills.heavyAttack)) //Ciężki blok
                                        {
                                            DefenceValue = EnemyStats.ReturnDMG();
                                            if(!PlayerStats.ReturnAllStatuses().contains(AllStatus.PenetrationAttack))
                                            {
                                                DefenceValue += EnemyStats.ReturnArmor();
                                            }
                                            else
                                            {
                                                DefenceValue += ( (int) Math.ceil((double) EnemyStats.ReturnArmor()/2));
                                            }
                                            //Redukcja punktu Akcji 
                                            Integer Chance = OtherFunctions.RandInt(0,20);
                                            if(Chance < 10)
                                            {
                                                PlayerStats.ChangeMana(-1);
                                                AdditionalDMessage = "Blok przeciwnika zredukował 1 punkt akcji";
                                            }
                                        }
                                        else if(EnemyActions.get(y).equals(AllSkills.lightAttack) || EnemyActions.get(y).equals(AllSkills.biteAttack)) //Lekki atak
                                        {
                                            Integer Chance = OtherFunctions.RandInt(0,20);
                                            if(Chance < 10+EnemyStats.ReturnSpeed()-PlayerStats.ReturnSpeed() && !PlayerActions.get(x).equals(AllSkills.lightAttack))
                                            {
                                                DefenceValue = AttackValue;
                                            }
                                            else
                                            {
                                                if(!PlayerStats.ReturnAllStatuses().contains(AllStatus.PenetrationAttack))
                                                {
                                                    DefenceValue = EnemyStats.ReturnArmor();
                                                }
                                                else
                                                {
                                                    DefenceValue = new Integer (( (int) Math.ceil((double) EnemyStats.ReturnArmor()/2)));
                                                }
                                            }
                                        }
                                        else if(EnemyActions.get(x).equals(AllSkills.counterAttack))
                                        {
                                            DefenceValue = EnemyStats.ReturnArmor();
                                            newEnemyStatus.add(AllStatus.CounterAttack);
                                        }
                                        else if(EnemyActions.get(y).equals(AllSkills.Wait))
                                        {
                                            if(!PlayerStats.ReturnAllStatuses().contains(AllStatus.PenetrationAttack))
                                            {
                                                DefenceValue = EnemyStats.ReturnArmor();
                                            }
                                            else
                                            {
                                                DefenceValue = new Integer (( (int) Math.ceil((double) EnemyStats.ReturnArmor()/2)));
                                            }
                                        }
                                        else if(EnemyActions.get(y).equals(AllSkills.biteAttack))
                                        {
                                            if(!PlayerStats.ReturnAllStatuses().contains(AllStatus.PenetrationAttack))
                                            {
                                                DefenceValue = EnemyStats.ReturnArmor();
                                            }
                                            else
                                            {
                                                DefenceValue = new Integer (( (int) Math.ceil((double) EnemyStats.ReturnArmor()/2)));
                                            }
                                            EnemyStats.ChangeMana(EnemyActions.get(y).ReturnCost());    //Odnowienie kosztu
                                        }
                                        if(PlayerActions.get(x).equals(AllSkills.Heal))
                                        {
                                            PlayerStats.ChangeHP(AttackValue);
                                            System.out.println("Uleczono "+AttackValue+" PZ");
                                        }
                                        else
                                        {
                                            CombatCommands.Result(EnemyStats,PlayerStats,AttackValue,DefenceValue,true,AdditionalOMessage,AdditionalDMessage,newPlayerStatus,newEnemyStatus);
                                        }
                                        if(PlayerActions.get(x) == AllSkills.StunAttack)
                                        {
                                            EnemyStats.ChangeMana(-2);
                                        }
                                        //Dokończyć
                                        if(PlayerStats.ReturnAllStatuses().contains(AllStatus.CounterAttack))        //Kontraatak
                                        {
                                            System.out.println("--- "+EnemyStats.ReturnName()+" Wykonuje Kontraatak ---");
                                            DefenceValue = PlayerStats.ReturnArmor();
                                            AttackValue = new Integer(( (int) Math.ceil((double) EnemyStats.ReturnDMG()/2)));
                                            CombatCommands.Result(PlayerStats,EnemyStats,AttackValue,DefenceValue,true,"","",new ArrayList<Status>(),new ArrayList<Status>());
                                            PlayerStats.RemoveStatus(AllStatus.CounterAttack);
                                        }
                                        if(EnemyStats.ReturnAllStatuses().contains(AllStatus.Thorns) && AttackValue > DefenceValue && PlayerActions.get(x).equals(AllSkills.Heal))           //Ciernie
                                        {
                                            PlayerStats.ChangeHP(-1);
                                            System.out.println("Kolczasta zbroja zadaje tobie obrażenia równe 1");
                                        }
                                        EnemyActions.remove(EnemyActions.get(y));
                                        DefenceActionDone = true;
                                        //Sprawdzenie czy ktoś jest martwy
                                        if(PlayerStats.ReturnHP()<=0)
                                        {
                                            CombatWon = false;
                                            EndFight = true;
                                        }
                                        else if(EnemyStats.ReturnHP()<=0)
                                        {
                                            CombatWon = true;
                                            EndFight = true;
                                        }
                                    }
                                }
                                PlayerActions.remove(PlayerActions.get(x));
                                //Losowanie akcji defensywnej prz
                            }
                            else
                            {
                                for(int i=PlayerActions.size();i<5;i++)
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
                            OtherFunctions.clearScreen();
                            System.out.println("Nie posiadasz wystarczającej liczby punktów akcji");
                        }
                    }
                    else
                    {
                        OtherFunctions.clearScreen();
                        System.out.println("Nie znana komenda.");
                    }
                }
                else
                {
                    OtherFunctions.clearScreen();
                    System.out.println("Nie znana komenda.");
                }
            }
            else //Atak przeciwnika
            {
                OtherFunctions.clearScreen();
                AdditionalOMessage = "";
                AdditionalDMessage = "";
                Boolean OffenceActionDone = false;
                //Nakładanie statusów
                for(int i=0;i<EnemyStats.AmountOfStatuses();i++)
                {
                    if(EnemyStats.ReturnStatusByIndex(i).ReturnName().equals(AllStatus.Poison.ReturnName()))
                    {
                        EnemyStats.ChangeHP(-3);
                        System.out.println(EnemyStats.ReturnName()+" otrzymuje 3 obrażenia od trucizny");
                    }
                    else if(EnemyStats.ReturnStatusByIndex(i).ReturnName().equals(AllStatus.Bleed.ReturnName()))
                    {
                        EnemyStats.ChangeHP(-2);
                        System.out.println(EnemyStats.ReturnName()+" otrzymuje 2 obrażenia od krwawienia");
                    }
                    else if(EnemyStats.ReturnStatusByIndex(i).ReturnName().equals(AllStatus.Stun.ReturnName()))
                    {
                        EnemyStats.ChangeMana(-1);
                        System.out.println(EnemyStats.ReturnName()+" jest oszołomiony i traci 1 punkt akcji");
                    }
                }
                EnemyStats.ShortStatusDown(); //Zmniejszenie czasu trwania krótkich statusów
                //Sprawdzenie czy statusy nie zabiły przeciwnika
                if(EnemyStats.ReturnHP() <=0)
                {
                    OffenceActionDone = true;
                    CombatWon = true;
                    EndFight = true;
                }
                while(!OffenceActionDone)
                {
                    //Losowanie akcji
                    Integer y = OtherFunctions.RandInt(0,EnemyActions.size()-1);
                    if(EnemyActions.get(y).equals(AllSkills.Wait))
                    {
                        //Zapobieganie wybraniu pominięcia tury
                        continue;
                    }
                    //Sprawdzenie czy można użyć akcji (przeciwnik będzie atakował dopóki posiada wystarczającą liczbę Punktów Akcji na najtańszą umiejętność)
                    if(EnemyStats.ReturnMana() < CombatCommands.TheCheapestAction(EnemyActions,true) || EnemyActions.size() <= 1)
                    {
                        //Koniec tury przeciwnika
                        for(int i=EnemyActions.size();i<5;i++)
                        {
                            int n;
                            n = OtherFunctions.RandInt(0,EnemyStats.AmountOfSkills());
                            EnemyActions.add(EnemyStats.ReturnSkillByIndex(n));
                        }
                        EnemyStats.ResetMana();
                        EnemyActions.remove(AllSkills.Wait);
                        EnemyActions.add(AllSkills.Wait);
                        
                        OffenceActionDone = true;
                        PlayerAttack = true;

                        //OtherFunctions.clearScreen();
                        System.out.println(name+": Kończy turę");
                    }
                    else
                    {
                        //Czy koszt akcji się zgadza
                        if(EnemyActions.get(y).ReturnCost() <= EnemyStats.ReturnMana())
                        {
                            //Atak Przeciwnika
                            Integer AttackValue = 0;
                            Integer DefenceValue = 0;

                            ArrayList<Status> newEnemyStatus = new ArrayList<Status>();     //Status nakładany przez przeciwnika
                            ArrayList<Status> newPlayerStatus = new ArrayList<Status>();    //Status nakładany przez gracza
                            
                            EnemyStats.ChangeMana(-EnemyActions.get(y).ReturnCost());       //Zmniejszenie punktów akcji
                            //Atak Przeciwnika
                            if(EnemyActions.get(y).equals(AllSkills.normalAttack))          //Zwykły atak
                            {
                                AttackValue = new Integer(EnemyStats.ReturnDMG());
                                newEnemyStatus = CombatCommands.AttackStatuses(EnemyStats.ReturnAllStatuses());
                            }
                            else if(EnemyActions.get(y).equals(AllSkills.lightAttack)) //Lekki atak
                            {
                                AttackValue = new Integer(( (int) Math.ceil((double) EnemyStats.ReturnDMG()/2)));
                                newEnemyStatus = CombatCommands.AttackStatuses(EnemyStats.ReturnAllStatuses());
                            }
                            else if(EnemyActions.get(y).equals(AllSkills.heavyAttack)) //Ciężki atak
                            {
                                AttackValue = new Integer(( (int) Math.ceil((double) EnemyStats.ReturnDMG()*1.5)));
                                newEnemyStatus = CombatCommands.AttackStatuses(EnemyStats.ReturnAllStatuses());
                            }
                            else if(EnemyActions.get(y).equals(AllSkills.biteAttack)) //Ugryzienie
                            {
                                AttackValue = new Integer(( (int) Math.ceil((double) EnemyStats.ReturnDMG()/2)));
                                newEnemyStatus = CombatCommands.AttackStatuses(EnemyStats.ReturnAllStatuses());
                            }
                            else if(EnemyActions.get(y).equals(AllSkills.jumpAttack)) //Doskok
                            {
                                AttackValue = new Integer(EnemyStats.ReturnDMG());
                                newEnemyStatus = CombatCommands.AttackStatuses(EnemyStats.ReturnAllStatuses());
                            }
                            else if(EnemyActions.get(y).equals(AllSkills.counterAttack)) //kontraatak
                            {
                                AttackValue = new Integer(EnemyStats.ReturnDMG());
                                newEnemyStatus = CombatCommands.AttackStatuses(EnemyStats.ReturnAllStatuses());
                                newEnemyStatus.add(AllStatus.Bleed);
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
                                                    if(Chance < 10+PlayerStats.ReturnSpeed()-EnemyStats.ReturnSpeed() && !EnemyActions.get(y).equals(AllSkills.lightAttack))
                                                    {
                                                        DefenceValue = AttackValue;
                                                    }
                                                    else
                                                    {
                                                        DefenceValue = PlayerStats.ReturnArmor();
                                                    }
                                                }
                                                if(PlayerActions.get(x).equals(AllSkills.heavyAttack))      //Blok
                                                {
                                                    DefenceValue = PlayerStats.ReturnDMG() + PlayerStats.ReturnArmor();
                                                    //Redukcja punktu Akcji 
                                                    Integer Chance = OtherFunctions.RandInt(0,20);
                                                    if(Chance < 10)
                                                    {
                                                        EnemyStats.ChangeMana(-1);
                                                        AdditionalDMessage = "Blok Zredukował 1 punkt akcji przeciwnika";
                                                    }
                                                }
                                                if(PlayerActions.get(x).equals(AllSkills.StunAttack)) 
                                                {
                                                    DefenceValue = PlayerStats.ReturnArmor();
                                                    AdditionalDMessage = "Odzyskano 3 punkty akcji";
                                                    PlayerStats.ChangeMana(5);
                                                }
                                                if(PlayerActions.get(x).equals(AllSkills.counterAttack))
                                                {
                                                    DefenceValue = PlayerStats.ReturnArmor();
                                                    newPlayerStatus.add(AllStatus.CounterAttack);
                                                }
                                                if(PlayerActions.get(x).equals(AllSkills.Heal))
                                                {
                                                    DefenceValue = PlayerStats.ReturnArmor();
                                                    PlayerStats.ChangeHP(10);
                                                    AdditionalDMessage = "Leczysz 10 PZ";
                                                }
                                            }
                                            else
                                            {
                                                //Przyjęcie ciosu
                                                DefenceValue = PlayerStats.ReturnArmor();
                                            }
                                            //Wynik
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
                                            OtherFunctions.clearScreen();
                                            System.out.println("Nie wystarczająca liczba punktów akcji");
                                        }
                                    }
                                    else
                                    {
                                        OtherFunctions.clearScreen();
                                        System.out.println("Nie znana komenda");
                                    }
                                }
                                else
                                {
                                    OtherFunctions.clearScreen();
                                    System.out.println("Nie znana komenda");
                                }
                            }
                            CombatCommands.Result(PlayerStats,EnemyStats,AttackValue,DefenceValue,false,AdditionalOMessage,AdditionalDMessage,newEnemyStatus,newPlayerStatus);
                            if(EnemyActions.get(y).equals(AllSkills.biteAttack) && AttackValue < DefenceValue)    //Odnowienie PA
                            {
                                EnemyStats.ChangeMana(2);
                                System.out.println(EnemyStats.ReturnName()+" Odzyskuje 2 punkty akcji");
                            }
                            if(EnemyActions.get(y).equals(AllSkills.jumpAttack) && AttackValue > DefenceValue)    //Odnowienie PA
                            {
                                PlayerStats.ChangeMana(-1);
                                System.out.println("Tracisz 2 punkty akcji");
                            }
                            if(EnemyStats.ReturnAllStatuses().contains(AllStatus.CounterAttack))        //Kontraatak
                            {
                                System.out.println("--- Wykonujesz Kontraatak ---");
                                DefenceValue = EnemyStats.ReturnArmor();
                                AttackValue = new Integer(( (int) Math.ceil((double) PlayerStats.ReturnDMG()/2)));
                                CombatCommands.Result(EnemyStats,PlayerStats,AttackValue,DefenceValue,true,"","",new ArrayList<Status>(),new ArrayList<Status>());
                                EnemyStats.RemoveStatus(AllStatus.CounterAttack);
                            }
                            if(PlayerStats.ReturnAllStatuses().contains(AllStatus.Thorns) && AttackValue > DefenceValue)           //Ciernie
                            {
                                EnemyStats.ChangeHP(-1);
                                System.out.println("Kolczasta zbroja zadaje przeciwnikowi obrażenia równe 1");
                            }
                            EnemyActions.remove(EnemyActions.get(y));
                            //Sprawdzenie czy któryś z przeciwników jest martwy
                            if(PlayerStats.ReturnHP()<=0)
                            {
                                OffenceActionDone = true;
                                CombatWon = false;
                                EndFight = true;
                            }
                            else if(EnemyStats.ReturnHP()<=0)
                            {
                                OffenceActionDone = true;
                                CombatWon = true;
                                EndFight = true;
                            }
                        }
                    }
                }
            }
        }
        //Koniec walki reset statystyk przyznanie złota
        OtherFunctions.clearScreen();
        if(CombatWon)
        {
            PlayerStats.CombatStatusDown();
            PlayerStats.SetArmor(DefaultPStats.ReturnArmor());
            PlayerStats.SetDMG(DefaultPStats.ReturnDMG());
            PlayerStats.ResetSpeed();
            PlayerStats.ResetMana();
            PlayerStats.ChangeGold(EnemyStats.ReturnGold());
            System.out.println("Zwycięstwo! Otrzymujesz: "+EnemyStats.ReturnGold()+" złota");
        }
        return CombatWon;
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
