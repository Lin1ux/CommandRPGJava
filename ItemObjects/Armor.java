package ItemObjects;

public class Armor extends Items 
{
    Integer armor;
    public Armor(String Name,String Description,Integer DMG)
    {
        SetName(Name);
        SetDescr(Description);
        this.armor = DMG; 
    }
    public Integer ReturnArmor()
    {
        return armor;
    }
    public String ReturnName()
    {
        return name;
    }
}
