package alwaysontime;

public class Edge <V extends Comparable <V>, E extends Comparable<E>>{
    Vertex<V,E>toVertex;
    E Stopcost;
    Edge<V,E>nextEdge;

    public Edge() {
        toVertex=null;
        Stopcost=null;
        nextEdge=null;
    }

    public Edge(Vertex<V, E> toVertex, E Stopcost, Edge<V, E> nextEdge) {
        this.toVertex = toVertex;
        this.Stopcost = Stopcost;
        this.nextEdge = nextEdge;
    }
}




