public class Box<T> {
    private T item;

    public Box(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }

    public void set(T item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Box[" + item + "]";
    }
}
