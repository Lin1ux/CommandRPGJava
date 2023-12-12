package Combat;

public class Skills 
{
    String OffensiveName;
    String DeffensiveName;
    String ODescription;
    String DDescription;
    Integer Cost;
    public Skills(String OName, String DName, Integer NewCost,String ODescr, String DDescr)
    {
        this.OffensiveName = OName;
        this.DeffensiveName = DName;
        this.Cost = NewCost;
        this.ODescription = ODescr;
        this.DDescription = DDescr;
    }    
    public void SetODescriptions(String newDescription)
    {
        this.ODescription = newDescription;
    }
    public void SetDDescriptions(String newDescription)
    {
        this.DDescription = newDescription;
    }
    public String ReturnName(String mode)
    {
        if(mode.equals("O"))
        {
            return OffensiveName;
        }
        else if(mode.equals("D"))
        {
            return DeffensiveName;
        }
        else
        {
            return "none";
        }
    }
    public String ReturnDescr(String mode)
    {
        if(mode.equals("O"))
        {
            return ODescription;
        }
        else if(mode.equals("D"))
        {
            return DDescription;
        }
        else
        {
            return "none";
        }
    }
    public Integer ReturnCost()
    {
        return Cost;
    }
}
