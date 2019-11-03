package de.fhws.uebung4;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        DirectedWeightedGraph oGraph = DirectedWeightedGraph.fromFile("test_graph.txt");
        oGraph.display();
        System.out.println("-----------------------");
        int vStart = 0;        //compute shortest paths from this node

        // idea:
        // greedy approach,
        // always extend a shortest path tree by the minimum reachable node
        // reachable nodes and shortest paths lengths are efficiently stored in a heap

        double[] pi = new double[oGraph.getNodeCount()];    //shortest known path lengths
        int[] pred = new int[oGraph.getNodeCount()];        //predeceesor nodes for these paths
        for (int i = 0; i < oGraph.getNodeCount(); i++) {
            pi[i] = Double.POSITIVE_INFINITY;
            pred[i] = -1;
        }
        IndexedPriorityQueue oHeap = new IndexedHeap(oGraph.getNodeCount());
        pi[vStart] = 0.0;
        oHeap.insert(vStart, pi[vStart]);
        while (!oHeap.empty()) {
            int v = oHeap.deleteMin();            //minimum reachable node
            List<DirectedWeightedGraph.Edge> oEdges = oGraph.getNode(v).getEdges();
            for (int i = 0; i < oEdges.size(); i++)    //test edges from v
            {
                int w = oEdges.get(i).to;
                double weight = oEdges.get(i).weight;
                if (pi[w] > pi[v] + weight)        //new vertex v leads to shorter path
                {
                    pi[w] = pi[v] + weight;
                    pred[w] = v;
                    if (oHeap.contains(w)) {
                        oHeap.change(w, pi[w]);
                    } else oHeap.insert(w, pi[w]);
                }
            }
        }

        // Output shortest paths
        System.out.println("Shortest Paths from " + vStart);
        for (int i = 0; i < oGraph.getNodeCount(); i++) {
            System.out.print("length " + pi[i] + ": " + i);
            int j = i;
            while (pred[j] != -1) {
                System.out.print(" <- " + pred[j]);
                j = pred[j];
            }
            System.out.println();
        }
    }
}
