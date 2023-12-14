package ProjectGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Combat.AllSkills;
import Combat.UnitStats;
import ItemObjects.Weapons;
import ItemObjects.Armor;
import ItemObjects.ItemList;


public class Location 
{
    String locationName;
    Integer locationId;
    ArrayList<String> commandsList;
    ArrayList<String> globalCommands;
    String visual;
    String lastText = "";
    ArrayList<String> ProgressArray;

    Inventory inventory;
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
    public void Armadillo(UnitStats Player)
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
            else if(!ProgressArray.contains("FirstTrainDone"))
            {
                Description("Jarosław: Mógłbyś coś dla mnie zrobić? Przynieś mi napój Panceriańśka. Z tego co pamiętam możesz go kupić u alchemika z wschodniej wioski.");
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
                newLocation = AllLocations.Armadillo;        //Chatka
            }
        }
        return newLocation;
    }
    public Integer East()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("wschod"))
        {
            if(locationId == AllLocations.Armadillo)
            {
                newLocation = AllLocations.Road;        //Droga
            }
            else if(locationId == AllLocations.Road)
            {
                newLocation = AllLocations.Village;        //Wioska
            }
        }
        return newLocation;
    }
    public Integer West()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("zachod"))
        {
            if(locationId == AllLocations.Road)
            {
                newLocation = AllLocations.Armadillo;        //Chatka
            }
            else if(locationId == AllLocations.Village)
            {
                newLocation = AllLocations.Road;        //Droga
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
                        System.out.println("Podaj wartość od 1-"+ShopItems.size()+":");
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
