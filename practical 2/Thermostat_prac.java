class Thermostat
{
    private String location;
    private int temperature;
    private static final int MIN=16,MAX=30;
    private static int ActiveCount=0;
    Thermostat(String location,int temperature)
    {
        if(temperature>=MIN && temperature<=MAX)
        {
            this.location=location;
            this.temperature=temperature;
            ActiveCount++;
        }
        else
        {
            this.location=location;
            this.temperature=22;
            ActiveCount++;
        }
    }
    Thermostat(String location)
    {
        this(location,22);
    }
    public int getTemperature() 
    {
        return temperature;
    }
    public String getLocation()
    {
        return location;
    }
    public static int getActiveCount()
    {
        return ActiveCount; 
    }
    public void raise()
    {
        if(temperature<MAX)
        {
            temperature+=1;
        }
        else
        {
            System.out.println("Already at maximum (30)");
        }
    }
    public void lower()
    {
        if(temperature>MIN)
        {
            temperature-=1;
        }
        else
        {
            System.out.println("Already at minimum (16)");
        }
    }
}
public class Thermostat_prac 
{
    public static void main(String[] args) 
    {
        Thermostat t1=new Thermostat("hall");
        Thermostat t2=new Thermostat("bedroom",25);
        int i;
        for(i=0;i<10;i++)
        {
            t1.raise();
            int temp=t1.getTemperature();
            String loc=t1.getLocation();
            System.out.print("Location : "+ loc);
            System.out.println("   temperature : "+ temp);
        }
        for(i=0;i<20;i++)
        {
            t1.lower();
            int temp=t1.getTemperature();
            String loc=t1.getLocation();
            System.out.print("Location : "+ loc);
            System.out.println("   temperature : "+ temp);
        }
        for(i=0;i<5;i++)
        {
            t2.raise();
            int temp=t2.getTemperature();
            String loc=t2.getLocation();
            System.out.print("Location : "+ loc);
            System.out.println("   temperature : "+ temp);
        }
        int cnt=Thermostat.getActiveCount();
        System.out.println("active count : "+ cnt);
    }    
}
