package de.fhws.uebung4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/// **************************************
/// undirected Graphs
/// implementation via adjacency lists
/// assumption:
/// vertices are enumerated by numbers 0 .. Nodes()-1
/// after creation, the number of vertices is fixed
/// **************************************
public class DirectedWeightedGraph {

    /// adjacency list
    final private ArrayList<Vertex> vertices;

    /// Creates an empty graph with V vertices (vertex == node)
    public DirectedWeightedGraph(int vertexCount) {
        if (vertexCount < 0) throw new IllegalArgumentException("Number of vertices should not be negative\n");
        vertices = new ArrayList<>(vertexCount);
        for (int v = 0; v < vertexCount; v++)
            vertices.add(new Vertex(v));
    }

    /// loads graph from file
    public static DirectedWeightedGraph fromFile(String file) {
        Path path = Paths.get(file);
        try {
            List<int[]> parseResult =
                    Files.readAllLines(Paths.get(file))
                            .stream()
                            .map(x -> x.split(" "))
                            .flatMap(Arrays::stream)
                            .filter(x -> !x.isEmpty())
                            .map(x -> x.replaceAll("\\{", "").replaceAll("}", "").replaceAll(" ", ""))
                            .map(x -> x.split(","))
                            .map(x -> new int[]{Integer.parseInt(x[0]), Integer.parseInt(x[1]), Integer.parseInt(x[2])})
                            .collect(Collectors.toList());
            int maxIndex = parseResult.stream().flatMapToInt(x -> Arrays.stream(new int[]{x[0], x[1]})).max().getAsInt();
            DirectedWeightedGraph graph = new DirectedWeightedGraph(maxIndex + 1);
            for (int[] arr : parseResult) {
                graph.addEdge(arr[0], arr[1], (double) arr[2]);
            }
            return graph;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to load your file " + path.toAbsolutePath().toString());
            throw new RuntimeException(e);
        }
    }

    // Creates a graph as copy of G
    public DirectedWeightedGraph clone() {
        DirectedWeightedGraph graph = new DirectedWeightedGraph(vertices.size());
        for (Vertex v : vertices) {
            for (Edge e : v.edges) {
                graph.addEdge(e.from, e.to, e.weight);
            }
        }
        return graph;
    }

    /// Prints the graph
    public void display() {
        for (Vertex v : vertices) {
            for (Edge e : v.getEdges()) {
                System.out.println(e);
            }
        }
    }

    /// Adds an edge
    public void addEdge(int v, int w, double weight) {
        vertices.get(v).addEdgeTo(w, weight);
    }

    /// Adds an edge
    public void addEdge(Vertex v, Vertex w, double weight) {
        addEdge(v.id, w.id, weight);
    }

    /// Deletes an edge
    public void deleteEdge(int v, int w) {
        vertices.get(v).deleteEdgeTo(w);
    }

    /// Deletes an edge
    public void deleteEdge(Vertex v, Vertex w) {
        deleteEdge(v.id, w.id);
    }

    /// Returns first node
    public Vertex getRootVertex() {
        if (getNodeCount() == 0) throw new RuntimeException("DirectedWeightedGraph is empty\n");
        return vertices.get(0);
    }

    public Vertex getNode(int id) {
        return vertices.get(id);
    }

    /// Returns number of vertices
    public int getNodeCount() {
        return vertices.size();
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<Edge> getAllEdges() {
        ArrayList<Edge> edges = new ArrayList<>();
        for (Vertex v : vertices) {
            edges.addAll(v.edges);
        }
        return edges;
    }

    public class Edge {
        public final int from, to;
        public final double weight;

        public Edge(int from, int to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public Vertex getStartVertex() {
            return getNode(from);
        }

        public Vertex getEndVertex() {
            return getNode(to);
        }

        @Override
        public String toString() {
            return "{" + from + ", " + to + ", " + weight + "}";
        }
    }

    public class Vertex {
        public final int id;
        private final ArrayList<Edge> edges = new ArrayList<>();

        public Vertex(int id) {
            this.id = id;
        }

        boolean hasOutgoingEdges() {
            return !edges.isEmpty();
        }

        ArrayList<Edge> getEdges() {
            return edges;
        }

        /// node degree
        int getEdgeCount() {
            return edges.size();
        }

        void addEdgeTo(Vertex v, double weight) {
            addEdgeTo(v.id, weight);
        }

        void addEdgeTo(int v, double weight) {
            edges.add(new Edge(id, v, weight));
        }

        void deleteEdgeTo(Vertex v) {
            deleteEdgeTo(v.id);
        }

        void deleteEdgeTo(int v) {
            edges.removeIf(x -> x.to == v);
        }

        ArrayList<Vertex> getConnectedVertices() {
            ArrayList<Vertex> vertices = new ArrayList<>();
            for (Edge e : getEdges()) {
                vertices.add(getNode(e.to));
            }
            return vertices;
        }

        public double getWeightTo(int w) {
            return getWeightTo(getNode(w));
        }

        /// returns weight of edge if existing, infinity if not existing
        public double getWeightTo(Vertex w) {
            for (Edge e : edges) {
                if (e.to == w.id) {
                    return e.weight;
                }
            }
            return Double.POSITIVE_INFINITY;
        }
    }
}
