package ItemObjects;

public class Armor extends Items 
{
    private Integer armor;
    private Integer speed;
    public Armor(String Name,String Description,Integer armorValue,Integer speedValue)
    {
        SetName(Name);
        SetDescr(Description);
        SetCost(0);
        this.armor = armorValue; 
        this.speed = speedValue;
    }
    public Armor(String Name,String Description,Integer armorValue,Integer speedValue,Integer armorCost)
    {
        SetName(Name);
        SetDescr(Description);
        SetCost(armorCost);
        this.armor = armorValue; 
        this.speed = speedValue;
    }
    public Integer ReturnArmor()
    {
        return armor;
    }
    public Integer ReturnSpeed()
    {
        return speed;
    }
}
