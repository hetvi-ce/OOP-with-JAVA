import java.util.Scanner;
public class Driver_Employee 
{
    public static void main(String[] args) 
    {
        String f_name,p_name,i_name;
        int f_id,p_id,i_id,hour;
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter name for fulltime employee: ");
        f_name=sc.nextLine();
        System.out.print("Enter id for fulltime employee: ");
        f_id=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter name for parttime employee: ");
        p_name=sc.nextLine();
        System.out.print("Enter id for parttime employee: ");
        p_id=sc.nextInt();
        sc.nextLine();  
        System.out.print("Enter hours for parttime employee: ");
        hour=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter name for intern employee: ");
        i_name=sc.nextLine();
        System.out.print("Enter id for intern employee: ");
        i_id=sc.nextInt();
        double total=0;
        FullTime f=new FullTime(f_name,f_id);
        PartTime p=new PartTime(p_name,p_id,hour);
        Intern i=new Intern(i_name,i_id);
        int salary;
        Employee[] e={f,p,i};
        for(Employee em:e)
        {
            salary=em.monthlySalary();
            System.out.println("Salary : "+salary);
            total+=salary;
            if(em instanceof Intern)
            {
                System.out.println("NOTE for Intern");
            }
        }
        System.out.println("Total : "+total);
        sc.close();
    }
}
