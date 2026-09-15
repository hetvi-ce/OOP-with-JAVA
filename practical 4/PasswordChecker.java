import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class PasswordChecker 
{
    boolean check_length(String pass)
    {
        return (pass.length()>=8);
    }    
    boolean check_capital(String pass)
    {
        Pattern p=Pattern.compile("[A-Z]");
        Matcher m=p.matcher(pass);
        return m.find();
    }
    boolean check_digit(String pass)
    {
        Pattern p=Pattern.compile("[0-9]");
        Matcher m=p.matcher(pass);
        return m.find();
    }
    boolean check_spech(String pass)
    {
        Pattern p=Pattern.compile("[^a-zA-Z0-9]");
        Matcher m=p.matcher(pass);
        return m.find();
    }
    int count_str(String pass)
    {
        int count=0;
        if(check_length(pass))
            count++;
        if(check_capital(pass))
            count++;
        if(check_digit(pass))
            count++;
        if(check_spech(pass))
            count++;

        return count;
    }
    public String strength(String pass)
    {
        int c=count_str(pass);
        if(c==0 || c==1)
            return "Weak";
        else if(c==2 || c==3)
            return "Medium";
        else
            return "Strong";
    }
}
