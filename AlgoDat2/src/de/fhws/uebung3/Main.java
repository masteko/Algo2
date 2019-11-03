package de.fhws.uebung3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collector;

public class Main {

    public static void main(String[] args) {
        String inputFile = args.length == 0 ? "blatt3_aufgabe1_b_graph.txt" : args[0];
        Graph graph = Graph.fromFile(inputFile);
        graph.display();
        
        // Test whether there is an Eulerian cycle
        if(!graph.connected()){
            System.out.println("Graph is not connected");
            return;
        }

        boolean isEulerCycle =
                graph.getVertices()
                        .stream()
                        .allMatch(v -> v.getConnectedVerticesCount() % 2 == 0);
        
        boolean isEulerPath =
        		graph.getVertices()
        				.stream()
        				.map(v -> v.getConnectedVerticesCount())
        				.filter(v -> (v % 2 != 0))
        				.count() == 2;      				

        if(isEulerCycle){
        	showEulerCycle(graph);
        } else if(isEulerPath) {
        	showEulerPath(graph);
        } else {
        	System.out.println("Graph has more than 2 odd connections");
        }
    }
    
    public static void showEulerPath(Graph graph) {
    	showEulerCycle(graph);
    }
    
    public static void showEulerCycle(Graph graph) {
    	Graph graphCopy = graph.clone();
        Stack<Integer> oTour = new Stack<>();
        Stack<Integer> hStack= new Stack<>();
        int v = 0; // start node
        hStack.push(v);
        while (hStack.size()>0)
        {
            v = hStack.pop();
            oTour.push(v);
            while (graphCopy.getNode(v).hasConnectedVertices())
            {
                hStack.push(v);
                int w = graphCopy.getNode(v).getConnectedNodeIDs().get(0); // next node
                graphCopy.deleteEdge(v,w);
                v=w;
            }
        }
        Integer[] oOutput = new Integer[oTour.size()];
        oTour.toArray(oOutput);
        Collections.reverse(Arrays.asList(oOutput));
        System.out.println("Eulertour: ");
        System.out.println(Arrays.toString(oOutput));
    }
}
