package Combat;

import java.util.ArrayList;

public class UnitStats 
{
    String Name;
    Integer HP;
    Integer MaxHP;
    Integer DMG;
    Integer DefaultDMG;
    Integer Mana;
    Integer MaxMana;
    Integer Armor;
    Integer DefaultArmor;
    Integer Speed;
    Integer DefaultSpeed;
    Integer Gold;

    ArrayList<Skills> SkillsParam;
    //Ustawianie wartości
    public void SetMaxHP(Integer newHP)
    {
        MaxHP = newHP;
    }
    //Obrażenia
    public void SetDMG(Integer newDMG)
    {
        DMG = newDMG;
    }
    public void SetDefaultDMG(Integer newDMG)
    {
        DefaultDMG = newDMG;
    }
    public void ResetDMG()
    {
        DMG = DefaultDMG;
    }
    public void SetMaxMana(Integer newMana)
    {
        MaxMana = newMana;
    }
    //Pancerz
    public void SetArmor(Integer newArmor)
    {
        Armor = newArmor;
    }
    public void SetDefaultArmor(Integer newArmor)
    {
        DefaultArmor = newArmor;
    }
    public void ResetArmor()
    {
        Armor = DefaultArmor;
    }
    //Prędkość
    public void ChangeSpeed(Integer newSpeed)
    {
        Speed = newSpeed;
    }
    public void ResetSpeed()
    {
        Speed = DefaultSpeed;
    }
    public void SetDefaultSpeed(Integer newSpeed)
    {
        DefaultSpeed = newSpeed;
    }
    public void SetGold(Integer newGold)
    {
        Gold = newGold;
    }
    public void AddGold(Integer Amount)
    {
        Gold += Amount;
    }
    public void Rest()
    {
        HP = MaxHP;
        Mana = MaxMana;
    }
    public void SetName(String newName)
    {
        Name = newName;
    }
    //Zmainy wartości
    public void ChangeHP(Integer addition)
    {
        HP += addition;
        if(HP > MaxHP)
        {
            HP = MaxHP;
        }
        if(HP < 0)
        {
            HP = 0;
        }
    }
    public void ChangeMana(Integer addition)
    {
        Mana += addition;
        if(Mana > MaxMana)
        {
            Mana = MaxMana;
        }
        if(Mana < 0)
        {
            Mana = 0;
        }
    }
    public void ChangeGold(Integer addition)
    {
        Gold += addition;
        if(Gold < 0)
        {
            Gold = 0;
        }
    }
    public void ResetMana()
    {
        Mana = MaxMana;
    }
    //Umięjętności postaci
    public void AddSkill(Skills newSkill)
    {
        if(!SkillsParam.contains(newSkill))
        {
            SkillsParam.add(newSkill);
        }
    }
    public Integer AmountOfSkills()
    {
        return SkillsParam.size();
    }
    public Skills ReturnSkillByIndex(int x)
    {
        return SkillsParam.get(x);
    }
    //Zwracanie wartości
    public String ReturnName()
    {
        return Name;
    }
    public Integer ReturnHP()
    {
        return HP;
    }
    public Integer ReturnMaxHP()
    {
        return MaxHP;
    }
    public Integer ReturnMana()
    {
        return Mana;
    }
    public Integer ReturnMaxMana()
    {
        return MaxMana;
    }
    public Integer ReturnSpeed()
    {
        return Speed;
    }
    public Integer ReturnDMG()
    {
        return DMG;
    }
    public Integer ReturnArmor()
    {
        return Armor;
    }
    public Integer ReturnGold()
    {
        return Gold;
    }
    public UnitStats()
    {

    }
    public UnitStats(String name,Integer newHP,Integer newDMG,Integer newMana,Integer newArmor,Integer newSpeed,Integer newGold)
    {
        //Imie
        if(!name.equals(""))
        {
            SetName(name);
        }
        else
        {
            SetName("???");
        }
        //Zdrowie
        SetMaxHP(newHP);
        this.HP = this.MaxHP;
        //Obrażenia
        SetDefaultDMG(newDMG);
        ResetDMG();
        //Punkty Akcji
        SetMaxMana(newMana);
        this.Mana = this.MaxMana;
        //Pancerz
        SetDefaultArmor(newArmor);
        ResetArmor();
        //Prędkość
        SetDefaultSpeed(newSpeed);
        ResetSpeed();
        //Złoto
        SetGold(newGold);
        SkillsParam = new ArrayList<>();
    }
    public UnitStats(String name,Integer newHP,Integer newDMG,Integer newMana,Integer newArmor,Integer newSpeed,Integer newGold,ArrayList<Skills> Skills)
    {
        //Imie
        if(!name.equals(""))
        {
            SetName(name);
        }
        else
        {
            SetName("???");
        }
        //Zdrowie
        SetMaxHP(newHP);
        this.HP = this.MaxHP;
        //Obrażenia
        SetDefaultDMG(newDMG);
        ResetDMG();
        //Punkty Akcji
        SetMaxMana(newMana);
        this.Mana = this.MaxMana;
        //Pancerz
        SetDefaultArmor(newArmor);
        ResetArmor();
        //Prędkość
        SetDefaultSpeed(newSpeed);
        ResetSpeed();
        //Złoto
        SetGold(newGold);
        SkillsParam = Skills;
    }
    public void PrintStats()
    {
        System.out.println("Zdrowie "+HP.toString()+"/"+MaxHP.toString());
        System.out.println("Obrażenia "+DMG.toString());
        System.out.println("Mana "+Mana.toString()+"/"+MaxMana.toString());
        System.out.println("Pancerz "+Armor.toString());
        System.out.println("Prędkość "+Speed.toString());
        System.out.println("Złoto "+Gold.toString());
        System.out.println("----------------------------");
    }

}
