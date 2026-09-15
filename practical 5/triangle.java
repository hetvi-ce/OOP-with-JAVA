class triangle extends Shape
{
    double b,h;
    triangle(double b,double h)
    {
        this.b=b;
        this.h=h;
    }
    double area()
    {
        return (b*h*1/2);
    }
}
