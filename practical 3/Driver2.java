public class Driver2 
{
    public static void main(String[] args) 
    {
        int i,j;
        boolean found;
        Card[] c=new Card[5];

        c[0]=new Card("Ten","Spades");    
        c[1]=new Card("King","Reds");    
        c[2]=new Card("Queen","Diamonds");    
        c[3]=new Card("Ace","Clubs");    
        c[4]=new Card("Ten","Spades");    

        for(i=0;i<5;i++)
        {
            found=false;
            for(j=0;j<i;j++)
            {
                if(c[i].equals(c[j]))
                {
                    found=true;
                    break;
                }
            }
            if(found)
            {
                System.out.println("Duplicate Found : "+ c[i]);
            }
        }
    }
    
}
