interface SwitchDecision
{
    boolean maySwitchOn(Switchable device, int hour);
}

public class Driver_s
{
    public static void main(String[] args)
    {
        Fan f = new Fan();
        Light l = new Light();

        Switchable[] devices = {f, l};

        System.out.println("Toggling devices:");

        for(Switchable device : devices)
        {
            device.toggle();
        }

        System.out.println("\nUsing Anonymous Class:");

        SwitchDecision decision1 = new SwitchDecision()
        {
            public boolean maySwitchOn(Switchable device, int hour)
            {
                return hour >= 6 && hour <= 22;
            }
        };

        System.out.println("Fan at 10 AM: " + decision1.maySwitchOn(f, 10));
        System.out.println("Light at 11 PM: " + decision1.maySwitchOn(l, 23));

        System.out.println("\nUsing Lambda:");

        SwitchDecision decision2 =
            (device, hour) -> hour >= 6 && hour <= 22;

        System.out.println("Fan at 2 PM: " + decision2.maySwitchOn(f, 14));
        System.out.println("Light at 2 AM: " + decision2.maySwitchOn(l, 2));
    }
}