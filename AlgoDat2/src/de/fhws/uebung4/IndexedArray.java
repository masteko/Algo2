package de.fhws.uebung4;

import java.util.HashMap;

public class IndexedArray implements IndexedPriorityQueue{
	private int n;
	private HashMap<Integer, Double> list; 


    //create empty heap
    public IndexedArray(int n) {
        this.n = n;
    	this.list = new HashMap<>(n);
    }

    //test if array is empty
    public boolean empty() {
        if (list.size() == 0) return true;
        return false;
    }

    //test if index is contained
    public boolean contains(int index) {
        return list.get(index) == null ? false : true;
    }

    //delete root as minimum key
    public int deleteMin() {
    	double min = Double.POSITIVE_INFINITY;
    	int i = 0;
    	double val;
    	
    	for (int key : list.keySet()) {
    		val = list.get(key);
    		if (val < min) {
    			min = val;
    			i = key;
    		}
    	}
    	list.remove(i);
    	return i;
    }

    //insert new key
    public void insert(int index, double key) {
    	list.putIfAbsent(index, key);
    }

    //change value of key
    public void change(int index, double key) {
    	list.replace(index, key);
    }

    public void output() {
        System.out.println("Array");
        for (int key : list.keySet()) {
        	System.out.println("Key: " + key + " Value: " + list.get(key));
        }
    }
}
