public class ChatFilter 
{
    boolean contain(String msg,String key)
    {
        return (msg.toLowerCase().contains(key.toLowerCase()));
    }
}
