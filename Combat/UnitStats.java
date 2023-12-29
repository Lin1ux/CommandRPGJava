package Combat;

import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.BoldAction;

public class UnitStats 
{
    private String Name;
    private Integer HP;
    private Integer MaxHP;
    private Integer DMG;
    private Integer DefaultDMG;
    private Integer Mana;
    private Integer MaxMana;
    private Integer Armor;
    private Integer DefaultArmor;
    private Integer Speed;
    private Integer DefaultSpeed;
    private Integer Gold;

    private ArrayList<Skills> SkillsParam;
    private ArrayList<Status> CurrentStatuses;
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
    //Złoto
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
    //Status Postaci
    public void AddStatus(Status newStatus)
    {
        Boolean StatusFound = false;
        for(int i=0;i<CurrentStatuses.size();i++)
        {
            if(CurrentStatuses.get(i).ReturnName().equals(newStatus.ReturnName()))
            {
                StatusFound = true;
                if(CurrentStatuses.get(i).ReturnDuration() < newStatus.ReturnDuration())
                {
                    //Przedłużenie czasu trwanie
                    System.out.println("Add: "+newStatus.ReturnDuration());
                    CurrentStatuses.get(i).SetDuration(newStatus.ReturnDuration()); 
                }
                else
                {
                    //Brak zmiany długości
                    StatusFound = true;
                }
            }
            if(StatusFound)
            {
                i = CurrentStatuses.size(); //Break
            } 
        }
        if(!StatusFound)
        {
            CurrentStatuses.add(newStatus);
        }
    }
    public void RemoveStatus(Status status)
    {
        CurrentStatuses.remove(status);
    }
    public void ShortStatusDown()
    {
        for(int i=0;i<CurrentStatuses.size();i++)
        {
            if(CurrentStatuses.get(i).ReturnType().equals(Status.ShortTime))
            {   
                CurrentStatuses.get(i).durationDown();  
                if(CurrentStatuses.get(i).ReturnDuration() <= 0)
                {    
                    RemoveStatus(CurrentStatuses.get(i));
                    i-=1;
                }
            }
        }
    }
    public void CombatStatusDown()
    {
        for(int i=0;i<CurrentStatuses.size();i++)
        {
            if(CurrentStatuses.get(i).ReturnType().equals(Status.CombatTime))
            {   
                CurrentStatuses.get(i).durationDown();  
                if(CurrentStatuses.get(i).ReturnDuration() <= 0)
                {    
                    RemoveStatus(CurrentStatuses.get(i));
                    i-=1;
                }
            }
        }
    }
    public Integer AmountOfStatuses()
    {
        return CurrentStatuses.size();
    }
    public Status ReturnStatusByIndex(int x)
    {
        return CurrentStatuses.get(x);
    }
    public ArrayList<Status> ReturnAllStatuses()
    {
        return CurrentStatuses;
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
        SkillsParam = new ArrayList<Skills>();
        CurrentStatuses = new ArrayList<Status>();
    }
    public UnitStats(String name,Integer newHP,Integer newDMG,Integer newMana,Integer newArmor,Integer newSpeed,Integer newGold,ArrayList<Skills> Skills,ArrayList<Status> newStatus)
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
        CurrentStatuses = newStatus;
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
