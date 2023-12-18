package ItemObjects;

public class Weapons extends Items
{
    //Items Texts;
    private Integer Damage;
    public Weapons(String Name,String Description,Integer DMG, Integer Cost)
    {
        //Texts = new Items
        SetName(Name);
        SetDescr(Description);
        this.Damage = DMG; 
        SetCost(Cost);
    }
    public Integer ReturnDamage()
    {
        return Damage;
    }
}
