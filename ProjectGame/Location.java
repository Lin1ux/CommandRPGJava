package ProjectGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Combat.AllSkills;
import Combat.UnitStats;
import ItemObjects.Armor;
import ItemObjects.ItemList;
import ItemObjects.Items;
import ItemObjects.Weapons;


public class Location 
{
    private String locationName;
    private Integer locationId;
    private ArrayList<String> commandsList;
    private ArrayList<String> globalCommands;
    private String visual;
    private String lastText = "";
    private ArrayList<String> ProgressArray;

    private Inventory inventory;
    public Location(Integer Id,String name, String visualisation,ArrayList<String> commands)
    {
        this.locationName = name;
        this.locationId = Id;
        this.visual = visualisation;
        this.commandsList = commands; 
        this.ProgressArray = new ArrayList<String>();
        ProgressArray.add("Begin");

    }
    public void SetGlobalCommands(ArrayList<String> commands)
    {
        this.globalCommands = commands;
    }
    public void Description(String text)
    {
        lastText = text;
        System.out.println(text);
        System.out.println(visual);
        System.out.println("Dostępne komendy:");
        System.out.println(commandsList);
        System.out.println(globalCommands);
        System.out.println("Co robisz? (podaj komendę)");
    }
    public void Armadillo(Scanner s,UnitStats Player)
    {
        if(commandsList.contains("postac"))
        {
            CommandManager("remove","postac");
            CommandManager("add","trening");
            Description("Jarosław: Witaj jestem Jarosław. W końcu się obudziłeś jeśli będziesz potrzebować odpoczynku możesz to zrobić w mojej chatce.\nJeśli chcesz mogę ciebie podszkolić. W domu znajduje się miecz przynieś go i możemy zaczynać.");
        }
        else if(commandsList.contains("trening"))
        {
            if(!ProgressArray.contains("FirstTrainDone"))
            {
                if(inventory.ReturnCurentWeapon().equals(ItemList.shortSword))
                {
                    Description("Przechodzisz szkolenie i uczysz się zdolności [ Ogłuszenie Rękojeścią | Gniew ]");
                    Player.AddSkill(AllSkills.StunAttack);
                    ProgressArray.add("FirstTrainDone");
                }
                else
                {
                    Description("Jarosław: Wyjmij miecz, który był w mojej chatce i zaczynajmy trening\n(Możesz założyć miecz za pomocą komendy |ekwipunek|).");
                }
            }
            else if(ProgressArray.contains("FirstTrainDone") && !inventory.ReturnItems().contains(ItemList.Drink))
            {
                Description("Jarosław: Mógłbyś coś dla mnie zrobić? Przynieś mi napój Panceriańśka. Z tego co pamiętam możesz go kupić u alchemika z wschodniej wioski.");
            }
            else if(ProgressArray.contains("FirstTrainDone") && inventory.ReturnItems().contains(ItemList.Drink))
            {   
                String PlayerAction = "";
                Boolean AnswerDone = false;
                while(!AnswerDone)
                {
                    OtherFunctions.clearScreen();
                    System.out.println("Jarosław: Wróciłeś! Czy przyniosłeś mój napój?");
                    QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"daj","nie"})));
                    PlayerAction = s.nextLine();
                    if(PlayerAction.toLowerCase().equals("daj") || PlayerAction.equals("1"))
                    {
                        OtherFunctions.clearScreen();
                        Description("Jarosław: Dziękować! Przejdźmy do treningu\n"+"Przechodzisz szkolenie i uczysz się zdolności [ "+AllSkills.counterAttack.ReturnName("O")+" | "+AllSkills.counterAttack.ReturnName("D")+" ]");
                        Player.AddSkill(AllSkills.counterAttack);
                        ProgressArray.add("SecondTrainDone");
                        inventory.RemoveItem(ItemList.Drink);
                        AnswerDone = true;
                    }
                    else if(PlayerAction.toLowerCase().equals("nie") || PlayerAction.equals("2"))
                    {
                        OtherFunctions.clearScreen();
                        Description("Jarosław: Wielka szkoda");
                        AnswerDone = true;
                    }
                }
            }
            else if(ProgressArray.contains("SecondTrainDone"))
            {
                Description("Jarosław: Ukończyłeś wszystkie moje szkolenia.");
            }
        }
    }
    public Integer Door()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("drzwi"))
        {
            if(locationId == AllLocations.House)
            {
                newLocation = AllLocations.Armadillo;        //Chatka
            }
        }
        return newLocation;
    }
    public Integer House()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("chatka"))
        {
            if(locationId == AllLocations.Armadillo)
            {
                newLocation = AllLocations.House;        //Chatka
            }
        }
        return newLocation;
    }
    public Integer North()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("polnoc"))
        {
            if(locationId == AllLocations.Armadillo)
            {
                newLocation = AllLocations.Cart;        //Wózek
            }
            if(locationId == AllLocations.Cart)
            {
                newLocation = AllLocations.Town;        //Wózek
            }
        }
        return newLocation;
    }
    public Integer South()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("poludnie"))
        {
            if(locationId == AllLocations.Cart)
            {
                newLocation = AllLocations.Armadillo;      
            }
            else if(locationId == AllLocations.Town)
            {
                newLocation = AllLocations.Cart;    
            }
        }
        return newLocation;
    }
    public Integer West()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("zachod"))
        {
            if(locationId == AllLocations.Armadillo)
            {
                newLocation = AllLocations.Road;       
            }
            else if(locationId == AllLocations.Road)
            {
                newLocation = AllLocations.Village;      
            }
            else if(locationId == AllLocations.Town)
            {
                newLocation = AllLocations.Lake;    
            }
            else if(locationId == AllLocations.CaveEntrance)
            {
                newLocation = AllLocations.Town;    
            }
        }
        return newLocation;
    }
    public Integer East()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("wschod"))
        {
            if(locationId == AllLocations.Road)
            {
                newLocation = AllLocations.Armadillo;        
            }
            else if(locationId == AllLocations.Village)
            {
                newLocation = AllLocations.Road;        
            }
            else if(locationId == AllLocations.Town)
            {
                newLocation = AllLocations.CaveEntrance;    
            }
            else if(locationId == AllLocations.Lake)
            {
                newLocation = AllLocations.Town;    
            }
        }
        return newLocation;
    }
    public Integer Dungeon()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("loch"))
        {
            if(locationId == AllLocations.CaveEntrance)
            {
                newLocation = AllLocations.CaveQuest;    
            }
        }
        return newLocation;
    }
    public Integer Return()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("powrot"))
        {
            if(locationId == AllLocations.CaveQuest)
            {
                newLocation = AllLocations.CaveEntrance;    
            }
        }
        return newLocation;
    }
    public Integer Entrance()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("wejscie"))
        {
            if(locationId == AllLocations.CaveQuest)
            {
                newLocation = AllLocations.LastRoom;    
            }
        }
        return newLocation;
    }
    public void Sword(Scanner s)
    {
        if(commandsList.contains("miecz"))
        {
            String PlayerInput = "";
            while(!PlayerInput.toLowerCase().equals("podnies") && !PlayerInput.toLowerCase().equals("p") && !PlayerInput.toLowerCase().equals("1") && !PlayerInput.toLowerCase().equals("zostaw") && !PlayerInput.toLowerCase().equals("z") && !PlayerInput.toLowerCase().equals("2"))
            {
                OtherFunctions.clearScreen();
                System.out.println("Widzisz miecz leżący na ziemii czy chcesz go podnieść?");
                QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"podnies","zostaw"})));
                PlayerInput = s.nextLine();
            }
            if(PlayerInput.toLowerCase().equals("podnies") || PlayerInput.toLowerCase().equals("p") || PlayerInput.toLowerCase().equals("1"))
            {
                OtherFunctions.clearScreen();
                visual = Visuals.DoorVisual();
                commandsList.remove("miecz");
                Description("Miecz został podniesiony (za pomocą komendy |ekwipunek| możesz założyć tą broń)");
                inventory.AddWeapon(ItemList.shortSword);
            }
            else if(PlayerInput.equals("zostaw") || PlayerInput.toLowerCase().equals("z") || PlayerInput.toLowerCase().equals("2"))
            {
                OtherFunctions.clearScreen();
                Description("Postanawiasz nie dotykać miecza");
            }
        }
        else
        {
            return;
        }
    }
    public void Gate(Scanner s)
    {
        if(commandsList.contains("wrota"))
        {
            Boolean EndAction = false;
            String PlayerInput = "";
            while(!EndAction)
            {
                OtherFunctions.clearScreen();
                System.out.println("Przed tobą znajdują się wrota");
                QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"popchnij","kopnij","kompas","wyjdz"})));
                PlayerInput = s.nextLine();
                if(PlayerInput.toLowerCase().equals("popchnij") || PlayerInput.toLowerCase().equals("1"))
                {
                    OtherFunctions.clearScreen();
                    Description("Próbujesz popchnąć drzwi ale wydają się być za ciężkie");
                    EndAction = true;
                }
                else if(PlayerInput.toLowerCase().equals("kopnij") || PlayerInput.toLowerCase().equals("2"))
                {
                    OtherFunctions.clearScreen();
                    Description("Kopiesz prosto w drzwi ale nie przynosi to żadnych skutków");
                    EndAction = true;
                }
                else if(PlayerInput.toLowerCase().equals("kompas") || PlayerInput.toLowerCase().equals("3"))
                {
                    OtherFunctions.clearScreen();
                    commandsList.remove("wrota");
                    commandsList.add("loch");
                    Description("Wyjmujesz kompas, który wskazuje drzwi. Po chwili drzwi otwierają się");
                    EndAction = true;
                }
                else if(PlayerInput.toLowerCase().equals("wyjdz") || PlayerInput.toLowerCase().equals("4"))
                {
                    OtherFunctions.clearScreen();
                    Description("Odchodzisz od drzwi");
                    EndAction = true;
                }
            }
            if(PlayerInput.toLowerCase().equals("podnies") || PlayerInput.toLowerCase().equals("p") || PlayerInput.toLowerCase().equals("1"))
            {
                OtherFunctions.clearScreen();
                visual = Visuals.DoorVisual();
                commandsList.remove("miecz");
                Description("Miecz został podniesiony (za pomocą komendy |ekwipunek| możesz założyć tą broń)");
                inventory.AddWeapon(ItemList.shortSword);
            }
            else if(PlayerInput.equals("zostaw") || PlayerInput.toLowerCase().equals("z") || PlayerInput.toLowerCase().equals("2"))
            {
                OtherFunctions.clearScreen();
                Description("Postanawiasz nie dotykać miecza");
            }
        }
        else
        {
            return;
        }
    }
    public void Alchemists(Scanner s,UnitStats PlayerStats)
    {
        if(commandsList.contains("alchemik"))
        {
            String PlayerInput = "";
            while(!PlayerInput.toLowerCase().equals("wyjscie") && !PlayerInput.toLowerCase().equals("wy") && !PlayerInput.toLowerCase().equals("2"))
            {
                OtherFunctions.clearScreen();
                System.out.println("Alchemik: Witaj w moim sklepie czy chcesz zobaczyć moje towary?");
                QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"sklep","wyjscie"})));
                PlayerInput = s.nextLine();
                if(PlayerInput.toLowerCase().equals("sklep") || PlayerInput.toLowerCase().equals("sk") || PlayerInput.toLowerCase().equals("1"))
                {
                    //Przedmioty do kupienia
                    ArrayList<Items> ShopItems = new ArrayList<Items>();
                    ShopItems.add(ItemList.HPPotion);
                    ShopItems.add(ItemList.Drink);
                    ShopItems.add(ItemList.Poison);
                    PlayerInput = "";
                    Boolean ItemBought = false; //czy kupiono przedmiot
                    OtherFunctions.clearScreen();
                    while(!ItemBought)
                    {
                        System.out.println("Dostępne towary (Posiadane złoto: "+PlayerStats.ReturnGold()+"):");
                        for(int i=0;i<ShopItems.size();i++)
                        {
                            System.out.println((i+1)+") "+ShopItems.get(i).ReturnName()+" Koszt: "+ShopItems.get(i).ReturnCost());
                        }
                        System.out.println("4) Wyjście");
                        System.out.println("Podaj wartość od 1-"+(ShopItems.size()+1)+":");
                        PlayerInput = s.nextLine();
                        try
                        {
                            //wybeiranie przedmiotów
                            if(Integer.valueOf(PlayerInput) <= ShopItems.size()) //Czy komenda mieści się w zakresie
                            {
                                Integer x = Integer.valueOf(PlayerInput) - 1;
                                if(ShopItems.get(x).ReturnCost() <= PlayerStats.ReturnGold())
                                {
                                    PlayerStats.ChangeGold(-ShopItems.get(x).ReturnCost());
                                    inventory.AddItem(ShopItems.get(x));
                                    OtherFunctions.clearScreen();
                                    ItemBought = true;
                                    PlayerInput = "wyjscie";
                                }
                                else
                                {
                                    OtherFunctions.clearScreen();
                                    System.out.println("Posiadasz za mało złota");
                                }
                            }
                            else if(Integer.valueOf(PlayerInput) <= ShopItems.size()+1)
                            {
                                OtherFunctions.clearScreen();
                                ItemBought = true;
                                PlayerInput = "wyjscie";
                            }
                            else
                            {
                                OtherFunctions.clearScreen();
                                System.out.println("Nie znana komenda");
                            }
                        }
                        catch(Exception e) //Dokończyć sklep 
                        {
                            OtherFunctions.clearScreen();
                            System.out.println("Nie znana komenda"); 
                        }

                    }
                }
            }
        }
    }
    public void Armorer(Scanner s,UnitStats PlayerStats)
    {
        if(commandsList.contains("platnerz"))
        {
            String PlayerInput = "";
            while(!PlayerInput.toLowerCase().equals("wyjscie") && !PlayerInput.toLowerCase().equals("wy") && !PlayerInput.toLowerCase().equals("2"))
            {
                OtherFunctions.clearScreen();
                System.out.println("Płatnerz: Witaj w moim sklepie czy chcesz zobaczyć moje towary?");
                QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"sklep","wyjscie"})));
                PlayerInput = s.nextLine();
                if(PlayerInput.toLowerCase().equals("sklep") || PlayerInput.toLowerCase().equals("sk") || PlayerInput.toLowerCase().equals("1"))
                {
                    //Przedmioty do kupienia
                    ArrayList<Armor> ShopItems = new ArrayList<Armor>();
                    ShopItems.add(ItemList.chainmail);
                    ShopItems.add(ItemList.plateArmor);
                    ShopItems.add(ItemList.scaleArmor);
                    PlayerInput = "";
                    Boolean ItemBought = false; //czy kupiono przedmiot
                    OtherFunctions.clearScreen();
                    while(!ItemBought)
                    {
                        System.out.println("Dostępne towary (Posiadane złoto: "+PlayerStats.ReturnGold()+"):");
                        for(int i=0;i<ShopItems.size();i++)
                        {
                            System.out.println((i+1)+") "+ShopItems.get(i).ReturnName()+" | Pancerz: "+ShopItems.get(i).ReturnArmor()+" | Szybkość: "+ShopItems.get(i).ReturnSpeed()+" | Koszt: "+ShopItems.get(i).ReturnCost());
                        }
                        System.out.println("4) Wyjście");
                        System.out.println("Podaj wartość od 1-"+(ShopItems.size()+1)+":");
                        PlayerInput = s.nextLine();
                        try
                        {
                            //wybeiranie przedmiotów
                            if(Integer.valueOf(PlayerInput) <= ShopItems.size()) //Czy komenda mieści się w zakresie
                            {
                                Integer x = Integer.valueOf(PlayerInput) - 1;
                                if(ShopItems.get(x).ReturnCost() <= PlayerStats.ReturnGold())
                                {
                                    PlayerStats.ChangeGold(-ShopItems.get(x).ReturnCost());
                                    inventory.AddArmor(ShopItems.get(x));
                                    OtherFunctions.clearScreen();
                                    System.out.println("Kupiono: "+ShopItems.get(x).ReturnName()+" Wydano: "+ShopItems.get(x).ReturnCost());
                                    ItemBought = true;
                                    PlayerInput = "wyjscie";
                                }
                                else
                                {
                                    OtherFunctions.clearScreen();
                                    System.out.println("Posiadasz za mało złota");
                                }
                            }
                            else if(Integer.valueOf(PlayerInput) <= ShopItems.size()+1)
                            {
                                OtherFunctions.clearScreen();
                                ItemBought = true;
                                PlayerInput = "wyjscie";
                            }
                            else
                            {
                                OtherFunctions.clearScreen();
                                System.out.println("Nie znana komenda");
                            }
                        }
                        catch(Exception e) //Dokończyć sklep 
                        {
                            OtherFunctions.clearScreen();
                            System.out.println("Nie znana komenda"); 
                        }
                    }
                }
            }
        }
    }
    public void Blacksmith(Scanner s,UnitStats PlayerStats)
    {
        if(commandsList.contains("kowal"))
        {
            String PlayerInput = "";
            while(!PlayerInput.toLowerCase().equals("wyjscie") && !PlayerInput.toLowerCase().equals("wy") && !PlayerInput.toLowerCase().equals("2"))
            {
                OtherFunctions.clearScreen();
                System.out.println("Płatnerz: Witaj w mojej kuźni czy chcesz zobaczyć moje towary?");
                QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"sklep","wyjscie"})));
                PlayerInput = s.nextLine();
                if(PlayerInput.toLowerCase().equals("sklep") || PlayerInput.toLowerCase().equals("sk") || PlayerInput.toLowerCase().equals("1"))
                {
                    //Przedmioty do kupienia
                    ArrayList<Weapons> ShopItems = new ArrayList<Weapons>();
                    ShopItems.add(ItemList.Sword);
                    ShopItems.add(ItemList.LongSword);
                    ShopItems.add(ItemList.Hammer);
                    ShopItems.add(ItemList.Spear);
                    PlayerInput = "";
                    Boolean ItemBought = false; //czy kupiono przedmiot
                    OtherFunctions.clearScreen();
                    while(!ItemBought)
                    {
                        System.out.println("Dostępne towary (Posiadane złoto: "+PlayerStats.ReturnGold()+"):");
                        for(int i=0;i<ShopItems.size();i++)
                        {
                            System.out.println((i+1)+") "+ShopItems.get(i).ReturnName()+" | Obrażenia: "+ShopItems.get(i).ReturnDamage()+" | Koszt: "+ShopItems.get(i).ReturnCost());
                        }
                        System.out.println("4) Wyjście");
                        System.out.println("Podaj wartość od 1-"+(ShopItems.size()+1)+":");
                        PlayerInput = s.nextLine();
                        try
                        {
                            //wybeiranie przedmiotów
                            if(Integer.valueOf(PlayerInput) <= ShopItems.size()) //Czy komenda mieści się w zakresie
                            {
                                Integer x = Integer.valueOf(PlayerInput) - 1;
                                if(ShopItems.get(x).ReturnCost() <= PlayerStats.ReturnGold())
                                {
                                    PlayerStats.ChangeGold(-ShopItems.get(x).ReturnCost());
                                    inventory.AddWeapon(ShopItems.get(x));
                                    OtherFunctions.clearScreen();
                                    System.out.println("Kupiono: "+ShopItems.get(x).ReturnName()+" Wydano: "+ShopItems.get(x).ReturnCost());
                                    ItemBought = true;
                                    PlayerInput = "wyjscie";
                                }
                                else
                                {
                                    OtherFunctions.clearScreen();
                                    System.out.println("Posiadasz za mało złota");
                                }
                            }
                            else if(Integer.valueOf(PlayerInput) <= ShopItems.size()+1)
                            {
                                OtherFunctions.clearScreen();
                                ItemBought = true;
                                PlayerInput = "wyjscie";
                            }
                            else
                            {
                                OtherFunctions.clearScreen();
                                System.out.println("Nie znana komenda");
                            }
                        }
                        catch(Exception e) //Dokończyć sklep 
                        {
                            OtherFunctions.clearScreen();
                            System.out.println("Nie znana komenda"); 
                        }
                    }
                }
            }
        }
    }
    public void Tavern(Scanner s,UnitStats PlayerStats)
    {
        if(commandsList.contains("karczma"))
        {
            String PlayerInput = "";
            OtherFunctions.clearScreen();
            while(!PlayerInput.toLowerCase().equals("wyjscie") && !PlayerInput.toLowerCase().equals("wy") && !PlayerInput.toLowerCase().equals("4"))
            {
                System.out.println("Karczmarz: Witaj w mojej karczmie! Czego Ci trzeba?");
                QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"jedzenie","gra","odpoczynek","wyjscie"})));
                PlayerInput = s.nextLine();  
                Boolean ActionDone = false;
                if(PlayerInput.equals("jedzenie") || PlayerInput.equals("1"))
                {
                    //Kupowanie jedzenia
                    OtherFunctions.clearScreen();
                    while(!ActionDone)
                    {
                        System.out.println("Karczmarz: Będzie to kosztować 1 złoto (Posiadane złoto "+PlayerStats.ReturnGold()+")");
                        QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"zaplac","wyjscie"})));
                        PlayerInput = s.nextLine();
                        if(PlayerInput.equals("zaplac") || PlayerInput.equals("1"))
                        {
                            if(PlayerStats.ReturnGold() >= 1)
                            {
                                OtherFunctions.clearScreen();
                                PlayerStats.ChangeGold(-1);
                                PlayerStats.ChangeHP(10);
                                System.out.println("Zjedzono potrawę i odzyskano 10 Punktów Zdrowia (Aktualne zdrowie "+PlayerStats.ReturnHP()+"/"+PlayerStats.ReturnMaxHP()+")");
                                ActionDone = true;
                            }
                            else
                            {
                                OtherFunctions.clearScreen();
                                System.out.println("Za mało złota");
                                ActionDone = true;
                            }
                        } 
                        else if(PlayerInput.equals("wyjscie") || PlayerInput.equals("2"))
                        {
                            OtherFunctions.clearScreen();
                            ActionDone = true;
                        }
                    } 
                }
                else if(PlayerInput.equals("odpoczynek") || PlayerInput.equals("3"))
                {
                    //Odpoczynek
                    OtherFunctions.clearScreen();
                    while(!ActionDone)
                    {
                        System.out.println("Karczmarz: Będzie to kosztować 5 złoto (Posiadane "+PlayerStats.ReturnGold()+")");
                        QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"zaplac","wyjscie"})));
                        PlayerInput = s.nextLine();
                        if(PlayerInput.equals("zaplac") || PlayerInput.equals("1"))
                        {
                            if(PlayerStats.ReturnGold() >= 5)
                            {
                                OtherFunctions.clearScreen();
                                PlayerStats.Rest();
                                PlayerStats.ChangeGold(-5);
                                System.out.println("Wypoczęto i odzyskano siły");
                                ActionDone = true;
                            }
                            else
                            {
                                OtherFunctions.clearScreen();
                                System.out.println("Za mało złota");
                                ActionDone = true;
                            }
                        } 
                        else if(PlayerInput.equals("wyjscie") || PlayerInput.equals("2"))
                        {
                            OtherFunctions.clearScreen();
                            ActionDone = true;
                        }
                    } 
                }
                else if(PlayerInput.equals("gra") || PlayerInput.equals("2"))
                {
                    //Odpoczynek
                    OtherFunctions.clearScreen();
                    while(!ActionDone)
                    {
                        System.out.println("Karczmarz: Podaj wysokość zakładu (Posiadane złoto: "+PlayerStats.ReturnGold()+")");
                        QuickTexts.WhatToDoV(visual,new ArrayList<String>(Arrays.asList(new String[]{"10","20","50","100","wyjscie"})));
                        PlayerInput = s.nextLine();
                        Integer Bet = 0;
                        if(PlayerInput.equals("10") || PlayerInput.equals("1"))
                        {
                            Bet = 10;
                        }
                        else if(PlayerInput.equals("20") || PlayerInput.equals("2"))
                        {
                            Bet = 20;
                        }
                        else if(PlayerInput.equals("50") || PlayerInput.equals("3"))
                        {
                            Bet = 50;
                        }
                        else if(PlayerInput.equals("100") || PlayerInput.equals("4"))
                        {
                            Bet = 100;
                        }
                        else if(PlayerInput.equals("wyjscie") || PlayerInput.equals("wy") || PlayerInput.equals("5"))
                        {
                            OtherFunctions.clearScreen();
                            ActionDone = true;
                        }
                        if(Bet.equals(0))
                        {
                            OtherFunctions.clearScreen();
                            System.out.println("Nie znana komenda");
                        }
                        else if(Bet > PlayerStats.ReturnGold())
                        {
                            OtherFunctions.clearScreen();
                            System.out.println("Nie posiadasz wystarczającej ilości złota");
                        }
                        else
                        {
                            MiniGames.Game1(s,Bet,PlayerStats);
                            ActionDone = true;
                            OtherFunctions.clearScreen();
                        }
                    }
                }
            }
        }
    }
    public void BrokenCart(Scanner s,UnitStats Player)
    {
        if(commandsList.contains("woz"))
        {
            System.out.println("Przeszukujesz wóz i znajdujesz medalion");
            inventory.AddItem(ItemList.Healing);
            Player.AddSkill(AllSkills.Heal);
            CommandManager("remove","woz");
        }
    }
    public void Chest(UnitStats Player)
    {
        if(commandsList.contains("skrzynka"))
        {
            System.out.println("Podchodzisz do skrzynki i otwierasz ją. Widzisz, że jest pusta. Postanawiasz zabrać ją ze sobą");
            inventory.AddItem(ItemList.Chest);
            CommandManager("remove","skrzynka");
        }
    }
    public void Quest()
    {
        if(commandsList.contains("tekst"))
        {
            if(locationId == AllLocations.CaveQuest)
            {
                Description("Tabliczka: Ma mnie każdy, lecz nie każdy lubi. Potrafisz uwierzyć?\n"+ 
                "Możesz mnie dotknąć, zobaczyć, lecz nie zdołasz uderzyć.\n"+ 
                "Bawię dziecko, przygnębiam starca, cieszę dziewczę urocze.\n"+
                "Kiedy płaczesz ja szlocham, gdy się śmiejesz i ja chichoczę.");
            }
            else
            {
                Description("Tabliczka: Dzięki temu kielichowi pragnienie nie będzie twoim problemem.");
            }
        }
    }
    public void Vessel()
    {
        if(commandsList.contains("naczynie"))
        {
            System.out.println("Podchodzisz do naczynia i napełniasz je wodą z skrzynki nagle jedna ze ścian się przesuwa i odkrywa przejście");
            CommandManager("remove","naczynie");
            CommandManager("add","wejscie");
        }
    }
    public void Sleep(UnitStats Player)
    {
        Player.Rest();
        Description("Odpoczywasz i regenerujesz siły");
    }
    public void SetInventory(Inventory inv)
    {
        inventory = inv;
    }
    public void CommandManager(String mode,String commandName)
    {
        if(mode.toLowerCase().equals("add"))
        {
            if(!commandsList.contains(commandName))
            {
                commandsList.add(commandName);
            }
        }
        else if(mode.toLowerCase().equals("remove") || mode.toLowerCase().equals("rm"))
        {
            commandsList.remove(commandName);
        }
    }
    public void SetVisual(String newVisual)
    {
        visual = newVisual;
    }
    public ArrayList<String> ReturnCommandList()
    {
        return this.commandsList;
    }
    public Integer ReturnId()
    {
        return locationId;
    }
}
