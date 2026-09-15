class rectangle extends Shape
{
    double a,b;
    rectangle(double a,double b)
    {
        this.a=a;
        this.b=b;
    }
    double area()
    {
        return (a*b);
    }
}
