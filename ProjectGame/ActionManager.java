package ProjectGame;

import java.util.Scanner;

import Combat.UnitStats;
import Combat.AllSkills;
import Combat.Combat;

import ItemObjects.Items;
import ItemObjects.Weapons;
import ItemObjects.Armor;


import java.util.ArrayList;
import java.util.Arrays;

//import ProjectGame.UnitStats;
//import ProjectGame.OtherFunctions;
//import ProjectGame.Location;
//import ProjectGame.Visuals;

public class ActionManager 
{
    private UnitStats PlayerStats;
    private Inventory Inventory;
    private Integer CurrentPosition;
    
    public void GameOn()
    {
        Boolean GameOver = false;

        //Wejście
        Scanner s = new Scanner(System.in);
        String Action = "";
        
        //Gracz
        PlayerStats = new UnitStats(50,12,7,5,6,5); //Dokończyć ustawić obrażenia na 2
        PlayerStats.AddSkill(AllSkills.normalAttack);
        PlayerStats.AddSkill(AllSkills.lightAttack);
        PlayerStats.AddSkill(AllSkills.heavyAttack);
        
        Items compas = new Items("Kompas","Kompas, który wskazuje inny kierunek niż północ");
        Weapons firstWeapon = new Weapons("Pięści", "Każda istota może używać części ciała do walki", 2);
        ArrayList<Items> StartItems = new ArrayList<>();
        StartItems.add(compas);
        Inventory = new Inventory(firstWeapon,
            new Armor("Skórzany płaszcz","Stary zużyt płaszcz, który wciąż pełni funkcję ochronne",5),
            StartItems);
        Inventory.SetCurrentWeapon(firstWeapon);

        ArrayList<String> GlobalCommands = new ArrayList<String>(Arrays.asList(new String[]{"ekwipunek","statystyki"}));

        //Lokacje
        this.CurrentPosition = new Integer(0);
        ArrayList<Location> World = new ArrayList<Location>();
        ArrayList<String> Commands = new ArrayList<String>(Arrays.asList(new String[]{"drzwi","miecz"}));
        
        Location SwordLocation = new Location(0,"Pokój z mieczem",Visuals.SwordVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"polnoc","wschod","chatka","postac"}));
        Location SmallHouse = new Location(1,"Mała Chatka",Visuals.ArmadilloVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"zachod","walka"}));
        Location Road = new Location(2,"Droga",Visuals.GramlinVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"zachod","platnerz","alchemik"}));
        Location Village = new Location(3,"Wioska Dróżka",Visuals.RoadVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"poludnie","walka"}));
        Location Cart = new Location(4,"Wioska Dróżka",Visuals.EmptyVisual(),Commands);
        Commands = new ArrayList<String>(Arrays.asList(new String[]{"poludnie","wschod","zachod","karczma","kowal","sklep"}));
        Location Town = new Location(5,"Miasteczko Piachy",Visuals.EmptyVisual(),Commands);

        World.add(SwordLocation);           //0
        World.add(SmallHouse);              //1
        World.add(Road);                    //2
        World.add(Village);                 //3
        World.add(Cart);                    //4
        World.add(Town);                    //5

        //Przeciwnicy
        UnitStats Enemy = new UnitStats(15,6,5,1,7,5);
        //Enemy.AddSkill(AllSkills.normalAttack);
        Enemy.AddSkill(AllSkills.lightAttack);
        Enemy.AddSkill(AllSkills.normalAttack);
        Combat Gremlin = new Combat( "Zgred",Visuals.GramlinVisual(),Enemy);

        while(!GameOver)
        {
            //Wyświetlanie lokacji
            World.get(CurrentPosition).SetInventory(Inventory);
            if(CurrentPosition == 0)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Znajdujesz się w ciemnym pomieszczeniu");
            }
            else if(CurrentPosition == 1)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Zauważasz małą chatkę, a obok niej stoi dziwna postać w skorupie");
            }
            else if(CurrentPosition == 2)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Na środku drogi stoi Zgredek, który nie chce przepuścić ciebie dalej");
            }
            else if(CurrentPosition == 4)
            {
                World.get(CurrentPosition).SetGlobalCommands(GlobalCommands);
                World.get(CurrentPosition).Description("Przed tobą znajduje się przewrócony wóz oraz Raptora atakującego jego właścicieli");
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
            else if(Action.toLowerCase().equals("walka"))
            {
                if(World.get(CurrentPosition).ReturnCommandList().contains("walka"))
                {
                    Gremlin.CombatScript(s,PlayerStats);
                }
                //Dokończyć Sprawdzić czy taka komenda jest dostępna
                
                //World.get(CurrentPosition).Sleep(PlayerStats);
            }
            else if(Action.toLowerCase().equals("miecz"))
            {   
                World.get(CurrentPosition).Sword(s);
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
            if(!GameOver)
            {
                Action = s.nextLine();  
            }
            OtherFunctions.clearScreen();
        }
        s.close();
    }    
}
