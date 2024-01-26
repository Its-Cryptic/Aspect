package dev.cryptic.aspect.misc;

import java.util.Arrays;

public class CircularBufferTracker<T> {
    private final T[] values;
    private int size;

    @SuppressWarnings("unchecked")
    public CircularBufferTracker(int capacity) {
        this.values = (T[]) new Object[capacity];
        this.size = 0;
    }

    public void recordValue(T value) {
        // Shift all elements to the right
        if (size < values.length) {
            size++;
        }
        for (int i = size - 1; i > 0; i--) {
            values[i] = values[i - 1];
        }

        values[0] = value;
    }

    public T getValue(int index) {
        if (index >= size) {
            return null;
        }
        return values[index];
    }

    public T[] getValues() {
        return values;
    }

    public String toString() {
        return Arrays.toString(values);
    }
}
