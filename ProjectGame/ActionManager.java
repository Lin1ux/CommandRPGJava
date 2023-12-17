package ProjectGame;

import java.util.Scanner;

import Combat.UnitStats;
import Combat.Combat;
import Combat.Units;

import ItemObjects.Items;
import ItemObjects.ItemList;

import java.util.ArrayList;
import java.util.Arrays;

public class ActionManager 
{
    private UnitStats PlayerStats;
    private Inventory Inventory;
    private Integer CurrentPosition;
    
    public void GameOn()
    {
        Boolean CombatWon = true;
        Boolean GameOver = false;

        //Wejście
        Scanner s = new Scanner(System.in);
        String Action = "";
        
        //Gracz
        PlayerStats = Units.Ryszard;
        
        ArrayList<Items> StartItems = new ArrayList<>();
        StartItems.add(ItemList.compass);
        Inventory = new Inventory(ItemList.dagger,ItemList.leatherCoat,StartItems);
        Inventory.SetCurrentWeapon(ItemList.dagger);

        ArrayList<String> GlobalCommands = new ArrayList<String>(Arrays.asList(new String[]{"ekwipunek","statystyki","umiejetnosci"}));

        //Lokacje
        this.CurrentPosition = new Integer(AllLocations.House);
        ArrayList<Location> World = new ArrayList<Location>();

        World.add(AllLocations.SwordLocation);                   //0
        World.add(AllLocations.SmallHouseLocation);              //1
        World.add(AllLocations.RoadLocation);                    //2
        World.add(AllLocations.VillageLocation);                 //3
        World.add(AllLocations.CartLocation);                    //4
        World.add(AllLocations.TownLocation);                    //5

        //Przeciwnicy
        UnitStats Enemy = Units.Gramlin;
        //Walki
        Combat Gremlin = new Combat( "Zgred",Visuals.GramlinVisual(),Enemy);

        while(!GameOver)
        {
            //Wyświetlanie lokacji
            World.get(CurrentPosition).SetInventory(Inventory);
            if(CurrentPosition == AllLocations.House)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Znajdujesz się w ciemnym pomieszczeniu");
            }
            else if(CurrentPosition == AllLocations.Armadillo)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Zauważasz małą chatkę, a obok niej stoi dziwna postać w skorupie");
            }
            else if(CurrentPosition == AllLocations.Road)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                if(World.get(CurrentPosition).ReturnCommandList().contains("walka"))
                {
                    World.get(CurrentPosition).Description("Na środku drogi stoi Zgredek, który nie chce przepuścić ciebie dalej");
                }
                else
                {
                    World.get(CurrentPosition).SetVisual(Visuals.RoadVisual());
                    World.get(CurrentPosition).Description("Otaczają ciebie równiny");
                }
            }
            else if(CurrentPosition == AllLocations.Village)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Znajdujesz się w wiosce Dróżka");
            }
            else if(CurrentPosition == AllLocations.Cart)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Przed tobą zauważasz się przewrócony wóz oraz Raptora atakującego jego właścicieli");
            }
            //Komendy gracza
            if(Action.toLowerCase().equals("statystyki") || Action.toLowerCase().equals("st"))
            {
                OtherFunctions.clearScreen();
                System.out.println("Co robisz?");
                PlayerStats.PrintStats();
                
                System.out.println(World.get(CurrentPosition).ReturnCommandList());
                System.out.println(GlobalCommands);
                System.out.println("Co robisz?");
            }
            else if(Action.toLowerCase().equals("umiejetnosci") || Action.toLowerCase().equals("u"))
            {
                OtherFunctions.clearScreen();
                System.out.println("Dostępne umiejętności podczas walki");
                for(int i=0;i<PlayerStats.AmountOfSkills();i++)
                {
                    System.out.println("---------------------");
                    System.out.println("Koszt: "+PlayerStats.ReturnSkillByIndex(i).ReturnCost());
                    System.out.println("O) "+PlayerStats.ReturnSkillByIndex(i).ReturnName("O")+" - "+PlayerStats.ReturnSkillByIndex(i).ReturnDescr("O"));
                    System.out.println("D) "+PlayerStats.ReturnSkillByIndex(i).ReturnName("D")+" - "+PlayerStats.ReturnSkillByIndex(i).ReturnDescr("D"));
                }
                System.out.println("---------------------");
                System.out.println("Zdolności Pasywne");
                if(PlayerStats.AmountOfStatuses()>0)
                {
                    for(int i=0;i<PlayerStats.AmountOfStatuses();i++)
                    {
                        System.out.println("---------------------");
                        System.out.println(PlayerStats.ReturnStatusByIndex(i).ReturnName()+" - "+PlayerStats.ReturnStatusByIndex(i).ReturnDescr());
                    }
                }
                else
                {
                    System.out.println("Brak");
                }
                System.out.println("---------------------");
                System.out.println(World.get(CurrentPosition).ReturnCommandList());
                System.out.println(GlobalCommands);
                System.out.println("Co robisz?");
            }
            else if(Action.toLowerCase().equals("ekwipunek") || Action.toLowerCase().equals("e"))
            {
                //Ekwipunek
                ChangeCurrentPosition(Inventory.InventoryMenu(s,PlayerStats,CurrentPosition));
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("odpoczynek"))
            {
                OtherFunctions.clearScreen(); 
                World.get(CurrentPosition).Sleep(PlayerStats);
            }
            else if(Action.toLowerCase().equals("walka") || Action.toLowerCase().equals("wa"))
            {
                if(World.get(CurrentPosition).ReturnCommandList().contains("walka"))
                {
                    if(CurrentPosition == 2)
                    {
                        CombatWon = Gremlin.CombatScript(s,PlayerStats);
                        World.get(CurrentPosition).CommandManager("remove","walka");
                        World.get(CurrentPosition).CommandManager("add","wschod");
                    }
                }
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("miecz"))
            {   
                World.get(CurrentPosition).Sword(s);
            }
            else if(Action.toLowerCase().equals("platnerz"))
            {
                World.get(CurrentPosition).Armorer(s,PlayerStats);
                OtherFunctions.clearScreen();
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("alchemik"))
            {
                World.get(CurrentPosition).Alchemists(s,PlayerStats);
                OtherFunctions.clearScreen();
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("karczma"))
            {
                World.get(CurrentPosition).Tavern(s,PlayerStats);
                OtherFunctions.clearScreen();
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("postac"))
            {   
                OtherFunctions.clearScreen();
                World.get(CurrentPosition).Armadillo(PlayerStats);
                if(World.get(CurrentPosition).ReturnId() == 1)
                {
                    World.get(0).CommandManager("Add", "odpoczynek");
                }
            }
            else if(Action.toLowerCase().equals("trening"))
            {   
                OtherFunctions.clearScreen();
                World.get(CurrentPosition).Armadillo(PlayerStats);
            }
            else if(Action.toLowerCase().equals("drzwi") || Action.toLowerCase().equals("dr"))
            {
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).Door()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("chatka") || Action.toLowerCase().equals("ch"))
            {
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).House()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("polnoc") || Action.toLowerCase().equals("w") || Action.toLowerCase().equals("pn"))
            {
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).North()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("poludnie") || Action.toLowerCase().equals("s") || Action.toLowerCase().equals("pd"))
            {
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).South()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("wschod") || Action.toLowerCase().equals("a") || Action.toLowerCase().equals("ws"))
            {
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).East()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("zachod") || Action.toLowerCase().equals("d") || Action.toLowerCase().equals("za"))
            {
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).West()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            if(!CombatWon)
            {
                OtherFunctions.clearScreen();
                System.out.println("Zostałeś pokonany!");
                System.out.println("Wciśnij Enter aby zakończyć grę.");
                Action = s.nextLine();
                GameOver = true;
            }
            if(!GameOver)
            {
                Action = s.nextLine();
                try
                {
                    //Możliwość wywoływania komend za pomocą
                    if(Integer.valueOf(Action)-1 <= World.get(CurrentPosition).ReturnCommandList().size())
                    {
                        Action = World.get(CurrentPosition).ReturnCommandList().get(Integer.valueOf(Action)-1);
                    }
                }
                catch(Exception e)
                {

                }  
            }
            OtherFunctions.clearScreen();
        }
        s.close();
    }    
    public void ChangeCurrentPosition(Integer newPos)
    {
        CurrentPosition = newPos;
    }
}
