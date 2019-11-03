package de.fhws.uebung4;

public interface IndexedPriorityQueue {
    //change value of key
    void change(int index, double key);

    //test if heap is empty
    boolean empty();

    //test if index is contained
    boolean contains(int index);

    //delete root as minimum key
    int deleteMin();

    //insert new key
    void insert(int index, double key);

    // implements a console printout
    void output();
}
