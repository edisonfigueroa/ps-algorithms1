import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int INITIAL_CAPACITY = 16;

    private final int GROWTH_FACTOR = 2;

    private final double SHRINK_FACTOR = 2;

    private final double MIN_USED_SPACE = 0.25;

    private int usedSpaceThresholdIndex = INITIAL_CAPACITY - 1;

    private int reduceCapacityThreshold = (int) (INITIAL_CAPACITY * MIN_USED_SPACE);

    private int currentItemIndex = -1;

    private Object[] items;

    public RandomizedQueue() {
        items = new Object[INITIAL_CAPACITY];
    }

    public boolean isEmpty() {
        return currentItemIndex < 0;
    }

    public int size() {
        return currentItemIndex + 1;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        currentItemIndex++;
        if (currentItemIndex == usedSpaceThresholdIndex) {
            resize(items.length * GROWTH_FACTOR);
        }

        items[currentItemIndex] = item;

        // Randomize position. r between 0 and currentIndex+1 (exclusive)
        int r = StdRandom.uniform(currentItemIndex + 1);
        Object temp = items[currentItemIndex];
        items[currentItemIndex] = items[r];
        items[r] = temp;
    }

    public Item dequeue() {
        if (currentItemIndex < 0) {
            throw new NoSuchElementException();
        }
        ensureMinSpaceUsed();
        Item item = (Item) items[currentItemIndex];
        items[currentItemIndex] = null;
        currentItemIndex--;
        return item;
    }

    public Item sample() {
        if (currentItemIndex < 0) {
            throw new NoSuchElementException();
        }
        int r = StdRandom.uniform(currentItemIndex + 1);
        return (Item) items[r];
    }

    private void resize(int newCapacity) {
        Object[] newItems = new Object[newCapacity];

        for (int i = 0; i <= currentItemIndex; i++) {
            newItems[i] = items[i];
        }
        usedSpaceThresholdIndex = newCapacity - 1;
        if (newCapacity == INITIAL_CAPACITY) {
            reduceCapacityThreshold = -1;
        } else {
            reduceCapacityThreshold = (int) (newCapacity * MIN_USED_SPACE);
        }
        items = newItems;
    }

    private void ensureMinSpaceUsed() {
        if (currentItemIndex == reduceCapacityThreshold) {
            // reduce item array size
            int newCapacity = (int) (items.length / SHRINK_FACTOR);
            resize(newCapacity);
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Object[] itrItems = new Object[0];

        private int itrIndex;

        public RandomizedQueueIterator() {
            if (currentItemIndex > 0) {
                int iteratorSize = currentItemIndex + 1;
                itrItems = new Object[iteratorSize];
                
                itrItems[0] = items[0];
                for (int i = 1; i < iteratorSize; i++) {
                    int r = StdRandom.uniform(0, i+1);
                    itrItems[i] = items[i];
                    Object temp = itrItems[r];
                    itrItems[r] = items[i];
                    itrItems[i] = temp;
                }
            }
        }

        public Item next() {
            if (itrIndex == itrItems.length) {
                throw new NoSuchElementException();
            }
            return (Item) itrItems[itrIndex++];
        }

        public boolean hasNext() {
            return itrIndex < itrItems.length;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                "Remove operation is not supported.");
        }
    }
}
