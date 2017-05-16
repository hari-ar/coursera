package week2;

import week2.RandomizedQueue;
import edu.princeton.cs.algs4.StdIn;

/**
 * Created by hari.l on 12/11/16.
 */
public class Subset {
    public static void main(String[] args) {
        int number = Integer.parseInt(args[0]);
        RandomizedQueue<String> randqueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) { randqueue.enqueue(StdIn.readString()); }
        int count=0;
        for (String data:randqueue
             ) {
            if(count<number){
                System.out.println(data);
                count++;
            }
            else
            break;
        }
    }
}
