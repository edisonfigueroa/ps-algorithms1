import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class DequeResizingArrayTest {

    @Test
    public void testSize() {
        DequeResizingArray<Integer> deque = new DequeResizingArray<Integer>();
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
        DequeResizingArray<Integer> deque = new DequeResizingArray<Integer>();

        deque.addFirst(4);
        deque.addLast(5);
        deque.addFirst(6);
        deque.addLast(7);

        assertEquals(4, deque.size());
        assertEquals(6, deque.removeFirst().intValue());
        assertEquals(7, deque.removeLast().intValue());
        assertEquals(5, deque.removeLast().intValue());
        assertEquals(4, deque.removeLast().intValue());

        assertNull(deque.removeLast());
        assertNull(deque.removeFirst());
        assertEquals(0, deque.size());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testResize() {
        DequeResizingArray<Integer> deque = new DequeResizingArray<Integer>();

        for (int i = 0; i < 17; i++) {
            deque.addFirst(i);
        }
        assertEquals(17, deque.size());
        assertFalse(deque.isEmpty());

        deque = new DequeResizingArray<Integer>();

        for (int i = 0; i < 17; i++) {
            deque.addLast(i);
        }
        assertEquals(17, deque.size());
        assertFalse(deque.isEmpty());

    }

    @Test
    public void testIterator() {

        DequeResizingArray<Integer> deque = new DequeResizingArray<Integer>();

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
    }

}
