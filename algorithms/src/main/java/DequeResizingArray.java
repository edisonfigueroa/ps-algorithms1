import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeResizingArray<T> implements Iterable<T> {

    private int INITIAL_CAPACITY = 16;

    private final double SPACE_LEFT_PERCENTAGE_THRESHOLD = 0.125; // 1/8;

    private final double REDUCE_SIZE_USED_SPACE_FACTOR = 0.25;// 1/4;

    private int emptySpaceLeftThreshold = (int) (INITIAL_CAPACITY * SPACE_LEFT_PERCENTAGE_THRESHOLD);

    private int reduceCapacityThreshold = (int) (INITIAL_CAPACITY * REDUCE_SIZE_USED_SPACE_FACTOR);

    private int firstIndex = INITIAL_CAPACITY / 2;

    private int lastIndex = (INITIAL_CAPACITY / 2) - 1;

    private int itemCount;

    private Object[] items;

    public DequeResizingArray() {
        items = new Object[INITIAL_CAPACITY];
    }

    public boolean isEmpty() {
        return itemCount == 0;
    }

    public int size() {
        return itemCount;
    }

    public void addFirst(T item) {
        if (item == null) {
            throw new NullPointerException("Adding a null item is not allowed.");
        }
        ensureMaxCapacity(firstIndex);
        items[--firstIndex] = item;
        itemCount++;
    }

    public void addLast(T item) {
        if (item == null) {
            throw new NullPointerException("Adding a null item is not allowed.");
        }
        ensureMaxCapacity(items.length - (lastIndex + 1));
        items[++lastIndex] = item;
        itemCount++;
    }

    @SuppressWarnings("unchecked")
    public T removeFirst() {
        if (itemCount == 0) {
            throw new NoSuchElementException("Deque is empty.");
        }
        if (itemCount > 0) {
            ensureMinSpaceUsed();
            T removedItem = (T) items[firstIndex];
            items[firstIndex++] = null;
            itemCount--;
            return removedItem;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public T removeLast() {
        if (itemCount == 0) {
            throw new NoSuchElementException("Deque is empty.");
        }
        if (itemCount > 0) {
            ensureMinSpaceUsed();
            T removedItem = (T) items[lastIndex];
            items[lastIndex--] = null;
            itemCount--;
            return removedItem;
        }
        return null;
    }

    private void ensureMaxCapacity(int emptyArrayPositionsLeft) {
        if (emptyArrayPositionsLeft <= emptySpaceLeftThreshold) {
            int newCapacity = items.length * 2;
            resize(newCapacity);
        }
    }

    private void ensureMinSpaceUsed() {
        if (items.length > INITIAL_CAPACITY
            && (itemCount - 1) == reduceCapacityThreshold) {
            // reduce item array size
            int newCapacity = items.length / 2;
            resize(newCapacity);
        }
    }

    private void resize(int newCapacity) {
        Object[] newItems = new Object[newCapacity];

        int newFirstIndex = (newCapacity - itemCount) / 2;
        int index = firstIndex;
        firstIndex = newFirstIndex;

        for (; index <= lastIndex; index++, newFirstIndex++) {
            newItems[newFirstIndex] = items[index];
        }
        lastIndex = newFirstIndex - 1;
        emptySpaceLeftThreshold = (int) (newCapacity * SPACE_LEFT_PERCENTAGE_THRESHOLD);
        reduceCapacityThreshold = (int) (newCapacity * REDUCE_SIZE_USED_SPACE_FACTOR);
        items = newItems;
    }

    public static void main(String[] args) {

    }

    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {

        private int currentIndex = firstIndex;

        @SuppressWarnings("unchecked")
        public T next() {
            return (T) items[currentIndex++];
        }

        public boolean hasNext() {
            return currentIndex <= lastIndex;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                "Remove operation is not supported.");
        }

    }
}
