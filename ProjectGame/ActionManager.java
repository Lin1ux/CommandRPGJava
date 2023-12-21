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

        World.add(AllLocations.SwordLocation);                   
        World.add(AllLocations.SmallHouseLocation);              
        World.add(AllLocations.RoadLocation);                    
        World.add(AllLocations.VillageLocation);                 
        World.add(AllLocations.CartLocation);                    
        World.add(AllLocations.TownLocation);                    
        World.add(AllLocations.LakeLocation);   
        World.add(AllLocations.CaveLocation);   
        World.add(AllLocations.CaveQuestLocation);              
        World.add(AllLocations.LastRoomLocation); 

        //Przeciwnicy
        UnitStats Enemy1 = Units.Gramlin;
        UnitStats Enemy2 = Units.Raptor;
        UnitStats Enemy3 = Units.BloodThister;
        UnitStats Enemy4 = Units.Golem;
        //Walki
        Combat Gremlin = new Combat( "Zgred",Visuals.GramlinVisual(),Enemy1);
        Combat Raptor = new Combat( "Raptor",Visuals.RaptorVisual(),Enemy2);
        Combat BloodThister = new Combat( "Krwiopijec",Visuals.BloodThisterVisual(),Enemy3);
        Combat Golem = new Combat( "Golem",Visuals.GramlinVisual(),Enemy4);

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
                if(World.get(CurrentPosition).ReturnCommandList().contains("walka"))
                {    
                    World.get(CurrentPosition).Description("Na twojej drodze stoi wóz");
                }
                else
                {
                    World.get(CurrentPosition).SetVisual(Visuals.CartVisual());
                    World.get(CurrentPosition).Description("Na drodze znajduje się zniszczony wóz");
                }
            }
            else if(CurrentPosition == AllLocations.Town)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Znajdujesz się w miasteczku Piachy");
            }
            else if(CurrentPosition == AllLocations.Lake)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                if(World.get(CurrentPosition).ReturnCommandList().contains("walka"))
                {    
                    World.get(CurrentPosition).Description("Przed tobą znajduje się przerażający wojownik");
                }
                else
                {
                    World.get(CurrentPosition).SetVisual(Visuals.LakeVisual());
                    World.get(CurrentPosition).Description("Przed tobą znajduje się spokojne jezioro");
                }
            }
            else if(CurrentPosition == AllLocations.CaveEntrance)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Przed tobą znajdują się zamknięte wrota");
            }
            else if(CurrentPosition == AllLocations.CaveQuest)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                if(World.get(CurrentPosition).ReturnCommandList().contains("wejscie"))
                {    
                    World.get(CurrentPosition).Description("Wchodzisz do pokoju i zauważasz naczynie");
                }
                else
                {
                    World.get(CurrentPosition).Description("Pokój wygląda na ślepy zaułek. Na jego końcu ktoś wyrył w skale jakiś tekst.");
                }
            }
            else if(CurrentPosition == AllLocations.LastRoom)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Drzwi zamykają się za tobą, a przed tobą znajduje się artefakt.");
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
                    System.out.println("O) "+PlayerStats.ReturnSkillByIndex(i).ReturnName("O")+" - "+PlayerStats.ReturnSkillByIndex(i).ReturnDescr("O"));
                    System.out.println("D) "+PlayerStats.ReturnSkillByIndex(i).ReturnName("D")+" - "+PlayerStats.ReturnSkillByIndex(i).ReturnDescr("D"));
                    System.out.println("Koszt: "+PlayerStats.ReturnSkillByIndex(i).ReturnCost());
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
                ChangeCurrentPosition(Inventory.InventoryMenu(s,PlayerStats,CurrentPosition,World));
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
                    if(CurrentPosition == AllLocations.Road)
                    {
                        CombatWon = Gremlin.CombatScript(s,PlayerStats);
                        World.get(CurrentPosition).CommandManager("remove","walka");
                        World.get(CurrentPosition).CommandManager("add","zachod");
                    }
                    else if(CurrentPosition == AllLocations.Cart)
                    {
                        CombatWon = Raptor.CombatScript(s,PlayerStats);
                        World.get(CurrentPosition).CommandManager("remove","walka");
                        World.get(CurrentPosition).CommandManager("add","polnoc");
                        World.get(CurrentPosition).CommandManager("add","woz");
                    }
                    else if(CurrentPosition == AllLocations.Lake)
                    {
                        CombatWon = BloodThister.CombatScript(s,PlayerStats);
                        World.get(CurrentPosition).CommandManager("remove","walka");
                        Inventory.AddArmor(ItemList.ThornArmor);
                        System.out.println("Otrzymujesz kolczastą zbroję");
                    }
                    else if(CurrentPosition == AllLocations.LastRoom)
                    {
                        CombatWon = Golem.CombatScript(s,PlayerStats);
                        World.get(CurrentPosition).CommandManager("remove","walka");
                        World.get(CurrentPosition).CommandManager("add","artefakt");
                    }
                }
                else
                {
                    OtherFunctions.clearScreen(); 
                }
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("miecz"))
            {   
                World.get(CurrentPosition).Sword(s);
            }
            else if(Action.toLowerCase().equals("wrota"))
            {   
                World.get(CurrentPosition).Gate(s);
            }
            else if(Action.toLowerCase().equals("platnerz"))
            {
                World.get(CurrentPosition).Armorer(s,PlayerStats);
                OtherFunctions.clearScreen();
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("kowal"))
            {
                World.get(CurrentPosition).Blacksmith(s,PlayerStats);
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
                World.get(CurrentPosition).Armadillo(s,PlayerStats);
                if(World.get(CurrentPosition).ReturnId() == 1)
                {
                    World.get(0).CommandManager("Add", "odpoczynek");
                }
            }
            else if(Action.toLowerCase().equals("trening"))
            {   
                OtherFunctions.clearScreen();
                World.get(CurrentPosition).Armadillo(s,PlayerStats);
            }
            else if(Action.toLowerCase().equals("woz"))
            {   
                OtherFunctions.clearScreen();
                World.get(CurrentPosition).BrokenCart(s,PlayerStats);
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("skrzynka"))
            {   
                OtherFunctions.clearScreen();
                World.get(CurrentPosition).Chest(PlayerStats);
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("naczynie"))
            {   
                OtherFunctions.clearScreen();
                World.get(CurrentPosition).Vessel();
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("tekst"))
            {
                OtherFunctions.clearScreen();
                World.get(CurrentPosition).Quest();
            }
            else if(Action.toLowerCase().equals("powrot"))
            {   
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).Return()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
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
            else if(Action.toLowerCase().equals("loch"))
            {   
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).Dungeon()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("wejscie"))
            {   
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).Entrance()));
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
            else if(Action.toLowerCase().equals("wschod") || Action.toLowerCase().equals("d") || Action.toLowerCase().equals("ws"))
            {
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).East()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("zachod") || Action.toLowerCase().equals("a") || Action.toLowerCase().equals("za"))
            {
                ChangeCurrentPosition(new Integer(World.get(CurrentPosition).West()));
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            if(!CombatWon)
            {
                OtherFunctions.clearScreen();
                System.out.println("Pokonano cię!");
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
