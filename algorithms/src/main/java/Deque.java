import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> sentinelToRight, sentinelToLeft;

    private int itemCount;

    public Deque() {
        sentinelToRight = new Node<Item>();
        sentinelToLeft = new Node<Item>();

        sentinelToRight.next = sentinelToRight;
        sentinelToRight.previous = sentinelToLeft;
        sentinelToLeft.next = sentinelToRight;
        sentinelToLeft.previous = sentinelToLeft;
    }

    public boolean isEmpty() {
        return itemCount == 0;
    }

    public int size() {
        return itemCount;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException(
                "This Deque does not support null items.");
        }

        Node<Item> node = new Node<Item>();
        node.data = item;

        Node<Item> previous = sentinelToRight.previous;
        sentinelToRight.previous = node;
        previous.next = node;
        node.next = sentinelToRight;
        node.previous = previous;
        itemCount++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException(
                "This Deque does not support null items.");
        }

        Node<Item> node = new Node<Item>();
        node.data = item;

        Node<Item> next = sentinelToLeft.next;
        sentinelToLeft.next = node;
        next.previous = node;
        node.next = next;
        node.previous = sentinelToLeft;
        itemCount++;
    }

    public Item removeFirst() {
        if (itemCount == 0) {
            throw new NoSuchElementException(
                "Cannot remove items from an empty Deque.");
        }

        Node<Item> removedNode = sentinelToRight.previous;
        sentinelToRight.previous = removedNode.previous;
        removedNode.previous.next = sentinelToRight;
        itemCount--;
        removedNode.next = null;
        removedNode.previous = null;
        return removedNode.data;
    }

    public Item removeLast() {
        if (itemCount == 0) {
            throw new NoSuchElementException(
                "Cannot remove items from an empty Deque.");
        }

        Node<Item> removedNode = sentinelToLeft.next;
        sentinelToLeft.next = removedNode.next;
        removedNode.next.previous = sentinelToLeft;
        itemCount--;
        removedNode.next = null;
        removedNode.previous = null;
        return removedNode.data;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> lastNode;

        public DequeIterator() {
            this.lastNode = sentinelToRight.previous;
        }

        public Item next() {
            if (lastNode == sentinelToLeft) {
                throw new NoSuchElementException();
            }
            Item data = lastNode.data;
            lastNode = lastNode.previous;
            return data;
        }

        public boolean hasNext() {
            return lastNode != sentinelToLeft;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                "Remove operation is not supported.");
        }
    }

    @SuppressWarnings("hiding")
    private class Node<Item> {

        private Item data;

        private Node<Item> next, previous;

        public String toString() {
            return data.toString();
        }
    }

}
