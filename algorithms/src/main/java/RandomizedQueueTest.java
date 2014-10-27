import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class RandomizedQueueTest {

    @Test
    public void testEmpty() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void testEnqueueSampleDequeue() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());

        queue.enqueue(2);
        assertEquals(1, queue.size());

        assertEquals(2, queue.sample().intValue());
        assertEquals(1, queue.size());

        assertEquals(2, queue.dequeue().intValue());
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testGrowArray() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        for (int i = 0; i < 16; i++) {
            queue.enqueue(i);
        }
        assertEquals(16, queue.size());
    }

    @Test
    public void testShrinkArray() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        for (int i = 0; i < 16; i++) {
            queue.enqueue(i);
        }
        assertEquals(16, queue.size());

        for (int i = 0; i < 8; i++) {
            queue.dequeue();
        }
        assertEquals(8, queue.size());

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        assertEquals(1, queue.size());

        queue.dequeue();
        assertEquals(0, queue.size());

        try {
            queue.dequeue();
        } catch (NoSuchElementException e) {
            assertTrue(queue.isEmpty());
        }
    }

    @Test
    public void testIterator() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        for (int i = 0; i < 8; i++) {
            queue.enqueue(i);
        }
        assertEquals(8, queue.size());

        Iterator<Integer> iterator = queue.iterator();

        for (int i = 0; i < 8; i++) {
            assertTrue(iterator.hasNext());
            int num = iterator.next();
            assertTrue(num >= 0 && num < 8);
        }
        assertFalse(iterator.hasNext());

        try {
            iterator.next();
            fail();
        } catch (NoSuchElementException e) {
        }

        try {
            iterator.remove();
            fail();
        } catch (UnsupportedOperationException e) {
        }
    }
}
