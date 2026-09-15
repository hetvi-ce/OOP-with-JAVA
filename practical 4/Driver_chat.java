import java.util.Scanner;
public class Driver_chat 
{
    public static void main(String[] args) 
    {
        int i,count=0;
        boolean f;
        String time;
        String name;
        String msg;
        StringBuilder report=new StringBuilder();
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter number of logs : ");
        int n=sc.nextInt();
        sc.nextLine();
        String[] log=new String[n];
        for(i=0;i<n;i++)
        {
            log[i]=sc.nextLine();
        }
        System.out.print("Enter Keyword : ");
        String key=sc.nextLine();
        ChatFilter cf=new ChatFilter();
        for(String line:log)
        {
            String[] parts =line.split(" ",3);
            if(parts.length<3)
            {
                continue;
            }
            else
            {
                time=parts[0];
                name=parts[1];
                msg=parts[2];
            }
            f=cf.contain(msg, key);
            if(f==true)
            {
                count++;
                report.append(time).append(" ").append(name).append(" : ").append(msg)
                      .append("\n");
            }
        }
        System.out.println("matches : "+count);
        System.out.println(report);
        sc.close();
    }
}
