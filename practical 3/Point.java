import java.util.Objects;
public class Point
{
    private int x,y;
    Point(int x,int y)
    {
        this.x=x;
        this.y=y;
    }
    @Override
    public String toString() 
    {
        return ("(" + x + "," + y + ")");
    }
    @Override
    public boolean equals(Object obj)
    {
        Point p=(Point) obj;
        return (p.x==x && p.y==y);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(x,y);
    }
}

