import java.util.EmptyStackException;

public class ArrayStack<E> {
    private final Object[] elements;
    private int top = 0;

    public ArrayStack(int capacity) {
        this.elements = new Object[capacity];
    }

    public void push(E item) {
        if (top == elements.length) {
            throw new IllegalStateException("Stack capacity (" + elements.length + ") reached.");
        }
        elements[top++] = item;
    }

    @SuppressWarnings("unchecked")
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E item = (E) elements[--top];
        elements[top] = null;
        return item;
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }
}
