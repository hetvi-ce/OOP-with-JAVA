class Cinema 
{
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked=0;
    Cinema(String title,int capacity)
    {
        this.title=title;
        this.capacity=capacity;
        this.seatsAvailable=capacity;
    }
    Cinema(String title)
    {
        this(title,100);
    }
    public boolean book(int n)
    {
        if(seatsAvailable>=n)
        {
            seatsAvailable-=n;
            totalBooked+=n;
            return true;
        }
        else
        {
            return false;
        }
    }
    public void cancel(int n)
    {
        if(seatsAvailable+n<=capacity)
        {
            seatsAvailable+=n;
            
        }
        else
        {
            seatsAvailable=capacity;
        }
    }
    public int getSeatsAvailable()
    {
        return seatsAvailable;
    }
    public static int getTotalBooked()
    {
        return totalBooked;
    }
    public void display()
    {
        System.out.println("title :"+title);
        System.out.println("seats available :"+getSeatsAvailable());
        System.out.println("total booked :"+getTotalBooked());
    }
}
public class cinema_prac 
{
    public static void main(String[] args)
    {
        Cinema c1=new Cinema("movie1",100);
        Cinema c2=new Cinema("movie2",200);

        boolean result=c1.book(50);
        System.out.println("booking result :"+result);
        c1.display();
        c1.cancel(20);
        c1.display();

        result = c1.book(100);
        System.out.println("booking result : " + result);
        c1.display();   

        result=c2.book(20);
        System.out.println("booking result :"+result);
        c2.display();
        c2.cancel(30);
        c2.display();

        System.out.println("Final Total Booked : " + Cinema.getTotalBooked());
    }
}
