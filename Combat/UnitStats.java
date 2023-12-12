package Combat;

import java.util.ArrayList;

public class UnitStats 
{
    String Name;
    Integer HP;
    Integer MaxHP;
    Integer DMG;
    Integer Mana;
    Integer MaxMana;
    Integer Armor;
    Integer Speed;
    Integer MaxSpeed;
    Integer Gold;

    ArrayList<Skills> SkillsParam;
    //Ustawianie wartości
    public void SetMaxHP(Integer newHP)
    {
        MaxHP = newHP;
    }
    public void SetDMG(Integer newDMG)
    {
        DMG = newDMG;
    }
    public void SetMaxMana(Integer newMana)
    {
        MaxMana = newMana;
    }
    public void SetArmor(Integer newArmor)
    {
        Armor = newArmor;
    }
    //Prędkość
    public void ChangeSpeed(Integer newSpeed)
    {
        Speed = newSpeed;
    }
    public void ResetSpeed()
    {
        Speed = MaxSpeed;
    }
    public void SetMaxSpeed(Integer newSpeed)
    {
        MaxSpeed = newSpeed;
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
    public UnitStats()
    {

    }
    public UnitStats(Integer newHP,Integer newDMG,Integer newMana,Integer newArmor,Integer newSpeed,Integer newGold)
    {
        Name = new String("???");
        SetMaxHP(newHP);
        this.HP = this.MaxHP;
        SetDMG(newDMG);
        SetMaxMana(newMana);
        this.Mana = this.MaxMana;
        SetArmor(newArmor);
        SetMaxSpeed(newSpeed);
        this.Speed = MaxSpeed;
        SetGold(newGold);
        SkillsParam = new ArrayList<>();
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
