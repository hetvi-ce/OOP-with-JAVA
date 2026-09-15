import java.util.Scanner;
public class Driver_shape 
{
    public static void main(String[] args) 
    {
        double total=0,largest=0;
        double rad,b,h,x,y;
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter radius for circle :");
        rad=sc.nextDouble();
        System.out.print("Enter hight of rectangle :");
        x=sc.nextDouble();
        System.out.print("Enter width of rectangle :");
        y=sc.nextDouble();
        System.out.print("Enter base of triangle :");
        b=sc.nextDouble();
        System.out.print("Enter hight of triangle :");
        h=sc.nextDouble();
        circle c =new circle(rad);
        rectangle r =new rectangle(x,y);
        triangle t =new triangle(b,h);
        Shape[] s={c,r,t}; 
        double area;
        for (Shape sh:s)
        {
            area = sh.area();
            System.out.println("Area : "+area);
            total+=area;
            if(largest<area)
            {
                largest=area;
            }
        }
        System.out.println("total area: "+total);
        System.out.println("largest area is: "+largest);
        sc.close();
    }
}
