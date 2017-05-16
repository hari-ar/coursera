package week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by hari.l on 12/11/16.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] item = null;
    private int size = 0;

    public RandomizedQueue() {
        item = (Item[]) new Object[2];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        //private Node current = first;
        private int[] iteratorArray = new int[size];
        private boolean isIteratorInitialised = false;
        private int currentPointer = 0;

        @Override
        public boolean hasNext() {
            return currentPointer < size;
        }

        @Override
        public Item next() {
            if (size == 0 || currentPointer >= size)
                throw new NoSuchElementException();

            if (!isIteratorInitialised) {
                for (int i = 0; i < size; i++) {
                    iteratorArray[i] = i;
                }
                StdRandom.shuffle(iteratorArray);
                isIteratorInitialised = true;
            }

            int random = iteratorArray[currentPointer];
            currentPointer++;
            // System.out.println("Value in shuffled Array is " + iteratorArray[random]);
            return item[random];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Can not access this method.");
        }

    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        //System.out.println("Length is "+this.item.length);
        if(item == null)
            throw new NullPointerException();
        if (size == this.item.length)
            resize(2 * this.item.length);
        // System.out.println("Adding item "+item+" to index "+size);
        this.item[size] = item;
        size++;

    }

    private void resize(int capacity) {
        //System.out.println("Resize called for capacity..!!"+capacity);
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = item[i];
        item = copy;
    }

    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            int random = StdRandom.uniform(0, size);
            Item returnItem = item[random];
            try {
                for (int i = random; i <= size - 2; i++)
                    item[random] = item[random + 1];
                item[size-1] = null;
                if (size > 0 && size == item.length / 4)
                    resize(item.length / 2);
                size--;
                return returnItem;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                for(int i=0;i<size;i++){
                    System.out.println(i);
                }
                return returnItem;
            }
        }
    }   // remove and return a random item

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            int random = StdRandom.uniform(0, size);
            return item[random];

        }
    }   // return (but do not remove) a random item

    public static void main(String[] args) {
        /*week2.RandomizedQueue<String> dequeString = new week2.RandomizedQueue<>();
        dequeString.enqueue("Hi");
        dequeString.enqueue("There");
        dequeString.enqueue("There..!");
        dequeString.enqueue("A");
        dequeString.enqueue("B");
        dequeString.enqueue("C..!");
        System.out.println("-----------Between---------------");
        for (String value : dequeString
                ) {
            System.out.println(value);
        }
        System.out.println("--------------Between------------");
        System.out.println(dequeString.sample());
        System.out.println("---------------Post Sample-----------");
        for (String value : dequeString
                ) {
            System.out.println(value);
        }
        System.out.println("--------------------------");
        System.out.println("------------Dequeue--------------");
        System.out.println(dequeString.dequeue());
        System.out.println(dequeString.size());
        for (String value : dequeString
                ) {
            System.out.println(value);
        }
        System.out.println(dequeString.dequeue());
        System.out.println(dequeString.size());
        System.out.println(dequeString.dequeue());
        System.out.println(dequeString.size());
        System.out.println(dequeString.dequeue());
        System.out.println(dequeString.size());
        dequeString.enqueue("Test.....!");
        System.out.println(dequeString.size());
        System.out.println("---------------Finally-----------");
        for (String value : dequeString
                ) {
            System.out.println(value);
        }
        System.out.println("----------------------------");
        System.out.println(dequeString.isEmpty());
        System.out.println(dequeString.size());*/
    }  // unit testing
}