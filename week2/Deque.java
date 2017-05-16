package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by hari.l on 11/11/16.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    // private Node last = null;
    private int size = 0;

    public Deque() {
        first = new Node();
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null)
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Can not access this method.");
        }

    }

    private class Node {
        private Item item;
        private Node next;
    }





    public boolean isEmpty() {
        return size == 0;
    }               // is the deque empty?

    public int size() {
        // System.out.println("Returning size = "+size);
        return size;
    }             // return the number of items
    // on the deque

    public void addFirst(Item item) {
        if (isEmpty()) {
            first = new Node();
            first.item = item;
        } else {
            if (item == null) {
                throw new NullPointerException();
            }
            Node newFirst = new Node();
            newFirst.item = item;
            newFirst.next = first;
            first = newFirst;
        }
        size++;
    }         // add the item to the front

    public void addLast(Item item) {

        if (isEmpty()) {
            first = new Node();
            first.item = item;
        } else {
            if (item == null) {
                throw new NullPointerException();
            }

            Node pointer = first;
            while (pointer.next != null)
                pointer = pointer.next;
            pointer.next = new Node();
            pointer.next.item = item;
        }
        size++;
    }       // add the item to the end

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }     // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else if(size()==1){
            Item item = first.item;
            first=null;
            size--;
            return item;
        }
        else {
            Node pointer = first;
            Node previous = first;
            while (pointer.next != null)
            {
                previous = pointer;
                pointer = pointer.next;
            }

            Item item = pointer.item;
            previous.next = null;
            size--;
            return item;
        }
    }   // remove and return the item from the end

    public static void main(String[] args) {
         Deque<String> dequeString = new Deque<String>();
        //dequeString.addFirst("Hi");
        //dequeString.addFirst("There");
        dequeString.addLast("There..!");
        System.out.println("---------Data Post Addition-----------------");
        for (String value : dequeString
                ) {
            System.out.println(value);
        }
        System.out.println("--------------------------");
        System.out.println(dequeString.removeLast());
        System.out.println("------------Post remove last--------------");
        for (String value : dequeString
                ) {
            System.out.println(value);
        }
        System.out.println("--------------------------");
        System.out.println("--------------------------");
        //System.out.println(dequeString.removeLast());
        System.out.println("---------------Finally-----------");
        for (String value : dequeString
                ) {
            System.out.println(value);
        }
        System.out.println(dequeString.isEmpty());
        System.out.println(dequeString.size());

    } // unit testing


}

