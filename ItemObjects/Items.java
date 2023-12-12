package ItemObjects;

public class Items 
{
    String name;
    String Description;
    
    public String ShowDescription()
    {
        return Description;
    }
    public void SetName(String text)
    {
        this.name = text; 
    }
    public void SetDescr(String text)
    {
        this.Description = text; 
    }
    public String ReturnName()
    {
        return name;
    }
    public Items(String name, String Description)
    {
        this.name = name;
        this.Description = Description;
    }
    public Items()
    {
        this.name = "";
        this.Description = "";
    }
}
