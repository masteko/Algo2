package de.fhws.uebung4;

public class IndexedHeap implements IndexedPriorityQueue {
    private int V;
    private double[] keys;    //key of heap vertex
    private int[] index;    //index of heap vertex
    private int[] pos;        //position of index i in heap
    private int last;        //first empty position


    //create empty heap
    public IndexedHeap(int V) {
        this.V = V;
        keys = new double[V];
        index = new int[V];
        pos = new int[V];
        last = 0;
        for (int i = 0; i < V; i++) pos[i] = -1;
    }

    //test if heap is empty
    public boolean empty() {
        if (last == 0) return true;
        return false;
    }

    //test if index is contained
    public boolean contains(int index) {
        if (pos[index] != -1) return true;
        return false;
    }

    //delete root as minimum key
    public int deleteMin() {
        if (last == 0) throw new RuntimeException("Empty Heap");
        int min = index[0];
        pos[min] = -1;
        keys[0] = 0;
        index[0] = 0;
        last--;
        if (last > 0) {
            keys[0] = keys[last];
            keys[last] = 0;
            index[0] = index[last];
            index[last] = 0;
            pos[index[0]] = 0;
            sink(0);
        }
        return min;
    }

    //insert new key
    public void insert(int index, double key) {
        if (!testrange(index)) return;
        if (contains(index)) return;
        pos[index] = last;
        keys[last] = key;
        this.index[last] = index;
        last++;
        swim(last - 1);
    }

    //change value of key
    public void change(int index, double key) {
        if (!testrange(index)) return;
        if (!contains(index)) return;
        double hkey = keys[pos[index]];
        keys[pos[index]] = key;
        if (hkey > key) swim(pos[index]);
        if (hkey < key) sink(pos[index]);
    }

    public void output() {
        System.out.println("V " + V + " last " + last);
        for (int i = 0; i < V; i++)
            System.out.println(keys[i] + " " + index[i] + " " + pos[i]);
    }

    private boolean testrange(int i) {
        if (i < 0) return false;
        if (i >= V) return false;
        return true;
    }

    private void exchange(int i, int j) {
        pos[index[i]] = j;
        pos[index[j]] = i;
        double hdoub = keys[i];
        keys[i] = keys[j];
        keys[j] = hdoub;
        int hint = index[i];
        index[i] = index[j];
        index[j] = hint;
    }

    private void sink(int v) {
        if (2 * v >= last) return;
        int min = 2 * v;
        if (((2 * v + 1) < last) && (keys[2 * v] > keys[2 * v + 1])) min = 2 * v + 1;
        if (keys[v] > keys[min]) {
            exchange(v, min);
            sink(min);
        }
    }

    private void swim(int v) {
        if (v == 0) return;
        if (keys[v] < keys[v / 2]) {
            exchange(v, v / 2);
            swim(v / 2);
        }
    }
}