package ProjectGame;

import java.util.ArrayList;
import java.util.Scanner;

import Combat.UnitStats;
import ItemObjects.Weapons;
import ItemObjects.Armor;

public class Location 
{
    String locationName;
    Integer locationId;
    ArrayList<String> commandsList;
    ArrayList<String> globalCommands;
    String visual;
    String lastText = "";
    ArrayList<Integer> ProgressArray;

    Inventory inventory;
    public Location(Integer Id,String name, String visualisation,ArrayList<String> commands)
    {
        this.locationName = name;
        this.locationId = Id;
        this.visual = visualisation;
        this.commandsList = commands; 
        this.ProgressArray = new ArrayList<Integer>();
        for(int i=0;i<3;i++)
        {
            ProgressArray.add(0);
        }
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
    public void Dialog()
    {
        if(commandsList.contains("postac"))
        {
            Description("Postać - Witaj w końcu się obudziłeś jeśli będziesz potrzebować odpoczynku możesz to zrobić w mojej chatce.");
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
            while(!PlayerInput.toLowerCase().equals("podnies") && !PlayerInput.toLowerCase().equals("p") && !PlayerInput.toLowerCase().equals("zostaw") && !PlayerInput.toLowerCase().equals("z"))
            {
                OtherFunctions.clearScreen();
                System.out.println("Widzisz miecz leżący na ziemii ziemię czy chcesz go podnieść?");
                System.out.println(visual);
                System.out.println("Dostępne komendy:");
                System.out.println("[podnies, zostaw]");
                System.out.println("Co robisz?");
                PlayerInput = s.nextLine();
            }
            if(PlayerInput.toLowerCase().equals("podnies") || PlayerInput.toLowerCase().equals("p"))
            {
                OtherFunctions.clearScreen();
                visual = Visuals.DoorVisual();
                commandsList.remove("miecz");
                Description("Miecz został podniesiony (za pomocą komendy |ekwipunek| możesz założyć tą broń)");
                Weapons w = new Weapons("krotki_miecz","",12);
                inventory.AddWeapon((w));
            }
            else if(PlayerInput.equals("zostaw") || PlayerInput.toLowerCase().equals("z"))
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
    public void Armorer(Scanner s)
    {
        if(commandsList.contains("platnerz"))
        {
            String PlayerInput = "";
            while(!PlayerInput.toLowerCase().equals("wyjscie") && !PlayerInput.toLowerCase().equals("wy"))
            {
                OtherFunctions.clearScreen();
                System.out.println("Płatnerz: Witaj w moim sklepie czy chcesz zobaczyć moje towary?");
                System.out.println(visual);
                System.out.println("Dostępne komendy:");
                System.out.println("[sklep, wyjscie]");
                System.out.println("Co robisz?");
                PlayerInput = s.nextLine();
                if(PlayerInput.toLowerCase().equals("sklep") && !PlayerInput.toLowerCase().equals("sk"))
                {
                    //Przedmioty do kupienia
                    ArrayList<Armor> ShopItems = new ArrayList<Armor>();
                    ShopItems.add(new Armor("Kolczuga","Powszechna zbroja używana przez lekko uzbrojonych żołnierzy",5,6,30));
                    ShopItems.add(new Armor("Stalowa Zbroja","Ceniona za wysoki poziom ochrony",7,5,50));
                    ShopItems.add(new Armor("Łuskowa Zbroja","Lekka zbroja zrobiona z łusek dużego gada",3,6,15));
                    PlayerInput = "";
                    Boolean ItemBought = false; //czy kupiono przedmiot
                    while(!ItemBought)
                    {
                        OtherFunctions.clearScreen();
                        System.out.println("Dostępne towary:");
                        for(int i=0;i<ShopItems.size();i++)
                        {
                            System.out.println((i+1)+") "+ShopItems.get(i).ReturnName()+" | Pancerz: "+ShopItems.get(i).ReturnArmor()+" | Szybkość: "+ShopItems.get(i).ReturnSpeed()+" | Koszt: "+ShopItems.get(i).ReturnCost());
                        }
                        System.out.println("Podaj wartość od 1-"+ShopItems.size()+":");
                        PlayerInput = s.nextLine();
                        try
                        {
                            //wybeiranie przedmiotów
                            if(Integer.valueOf(PlayerInput) <= ShopItems.size()) //Czy komenda mieści się w zakresie
                            {
                                
                            }
                        }
                        catch() //Dokończyć sklep 
                        {
                           System.out.println("Nie znana komenda"); 
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
