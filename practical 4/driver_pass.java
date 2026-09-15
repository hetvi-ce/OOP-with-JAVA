import java.util.Scanner;
public class driver_pass
{
    public static void main(String[] args) 
    {
        PasswordChecker pc=new PasswordChecker();
        Scanner sc=new Scanner(System.in);
        String password;
        while(true)
        {
            System.out.print("Enetr password or done to exit : ");
            password=sc.nextLine();
            if(password=="done")
            {
                sc.close();;
                return;
            }
            System.out.println("your password is "+pc.strength(password));
        }
    }
}