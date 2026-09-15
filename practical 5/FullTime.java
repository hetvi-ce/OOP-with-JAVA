public class FullTime extends Employee
{
    int salary=50000;
    FullTime(String name,int id)
    {  
        super(name,id);
    }
    int monthlySalary()
    {
        return salary;
    } 
}
