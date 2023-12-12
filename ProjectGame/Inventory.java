package ProjectGame;

import java.util.ArrayList;
import java.util.Scanner;

import ItemObjects.Weapons;
import ItemObjects.Armor;
import ItemObjects.Items;

import Combat.UnitStats;

public class Inventory 
{
    Weapons CurrentWeapon;
    Armor CurrentArmor;
    ArrayList<Items> OtherItems;
    ArrayList<Weapons> Weapons;
    ArrayList<Armor> Armors;

    public Inventory(Weapons weapon, Armor armor,ArrayList<Items> items)
    {
        SetCurrentWeapon(weapon);
        this.CurrentArmor = armor;
        this.OtherItems = items;
        this.Weapons = new ArrayList<Weapons>();
        this.Armors = new ArrayList<Armor>();
    }
    public void InventoryMenu(Scanner scan, UnitStats Player)
    {
        String PlayerInput = "";
        while(!PlayerInput.toLowerCase().equals("wyjscie") && !PlayerInput.toLowerCase().equals("w"))
        {
            OtherFunctions.clearScreen();
            System.out.println("Ekwipunek");
            System.out.println("|Używana broń:     |"+CurrentWeapon.ReturnName()+"| Obrażenia: "+CurrentWeapon.ReturnDamage().toString());
            System.out.println("|Założony Pancerz: |"+  CurrentArmor.ReturnName()+"| Pancerz: "+   CurrentArmor.ReturnArmor().toString());
            System.out.println("Komendy");
            System.out.println("[bron, zbroja, plecak, wyjscie]");
            System.out.println("Co chcesz zrobić? ");
            PlayerInput = scan.nextLine();
            if(PlayerInput.toLowerCase().equals("bron") || PlayerInput.toLowerCase().equals("b"))
            {
                OtherFunctions.clearScreen();
                System.out.println("|Używana broń:     |"+CurrentWeapon.ReturnName()+"| Obrażenia: "+CurrentWeapon.ReturnDamage().toString());
                System.out.println("Posiadane bronie:");
                if(!Weapons.isEmpty())
                {
                    for(int i=0;i<Weapons.size();i++)
                    {
                        System.out.println(Weapons.get(i).ReturnName()+"| Obrażenia "+Weapons.get(i).ReturnDamage());
                    }
                }
                else
                {
                    System.out.println("Brak dostępnych broni");
                }
                System.out.println("--------------------------------");
                System.out.println("Podaj nazwę broni aby ją założyć");
                PlayerInput = scan.nextLine();
                ChangeWeapon(PlayerInput);
                Player.SetDMG(ReturnWeaponsDamage());
            }
            else if(PlayerInput.toLowerCase().equals("zbroja") || PlayerInput.toLowerCase().equals("z"))
            {
                OtherFunctions.clearScreen();
                System.out.println("|Używana zbroja:     |"+CurrentArmor.ReturnName()+"| Pancerz: "+CurrentArmor.ReturnArmor().toString());
                System.out.println("Posiadane zbroje:");
                if(!Armors.isEmpty())
                {
                    for(int i=0;i<Armors.size();i++)
                    {
                        System.out.println(Armors.get(i).ReturnName()+"| Obrażenia "+Armors.get(i).ReturnArmor());
                    }
                }
                else
                {
                    System.out.println("Brak dostępnych zbroi");
                }
                System.out.println("--------------------------------");
                System.out.println("Podaj nazwę zbroi aby ją założyć");
                PlayerInput = scan.nextLine();
                ChangeArmor(PlayerInput);
                Player.SetArmor(ReturnArmorsDamage());
            }
            else if(PlayerInput.toLowerCase().equals("plecak") || PlayerInput.toLowerCase().equals("p"))
            {
                String text = new String("");
                while(!PlayerInput.toLowerCase().equals("wyjscie") && !PlayerInput.toLowerCase().equals("w"))
                {
                    OtherFunctions.clearScreen();
                    System.out.println(text+"Posiadane przedmioty:");
                    if(!OtherItems.isEmpty())
                    {
                        for(int i=0;i<OtherItems.size();i++)
                        {
                            System.out.println(OtherItems.get(i).ReturnName());
                        }
                    }
                    else
                    {
                        System.out.println("Brak posiadanych przedmiotów");
                    }
                    System.out.println("--------------------------------");
                    System.out.println("Podaj nazwę przedmotu aby zobaczyć go użyć lub zobaczyć jego opis (wpisz wyjscie aby wrócić )");
                    PlayerInput = scan.nextLine();
                    text = new String(ItemFunction(PlayerInput));
                }
            }
            //PlayerInput = s.nextLine(); 
        }
        OtherFunctions.clearScreen();
        
    }
    public void SetCurrentWeapon(Weapons weapon)
    {
        CurrentWeapon = weapon;
    }
    public void AddWeapon(Weapons weapon)
    {
        Weapons.add(weapon);
    }
    public void ChangeWeapon(String weaponName)
    {
        for(int i=0;i<Weapons.size();i++)
        {
            if(weaponName.toLowerCase().equals(Weapons.get(i).ReturnName().toLowerCase()));
            {
                AddWeapon(CurrentWeapon);
                SetCurrentWeapon(Weapons.get(i));
                Weapons.remove(i);
                i=Weapons.size();
            }
        }
    }
    public void AddItem(Weapons item)
    {
        OtherItems.add(item);
    }
    public void AddItem(Items item)
    {
        OtherItems.remove(item);
    }
    public String ItemFunction(String itemName)
    {
        String t = new String("");
        for(int i=0;i<OtherItems.size();i++)
        {
            if(itemName.toLowerCase().equals(OtherItems.get(i).ReturnName().toLowerCase()));
            {
                t = new String("---------------------\n"+OtherItems.get(i).ReturnName()+"\nOpis: "+OtherItems.get(i).ShowDescription()+"\n---------------------\n\n");
                //Inne Funkcje przedmiotu
                i=OtherItems.size();
            }
        }
        return t;
    }
    public void AddArmor(Armor armor)
    {
        Armors.add(armor);
    }
    public void SetCurrentArmor(Armor armor)
    {
        CurrentArmor = armor;
    }
    public void ChangeArmor(String armorName)
    {
        for(int i=0;i<Armors.size();i++)
        {
            if(armorName.toLowerCase().equals(Armors.get(i).ReturnName().toLowerCase()));
            {
                AddArmor(CurrentArmor);
                SetCurrentArmor(Armors.get(i));
                Armors.remove(i);
                i=Armors.size();
            }
        }
    }
    public Integer ReturnWeaponsDamage()
    {
        return CurrentWeapon.ReturnDamage();
    }
    public Integer ReturnArmorsDamage()
    {
        return CurrentArmor.ReturnArmor();
    }
}
