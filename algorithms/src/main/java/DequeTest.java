import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class DequeTest {

    @Test
    public void testSize() {
        Deque<Integer> deque = new Deque<Integer>();
        assertEquals(0, deque.size());
        assertTrue(deque.isEmpty());

        deque.addFirst(4);
        assertEquals(1, deque.size());
        assertFalse(deque.isEmpty());

        deque.addFirst(5);
        assertEquals(2, deque.size());
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testAddRemove() {
        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(4);
        deque.addLast(5);
        deque.addFirst(6);
        deque.addLast(7);

        assertEquals(4, deque.size());
        assertEquals(6, deque.removeFirst().intValue());
        assertEquals(7, deque.removeLast().intValue());
        assertEquals(5, deque.removeLast().intValue());
        assertEquals(4, deque.removeLast().intValue());

        try {
            assertNull(deque.removeLast());
        } catch (NoSuchElementException e) {
            assertEquals(0, deque.size());
        }
        try {
            assertNull(deque.removeFirst());
        } catch (NoSuchElementException e) {
            assertEquals(0, deque.size());
        }
    }

    @Test
    public void testResize() {
        Deque<Integer> deque = new Deque<Integer>();

        for (int i = 0; i < 17; i++) {
            deque.addFirst(i);
        }
        assertEquals(17, deque.size());
        assertFalse(deque.isEmpty());

        deque = new Deque<Integer>();

        for (int i = 0; i < 17; i++) {
            deque.addLast(i);
        }
        assertEquals(17, deque.size());
        assertFalse(deque.isEmpty());

    }

    @Test
    public void testIterator() {

        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(4);
        deque.addLast(5);
        deque.addFirst(6);
        deque.addLast(7);

        Iterator<Integer> itr = deque.iterator();

        assertTrue(itr.hasNext());
        assertEquals(6, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertEquals(4, itr.next().intValue());

        assertTrue(itr.hasNext());
        assertEquals(5, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertEquals(7, itr.next().intValue());

        assertFalse(itr.hasNext());

        try {
            itr.next();
            fail();
        } catch (NoSuchElementException e) {
        }
    }

}
