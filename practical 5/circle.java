class circle extends Shape
{
    double r;
    circle(double r)
    {
        this.r=r;
    }
    double area()
    {
        return (3.1415*r*r);
    }
}