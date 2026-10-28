interface Switchable
{
    void on();
    void off();

    default void toggle()
    {
        if(this instanceof Fan)
        {
            Fan f = (Fan)this;
            if(f.state)
                f.off();
            else
                f.on();
        }
        else if(this instanceof Light)
        {
            Light l = (Light)this;
            if(l.state)
                l.off();
            else
                l.on();
        }
    }
}

class Fan implements Switchable
{
    boolean state = false;

    public void on()
    {
        state = true;
        System.out.println("Fan is ON");
    }

    public void off()
    {
        state = false;
        System.out.println("Fan is OFF");
    }
}

class Light implements Switchable
{
    boolean state = false;

    public void on()
    {
        state = true;
        System.out.println("Light is ON");
    }

    public void off()
    {
        state = false;
        System.out.println("Light is OFF");
    }
}