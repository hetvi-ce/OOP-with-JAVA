
public class Driver1
{
    public static void main(String[] args) 
    {
        int i,j,distinct=0;
        boolean found;
        Point[] p=new Point[5];

        p[0] = new Point(2,3);
        p[1] = new Point(5,6);
        p[2] = new Point(1,1);
        p[3] = new Point(2,3);
        p[4] = new Point(5,5);

        for(i=0;i<5;i++)
        {
            found=false;
            for(j=0;j<i;j++)
            {
                if(p[i].equals(p[j]))
                {
                    found=true;
                    break;                
                }
            }
            if(!found)
            {
                distinct++;
            }
        }
        System.out.println("distinct : "+distinct);
    }    
}
