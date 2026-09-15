public class Intern extends Employee
{
    int stipend =10000;  
    Intern(String name,int id)
    {  
        super(name,id);
    } 
    int monthlySalary()
    {
        return stipend;
    }
}
