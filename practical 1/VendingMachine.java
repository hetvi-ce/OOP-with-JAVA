import java.util.Scanner;
enum Coin
{
    ONE,TWO,FIVE,TEN
};
public class VendingMachine {
    public static void main(String[] args) 
    {
        int snack_price=15;
        int total=0;
        Scanner sc=new Scanner(System.in);
        while(total<snack_price)
        {
            System.out.println("enter coin : ");
            String coin=sc.next();
            Coin coin_in = Coin.valueOf(coin);
            int value=switch(coin_in)
            {
                case ONE -> 1;
                case TWO -> 2;
                case FIVE -> 5;
                case TEN -> 10;
                default -> 0;
            };
            total+=value;
            System.out.println(" current total :" + total);
        }
        int rem=total-snack_price;
        System.out.println("paid."+"change : "+rem);
        sc.close();
    }
}
