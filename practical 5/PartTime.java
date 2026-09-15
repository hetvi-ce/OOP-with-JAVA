public class PartTime extends Employee
{
    int hours,rate=200;
    PartTime(String name,int id,int hours)
    {  
        super(name,id);
        this.hours=hours;
    }
    int monthlySalary()
    {
        return hours*rate;
    }
}
