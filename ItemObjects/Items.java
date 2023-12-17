package ItemObjects;

public class Items 
{
    private String name;
    private String Description;
    private Integer Cost;
    
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
    public void SetCost(Integer newCost)
    {
        this.Cost = newCost; 
    }
    public String ReturnName()
    {
        return name;
    }
    public String ReturnDescr()
    {
        return Description;
    }
    public Integer ReturnCost()
    {
        return Cost;
    }
    public Items(String name, String Description)
    {
        this.name = name;
        this.Description = Description;
        this.Cost = 0;
    }
    public Items(String name, String Description,Integer newCost)
    {
        this.name = name;
        this.Description = Description;
        this.Cost = newCost;
    }
    public Items()
    {
        this.name = "";
        this.Description = "";
        this.Cost = 0;
    }
}
