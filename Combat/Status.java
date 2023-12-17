package Combat;

public class Status 
{
    public static int Passive = 1;          //Stały status
    public static int CombatTime = 2;       //Do końca walki
    public static int ShortTime = 3;        //Do końca tury (lub duration tur) 

    private String name;
    private String descr;
    private Integer duration;
    private Integer type;
    public Status(String newName,String newDescr,Integer type ,Integer newDuration)
    {
        this.name = newName;
        this.descr = newDescr;
        this.type = type;
        this.duration = newDuration; 
    }
    public Status(String newName,String newDescr,Integer type)
    {
        this.name = newName;
        this.descr = newDescr;
        this.type = type;
        this.duration = 1; 
    }
    public Status(Status newStatus)
    {
        this.name = newStatus.ReturnName();
        this.descr = newStatus.ReturnDescr();
        this.type = newStatus.ReturnType();
        this.duration = newStatus.ReturnDuration(); 
    }
    //Obniża czas trwania statusu o 1
    public void SetDuration(Integer newDuration)
    {
        duration=newDuration;
    }
    public void durationDown()
    {
        duration-=1;
    }
    public String ReturnName()
    {
        return name;
    }
    public String ReturnDescr()
    {
        return descr;
    }
    public Integer ReturnType()
    {
        return type;
    }
    public Integer ReturnDuration()
    {
        return duration;
    }
}
