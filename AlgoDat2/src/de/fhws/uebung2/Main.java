package de.fhws.uebung2;

import java.util.ArrayList;

import de.fhws.uebung2.Graph.Vertex;

public class Main {

    public static void main(String[] args) {
	    Graph g1 = Graph.fromFile("blatt2_aufgabe1_a_graph.txt").display();
	    System.out.println();
	    Graph g2 = Graph.fromFile("blatt2_aufgabe1_b_graph.txt").display();
	    System.out.println();
	    Graph g3 = Graph.fromFile("blatt2_aufgabe1_c_graph.txt").display();
	    System.out.println();
	    System.out.print(g1.connected());
	    System.out.print(", " + g1.bfs());
	    System.out.print(", " + g1.dfs());
	    System.out.println(" " + g1.colonyCount());
	    System.out.print(g2.connected());
	    System.out.print(", " + g2.bfs());
	    System.out.print(", " + g2.dfs());
	    System.out.println(" " + g2.colonyCount());
	    System.out.print(g3.connected());
	    System.out.print(", " + g3.bfs());
	    System.out.print(", " + g3.dfs());
	    System.out.println(" " + g3.colonyCount());
    }
}
