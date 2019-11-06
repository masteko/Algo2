package de.fhws.uebung4b;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 *  Compilation:  javac QuadTree.java
 *  Execution:    java QuadTree M N
 *
 *  Quad tree.
 *  original from https://algs4.cs.princeton.edu/92search/QuadTree.java 
 ******************************************************************************/

public class QuadTree<Key extends Comparable<Key>, Value>  {
    private Node root;

    // helper node data type
    private class Node {
        Key x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;   // four subtrees
        Value value;           // associated data

        Node(Key x, Key y, Value value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }


  /***********************************************************************
    *  Insert (x, y) into appropriate quadrant
    ***************************************************************************/
    public void insert(Key x, Key y, Value value) {
        root = insert(root, x, y, value);
    }

    private Node insert(Node h, Key x, Key y, Value value) {
        if (h == null) return new Node(x, y, value);
        //// if (eq(x, h.x) && eq(y, h.y)) h.value = value;  // duplicate
        else if ( less(x, h.x) &&  less(y, h.y)) h.SW = insert(h.SW, x, y, value);
        else if ( less(x, h.x) && !less(y, h.y)) h.NW = insert(h.NW, x, y, value);
        else if (!less(x, h.x) &&  less(y, h.y)) h.SE = insert(h.SE, x, y, value);
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = insert(h.NE, x, y, value);
        return h;
    }


  /***********************************************************************
    *  Range search.
    ***************************************************************************/

    public void query2D(Interval2D<Key> rect) {
        query2D(root, rect);
    }

    private void query2D(Node h, Interval2D<Key> rect) {
        if (h == null) return;
        Key xmin = rect.intervalX.min();
        Key ymin = rect.intervalY.min();
        Key xmax = rect.intervalX.max();
        Key ymax = rect.intervalY.max();
        if (rect.contains(h.x, h.y))
            System.out.println("    (" + h.x + ", " + h.y + ") " + h.value);
        if ( less(xmin, h.x) &&  less(ymin, h.y)) query2D(h.SW, rect);
        if ( less(xmin, h.x) && !less(ymax, h.y)) query2D(h.NW, rect);
        if (!less(xmax, h.x) &&  less(ymin, h.y)) query2D(h.SE, rect);
        if (!less(xmax, h.x) && !less(ymax, h.y)) query2D(h.NE, rect);
    }


   /***************************************************************************
    *  helper comparison functions
    ***************************************************************************/

    private boolean less(Key k1, Key k2) { return k1.compareTo(k2) <  0; }
    private boolean eq  (Key k1, Key k2) { return k1.compareTo(k2) == 0; }


   /***************************************************************************
    *  test client
    ***************************************************************************/
    public static void main(String[] args) {
    	int M = 4;
        QuadTree<Integer, String> st = new QuadTree<Integer, String>();

        List<Triplet<Float, Float, String>> irisData = loadIrisData();
        
        for (Triplet<Float, Float, String> item : irisData) {
        	st.insert((int)(item.getFirst() * 10), (int) (item.getSecond() * 10), item.getThird());
        }
        
        // do some range searches
        for (int i = 0; i < M; i++) {
            Integer xmin = (int) (100 * Math.random());
            Integer ymin = (int) (100 * Math.random());
            Integer xmax = xmin + (int) (10 * Math.random());
            Integer ymax = ymin + (int) (20 * Math.random());
            Interval<Integer> intX = new Interval<Integer>(xmin, xmax);
            Interval<Integer> intY = new Interval<Integer>(ymin, ymax);
            Interval2D<Integer> rect = new Interval2D<Integer>(intX, intY);
            System.out.println(rect + " : ");
            st.query2D(rect);
        }
    }
    
    private static List<Triplet<Float, Float, String>> loadIrisData() {
    	List<Triplet<Float, Float, String>> result = new ArrayList<Triplet<Float, Float, String>>();
    	final int X = 2;
    	final int Y = 3;
    	final int V = 4;
    	int idx = 0;
    	
    	try(BufferedReader br = new BufferedReader(new FileReader("iris.csv"))) {
    		br.readLine();
    		String line = br.readLine(); //skip header line
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				float x = Float.parseFloat(data[X]);
				float y = Float.parseFloat(data[Y]);
				result.add(new Triplet<>(x, y, data[V] + idx++));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }

}
