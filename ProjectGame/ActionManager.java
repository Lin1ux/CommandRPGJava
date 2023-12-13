package ProjectGame;

import java.util.Scanner;

import Combat.UnitStats;
import Combat.AllSkills;
import Combat.Combat;
import Combat.Units;

import ItemObjects.Items;
import ItemObjects.Weapons;
import ItemObjects.Armor;


import java.util.ArrayList;
import java.util.Arrays;

public class ActionManager 
{
    private UnitStats PlayerStats;
    private Inventory Inventory;
    private Integer CurrentPosition;
    
    public void GameOn()
    {
        System.out.println("test");
        Boolean CombatWon = true;
        Boolean GameOver = false;

        //Wejście
        Scanner s = new Scanner(System.in);
        String Action = "";
        
        //Gracz
        /*PlayerStats = new UnitStats("Ryszard",1,12,7,1,6,5); //Dokończyć ustawić obrażenia na 2
        PlayerStats.AddSkill(AllSkills.normalAttack);
        PlayerStats.AddSkill(AllSkills.lightAttack);
        PlayerStats.AddSkill(AllSkills.heavyAttack);*/
        PlayerStats = Units.Ryszard;
        
        Items compas = new Items("Kompas","Kompas, który wskazuje inny kierunek niż północ");
        Weapons firstWeapon = new Weapons("Sztylet", "Dość stary ale wciąż wystarczająco ostry", 6);
        ArrayList<Items> StartItems = new ArrayList<>();
        StartItems.add(compas);
        Inventory = new Inventory(firstWeapon,
            new Armor("Skórzany płaszcz","Stary zużyty płaszcz, który wciąż pełni funkcję ochronne",2,6),
            StartItems);
        Inventory.SetCurrentWeapon(firstWeapon);

        ArrayList<String> GlobalCommands = new ArrayList<String>(Arrays.asList(new String[]{"ekwipunek","statystyki"}));

        //Lokacje
        this.CurrentPosition = new Integer(AllLocations.House);
        ArrayList<Location> World = new ArrayList<Location>();
        ArrayList<String> Commands = new ArrayList<String>(Arrays.asList(new String[]{"drzwi","miecz"}));
        
        Location SwordLocation = new Location(AllLocations.House,"Pokój z mieczem",Visuals.SwordVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"polnoc","wschod","chatka","postac"}));
        Location SmallHouse = new Location(AllLocations.Armadillo,"Mała Chatka",Visuals.ArmadilloVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"zachod","walka","wschod"}));   //Dokończyć usunąć komendę wschód
        Location Road = new Location(AllLocations.Road,"Droga",Visuals.GramlinVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"zachod","platnerz","alchemik","karczma"}));
        Location Village = new Location(AllLocations.Village,"Wioska Dróżka",Visuals.RoadVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"poludnie","walka"}));
        Location Cart = new Location(AllLocations.Cart,"Zniszczony wóz",Visuals.EmptyVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"poludnie","wschod","zachod","karczma","kowal","sklep"}));
        Location Town = new Location(AllLocations.Town,"Miasteczko Piachy",Visuals.EmptyVisual(),Commands);

        World.add(SwordLocation);           //0
        World.add(SmallHouse);              //1
        World.add(Road);                    //2
        World.add(Village);                 //3
        World.add(Cart);                    //4
        World.add(Town);                    //5

        //Przeciwnicy
        UnitStats Enemy = Units.Gremlin;
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
            //Komenda gracza
            if(Action.toLowerCase().equals("statystyki") || Action.toLowerCase().equals("st"))
            {
                OtherFunctions.clearScreen();
                PlayerStats.PrintStats();
                System.out.println(World.get(CurrentPosition).ReturnCommandList());
                System.out.println(GlobalCommands);
                System.out.println("Co robisz?");
            }
            else if(Action.toLowerCase().equals("ekwipunek") || Action.toLowerCase().equals("e"))
            {
                Inventory.InventoryMenu(s,PlayerStats);
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
                World.get(CurrentPosition).Armorer(s);
                OtherFunctions.clearScreen();
                Action = "";
                continue;
            }
            else if(Action.toLowerCase().equals("postac"))
            {   
                OtherFunctions.clearScreen();
                World.get(CurrentPosition).Dialog();
                if(World.get(CurrentPosition).ReturnId() == 1)
                {
                    World.get(0).CommandManager("Add", "odpoczynek");
                }
            }
            else if(Action.toLowerCase().equals("drzwi") || Action.toLowerCase().equals("dr"))
            {
                CurrentPosition = new Integer(World.get(CurrentPosition).Door());
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("chatka") || Action.toLowerCase().equals("ch"))
            {
                CurrentPosition = new Integer(World.get(CurrentPosition).House());
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("polnoc") || Action.toLowerCase().equals("w") || Action.toLowerCase().equals("pn"))
            {
                CurrentPosition = new Integer(World.get(CurrentPosition).North());
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("poludnie") || Action.toLowerCase().equals("s") || Action.toLowerCase().equals("pd"))
            {
                CurrentPosition = new Integer(World.get(CurrentPosition).South());
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("wschod") || Action.toLowerCase().equals("a") || Action.toLowerCase().equals("ws"))
            {
                CurrentPosition = new Integer(World.get(CurrentPosition).East());
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            else if(Action.toLowerCase().equals("zachod") || Action.toLowerCase().equals("d") || Action.toLowerCase().equals("za"))
            {
                CurrentPosition = new Integer(World.get(CurrentPosition).West());
                Action = "";
                OtherFunctions.clearScreen();
                continue;
            }
            if(!CombatWon)
            {
                OtherFunctions.clearScreen();
                System.out.println("Zostałeś pokonany");
                GameOver = false;
            }
            if(!GameOver)
            {
                Action = s.nextLine();  
            }
            OtherFunctions.clearScreen();
        }
        s.close();
    }    
}
