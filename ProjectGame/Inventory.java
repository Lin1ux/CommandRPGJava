package ProjectGame;

import java.util.ArrayList;
import java.util.Scanner;

import ItemObjects.Weapons;
import ItemObjects.Armor;
import ItemObjects.ItemList;
import ItemObjects.Items;
import ItemObjects.ItemFunctions;
import Combat.AllStatus;
import Combat.UnitStats;

public class Inventory 
{
    private Weapons CurrentWeapon;
    private Armor CurrentArmor;
    private ArrayList<Items> OtherItems;
    private ArrayList<Weapons> Weapons;
    private ArrayList<Armor> Armors;

    public Inventory(Weapons weapon, Armor armor,ArrayList<Items> items)
    {
        SetCurrentWeapon(weapon);
        this.CurrentArmor = armor;
        this.OtherItems = items;
        this.Weapons = new ArrayList<Weapons>();
        this.Armors = new ArrayList<Armor>();
    }
    public Integer InventoryMenu(Scanner s, UnitStats Player,Integer Position)
    {
        String PlayerInput = "";
        while(!PlayerInput.toLowerCase().equals("wyjscie") && !PlayerInput.toLowerCase().equals("w") && !PlayerInput.toLowerCase().equals("4"))
        {
            OtherFunctions.clearScreen();
            System.out.println("Ekwipunek");
            System.out.println("|Używana broń:     |"+CurrentWeapon.ReturnName()+"| Obrażenia: "+CurrentWeapon.ReturnDamage());
            System.out.println("|Założony Pancerz: |"+  CurrentArmor.ReturnName()+"| Pancerz: "+CurrentArmor.ReturnArmor()+"| Szybkość: "+CurrentArmor.ReturnSpeed() );
            System.out.println("Komendy");
            System.out.println("[bron, zbroja, plecak, wyjscie]");
            System.out.println("Co chcesz zrobić? ");
            PlayerInput = s.nextLine();
            if(PlayerInput.toLowerCase().equals("bron") || PlayerInput.toLowerCase().equals("b") || PlayerInput.toLowerCase().equals("1"))
            {
                OtherFunctions.clearScreen();
                System.out.println("|Używana broń:     |"+CurrentWeapon.ReturnName()+" | Obrażenia: "+CurrentWeapon.ReturnDamage().toString());
                System.out.println("Posiadane bronie:");
                //System.out.println(Integer.valueOf("df"));
                if(!Weapons.isEmpty())
                {
                    for(int i=0;i<Weapons.size();i++)
                    {
                        System.out.println((i+1)+") "+Weapons.get(i).ReturnName()+"| Obrażenia "+Weapons.get(i).ReturnDamage());
                    }
                }
                else
                {
                    System.out.println("Brak dostępnych broni");
                }
                System.out.println("--------------------------------");
                System.out.println("Podaj numer broni");
                PlayerInput = s.nextLine();
                ChangeWeapon(PlayerInput);
                Player.SetDefaultDMG(ReturnWeaponsDamage());
                Player.ResetDMG();
            }
            else if(PlayerInput.toLowerCase().equals("zbroja") || PlayerInput.toLowerCase().equals("z") || PlayerInput.toLowerCase().equals("2"))
            {
                OtherFunctions.clearScreen();
                System.out.println("|Używana zbroja:     | "+CurrentArmor.ReturnName()+" | Pancerz: "+CurrentArmor.ReturnArmor()+" | Szybkość: "+CurrentArmor.ReturnSpeed());
                System.out.println("Posiadane zbroje:");
                if(!Armors.isEmpty())
                {
                    for(int i=0;i<Armors.size();i++)
                    {
                        System.out.println((i+1)+") "+Armors.get(i).ReturnName()+"| Pancerz "+Armors.get(i).ReturnArmor()+" | Szybkość: "+Armors.get(i).ReturnSpeed());
                    }
                }
                else
                {
                    System.out.println("Brak dostępnych zbroi");
                }
                System.out.println("--------------------------------");
                System.out.println("Podaj numer zbroi");
                PlayerInput = s.nextLine();
                ChangeArmor(PlayerInput);
                Player.SetDefaultArmor(ReturnArmorsValue());
                Player.ResetArmor();
                Player.SetDefaultSpeed(ReturnArmorsSpeed());
                Player.ResetSpeed();
            }
            else if(PlayerInput.toLowerCase().equals("plecak") || PlayerInput.toLowerCase().equals("p") || PlayerInput.toLowerCase().equals("3"))
            {
                String text = new String("");
                OtherFunctions.clearScreen();
                while(!PlayerInput.toLowerCase().equals("wyjscie") && !PlayerInput.toLowerCase().equals("w"))
                {
                    System.out.println(text+"Posiadane przedmioty:");
                    if(!OtherItems.isEmpty())
                    {
                        for(int i=0;i<OtherItems.size();i++)
                        {
                            System.out.println(OtherItems.get(i).ReturnName()+" - "+OtherItems.get(i).ReturnDescr());
                        }
                    }
                    else
                    {
                        System.out.println("Brak posiadanych przedmiotów");
                    }
                    System.out.println("--------------------------------");
                    System.out.println("Podaj nazwę przedmiotu aby go użyć (wpisz wyjscie aby wrócić)");
                    PlayerInput = s.nextLine();
                    //text = new String(ItemFunction(PlayerInput));
                    Position = UseItem(PlayerInput,Player,Position);
                }
            }
            //PlayerInput = s.nextLine(); 
        }
        OtherFunctions.clearScreen();
        return Position;
        
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
        try 
        {
            Integer i = Integer.valueOf(weaponName)-1;
            AddWeapon(CurrentWeapon);
            SetCurrentWeapon(Weapons.get(i));
            Weapons.remove(Weapons.get(i));
            i=Weapons.size();
        } 
        catch (Exception e) 
        {
            
        }
    }
    public void AddItem(Items item)
    {
        OtherItems.add(item);
    }
    public void RemoveItem(Items item)
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
    public Integer UseItem(String itemName,UnitStats PlayerStats, Integer Position)
    {
        for(int i=0;i<OtherItems.size();i++)
        {
            OtherFunctions.clearScreen();
            if(OtherItems.get(i).ReturnName().toLowerCase().equals(itemName.toLowerCase()))
            {
                if(OtherItems.get(i).equals(ItemList.HPPotion))
                {
                    PlayerStats.ChangeHP(25);
                    System.out.println("Wypito miksturę zdrowia i odzyskano 25 Punktów Zdrowia");
                    OtherItems.remove(OtherItems.get(i));
                }
                else if(OtherItems.get(i).equals(ItemList.compass))
                {
                    ItemFunctions.CompassTarget(Position);
                }
                else if(OtherItems.get(i).equals(ItemList.Drink))
                {
                    Position = new Integer(ItemFunctions.Alcohol(Position));
                    OtherItems.remove(OtherItems.get(i));
                }
                else if(OtherItems.get(i).equals(ItemList.Poison))
                {
                    PlayerStats.AddStatus(AllStatus.PoisonAttack);
                    System.out.println("Twoja broń została posmarowana trucizną");
                    OtherItems.remove(OtherItems.get(i));
                }
                System.out.println("---------------------");
                i = OtherItems.size();
            }
        }
        return Position;
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
        try 
        {
            Integer i = Integer.valueOf(armorName)-1;
            SetCurrentArmor(Armors.get(i));
            AddArmor(CurrentArmor);
            Armors.remove(Armors.get(i));
            i=Armors.size();
        } 
        catch (Exception e) 
        {
            
        }
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
    public Weapons ReturnCurentWeapon()
    {
        return CurrentWeapon;
    }
    public Integer ReturnArmorsValue()
    {
        return CurrentArmor.ReturnArmor();
    }
    public Integer ReturnArmorsSpeed()
    {
        return CurrentArmor.ReturnSpeed();
    }
    public ArrayList<Items> ReturnItems()
    {
        return OtherItems;
    }
}
