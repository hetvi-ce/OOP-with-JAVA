import java.util.Scanner;
record Vehicle(String number,String type) 
{

}
public class TollBooth {
    public static void main(String[] args) 
    {
        int total=0;
        int car=0,bike=0,truck=0;
        Scanner sc =new Scanner(System.in);
        while(true)
        {
            System.out.print("Enter vehicle number : ");
            String num= sc.next();
            if(num.equals("done"))
            {
                break;
            }
            System.out.print("Enter vehicle type : ");
            String typ= sc.next();

            Vehicle v= new Vehicle(num,typ);
            int value=switch(v.type())
            {
                case "bike" -> 
                {
                    bike++;
                    yield 20;
                }
                case "car" ->
                {
                    car++;
                    yield 50;
                }
                case "truck"->
                {
                    truck++;
                    yield 150;
                }
                default ->
                {
                    System.out.println("invalid vehicle type");
                    yield 0;
                }
            };
            total+=value;
        }
        System.out.println("total toll : "+ total);
        String most;
        if(car>=bike && car>=truck)
        {
            most="car";
        }
        else if(bike>=car && bike>=truck)
        {
            most="bike";
        }
        else
        {
            most="truck";
        }
        System.out.println("most frequently : "+ most);
        sc.close();
    }
}
