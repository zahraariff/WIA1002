package alwaysontime;

public class Vertex<V extends Comparable <V>, E extends Comparable<E>>{
    V vertexInfo;
    int indeg,outdeg;
    Vertex<V,E>nextVertex;
    Edge<V,E>firstEdge;
    
    public Vertex(){
        vertexInfo=null;
        indeg=0;
        outdeg=0;
        nextVertex=null;
        firstEdge=null;
    }

    public Vertex(V vertexInfo, Vertex<V,E> nextVertex) {
        this.vertexInfo = vertexInfo;
        this.nextVertex = nextVertex;
        indeg=0;
        outdeg=0;
        firstEdge=null;
    }
    
    
}
