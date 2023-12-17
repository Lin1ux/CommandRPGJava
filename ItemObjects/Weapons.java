package ItemObjects;

public class Weapons extends Items
{
    //Items Texts;
    private Integer Damage;
    public Weapons(String Name,String Description,Integer DMG)
    {
        //Texts = new Items
        SetName(Name);
        SetDescr(Description);
        this.Damage = DMG; 
    }
    public Integer ReturnDamage()
    {
        return Damage;
    }
}
