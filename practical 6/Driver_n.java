public class Driver_n
{
    public static void main(String[] args)
    {
        Notifier email = (Notifier & Urgent) message ->
            System.out.println("Email: " + message);

        Notifier sms = message ->
            System.out.println("SMS: " + message);

        Notifier[] senders = {email, sms};

        String message = "Important notification";

        for(Notifier sender : senders)
        {
            sender.send(message);

            if(sender instanceof Urgent)
            {
                sender.send(message);
            }
        }
    }
}