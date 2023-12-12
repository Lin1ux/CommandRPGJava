package ProjectGame;

import java.util.ArrayList;
import java.util.Scanner;

import Combat.UnitStats;
import ItemObjects.Weapons;

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
            if(locationId == 0)
            {
                newLocation = 1;        //Chatka
            }
        }
        return newLocation;
    }
    public Integer House()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("chatka"))
        {
            if(locationId == 1)
            {
                newLocation = 0;        //Chatka
            }
        }
        return newLocation;
    }
    public Integer North()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("polnoc"))
        {
            if(locationId == 1)
            {
                newLocation = 4;        //Wózek
            }
        }
        return newLocation;
    }
    public Integer South()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("poludnie"))
        {
            if(locationId == 4)
            {
                newLocation = 1;        //Chatka
            }
        }
        return newLocation;
    }
    public Integer East()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("wschod"))
        {
            if(locationId == 1)
            {
                newLocation = 2;        //droga
            }
        }
        return newLocation;
    }
    public Integer West()
    {
        Integer newLocation = locationId;
        if(commandsList.contains("zachod"))
        {
            if(locationId == 2)
            {
                newLocation = 1;        //Chatka
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
    public ArrayList<String> ReturnCommandList()
    {
        return this.commandsList;
    }
    public Integer ReturnId()
    {
        return locationId;
    }
}
